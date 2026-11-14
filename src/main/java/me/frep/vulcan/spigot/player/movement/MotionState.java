package me.frep.vulcan.spigot.player.movement;

import com.github.retrooper.packetevents.protocol.attribute.Attribute;
import com.github.retrooper.packetevents.protocol.attribute.Attributes;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.potion.PotionType;
import com.github.retrooper.packetevents.protocol.potion.PotionTypes;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClientStatus;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClientStatus.Action;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity.InteractAction;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerChangeGameState;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEffect;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerPositionAndLook;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerRemoveEntityEffect;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerChangeGameState.Reason;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes.Property;
import java.util.List;
import me.frep.vulcan.spigot.check.configuration.CheckUtils;
import me.frep.vulcan.spigot.check.exemption.ExemptionManager;
import me.frep.vulcan.spigot.collection.list.EvictingList;
import me.frep.vulcan.spigot.data.RotationSample;
import me.frep.vulcan.spigot.lifecycle.PluginServices;
import me.frep.vulcan.spigot.player.collision.CollisionState;
import me.frep.vulcan.spigot.player.session.PlayerData;
import me.frep.vulcan.spigot.task.TickProcessor;
import me.frep.vulcan.spigot.util.minecraft.AttributeUtils;
import me.frep.vulcan.spigot.util.numbers.MathUtils;
import me.frep.vulcan.spigot.world.registry.EnchantmentMapper;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import java.util.Iterator;

public class MotionState {

    private final PlayerData playerData;

    private boolean sprinting;

    private boolean entityActionReceived;

    private boolean itemUseReceived;

    private boolean digging;

    private boolean releaseUseItemReceived;

    private boolean diggingPacketReceived;

    private boolean teleporting;

    private boolean speedEffect;

    private boolean packetJumpBoostEffect;

    private boolean packetLevitationEffect;

    private boolean slowFallingEffect;

    private boolean slownessEffect;

    private boolean conduitPowerEffect;

    private boolean berserk;

    private boolean depthStrider;

    private boolean wearingElytra;

    private boolean gliding;

    private boolean pendingSwimFix;

    private boolean swiftSneak;

    private boolean lastPlacementValid;

    private boolean jumpBoostEffect;

    private boolean raining;

    private boolean aquaAffinity;

    private boolean packetGliding;

    private boolean packetSwimming;

    private boolean packetRiptide;

    private double movementSpeed = 0.1;

    private double stepHeight = 0.6;

    private double jumpStrength = 0.42;

    private double gravity = 0.08;

    private double previousJumpStrength = 0.42;

    private double previousGravity = 0.08;

    private double entityInteractionRange = 3.0;

    private double bounciness = 0.0;

    private double airDragModifier = 1.0;

    private double frictionModifier = 1.0;

    public final EvictingList<Vector> pendingServerPositions = new EvictingList<>(15);

    private int lastDigTick;

    private int lastDroppedItemTick;

    private int lastAbortDigTick;

    private int ticksNotSprinting;

    private int ticksSinceJoin = 0;

    private int lastWindowClickTick;

    private int heldItemAmount;

    private int previousHeldItemAmount;

    private int heldItemSlot;

    private int previousHeldItemSlot;

    private int ticksSinceServerPosition = 100;

    private int lastBedEnterTick;

    private int lastBedLeaveTick;

    private int lastItemPickupTick;

    private int lastCancelledBreakTick;

    private int lastDamageTick;

    private int ticksSinceDragonDamage = 100;

    private int sprintingTicks;

    private int lastRespawnTick;

    private int lastProjectileLaunchTick;

    private int ticksSinceAttackDamage = 100;

    private int ticksSinceFallDamage = 100;

    private int ticksSinceProjectileDamage;

    private int ticksSinceBucketEmpty = 100;

    private int ticksSinceBlockPlace = 100;

    private int ticksSinceClimbablePlace = 100;

    private int ticksSinceSlimePlace = 150;

    private int ticksSinceCancelledPlace = 100;

    private int ticksSinceWorldChange = 100;

    private int ticksSinceTeleport = 100;

    private int ticksSinceDeath = 100;

    private int ticksSinceKnockbackDamage = 100;

    private int lastPlacedBlockX;

    private int lastPlacedBlockY;

    private int lastPlacedBlockZ;

    private int ticksSinceBottleUse = 100;

    private int ticksSinceFireball = 100;

    private int speedAmplifier = 0;

    private int jumpBoostAmplifier = 0;

    private int levitationAmplifier = 0;

    private int ticksSinceChorusFruit = 100;

    private int ticksSinceWebPlace = 100;

    private int ticksSinceEnderPearl = 100;

    private int ticksSinceDamage = 100;

    private int ticksSinceIcePlace = 100;

    private int ticksSinceBlockBreak = 100;

    private int ticksExisted = 0;

    private int strafeGraceTicks = 100;

    private int ticksSinceMythicMob = 500;

    private int ticksSinceCancelledMove = 1000;

    private int ticksSinceRavagerDamage = 1000;

    private int ticksSinceCrystalDamage = 100;

    private int ticksSinceBucketUse = 100;

    private int lastSpeedAmplifier = 0;

    private int ticksSinceValidPlacement = 100;

    private int slownessAmplifier = 0;

    private int ticksSinceJumpStrengthChange;

    private int ticksSinceGravityChange;

    private int ticksSinceGliding = 100;

    private int ticksSinceSoulPlace = 100;

    private double lastDiggedBlockX;

    private double lastDiggedBlockZ;

    private double distanceFromDiggedBlock;

    private double lastBrokenBlockX;

    private double lastBrokenBlockY;

    private double lastBrokenBlockZ;

    private double distanceFromBrokenBlock;

    private double distanceFromPlacedBlock;

    private double lastGlidingHorizontalSpeed;

    private long digStartedAtMillis;

    private long digFinishedAtMillis;

    private long digCancelledAtMillis;

    private GameMode gameMode = GameMode.SURVIVAL;

    private ItemStack helmet;

    private ItemStack chestplate;

    private ItemStack leggings;

    private ItemStack boots;

    private ItemStack offHandItem;

    public MotionState(PlayerData playerData2) {
        this.playerData = playerData2;
    }

    public void handleEntityAction(WrapperPlayClientEntityAction wrapperPlayClientEntityAction) {
        this.entityActionReceived = true;
        switch(wrapperPlayClientEntityAction.getAction()) {
            case START_SPRINTING:
                this.sprinting = true;
                break;
            case STOP_SPRINTING:
                this.sprinting = false;
                break;
            case START_SNEAKING:
                break;
            case STOP_SNEAKING:
                break;
            case START_FLYING_WITH_ELYTRA:
                PlayerData playerData2 = this.playerData;
                if ((playerData2.getPlayer() != null) && (!ExemptionManager.isBefore1_9())) {
                    playerData2 = this.playerData;
                    if (!playerData2.getClientVersion().isOlderThan(ClientVersion.V_1_15)) {
                        playerData2 = this.playerData;
                        CollisionState collisionState = playerData2.getCollisionState();
                        if (collisionState.isOnGround()) {
                            return;
                        }
                        playerData2 = this.playerData;
                        CollisionState collisionState2 = playerData2.getCollisionState();
                        if (collisionState2.isPreviousOnGround()) {
                            return;
                        }
                        playerData2 = this.playerData;
                        ItemStack chestplate2 = playerData2.getPlayer().getInventory().getChestplate();
                        boolean condition = chestplate2 != null && chestplate2.getType() == Material.ELYTRA;
                        if (condition) {
                            this.packetGliding = true;
                        }
                    }
                }
        }
    }

    public void handleAttributes(WrapperPlayServerUpdateAttributes wrapperPlayServerUpdateAttributes) {
        this.playerData.getConfirmationTracker().runAfterConfirmation(() -> this.applyAttributes(wrapperPlayServerUpdateAttributes));
    }

    public void handleEntityInteraction(WrapperPlayClientInteractEntity wrapperPlayClientInteractEntity) {
        if (wrapperPlayClientInteractEntity.getAction() == InteractAction.ATTACK) {
            this.digging = false;
            this.distanceFromDiggedBlock = 1000.0;
        }
    }

    public void setLastItemPickupTick() {
        TickProcessor tickProcessor = PluginServices.INSTANCE.getTickProcessor();
        this.lastItemPickupTick = tickProcessor.getTotalTicks();
    }

    public void recordDig() {
        TickProcessor tickProcessor = PluginServices.INSTANCE.getTickProcessor();
        this.lastDigTick = tickProcessor.getTotalTicks();
    }

    public void handleDigging(WrapperPlayClientPlayerDigging wrapperPlayClientPlayerDigging) {
        this.diggingPacketReceived = true;
        switch(wrapperPlayClientPlayerDigging.getAction()) {
            case START_DIGGING:
                this.digging = true;
                this.digStartedAtMillis = System.currentTimeMillis();
                break;
            case FINISHED_DIGGING:
                this.digFinishedAtMillis = System.currentTimeMillis();
                this.lastDiggedBlockX = wrapperPlayClientPlayerDigging.getBlockPosition().getX();
                this.lastDiggedBlockZ = wrapperPlayClientPlayerDigging.getBlockPosition().getZ();
                this.digging = false;
                break;
            case CANCELLED_DIGGING:
                this.digCancelledAtMillis = System.currentTimeMillis();
                this.lastDiggedBlockX = wrapperPlayClientPlayerDigging.getBlockPosition().getX();
                this.lastDiggedBlockZ = wrapperPlayClientPlayerDigging.getBlockPosition().getZ();
                this.digging = false;
                break;
            case RELEASE_USE_ITEM:
                this.releaseUseItemReceived = true;
                break;
            case DROP_ITEM:
            case DROP_ITEM_STACK:
                TickProcessor tickProcessor = PluginServices.INSTANCE.getTickProcessor();
                this.lastDroppedItemTick = tickProcessor.getTotalTicks();
        }
    }

    public void recordEntityDamage(EntityType entityType) {
        switch(entityType) {
            case ENDER_DRAGON:
                this.ticksSinceDragonDamage = 0;
                break;
            case IRON_GOLEM:
                break;
            case FIREBALL:
            case SMALL_FIREBALL:
            case DRAGON_FIREBALL:
                this.ticksSinceFireball = 0;
                break;
            case WITHER:
            case WITHER_SKULL:
                break;
            case ENDER_CRYSTAL:
                this.ticksSinceCrystalDamage = 0;
        }
        if (ExemptionManager.isAtLeast1_16() && entityType == EntityType.HOGLIN) {
        }
        if (ExemptionManager.isAtLeast1_14() && entityType == EntityType.RAVAGER) {
            this.ticksSinceRavagerDamage = 0;
        }
    }

    public void setLastCancelledBreakTick() {
        TickProcessor tickProcessor = PluginServices.INSTANCE.getTickProcessor();
        this.lastCancelledBreakTick = tickProcessor.getTotalTicks();
    }

    public void setLastRespawnTick(WrapperPlayClientClientStatus wrapperPlayClientClientStatus) {
        if (wrapperPlayClientClientStatus.getAction() == Action.PERFORM_RESPAWN) {
            TickProcessor tickProcessor = PluginServices.INSTANCE.getTickProcessor();
            this.lastRespawnTick = tickProcessor.getTotalTicks();
        }
    }

    public void recordItemUse() {
        this.itemUseReceived = true;
        if (ExemptionManager.isAtLeast1_9()) {
            PlayerData playerData2 = this.playerData;
            if (playerData2.getPlayer() == null) {
                return;
            }
            playerData2 = this.playerData;
            if (!playerData2.getPlayer().getInventory().getItemInMainHand().getType().toString().contains("FIREWORK")) {
                playerData2 = this.playerData;
                if (!playerData2.getPlayer().getInventory().getItemInOffHand().getType().toString().contains("FIREWORK")) {
                    return;
                }
            }
            playerData2 = this.playerData;
            CollisionState collisionState = playerData2.getCollisionState();
            collisionState.setTicksSinceFirework(0);
        }
    }

    public void recordWindowClick() {
        TickProcessor tickProcessor = PluginServices.INSTANCE.getTickProcessor();
        this.lastWindowClickTick = tickProcessor.getTotalTicks();
    }

    public void updateDiggingDistance() {
        if (this.digging) {
            TickProcessor tickProcessor = PluginServices.INSTANCE.getTickProcessor();
            this.lastAbortDigTick = tickProcessor.getTotalTicks();
        }
        if (this.lastDiggedBlockX != 0.0 && this.lastDiggedBlockZ != 0.0) {
            PlayerData playerData2 = this.playerData;
            CollisionState collisionState = playerData2.getCollisionState();
            double x = collisionState.getX();
            playerData2 = this.playerData;
            CollisionState collisionState2 = playerData2.getCollisionState();
            double z = collisionState2.getZ();
            double xDifference = Math.abs(x - this.lastDiggedBlockX);
            Object zDifference = Math.abs(z - this.lastDiggedBlockZ);
            this.distanceFromDiggedBlock = MathUtils.hypot(xDifference, Math.abs(z - this.lastDiggedBlockZ));
            if (this.distanceFromDiggedBlock > 12.0) {
                this.lastDiggedBlockX = this.lastDiggedBlockZ = this.distanceFromDiggedBlock = 0.0;
            }
        }
    }

    public void recordBlockPlace() {
        this.ticksSinceBlockPlace = 0;
    }

    public void updateHeldItem() {
        PlayerData playerData2 = this.playerData;
        int heldItemAmount2;
        if (playerData2.getPlayer() == null) {
            heldItemAmount2 = 0;
        } else {
            playerData2 = this.playerData;
            heldItemAmount2 = playerData2.getPlayer().getItemInHand().getAmount();
        }
        this.heldItemAmount = heldItemAmount2;
        playerData2 = this.playerData;
        int heldItemSlot2;
        if (playerData2.getPlayer() == null) {
            heldItemSlot2 = 0;
        } else {
            playerData2 = this.playerData;
            heldItemSlot2 = playerData2.getPlayer().getInventory().getHeldItemSlot();
        }
        this.heldItemSlot = heldItemSlot2;
        if (this.heldItemAmount == this.previousHeldItemAmount && this.heldItemSlot == this.previousHeldItemSlot) {
            this.ticksSinceCancelledPlace = 0;
        }
        this.previousHeldItemSlot = this.heldItemSlot;
        this.previousHeldItemAmount = this.heldItemAmount;
    }

    public void recordBlockBreak(double lastBrokenBlockX2, double lastBrokenBlockY2, double lastBrokenBlockZ2) {
        if (this.digging) {
            this.digging = false;
        }
        this.ticksSinceBlockBreak = 0;
        this.lastBrokenBlockX = lastBrokenBlockX2;
        this.lastBrokenBlockY = lastBrokenBlockY2;
        this.lastBrokenBlockZ = lastBrokenBlockZ2;
    }

    public void recordTeleport() {
        this.ticksSinceTeleport = 0;
    }

    public void handleServerPosition(WrapperPlayServerPlayerPositionAndLook wrapperPlayServerPlayerPositionAndLook) {
        this.playerData.getConfirmationTracker().runAfterConfirmation(() -> this.queueServerPosition(wrapperPlayServerPlayerPositionAndLook));
    }

    public void handlePotionEffect(WrapperPlayServerEntityEffect wrapperPlayServerEntityEffect) {
        this.playerData.getConfirmationTracker().runAfterConfirmation(() -> this.applyPotionEffect(wrapperPlayServerEntityEffect));
    }

    public void handleRemovedPotionEffect(WrapperPlayServerRemoveEntityEffect wrapperPlayServerRemoveEntityEffect) {
        this.playerData.getConfirmationTracker().runAfterConfirmation(() -> this.removePotionEffect(wrapperPlayServerRemoveEntityEffect));
    }

    public void handleGameState(WrapperPlayServerChangeGameState wrapperPlayServerChangeGameState) {
        this.playerData.getConfirmationTracker().runAfterConfirmation(() -> this.applyGameMode(wrapperPlayServerChangeGameState));
    }

    public void setTicksSinceWorldChange() {
        this.ticksSinceWorldChange = 0;
    }

    public void setTicksSinceDeath() {
        this.ticksSinceDeath = 0;
    }

    public void recordDamageCause(DamageCause damageCause) {
        if (damageCause != null) {
            TickProcessor tickProcessor = PluginServices.INSTANCE.getTickProcessor();
            this.lastDamageTick = tickProcessor.getTotalTicks();
            this.ticksSinceDamage = 0;
            if (damageCause != DamageCause.FALL && damageCause != DamageCause.SUFFOCATION) {
                this.ticksSinceKnockbackDamage = 0;
            }
            switch(damageCause) {
                case ENTITY_ATTACK:
                    this.ticksSinceAttackDamage = 0;
                    break;
                case FIRE:
                case FIRE_TICK:
                    break;
                case FALL:
                    this.ticksSinceFallDamage = 0;
                    break;
                case MAGIC:
                    break;
                case POISON:
                    break;
                case CONTACT:
                    break;
                case PROJECTILE:
                    this.ticksSinceProjectileDamage = 0;
                    break;
                case LAVA:
            }
        }
    }

    public void recordEnderPearl() {
        this.ticksSinceEnderPearl = 0;
    }

    public void setLastBedEnterTick() {
        TickProcessor tickProcessor = PluginServices.INSTANCE.getTickProcessor();
        this.lastBedEnterTick = tickProcessor.getTotalTicks();
    }

    public void setLastBedLeaveTick() {
        TickProcessor tickProcessor = PluginServices.INSTANCE.getTickProcessor();
        this.lastBedLeaveTick = tickProcessor.getTotalTicks();
    }

    public void processMovement(WrapperPlayClientPlayerFlying wrapperPlayClientPlayerFlying) {
        boolean gliding2;
        this.teleporting = false;
        if (wrapperPlayClientPlayerFlying.hasPositionChanged() && wrapperPlayClientPlayerFlying.hasRotationChanged() && !wrapperPlayClientPlayerFlying.isOnGround()) {
            for (Vector vector : this.pendingServerPositions) {
                double xDifference = vector.getX() - wrapperPlayClientPlayerFlying.getLocation().getX();
                double yDifference = vector.getY() - wrapperPlayClientPlayerFlying.getLocation().getY();
                double zDifference = vector.getZ() - wrapperPlayClientPlayerFlying.getLocation().getZ();
                if (Math.abs(xDifference) < 1.0E-8 && Math.abs(yDifference) < 1.0E-8 && Math.abs(zDifference) < 1.0E-8) {
                    this.teleporting = true;
                }
            }
        }
        if (this.teleporting) {
            PlayerData playerData2 = this.playerData;
            CollisionState collisionState = playerData2.getCollisionState();
            RotationSample latest = collisionState.getRotationSample(0);
            CollisionState collisionState2 = playerData2.getCollisionState();
            RotationSample previous = collisionState2.getRotationSample(1);
            latest.setDeltaX(0.0);
            latest.setDeltaY(0.0);
            latest.setDeltaZ(0.0);
            latest.setDeltaXZ(0.0);
            latest.setTeleportInvalidated(true);
            latest.setOnGround(previous.isOnGround());
        }
        if (this.teleporting) {
            this.ticksSinceServerPosition = 0;
        } else {
            this.ticksSinceServerPosition++;
        }
        if (this.ticksSinceServerPosition > 100) {
            this.pendingServerPositions.clear();
        }
        this.releaseUseItemReceived = false;
        this.diggingPacketReceived = false;
        this.entityActionReceived = false;
        this.itemUseReceived = false;
        this.ticksSinceSoulPlace++;
        this.ticksSinceGravityChange++;
        this.ticksSinceJumpStrengthChange++;
        this.ticksExisted++;
        this.ticksSinceFallDamage++;
        this.ticksSinceAttackDamage++;
        this.ticksSinceKnockbackDamage++;
        this.ticksSinceProjectileDamage++;
        this.ticksSinceDragonDamage++;
        this.ticksSinceBucketUse++;
        this.ticksSinceMythicMob++;
        this.ticksSinceBucketEmpty++;
        this.ticksSinceBlockPlace++;
        this.ticksSinceBottleUse++;
        this.ticksSinceClimbablePlace++;
        this.ticksSinceSlimePlace++;
        this.ticksSinceCrystalDamage++;
        this.ticksSinceWorldChange++;
        this.ticksSinceTeleport++;
        this.ticksSinceDeath++;
        this.ticksSinceFireball++;
        this.ticksSinceWebPlace++;
        this.ticksSinceChorusFruit++;
        this.strafeGraceTicks++;
        this.ticksSinceEnderPearl++;
        this.ticksSinceCancelledPlace++;
        this.ticksSinceDamage++;
        this.ticksSinceIcePlace++;
        this.ticksSinceBlockBreak++;
        this.ticksSinceRavagerDamage++;
        if (this.lastPlacementValid) {
            this.ticksSinceValidPlacement = 0;
        } else {
            this.ticksSinceValidPlacement++;
        }
        if (wrapperPlayClientPlayerFlying.hasPositionChanged()) {
            this.ticksSinceJoin++;
            if (this.playerData.getPlayer() != null) {
                PlayerData playerData3 = this.playerData;
                this.helmet = playerData3.getPlayer().getInventory().getHelmet();
                playerData3 = this.playerData;
                this.chestplate = playerData3.getPlayer().getInventory().getChestplate();
                playerData3 = this.playerData;
                this.leggings = playerData3.getPlayer().getInventory().getLeggings();
                playerData3 = this.playerData;
                this.boots = playerData3.getPlayer().getInventory().getBoots();
                if (ExemptionManager.isAtLeast1_9()) {
                    playerData3 = this.playerData;
                    this.offHandItem = playerData3.getPlayer().getInventory().getItemInOffHand();
                }
                this.depthStrider = ExemptionManager.isNewerThan1_7_10() && this.boots != null && this.boots.containsEnchantment(Enchantment.DEPTH_STRIDER);
                this.aquaAffinity = this.helmet != null && EnchantmentMapper.AQUA_AFFINITY != null && this.helmet.containsEnchantment(EnchantmentMapper.AQUA_AFFINITY);
                this.swiftSneak = ExemptionManager.isAtLeast1_19() && this.leggings != null && this.leggings.containsEnchantment(Enchantment.SWIFT_SNEAK);
                this.wearingElytra = ExemptionManager.isAtLeast1_9() && this.chestplate != null && this.chestplate.getType().toString().equals("ELYTRA");
                gliding2 = ((this.wearingElytra) && (CheckUtils.isGliding(this.playerData)));
                this.gliding = gliding2;
            }
            if (this.packetGliding) {
                this.ticksSinceGliding = 0;
            } else {
                this.ticksSinceGliding++;
            }
            if (this.gliding) {
                CollisionState collisionState3 = this.playerData.getCollisionState();
                this.lastGlidingHorizontalSpeed = collisionState3.getDeltaXZ();
            }
            if (!(this.gliding)) {
            }
            double x = wrapperPlayClientPlayerFlying.getLocation().getX();
            double y = wrapperPlayClientPlayerFlying.getLocation().getY();
            double z = wrapperPlayClientPlayerFlying.getLocation().getZ();
            if (this.sprinting) {
                this.sprintingTicks++;
            } else {
                this.sprintingTicks = 0;
            }
            if (!this.sprinting) {
                this.ticksNotSprinting++;
            } else {
                this.ticksNotSprinting = 0;
            }
            if (this.lastBrokenBlockX != 0.0 && this.lastBrokenBlockZ != 0.0) {
                double xDifference2 = Math.abs(x - this.lastBrokenBlockX);
                double yDifference2 = Math.abs(y - this.lastBrokenBlockY);
                Object zDifference2 = Math.abs(z - this.lastBrokenBlockZ);
                this.distanceFromBrokenBlock = MathUtils.length3(xDifference2, yDifference2, Math.abs(z - this.lastBrokenBlockZ));
                if (this.distanceFromBrokenBlock > 8.0) {
                    this.lastBrokenBlockX = this.lastBrokenBlockZ = this.distanceFromBrokenBlock = 0.0;
                }
            }
            if (this.lastPlacedBlockX != 0 && this.lastPlacedBlockY != 0 && this.lastPlacedBlockZ != 0) {
                double xDifference3 = Math.abs(x - this.lastPlacedBlockX);
                double yDifference3 = Math.abs(y - this.lastPlacedBlockY);
                Object zDifference3 = Math.abs(z - this.lastPlacedBlockZ);
                this.distanceFromPlacedBlock = MathUtils.length3(xDifference3, yDifference3, Math.abs(z - this.lastPlacedBlockZ));
                if (this.distanceFromPlacedBlock > 12.0) {
                    this.lastPlacedBlockX = this.lastPlacedBlockY = this.lastPlacedBlockZ = 0;
                }
            }
        }
    }

    public void setTicksSinceSlimePlace() {
        this.ticksSinceSlimePlace = 0;
    }

    public void recordCancelledPlace() {
        this.ticksSinceCancelledPlace = 0;
    }

    public void recordIcePlace() {
        this.ticksSinceIcePlace = 0;
    }

    public void recordClimbablePlace() {
        this.ticksSinceClimbablePlace = 0;
    }

    public void recordWebPlace() {
        this.ticksSinceWebPlace = 0;
    }

    public void setLastProjectileLaunchTick() {
        TickProcessor tickProcessor = PluginServices.INSTANCE.getTickProcessor();
        this.lastProjectileLaunchTick = tickProcessor.getTotalTicks();
    }

    public void recordPlacedBlock(BlockPlaceEvent blockPlaceEvent) {
        this.lastPlacedBlockX = blockPlaceEvent.getBlockPlaced().getX();
        this.lastPlacedBlockY = blockPlaceEvent.getBlockPlaced().getY();
        this.lastPlacedBlockZ = blockPlaceEvent.getBlockPlaced().getZ();
    }

    public void recordChorusFruit() {
        this.ticksSinceChorusFruit = 0;
    }

    public void handlePlayerMetadata(WrapperPlayServerEntityMetadata wrapperPlayServerEntityMetadata) {
        WrapperPlayServerEntityMetadata wrapperPlayServerEntityMetadata2 = wrapperPlayServerEntityMetadata;
        this.playerData.getConfirmationTracker().runAfterConfirmation(() -> this.applyPlayerMetadata(wrapperPlayServerEntityMetadata2));
    }

    public static EntityData findMetadata(List list, int index) {
        List list2 = list;
        int index2 = index;
        Iterator iterator = list2.iterator();
        while (iterator.hasNext()) {
            EntityData entityData = (EntityData) iterator.next();
            if (entityData.getIndex() != index2)
                continue;
            return entityData;
        }
        return null;
    }

    public double applyAirDrag(double amount) {
        return !ExemptionManager.isAtLeast26_1() ? amount : Math.max(0.0, Math.min(1.0, 1.0 - (1.0 - amount) * this.airDragModifier));
    }

    public double maximumHorizontalMotion(double amount, boolean condition) {
        double amount2 = this.applyAirDrag(0.91);
        if (amount2 <= 0.91) {
            return 0.0;
        }
        double amount3 = amount * amount2 * (condition ? 0.6 : 1.0);
        double amount4 = condition ? this.movementSpeed * 1.3 : 0.026;
        return amount3 + amount4 + 0.02;
    }

    public double applyFriction(double amount) {
        return !ExemptionManager.isAtLeast26_1() ? amount : Math.max(0.0, Math.min(1.0, 1.0 - (1.0 - amount) * this.frictionModifier));
    }

    public boolean isSprinting() {
        return this.sprinting;
    }

    public boolean isEntityActionReceived() {
        return this.entityActionReceived;
    }

    public boolean isItemUseReceived() {
        return this.itemUseReceived;
    }

    public boolean isDigging() {
        return this.digging;
    }

    public boolean isReleaseUseItemReceived() {
        return this.releaseUseItemReceived;
    }

    public boolean isDiggingPacketReceived() {
        return this.diggingPacketReceived;
    }

    public boolean isTeleporting() {
        return this.teleporting;
    }

    public boolean isSpeedEffect() {
        return this.speedEffect;
    }

    public boolean isPacketJumpBoostEffect() {
        return this.packetJumpBoostEffect;
    }

    public boolean isPacketLevitationEffect() {
        return this.packetLevitationEffect;
    }

    public boolean isSlowFallingEffect() {
        return this.slowFallingEffect;
    }

    public boolean isSlownessEffect() {
        return this.slownessEffect;
    }

    public boolean isConduitPowerEffect() {
        return this.conduitPowerEffect;
    }

    public boolean isBerserk() {
        return this.berserk;
    }

    public void setBerserk(boolean berserk2) {
        this.berserk = berserk2;
    }

    public boolean isDepthStrider() {
        return this.depthStrider;
    }

    public boolean isWearingElytra() {
        return this.wearingElytra;
    }

    public boolean isGliding() {
        return this.gliding;
    }

    public boolean isPendingSwimFix() {
        return this.pendingSwimFix;
    }

    public boolean isSwiftSneak() {
        return this.swiftSneak;
    }

    public boolean isLastPlacementValid() {
        return this.lastPlacementValid;
    }

    public boolean isJumpBoostEffect() {
        return this.jumpBoostEffect;
    }

    public boolean isRaining() {
        return this.raining;
    }

    public boolean isPacketGliding() {
        return this.packetGliding;
    }

    public boolean isPacketSwimming() {
        return this.packetSwimming;
    }

    public boolean isPacketRiptide() {
        return this.packetRiptide;
    }

    public double getMovementSpeed() {
        return this.movementSpeed;
    }

    public double getStepHeight() {
        return this.stepHeight;
    }

    public double getJumpStrength() {
        return this.jumpStrength;
    }

    public double getGravity() {
        return this.gravity;
    }

    public double getPreviousJumpStrength() {
        return this.previousJumpStrength;
    }

    public double getPreviousGravity() {
        return this.previousGravity;
    }

    public double getEntityInteractionRange() {
        return this.entityInteractionRange;
    }

    public double getBounciness() {
        return this.bounciness;
    }

    public double getAirDragModifier() {
        return this.airDragModifier;
    }

    public double getFrictionModifier() {
        return this.frictionModifier;
    }

    public int getLastDigTick() {
        return this.lastDigTick;
    }

    public int getLastDroppedItemTick() {
        return this.lastDroppedItemTick;
    }

    public int getLastAbortDigTick() {
        return this.lastAbortDigTick;
    }

    public int getTicksNotSprinting() {
        return this.ticksNotSprinting;
    }

    public int getTicksSinceJoin() {
        return this.ticksSinceJoin;
    }

    public int getLastWindowClickTick() {
        return this.lastWindowClickTick;
    }

    public int getTicksSinceServerPosition() {
        return this.ticksSinceServerPosition;
    }

    public int getLastBedEnterTick() {
        return this.lastBedEnterTick;
    }

    public int getLastBedLeaveTick() {
        return this.lastBedLeaveTick;
    }

    public int getLastItemPickupTick() {
        return this.lastItemPickupTick;
    }

    public int getLastCancelledBreakTick() {
        return this.lastCancelledBreakTick;
    }

    public int getTicksSinceDragonDamage() {
        return this.ticksSinceDragonDamage;
    }

    public int getSprintingTicks() {
        return this.sprintingTicks;
    }

    public int getLastRespawnTick() {
        return this.lastRespawnTick;
    }

    public int getLastProjectileLaunchTick() {
        return this.lastProjectileLaunchTick;
    }

    public int getTicksSinceAttackDamage() {
        return this.ticksSinceAttackDamage;
    }

    public int getTicksSinceFallDamage() {
        return this.ticksSinceFallDamage;
    }

    public int getTicksSinceProjectileDamage() {
        return this.ticksSinceProjectileDamage;
    }

    public int getTicksSinceBucketEmpty() {
        return this.ticksSinceBucketEmpty;
    }

    public int getTicksSinceBlockPlace() {
        return this.ticksSinceBlockPlace;
    }

    public int getTicksSinceClimbablePlace() {
        return this.ticksSinceClimbablePlace;
    }

    public int getTicksSinceSlimePlace() {
        return this.ticksSinceSlimePlace;
    }

    public int getTicksSinceCancelledPlace() {
        return this.ticksSinceCancelledPlace;
    }

    public int getTicksSinceWorldChange() {
        return this.ticksSinceWorldChange;
    }

    public int getTicksSinceTeleport() {
        return this.ticksSinceTeleport;
    }

    public int getTicksSinceDeath() {
        return this.ticksSinceDeath;
    }

    public int getTicksSinceKnockbackDamage() {
        return this.ticksSinceKnockbackDamage;
    }

    public int getTicksSinceBottleUse() {
        return this.ticksSinceBottleUse;
    }

    public int getTicksSinceFireball() {
        return this.ticksSinceFireball;
    }

    public int getSpeedAmplifier() {
        return this.speedAmplifier;
    }

    public int getJumpBoostAmplifier() {
        return this.jumpBoostAmplifier;
    }

    public int getLevitationAmplifier() {
        return this.levitationAmplifier;
    }

    public int getTicksSinceChorusFruit() {
        return this.ticksSinceChorusFruit;
    }

    public int getTicksSinceWebPlace() {
        return this.ticksSinceWebPlace;
    }

    public int getTicksSinceEnderPearl() {
        return this.ticksSinceEnderPearl;
    }

    public int getTicksSinceDamage() {
        return this.ticksSinceDamage;
    }

    public int getTicksSinceIcePlace() {
        return this.ticksSinceIcePlace;
    }

    public int getTicksSinceBlockBreak() {
        return this.ticksSinceBlockBreak;
    }

    public int getTicksExisted() {
        return this.ticksExisted;
    }

    public int getStrafeGraceTicks() {
        return this.strafeGraceTicks;
    }

    public void setStrafeGraceTicks(int strafeGraceTicks) {
        this.strafeGraceTicks = strafeGraceTicks;
    }

    public int getTicksSinceMythicMob() {
        return this.ticksSinceMythicMob;
    }

    public int getTicksSinceCancelledMove() {
        return this.ticksSinceCancelledMove;
    }

    public int getTicksSinceRavagerDamage() {
        return this.ticksSinceRavagerDamage;
    }

    public int getTicksSinceCrystalDamage() {
        return this.ticksSinceCrystalDamage;
    }

    public int getTicksSinceBucketUse() {
        return this.ticksSinceBucketUse;
    }

    public int getLastSpeedAmplifier() {
        return this.lastSpeedAmplifier;
    }

    public int getTicksSinceValidPlacement() {
        return this.ticksSinceValidPlacement;
    }

    public int getSlownessAmplifier() {
        return this.slownessAmplifier;
    }

    public int getTicksSinceJumpStrengthChange() {
        return this.ticksSinceJumpStrengthChange;
    }

    public int getTicksSinceGravityChange() {
        return this.ticksSinceGravityChange;
    }

    public int getTicksSinceGliding() {
        return this.ticksSinceGliding;
    }

    public int getTicksSinceSoulPlace() {
        return this.ticksSinceSoulPlace;
    }

    public double getDistanceFromDiggedBlock() {
        return this.distanceFromDiggedBlock;
    }

    public double getDistanceFromBrokenBlock() {
        return this.distanceFromBrokenBlock;
    }

    public double getDistanceFromPlacedBlock() {
        return this.distanceFromPlacedBlock;
    }

    public long getDigStartedAtMillis() {
        return this.digStartedAtMillis;
    }

    public long getDigFinishedAtMillis() {
        return this.digFinishedAtMillis;
    }

    public long getDigCancelledAtMillis() {
        return this.digCancelledAtMillis;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public ItemStack getBoots() {
        return this.boots;
    }

    public void setPendingSwimFix(boolean pendingSwimFix2) {
        this.pendingSwimFix = pendingSwimFix2;
    }

    public void setLastPlacementValid(boolean lastPlacementValid2) {
        this.lastPlacementValid = lastPlacementValid2;
    }

    public void setTicksSinceDragonDamage(int ticksSinceDragonDamage2) {
        this.ticksSinceDragonDamage = ticksSinceDragonDamage2;
    }

    public void setTicksSinceBucketEmpty(int ticksSinceBucketEmpty2) {
        this.ticksSinceBucketEmpty = ticksSinceBucketEmpty2;
    }

    public void setTicksSinceBottleUse(int ticksSinceBottleUse2) {
        this.ticksSinceBottleUse = ticksSinceBottleUse2;
    }

    public void setTicksSinceCancelledMove(int ticksSinceCancelledMove2) {
        this.ticksSinceCancelledMove = ticksSinceCancelledMove2;
    }

    public void setTicksSinceBucketUse(int ticksSinceBucketUse2) {
        this.ticksSinceBucketUse = ticksSinceBucketUse2;
    }

    public void setTicksSinceSoulPlace(int ticksSinceSoulPlace2) {
        this.ticksSinceSoulPlace = ticksSinceSoulPlace2;
    }

    public void resetDigStartTime() {
        this.digStartedAtMillis = 0L;
    }

    public void resetDigFinishTime() {
        this.digFinishedAtMillis = 0L;
    }

    public void resetDigCancelTime() {
        this.digCancelledAtMillis = 0L;
    }

    private void applyPlayerMetadata(WrapperPlayServerEntityMetadata packet) {
        EntityData<?> flags = findMetadata(packet.getEntityMetadata(), 0);
        if (flags != null && flags.getValue() instanceof Byte value) {
            this.packetGliding = (value & 128) != 0 && playerData.getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_9);
            this.packetSwimming = (value & 16) != 0;
        }
        if (ExemptionManager.isNewerThan1_13() && playerData.getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_9)) {
            EntityData<?> handState = findMetadata(packet.getEntityMetadata(), ExemptionManager.isAtLeast1_17() ? 8 : 7);
            if (handState != null && handState.getValue() instanceof Byte value)
                this.packetRiptide = (value & 4) != 0;
        }
    }

    private void applyGameMode(WrapperPlayServerChangeGameState packet) {
        if (packet.getReason() == Reason.CHANGE_GAME_MODE) {
            switch((int) packet.getValue()) {
                case 0 ->
                    gameMode = GameMode.SURVIVAL;
                case 1 ->
                    gameMode = GameMode.CREATIVE;
                case 2 ->
                    gameMode = GameMode.ADVENTURE;
                case 3 ->
                    {
                        if (ExemptionManager.isNewerThan1_7_10())
                            gameMode = GameMode.SPECTATOR;
                    }
            }
        } else if (packet.getReason() == Reason.BEGIN_RAINING)
            raining = true;
        else if (packet.getReason() == Reason.END_RAINING)
            raining = false;
    }

    private void removePotionEffect(WrapperPlayServerRemoveEntityEffect packet) {
        PotionType effect = packet.getPotionType();
        if (effect == PotionTypes.SPEED) {
            speedAmplifier = 0;
            speedEffect = false;
            playerData.getCollisionState().setTicksSinceSpeedEffect(0);
        } else if (effect == PotionTypes.SLOWNESS) {
            slownessEffect = false;
            slownessAmplifier = 0;
        } else if (effect == PotionTypes.HASTE)
            ;
        else if (effect == PotionTypes.JUMP_BOOST) {
            jumpBoostAmplifier = 0;
            packetJumpBoostEffect = false;
        } else if (effect == PotionTypes.LEVITATION) {
            levitationAmplifier = 0;
            packetLevitationEffect = false;
        } else if (effect == PotionTypes.SLOW_FALLING) {
            slowFallingEffect = false;
        } else if (effect == PotionTypes.CONDUIT_POWER) {
            conduitPowerEffect = false;
        } else if (effect == PotionTypes.DOLPHINS_GRACE) {
        }
    }

    private void applyPotionEffect(WrapperPlayServerEntityEffect packet) {
        int amplifier = packet.getEffectAmplifier() + 1;
        PotionType effect = packet.getPotionType();
        if (effect == PotionTypes.SPEED) {
            speedEffect = true;
            speedAmplifier = amplifier;
            lastSpeedAmplifier = amplifier;
        } else if (effect == PotionTypes.HASTE)
            ;
        else if (effect == PotionTypes.SLOWNESS) {
            slownessEffect = true;
            slownessAmplifier = amplifier;
        } else if (effect == PotionTypes.JUMP_BOOST) {
            packetJumpBoostEffect = true;
            jumpBoostAmplifier = amplifier;
        } else if (effect == PotionTypes.LEVITATION) {
            packetLevitationEffect = true;
            levitationAmplifier = amplifier;
        } else if (effect == PotionTypes.SLOW_FALLING) {
            slowFallingEffect = true;
        } else if (effect == PotionTypes.CONDUIT_POWER) {
            conduitPowerEffect = true;
        } else if (effect == PotionTypes.DOLPHINS_GRACE) {
        }
    }

    private void queueServerPosition(WrapperPlayServerPlayerPositionAndLook wrapperPlayServerPlayerPositionAndLook2) {
        Vector vector = new Vector(wrapperPlayServerPlayerPositionAndLook2.getX(), wrapperPlayServerPlayerPositionAndLook2.getY(), wrapperPlayServerPlayerPositionAndLook2.getZ());
        this.pendingServerPositions.add(vector);
    }

    private void applyAttributes(WrapperPlayServerUpdateAttributes packet) {
        for (Property property : packet.getProperties()) {
            Attribute attribute = property.getAttribute();
            if (attribute == Attributes.STEP_HEIGHT)
                stepHeight = AttributeUtils.getAttributeValue(property);
            else if (attribute == Attributes.JUMP_STRENGTH) {
                previousJumpStrength = jumpStrength;
                jumpStrength = AttributeUtils.getAttributeValue(property);
                if (jumpStrength != previousJumpStrength)
                    ticksSinceJumpStrengthChange = 0;
            } else if (attribute == Attributes.GRAVITY) {
                previousGravity = gravity;
                gravity = AttributeUtils.getAttributeValue(property);
                if (gravity != previousGravity)
                    ticksSinceGravityChange = 0;
            } else if (attribute == Attributes.MOVEMENT_SPEED)
                movementSpeed = AttributeUtils.getMovementSpeedWithoutSprint(property);
            else if (attribute == Attributes.ENTITY_INTERACTION_RANGE)
                entityInteractionRange = AttributeUtils.getAttributeValue(property);
            else if (attribute == Attributes.BOUNCINESS)
                bounciness = AttributeUtils.getAttributeValue(property);
            else if (attribute == Attributes.AIR_DRAG_MODIFIER)
                airDragModifier = AttributeUtils.getAttributeValue(property);
            else if (attribute == Attributes.FRICTION_MODIFIER)
                frictionModifier = AttributeUtils.getAttributeValue(property);
        }
    }
}
