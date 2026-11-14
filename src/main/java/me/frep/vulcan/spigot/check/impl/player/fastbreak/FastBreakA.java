package me.frep.vulcan.spigot.check.impl.player.fastbreak;

import me.frep.vulcan.spigot.check.AbstractCheck;
import me.frep.vulcan.spigot.check.api.CheckInfo;
import me.frep.vulcan.spigot.check.exemption.ExemptType;
import me.frep.vulcan.spigot.check.exemption.ExemptionManager;
import me.frep.vulcan.spigot.config.VulcanSettings;
import me.frep.vulcan.spigot.player.session.PlayerData;
import me.frep.vulcan.spigot.world.block.BlockUtils;
import me.frep.vulcan.spigot.world.registry.BukkitRegistry;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

@CheckInfo(name = "Fast Break", type = 'A', complexType = "Delay", description = "Breaking blocks too quickly.")
public class FastBreakA extends AbstractCheck {

    private long breakCompletionAtMillis;

    public FastBreakA(PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void update() {
    }

    public void handleEvent(Event event) {
        if (playerData.getPlayer() == null)
            return;
        if (event instanceof BlockBreakEvent broken)
            checkBlockBreak(broken);
        else if (event instanceof PlayerInteractEvent interaction && interaction.getAction() == Action.LEFT_CLICK_BLOCK)
            setTimestamp(interaction);
    }

    private void setTimestamp(PlayerInteractEvent event) {
        if (event.isCancelled())
            return;
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL)
            return;
        Block block = event.getClickedBlock();
        if (block == null)
            return;
        Material material = block.getType();
        if (BlockUtils.isLiquid(material) || isExemptMaterial(material) || checkItems(player))
            return;
        breakCompletionAtMillis = System.currentTimeMillis() + BukkitRegistry.getBlockBreakTime(block, player);
    }

    private void checkBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled() || player.getGameMode() != GameMode.SURVIVAL || isExempt(new ExemptType[] { ExemptType.VEHICLE }))
            return;
        Material material = event.getBlock().getType();
        if (isExemptMaterial(material) || checkItems(player) || checkState(player))
            return;
        double remainingMillis = System.currentTimeMillis() - breakCompletionAtMillis;
        if (remainingMillis < VulcanSettings.checksPlayerFastbreakAMinDifference)
            fail("block=" + material + " diff=" + remainingMillis + " tool=" + player.getItemInHand().getType());
        else
            decayBuffer();
    }

    private boolean checkItems(Player player) {
        if (player.getItemInHand().getType().name().contains("COPPER"))
            return true;
        return ExemptionManager.isAtLeast1_9() && player.getInventory().getItemInOffHand().getType().name().contains("COPPER");
    }

    private boolean checkState(Player player) {
        if (playerData.getMotionState().isBerserk())
            return true;
        if (playerData.getMotionState().isConduitPowerEffect())
            return true;
        if (ExemptionManager.isAtLeast1_21_2() && BukkitRegistry.hasEfficiencyAbove(player.getItemInHand(), 5))
            return true;
        return ExemptionManager.isAtLeast1_16() && BukkitRegistry.hasHasteEffect(player);
    }

    private boolean isExemptMaterial(Material material) {
        String name = material.name();
        return name.equals("TARGET") || name.equals("WATER_LILY") || name.equals("MANGROVE_ROOTS") || name.equals("CHISELED_BOOKSHELF") || name.equals("FIRE") || name.equals("SULFUR") || name.contains("DOOR") || name.contains("TRAP") || name.contains("INFESTED") || name.contains("CONCRETE") || name.contains("SCULK") || name.contains("MUSHROOM") || name.contains("NOTEBLOCK");
    }
}
