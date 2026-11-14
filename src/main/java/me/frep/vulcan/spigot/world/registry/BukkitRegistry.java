package me.frep.vulcan.spigot.world.registry;

import me.frep.vulcan.spigot.check.configuration.CheckUtils;
import me.frep.vulcan.spigot.util.minecraft.MaterialSets;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public final class BukkitRegistry {

    private static final Enchantment efficiencyEnchantment;

    private static final PotionEffectType hasteEffect;

    private static final PotionEffectType fatigueEffect;

    public static long getBlockBreakTime(Block block2, Player player) {
        if (!MaterialSets.blockHardness.containsKey(block2.getType())) {
            return 0L;
        }
        ItemStack itemInHand = player.getItemInHand();
        if (!(!itemInHand.getType().toString().equalsIgnoreCase("SHEARS") && !itemInHand.getType().toString().contains("COPPER"))) {
            return 0L;
        }
        boolean condition = !block2.getDrops(itemInHand).isEmpty();
        double amount = ((Float) MaterialSets.blockHardness.get(block2.getType())).floatValue() * (condition ? 1.5 : 5.0);
        double toolHarvestLevel = getToolHarvestLevel(itemInHand);
        if (efficiencyEnchantment != null && itemInHand.containsEnchantment(efficiencyEnchantment)) {
            int enchantmentLevel = itemInHand.getEnchantmentLevel(efficiencyEnchantment);
            toolHarvestLevel += enchantmentLevel * enchantmentLevel + 1;
        }
        if (hasteEffect != null && player.hasPotionEffect(hasteEffect)) {
            toolHarvestLevel *= 1.0 + 0.2 * CheckUtils.getPotionAmplifier(player, hasteEffect);
        }
        if (fatigueEffect != null && player.hasPotionEffect(fatigueEffect)) {
            toolHarvestLevel /= Math.pow(3.0, CheckUtils.getPotionAmplifier(player, fatigueEffect));
        }
        amount /= toolHarvestLevel;
        return (long) (amount * 1000.0);
    }

    public static boolean hasHasteEffect(Player player) {
        return hasteEffect != null && player.hasPotionEffect(hasteEffect);
    }

    public static boolean hasEfficiencyAbove(ItemStack itemStack, int level) {
        if (efficiencyEnchantment != null && itemStack.getEnchantmentLevel(efficiencyEnchantment) > level)
            return true;
        for (Map.Entry<Enchantment, Integer> entry : itemStack.getEnchantments().entrySet()) {
            String key = entry.getKey().getKey().getKey();
            if ((key.equals("efficiency") || key.equals("dig_speed")) && entry.getValue() > level)
                return true;
        }
        return false;
    }

    private static Enchantment getEfficiencyEnchantment() {
        Enchantment byName = Enchantment.getByName("EFFICIENCY");
        return byName != null ? byName : Enchantment.getByName("DIG_SPEED");
    }

    private static PotionEffectType getPotionEffectType(String text, String text2) {
        PotionEffectType byName = PotionEffectType.getByName(text);
        return byName != null ? byName : PotionEffectType.getByName(text2);
    }

    private static int getToolHarvestLevel(ItemStack itemStack) {
        String text = itemStack.getType().name();
        return switch(text) {
            case "IRON_PICKAXE", "IRON_AXE", "IRON_SHOVEL", "IRON_SPADE" ->
                ToolTier.IRON.getLevel();
            case "STONE_PICKAXE", "STONE_SHOVEL", "STONE_SPADE", "STONE_AXE" ->
                ToolTier.STONE.getLevel();
            case "WOODEN_PICKAXE", "WOODEN_AXE", "WOODEN_SHOVEL", "WOODEN_SPADE", "WOOD_PICKAXE", "WOOD_AXE", "WOOD_SHOVEL", "WOOD_SPADE" ->
                ToolTier.WOOD.getLevel();
            case "NETHERITE_AXE", "NETHERITE_SHOVEL", "NETHERITE_PICKAXE" ->
                ToolTier.NETHERITE.getLevel();
            case "DIAMOND_PICKAXE", "DIAMOND_AXE", "DIAMOND_SHOVEL", "DIAMOND_SPADE" ->
                ToolTier.DIAMOND.getLevel();
            case "GOLDEN_AXE", "GOLD_AXE", "GOLDEN_SHOVEL", "GOLDEN_SPADE", "GOLD_SHOVEL", "GOLD_SPADE", "GOLDEN_PICKAXE", "GOLD_PICKAXE" ->
                ToolTier.GOLD.getLevel();
            default ->
                0;
        };
    }

    private BukkitRegistry() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        efficiencyEnchantment = getEfficiencyEnchantment();
        hasteEffect = getPotionEffectType("HASTE", "FAST_DIGGING");
        fatigueEffect = getPotionEffectType("MINING_FATIGUE", "SLOW_DIGGING");
    }
}
