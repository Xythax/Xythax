package org.xythax.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xythax.core.Server;
import org.xythax.model.Client;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;
import org.xythax.world.PlayerManager;

/**
 * Handles all client IO through the selector.
 * 
 * @author Graham
 */
public class ConnectionListener implements Runnable {

	private ServerSocketChannel serverChannel;
	private Selector selector;
	private Map<SocketChannel, Login> ioClientMap;
	private Map<Login, SocketChannel> revIoClientMap;
	private Map<SocketChannel, Client> clientMap;
	private Map<Client, SocketChannel> revClientMap;
	private ByteBuffer buffer;
	public static final int BUFFER_SIZE = 4096;
	public static final int BACKLOG = 100;
	private boolean toggleShutdown = false;
	private volatile boolean isShutdown = false;

	public ConnectionListener() throws IOException {
		buffer = ByteBuffer.allocate(BUFFER_SIZE);
		ioClientMap = new HashMap<SocketChannel, Login>();
		clientMap = new HashMap<SocketChannel, Client>();
		revIoClientMap = new HashMap<Login, SocketChannel>();
		revClientMap = new HashMap<Client, SocketChannel>();
		selector = SelectorProvider.provider().openSelector();
		serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		InetSocketAddress iAddress = new InetSocketAddress(
				Constants.LISTENING_PORT);
		serverChannel.socket().bind(iAddress, BACKLOG);
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("listening for connections on port "
				+ Constants.LISTENING_PORT + ".");
	}

	@Override
	public void run() {
		while (true) {
			try {
				selector.select();
				if (toggleShutdown) {
					isShutdown = true;
					break;
				}
				Iterator<SelectionKey> selectedIterator = selector
						.selectedKeys().iterator();
				while (selectedIterator.hasNext()) {
					SelectionKey selectionKey = selectedIterator.next();
					selectedIterator.remove();
					synchronized (this) {
						if (selectionKey.isValid()) {
							if (selectionKey.isAcceptable()) {
								accept(selectionKey);
							} else if (selectionKey.isReadable()) {
								read(selectionKey);
							}
						}
					}
				}
			} catch (Exception e) {
				System.exit(0);
			}
		}
		System.out.println("Selector thread shut down.");
	}

	private void read(SelectionKey selectionKey) throws IOException {
		SocketChannel sc = (SocketChannel) selectionKey.channel();
		buffer.clear();
		int readStatus;

		String address = null;

		Login ioc = ioClientMap.get(sc);
		if (ioc != null) {
			address = ioc.connectedFrom;
		}

		Client c = clientMap.get(sc);
		if (c != null) {
			address = c.connectedFrom;
		}

		try {
			try {
				readStatus = sc.read(buffer);
			} catch (IOException e) {
				destroySocket(sc, address, true);
				return;
			}
			if (readStatus == -1) {
				destroySocket(sc, address, true);
				return;
			}
		} catch (Exception e) {
		}

		ioc = ioClientMap.get(sc);
		if (ioc != null) {
			ioc.read(buffer);
			return;
		}

		c = clientMap.get(sc);
		if (c != null) {
			synchronized (Server.getGameLogicLock()) {
				c.read(buffer);
			}
		}
	}

	private void accept(SelectionKey selectionKey) throws Exception {
		ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
		SocketChannel sc = ssc.accept();
		String address = sc.socket().getInetAddress().getHostAddress();
		synchronized (HostList.class) {
			if (!HostList.has(address, Constants.MAX_CONNECTIONS_PER_IP)) {
				Utils.print_debug("Accepted connection from " + address + ".");
				Login ioc = PlayerManager.getPlayerManager().newPlayerClient(
						sc.socket(), address);
				if (ioc == null) {
					destroySocket(sc, address, false);
				} else {
					HostList.add(address);
					ioClientMap.put(sc, ioc);
					revIoClientMap.put(ioc, sc);
					sc.socket().setTcpNoDelay(true);
					sc.configureBlocking(false);
					sc.register(selector, SelectionKey.OP_READ);
				}
			} else {
				Utils.print_debug("Rejected connection from " + address + ".");
				destroySocket(sc, address, false);
			}
		}
	}

	public void destroySocket(SocketChannel sc, String connectedFrom,
			boolean removeFromHostList) throws IOException, Exception {
		if (removeFromHostList) {
			synchronized (HostList.class) {
				HostList.remove(connectedFrom);
			}
		}
		if (ioClientMap.containsKey(sc)) {
			Login ioc = ioClientMap.get(sc);
			PlayerManager.getPlayerManager().removeIOClient(ioc);
			ioClientMap.remove(sc);
			revIoClientMap.remove(ioc);
		}
		if (clientMap.containsKey(sc)) {
			Client c = clientMap.get(sc);
			c.setLoggedOut(true);
			clientMap.remove(sc);
			revClientMap.remove(c);
		}
		if(sc != null)
			sc.close();
		Utils.print_debug("Closed connection from " + connectedFrom + ".");
	}

	public void switchIoClientToClient(Login ioc, Client c) {
		synchronized (this) {
			SocketChannel sc = revIoClientMap.get(ioc);
			revIoClientMap.remove(ioc);
			ioClientMap.remove(sc);
			clientMap.put(sc, c);
			revClientMap.put(c, sc);
			/*
			 * System.out.println("Registered " + c.getUsername() + " [idx=" +
			 * c.getUserID() + ", online=" +
			 * PlayerManager.getSingleton().getPlayerCount() + "]");
			 */
		}
	}

	public SocketChannel socketFor(Login ioc) {
		return revIoClientMap.get(ioc);
	}

	public SocketChannel socketFor(Client c) {
		return revClientMap.get(c);
	}

	public void writeReq(SocketChannel sc, ByteBuffer buf) throws IOException {
		sc.write(buf);
	}

	public void shutdown() {
		toggleShutdown = true;
		selector.wakeup();
	}

	public boolean isShutdown() {
		return this.isShutdown;
	}
}