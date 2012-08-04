package org.xythax.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xythax.content.controllers.Animation;
import org.xythax.content.controllers.Damage;
import org.xythax.content.controllers.Location;
import org.xythax.core.GameEngine;
import org.xythax.model.combat.CombatEngine;
import org.xythax.model.definition.XMLManager;
import org.xythax.model.definition.entity.NPCDefinition;
import org.xythax.model.definition.entity.NPCSizes;
import org.xythax.model.map.FlagMap;
import org.xythax.model.map.FollowEngine;
import org.xythax.model.map.Tile;
import org.xythax.model.map.TileManager;
import org.xythax.net.Stream;
import org.xythax.utils.Constants;
import org.xythax.utils.Utils;
import org.xythax.world.NPCManager;

/**
 * Represents a single NPC
 * 
 * @author Graham
 */
public class NPC extends MobileEntity {

	/**
	 * Graphics
	 **/
	public static int getNPCSize(int id) {
		for (NPCSizes s : XMLManager.sizes) {
			if (s.getType() == id)
				return s.getSize();
		}
		return 1;
	}

	public boolean walkingHome;
	public int lastMovementX = 0;
	public int lastMovementY = 0;
	public int mask80var1 = 0;
	public int mask80var2 = 0;
	protected boolean mask80update = false;

	public void gfx100(int gfx) {
		mask80var1 = gfx;
		mask80var2 = 6553600;
		mask80update = true;
		updateRequired = true;
	}

	public void gfx0(int gfx) {
		mask80var1 = gfx;
		mask80var2 = 65536;
		mask80update = true;
		updateRequired = true;
	}

	public long lastAttack = System.currentTimeMillis();
	/**
	 * The random walk factor.
	 */
	public static final double RANDOM_WALK_FACTOR = 0.2;

	private boolean inCombat;

	public boolean inCombat() {
		return inCombat;
	}

	public boolean inCombat(boolean combatStatus) {
		return this.inCombat = combatStatus;
	}

	/**
	 * NPC slot.
	 */
	private int npcId;

	/**
	 * NPC definition.
	 */
	private NPCDefinition definition;

	/**
	 * Walk boundaries.
	 */
	private int x1, x2, y1, y2;

	/**
	 * HP and max hp.
	 */
	private int hp, maxHP;

	/**
	 * Update flags.
	 */
	private boolean updateRequired = false, animUpdateRequired = false,
			hitUpdateRequired = false, dirUpdateRequired = false;

	/**
	 * Text update data
	 */
	private boolean textUpdateRequired = false;

	private String textUpdate;

	public void say(String text) {
		this.textUpdate = text;
		this.textUpdateRequired = true;

	}

	/**
	 * Integeral update data.
	 */
	private int animNumber, direction, hit, hitType, hitType2;

	/**
	 * Walking.
	 */
	private boolean isWalking, wasWalking;

	/**
	 * Raw direction.
	 */
	private int rawDirection = -1;

	/**
	 * Turn player to direction
	 */
	public int focusPointX, focusPointY;
	public boolean turnUpdateRequired;

	/**
	 * List of attackers.
	 */
	private Map<String, Integer> attackers = new HashMap<String, Integer>();

	/**
	 * List of attackers.
	 */
	private Map<String, Integer> previousAttackers = null;

	/**
	 * Primary attacker.
	 */
	private Client attacker = null;

	/**
	 * We're dead.
	 */
	private boolean isDead = false;

	/**
	 * Is dead waiting...
	 */
	private boolean isDeadWaiting = false;

	/**
	 * Timer lol. One of the only ones I use! :O
	 */
	private int isDeadTimer = -1;

	/**
	 * Are we hidden?
	 */
	private boolean isHidden = false;

	/**
	 * Is dead teleporting.
	 */
	private boolean isDeadTeleporting = false;

	/**
	 * Where to respawn.
	 */
	public int spawnAbsX, spawnAbsY;

	/**
	 * Turns an NPC to a player.
	 * 
	 * @param playerId
	 */
	public void faceTo(int playerId) {
		this.rawDirection = 32768 + playerId;
		this.dirUpdateRequired = true;
		this.updateRequired = true;
	}

	/**
	 * Turns player to
	 * 
	 * @param x
	 * @param y
	 */
	public void turnNpc(int x, int y) {
		focusPointX = 2 * x + 1;
		focusPointY = 2 * y + 1;
		updateRequired = true;
		turnUpdateRequired = true;
	}

	/**
	 * Turns an NPC.
	 * 
	 * @param dir
	 */
	public void face(int dir) {
		this.rawDirection = dir;
		this.dirUpdateRequired = true;
		this.updateRequired = true;
	}

	/**
	 * Creates the npc.
	 * 
	 * @param npcId
	 *            NPC slot.
	 * @param definition
	 *            NPC definition.
	 * @param absX
	 * @param absY
	 * @param heightLevel
	 */
	public NPC(int npcId, NPCDefinition definition, int absX, int absY,
			int heightLevel) {
		this.npcId = npcId;
		this.definition = definition;
		this.maxHP = definition.getHealth();
		this.hp = definition.getHealth();
		this.setAbsX(absX);
		this.setAbsY(absY);
		this.spawnAbsX = absX;
		this.spawnAbsY = absY;
		this.setHeightLevel(heightLevel);
	}

	public void destruct() {
		setAttacker(null);
		inCombat(false);
	}

	/**
	 * Hit the npc.
	 * 
	 * @param client
	 * @param hit
	 * @param type
	 */
	public void hit(Entity attacker, Entity victim, int damage) {
		if (attacker == null || victim == null) {
			return;
		}

		int hitTotal = 0;

		if (attacker instanceof Client) {
			if (victim instanceof NPC) {
				if (((NPC) victim).attackers.get(attacker) != null) {
					hitTotal = ((NPC) victim).attackers.get(attacker);
				}
				if (damage > ((NPC) victim).hp) {
					damage = ((NPC) victim).hp;
				}
				if (!hitUpdateRequired) {
					((NPC) victim).attackers.put(
							((Client) attacker).getUsername(), hitTotal
									+ damage);
					((NPC) victim).hit = damage;
					((NPC) victim).hp = ((NPC) victim).hp - damage;

					if (((NPC) victim).hp <= 0) {
						((NPC) victim).hp = 0;
					}
					((NPC) victim).hitType = damage > 0 ? 1 : 0;

					((NPC) victim).hitUpdateRequired = true;
					int npcId = ((NPC) victim).getDefinition().getType();
					Animation.createAnimation(victim,
							NPCManager.emotions[npcId][1]);
					victim.walkingHome = false;
					this.following = (Client) attacker;

				} else if (!hitUpdateRequired2) {
					((NPC) victim).attackers.put(
							((Client) attacker).getUsername(), hitTotal
									+ damage);
					((NPC) victim).hit2 = damage;
					((NPC) victim).hp = ((NPC) victim).hp - damage;

					if (((NPC) victim).hp <= 0) {
						((NPC) victim).hp = 0;
					}
					((NPC) victim).hitType2 = damage > 0 ? 1 : 0;

					((NPC) victim).hitUpdateRequired2 = true;
					int npcId = ((NPC) victim).getDefinition().getType();
					Animation.createAnimation(victim,
							NPCManager.emotions[npcId][1]);
					victim.walkingHome = false;
					this.following = (Client) attacker;
				} else {
					Damage.addNewHit(attacker, victim, CombatType.MELEE,
							damage, 0);
				}
				this.updateRequired = true;
			}
		}
	}

	public int getHP() {
		return this.hp;
	}

	/**
	 * Gives out this npcs drops. opitmized by killamess to produce a single
	 * random drop
	 */
	public void processDrops() {
		String highestHitter = "";
		int highestHit = -1;
		for (Map.Entry<String, Integer> entry : attackers.entrySet()) {
			if (entry.getValue() > highestHit) {
				highestHit = entry.getValue();
				highestHitter = entry.getKey();
			}
		}
		if (highestHitter != null) {
			List<NPCDrop> drops = definition.getDrops();

			ArrayList<FloorItem> toDrop = new ArrayList<FloorItem>();

			if (drops.isEmpty())
				return;

			boolean non100[] = new boolean[drops.size()];
			int dropCount = 0;

			for (NPCDrop drop : drops) {
				if (drop.getChance() < 100)
					non100[dropCount++] = true;
				else
					non100[dropCount++] = false;
			}

			ArrayList<Integer> random = new ArrayList<Integer>();

			for (int i = 0; i < non100.length; i++) {
				if (non100[i])
					random.add(i);
				else {
					FloorItem item = new FloorItem(drops.get(i).getId(), drops
							.get(i).getAmount(), highestHitter, getAbsX(),
							getAbsY(), getHeightLevel());
					toDrop.add(item);
				}
			}
			if (!random.isEmpty()) {
				int randomSelection = Utils.random(random.size());
				for (int i : random)
					if (i == randomSelection)
						if (Utils.random(100) < drops.get(i).getChance()) {
							FloorItem newRandomDrop = new FloorItem(drops.get(i)
									.getId(), drops.get(i).getAmount(),
									highestHitter, getAbsX(), getAbsY(),
									getHeightLevel());
							toDrop.add(newRandomDrop);
						}
			}
			for (FloorItem item : toDrop)
				GameEngine.getItemManager().newDropFromNPC(item, highestHitter);
		}
		switch (this.getDefinition().getType()) {
			case 173:
			case 442:
				GameEngine.getGlobalActions().sendMessage(
						"" + this.getDefinition().getName()
								+ " has been slain by: " + highestHitter + "");
				break;
			default:
				break;
		}
		previousAttackers = null;
	}

	public void setWalk(int tgtX, int tgtY, boolean restrict) {
		if (isDead) {
			return;
		}
		direction = Utils.direction(getAbsX(), getAbsY(), tgtX, tgtY);
		if (restrict) {
			if (tgtX > x1 || tgtX < x2 || tgtY > y1 || tgtY < y2) {
				direction = -1;
				return;
			}
		}
		Tile[] oldLocation = TileManager.getTiles(this);

		if (definition.getType() != Constants.PET_TYPE) {

			for (Tile oldTiles : oldLocation)
				FlagMap.set(TileManager.currentLocation(oldTiles), false);
		}

		if (direction != -1) {
			direction >>= 1;
			setAbsX(tgtX);
			setAbsY(tgtY);
		}
		Tile[] newLocation = TileManager.getTiles(this);

		if (definition.getType() != Constants.PET_TYPE) {

			for (Tile newTiles : newLocation)
				FlagMap.set(TileManager.currentLocation(newTiles), true);
		}
		updateRequired = true;
	}

	/**
	 * Sets face directions
	 */
	public void setFaceDirection() {
		switch (definition.getType()) {
			case 494:
			case 495:
				if (getAbsX() == 2928 && getAbsY() == 3288)
					turnNpc(getAbsX() + 1, getAbsY());

				if (getAbsX() == 3098 && getAbsY() == 3492)
					turnNpc(getAbsX(), getAbsY() + 1);

				if (getAbsX() == 3096 && getAbsY() == 3492)
					turnNpc(getAbsX(), getAbsY() + 1);

				if (getAbsX() == 3096 && getAbsY() == 3491)
					turnNpc(getAbsX() - 1, getAbsY());

				if (getAbsX() == 3096 && getAbsY() == 3489)
					turnNpc(getAbsX() - 1, getAbsY());

				if (getAbsX() == 3187 && getAbsY() > 3435 && getAbsY() < 3447)
					turnNpc(getAbsX() - 1, getAbsY());

				if (getAbsX() >= 3252 && getAbsX() <= 3256 && getAbsY() == 3418)
					turnNpc(getAbsX(), getAbsY() + 1);

				if (getAbsX() >= 2945 && getAbsX() <= 2949 && getAbsY() == 3366)
					turnNpc(getAbsX(), getAbsY() + 1);

				if (getAbsX() >= 3010 && getAbsX() <= 3015 && getAbsY() == 3353)
					turnNpc(getAbsX(), getAbsY() + 1);
				break;

			case 569:
				turnNpc(getAbsX(), getAbsY() - 1);
				break;

			case 595:
			case 570:
				turnNpc(getAbsX() - 1, getAbsY());
				break;
			case 804: // tanner
				if (getAbsX() == 3271 && getAbsY() == 3192)
					turnNpc(getAbsX() + 1, getAbsY());
				break;
		}
	}

	private boolean hitUpdateRequired2;
	private int hit2;
	private int deathDelay = 0;

	/**
	 * An idea to create more than 1 step walking.
	 */
	private int stepsAllocated = 0;
	private int[] walkingPaths = new int[2];
	private boolean pathCreated = false;

	/**
	 * Processing.
	 */
	public void process() {
		Entity ent = this;

		if (ent.getOwner() != null) {
			if (TileManager.calculateDistance(this, this.getOwner()) > 30) {
				removeMyNPC(this.getOwner());
			}
		}
		/*
		 * if (!isDead &&
		 * TileManager.calculateDistance(TileManager.currentLocation(this),
		 * NPCFollow.getHomeLocation(this)) > 10) { Location.addNewRequest(this,
		 * NPCFollow.getHomeLocation(this)[0],
		 * NPCFollow.getHomeLocation(this)[1],
		 * NPCFollow.getHomeLocation(this)[2], 0);
		 * CombatEngine.resetAttack(this, false); }
		 */
		setFaceDirection();
		this.deathDelay--;
		if (hp <= 0 && maxHP > 0 && !isDead) {
			CombatEngine.resetAttack(this, false);
			this.setInCombatWith(null);
			this.setInCombatTimer(0);
			isDead = true;
			this.deathDelay = 3;
			FollowEngine.resetFollowing(this);
		}
		if (isDead && this.deathDelay < 1) {
			if (!isDeadWaiting && !isDeadTeleporting) {
				animNumber = NPCManager.emotions[this.getDefinition().getType()][2];
				animUpdateRequired = true;
				updateRequired = true;
				isDeadWaiting = true;
				isDeadTimer = 4;
				if (isWalking) {
					wasWalking = true;
				}
				isWalking = false;
				if (previousAttackers == null) {
					previousAttackers = attackers;
					attackers = new HashMap<String, Integer>();
					attackers.putAll(previousAttackers);
				}
			} else if (isDeadTeleporting) {
				if (isDeadTimer == 0) {
					isDead = false;
					isHidden = false;
					if (wasWalking) {
						isWalking = true;
					}
					hp = maxHP;
					isDeadTeleporting = false;
					isDead = false;
					setAbsX(spawnAbsX);
					setAbsY(spawnAbsY);
				}
				isDeadTimer--;
			} else {
				if (isDeadTimer == 0) {
					setTarget(null);
					following = null;
					animNumber = -1;
					animUpdateRequired = true;
					updateRequired = true;
					isDeadWaiting = false;
					isHidden = true;
					isDeadTimer = definition.getRespawn();
					isDeadTeleporting = true;

					Tile[] oldLocation = TileManager.getTiles(this);

					for (Tile oldTiles : oldLocation)
						FlagMap.set(TileManager.currentLocation(oldTiles),
								false);

					if (ent.getOwner() != null) {
						removeMyNPC(ent.getOwner());
					} else {
						processDrops();
					}
				}
				isDeadTimer--;
			}
		}
		if (getOwner() != null) {
			if (getDefinition().getType() == Constants.PET_TYPE) {
				if (getOwner() != null && !((Client) getOwner()).disconnected) {
					if (((Client) getOwner()).getWildernessLevel() > 0) {
						this.setHidden(true);
					} else {
						this.setHidden(false);
					}
					if (TileManager.calculateDistance(this, getOwner()) > 16) {
						Location.addNewRequest(this, getOwner().getAbsX(),
								getOwner().getAbsY(), getOwner()
										.getHeightLevel(), 0);
						return;
					}

				}
			}

		}
		if (pickingUpItem) {
			return;
		}
		if (stepsAllocated < 1 || walkingPaths[0] == 0 && walkingPaths[1] == 0) {
			pathCreated = false;
		}

		if (getX1() > 0 && getX2() > 0 && getY1() > 0 && getY2() > 0) {

			if (ent.getFreezeDelay() > 0) {
				updateRequired = true;
				return;
			}

			/**
			 * generate a random direction
			 */
			if (ent.getTarget() == null && !pathCreated) {

				if (Utils.random(4) == 1) {

					int MoveX = Utils.random(1);
					int MoveY = Utils.random(1);

					int Rnd = Utils.random2(4);

					if (Rnd == 1) {
						MoveX = -(MoveX);
						MoveY = -(MoveY);
					} else if (Rnd == 2) {
						MoveX = -(MoveX);
					} else if (Rnd == 3) {
						MoveY = -(MoveY);
					}
					if (IsInRange(this, getAbsX() + MoveX, getAbsY() + MoveY) == true) {
						stepsAllocated = Utils.random(10);
						walkingPaths[0] = MoveX;
						walkingPaths[1] = MoveY;
						pathCreated = true;
					}
				}
			}
			if (pathCreated && stepsAllocated > 0 && ent.getTarget() == null) {

				/**
				 * Create a new random path on our walking path
				 */
				if (Utils.random(10) == 1) {

					int MoveX = Utils.random(1);
					int MoveY = Utils.random(1);

					int Rnd = Utils.random2(4);

					if (Rnd == 1) {
						MoveX = -(MoveX);
						MoveY = -(MoveY);
					} else if (Rnd == 2) {
						MoveX = -(MoveX);
					} else if (Rnd == 3) {
						MoveY = -(MoveY);
					}
					if (IsInRange(this, getAbsX() + MoveX, getAbsY() + MoveY) == true) {
						walkingPaths[0] = MoveX;
						walkingPaths[1] = MoveY;
					}
				}
				stepsAllocated--;

				int[] nextStep = new int[3];

				nextStep[0] = getAbsX() + walkingPaths[0];
				nextStep[1] = getAbsY() + walkingPaths[1];
				nextStep[2] = getHeightLevel();

				/**
				 * cannot continue any futher.
				 */
				if (IsInRange(this, nextStep[0], nextStep[1]) == false) {
					walkingPaths[0] = 0;
					walkingPaths[1] = 0;
					stepsAllocated = 0;
					pathCreated = false;

				} else {

					boolean canMove = true;

					Tile[] testTiles = TileManager.getTiles(this, nextStep);

					for (Tile tiles : testTiles)
						if (FlagMap.locationOccupied(
								TileManager.currentLocation(tiles), this))
							canMove = false;

					if (canMove) {
						setWalk(nextStep[0], nextStep[1], false);
					} else {
						walkingPaths[0] = 0;
						walkingPaths[1] = 0;
						stepsAllocated = 0;
						pathCreated = false;
					}
				}
			}
		}
		updateRequired = true;
	}

	/**
	 * Do NOT touch, this is correct way.
	 */
	public boolean IsInRange(NPC npc, int MoveX, int MoveY) {

		if ((MoveX <= npc.getX1()) && (MoveX >= npc.getY1())
				&& (MoveY <= npc.getX2()) && (MoveY >= npc.getY2())) {
			if (MoveX == MoveY) {
				return false;
			}
			return true;
		}
		return false;
	}

	public static void removeMyNPC(Entity entity) {

		HashMap<Integer, NPC> newMap = new HashMap<Integer, NPC>(GameEngine
				.getNPCManager().getNPCMap());

		for (Entry<Integer, NPC> entry : newMap.entrySet()) {

			NPC npc = entry.getValue();

			if (npc.getOwner() == entity) {
				npc.isHidden = true;
				GameEngine.getNpcManager().npcMap.entrySet().remove(entry);
			}
		}
	}

	/**
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param z
	 * @param theOwner
	 *            spawns a npc which will attack TheOwner
	 */
	public static void newNPC(int id, int x, int y, int z, MobileEntity theOwner) {

		try {

			int slot = GameEngine.getNPCManager().freeSlot();

			NPCDefinition def = GameEngine.getNPCManager().npcDefinitions
					.get(id);

			if (def == null)
				return;

			NPC npc = new NPC(slot, def, x, y, z);
			npc.setX1(npc.getAbsX());
			npc.setY1(npc.getAbsY());
			npc.setX2(npc.getAbsX());
			npc.setY2(npc.getAbsY());
			GameEngine.getNPCManager().npcMap.put(npc.getNpcId(), npc);
			npc.setOwner(theOwner);
			npc.setTarget(theOwner);
		} catch (Exception e) {}
	}

	/**
	 * Appends the NPC update block
	 * 
	 * @param str
	 */
	public void appendNPCUpdateBlock(Stream str) {
		if (!updateRequired)
			return; // nothing required
		int updateMask = 0;
		if (textUpdateRequired) {
			updateMask |= 1;
		}
		if (animUpdateRequired) {
			updateMask |= 0x10;
		}
		if (hitUpdateRequired2)
			updateMask |= 8;
		if (mask80update)
			updateMask |= 0x80;
		if (dirUpdateRequired) {
			updateMask |= 0x20;
		}
		if (hitUpdateRequired) {
			updateMask |= 0x40;
		}
		if (turnUpdateRequired)
			updateMask |= 4;

		// if(updateMask >= 0x100) {
		// updateMask |= 0x40;
		// str.writeByte(updateMask & 0xFF);
		// str.writeByte(updateMask >> 8);
		// } else {
		str.writeByte(updateMask);
		// }

		if (textUpdateRequired) {
			str.writeString(textUpdate);
		}
		if (animUpdateRequired) {
			appendAnimUpdate(str);
		}
		if (hitUpdateRequired2)
			appendHitUpdate2(str);
		if (mask80update)
			appendMask80Update(str);
		if (dirUpdateRequired) {
			appendDirUpdate(str);
		}
		if (hitUpdateRequired) {
			appendHitUpdate(str);
		}
		if (turnUpdateRequired)
			appendSetFocusDestination(str);
		// TODO: add the various other update blocks
	}

	/**
	 * Clears the update flags.
	 */
	public void clearUpdateFlags() {
		updateRequired = false;
		textUpdateRequired = false;
		animUpdateRequired = false;
		hitUpdateRequired2 = false;
		hitUpdateRequired = false;
		dirUpdateRequired = false;
		turnUpdateRequired = false;
		mask80update = false;
		if (rawDirection != -1) {
			rawDirection = -1;
			dirUpdateRequired = true;
			updateRequired = true;
		}
		hit = 0;
		direction = -1;
	}

	/**
	 * Appends an focus update
	 * 
	 * @param str
	 */
	private void appendSetFocusDestination(Stream str) {
		str.writeWordBigEndian(focusPointX);
		str.writeWordBigEndian(focusPointY);
	}

	/**
	 * Appends an animation update.
	 * 
	 * @param str
	 */
	public void appendAnimUpdate(Stream str) {
		str.writeWordBigEndian(animNumber);
		str.writeByte(1);
	}

	/**
	 * Appends a direction update.
	 * 
	 * @param str
	 */
	public void appendDirUpdate(Stream str) {
		str.writeWord(rawDirection);
	}

	public static int getCurrentHP(int i, int i1, int i2) {
		double x = (double) i / (double) i1;
		return (int) Math.round(x * i2);
	}

	/**
	 * Appends a hit update
	 * 
	 * @param str
	 */
	public void appendHitUpdate(Stream str) {
		str.writeByteC(hit);
		str.writeByteS(hitType);
		str.writeByteS(getCurrentHP(hp, maxHP, 100));
		str.writeByteC(100);
		System.out.println("" + hit + " " + hitType + " "
				+ getCurrentHP(hp, maxHP, 100) + " " + 100);
	}

	public void appendMask80Update(Stream str) {
		str.writeWord(mask80var1);
		str.writeDWord(mask80var2);
	}

	public void appendHitUpdate2(Stream str) {
		str.writeByteA(hit2);
		str.writeByteC(hitType2);
		str.writeByteA(getCurrentHP(hp, maxHP, 100));
		str.writeByte(100);
		System.out.println("" + hit2 + " " + hitType2 + " "
				+ getCurrentHP(hp, maxHP, 100) + " " + 100);
	}

	/**
	 * Updates NPC movement
	 * 
	 * @param str
	 */
	public void updateNPCMovement(Stream str) {
		if (direction == -1) {
			// don't have to update the npc position, because the npc is just
			// standing
			if (updateRequired) {
				// tell client there's an update block appended at the end
				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else
				str.writeBits(1, 0);
		} else {
			// send "walking packet"
			str.writeBits(1, 1);
			str.writeBits(2, 1); // updateType
			str.writeBits(3, Utils.xlateDirectionToClient[direction]);
			if (updateRequired)
				str.writeBits(1, 1); // tell client there's an update block
			// appended at the end
			else
				str.writeBits(1, 0);
		}
	}

	/**
	 * @param definition
	 *            the definition to set
	 */
	public void setDefinition(NPCDefinition definition) {
		this.definition = definition;
	}

	/**
	 * @return the definition
	 */
	public NPCDefinition getDefinition() {
		return definition;
	}

	/**
	 * @param animNumber
	 *            the animNumber to set
	 */
	public void setAnimNumber(int animNumber) {
		this.animNumber = animNumber;
	}

	/**
	 * @return the animNumber
	 */
	public int getAnimNumber() {
		return animNumber;
	}

	/**
	 * @param animUpdateRequired
	 *            the animUpdateRequired to set
	 */
	public void setAnimUpdateRequired(boolean animUpdateRequired) {
		this.animUpdateRequired = animUpdateRequired;
	}

	/**
	 * @return the animUpdateRequired
	 */
	public boolean isAnimUpdateRequired() {
		return animUpdateRequired;
	}

	/**
	 * @param updateRequired
	 *            the updateRequired to set
	 */
	public void setUpdateRequired(boolean updateRequired) {
		this.updateRequired = updateRequired;
	}

	/**
	 * @return the updateRequired
	 */
	public boolean isUpdateRequired() {
		return updateRequired;
	}

	/**
	 * @param attacker
	 *            the attacker to set
	 */
	public void setAttacker(Client attacker) {
		this.attacker = attacker;
	}

	/**
	 * @return the attacker
	 */
	public Client getAttacker() {
		return attacker;
	}

	/**
	 * @return the attackers
	 */
	public Map<String, Integer> getAttackers() {
		return attackers;
	}

	/**
	 * @param isWalking
	 *            the isWalking to set
	 */
	public void setWalking(boolean isWalking) {
		this.isWalking = isWalking;
	}

	/**
	 * @return the isWalking
	 */
	public boolean isWalking() {
		return isWalking;
	}

	/**
	 * @param wasWalking
	 *            the wasWalking to set
	 */
	public void setWasWalking(boolean wasWalking) {
		this.wasWalking = wasWalking;
	}

	/**
	 * @return the wasWalking
	 */
	public boolean isWasWalking() {
		return wasWalking;
	}

	/**
	 * @param isDead
	 *            the isDead to set
	 */
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	/**
	 * @return the isDead
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * @param npcId
	 *            the npcId to set
	 */
	public void setNpcId(int npcId) {
		this.npcId = npcId;
	}

	/**
	 * @return the npcId
	 */
	public int getNpcId() {
		return npcId;
	}

	/**
	 * @param isHidden
	 *            the isHidden to set
	 */
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	/**
	 * @return the isHidden
	 */
	public boolean isHidden() {
		return isHidden;
	}

	/**
	 * @param x1
	 *            the x1 to set
	 */
	public void setX1(int x1) {
		this.x1 = x1;
	}

	/**
	 * @return the x1
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * @param y1
	 *            the y1 to set
	 */
	public void setY1(int y1) {
		this.y1 = y1;
	}

	/**
	 * @return the y1
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * @param x2
	 *            the x2 to set
	 */
	public void setX2(int x2) {
		this.x2 = x2;
	}

	/**
	 * @return the x2
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * @param y2
	 *            the y2 to set
	 */
	public void setY2(int y2) {
		this.y2 = y2;
	}

	/**
	 * @return the y2
	 */
	public int getY2() {
		return y2;
	}

	public boolean pickingUpItem;

}
