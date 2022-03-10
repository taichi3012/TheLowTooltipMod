package com.github.taichi3012.thelowtooltipmod.util;

import com.github.taichi3012.thelowtooltipmod.api.TheLowAPI;
import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;

import java.util.*;

public class TheLowUtil {
    public static WeaponType generateWeaponType(ItemStack stack) {
        List<String> lore = ItemNBTUtil.getItemLore(stack);

        //loreから判断
        for (String str : lore) {
            if (str.matches(".*剣 [0-9]+以上.*")) return WeaponType.SWORD;
            if (str.matches(".*弓 [0-9]+以上.*")) return WeaponType.BOW;
            if (str.matches(".*魔法 [0-9]+以上.*")) return WeaponType.MAGIC;
        }

        //loreから判断できなかった場合はItemIDで判断
        String Id = stack.getUnlocalizedName();

        if (Id.contains("sword")) return WeaponType.SWORD;
        if (Id.equals("item.bow") || Id.equals("tile.banner")) return WeaponType.BOW;
        if (Id.contains("hoe")) return WeaponType.MAGIC;

        //当てはまらなかった場合はnull
        return null;
    }

    public static double getAttackMultiplyAbilityGain(WeaponType weaponType) {
        return (TheLowTooltipModConfig.getOSParkGainByWeaponType(weaponType) +
                getPlayerJobType().getGainByWeaponType(weaponType) +
                TheLowUtil.getEquipAttackGain(weaponType));
    }

    public static double getEquipAttackGain(WeaponType weaponType) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return 0.0d;
        }

        return  Arrays.stream(player.inventory.armorInventory)
                .mapToDouble(equip -> generateEquipAttackGain(equip).get(weaponType))
                .sum();
    }

    public static JobType getPlayerJobType() {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return JobType.UNKNOWN_JOB;
        }

        TheLowAPI.PlayerStatus status = TheLowAPI.getPlayerStatus(player.getUniqueID());
        if (status == null) {
            return JobType.UNKNOWN_JOB;
        }

        return status.jobType;
    }

    public static Map<WeaponType, Double> generateEquipAttackGain(ItemStack stack) {
        Map<WeaponType, Double> result = new HashMap<>();
        Arrays.stream(WeaponType.values()).forEach(type -> result.put(type, 0.0d));

        List<String> lore = ItemNBTUtil.getItemLore(stack);

        lore.stream()
                .filter(str -> str.matches("^( {4})(§.)*.*の攻撃力 ： (§.)*[+-]?[0-9]+\\.[0-9]+([eE][+-]?[0-9]+)?%$"))
                .forEach(line -> {
                    WeaponType weaponType = null;
                    double gain;

                    for (WeaponType type : WeaponType.values()) {
                        if (line.matches("^( {4})(§.)*" + type.getName() + "の攻撃力 ： (§.)*[+-]?[0-9]+\\.[0-9]+([eE][+-]?[0-9]+)?%$")) {
                            weaponType = type;
                        }
                    }

                    if (weaponType == null) {
                        return;
                    }

                    try {
                        gain = Double.parseDouble(
                                line.replaceAll("^( {4})(§.)*" + weaponType.getName() + "の攻撃力 ： (§.)*", "")
                                    .replaceAll("%$", "")
                        );
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }
                    
                    result.put(weaponType, gain);
                });

        return result;
    }

    public static double generateOSParkGain(ItemStack stack) {
        List<String> lore = ItemNBTUtil.getItemLore(stack);

        if (!lore.get(1).matches(".*による攻撃力増加\\+[0-9.]{1,7}%")) {
            return 0.0d;
        }

        try {
            return Double.parseDouble(lore.get(1).replaceAll(".*による攻撃力増加\\+|%", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0.0d;
    }

    public static int generateParkStage(ItemStack stack) {
        try {
            return Integer.parseInt(
                    stack.getDisplayName()
                    .replaceAll(".*\\(", "")
                    .replaceAll("/\\d+", "")
                    .replaceAll("\\)$", "")
            );
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isMagicMixtureWeapon(ItemStack stack) {
        WeaponType type = generateWeaponType(stack);

        return Objects.nonNull(type) && type.equals(WeaponType.BOW) && stack.getUnlocalizedName().equals("tile.banner");
    }

    public static double generateNoiseBySeed(long seed) {
        Random random = new Random(seed);
        double c = Math.sqrt(-2.0 * Math.log(random.nextDouble()));

        if (random.nextDouble() < 0.5) {
            return c * Math.sin(2.0 * Math.PI * random.nextDouble()) * 0.17 + 1;
        }

        return c * Math.cos(2.0 * Math.PI * random.nextDouble()) * 0.17 + 1;
    }
}
