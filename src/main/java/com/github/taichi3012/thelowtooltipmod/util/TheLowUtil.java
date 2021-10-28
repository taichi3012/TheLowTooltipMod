package com.github.taichi3012.thelowtooltipmod.util;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import net.minecraft.item.ItemStack;

import java.util.List;

public class TheLowUtil {
    public static WeaponType getWeaponType(ItemStack item) {
        List<String> lore = ItemNBTUtil.getItemLore(item);

        if (lore == null) return null;

        //loreから判断
        for (String str : lore) {
            if (str.matches(".*剣 [0-9]+以上.*")) return WeaponType.SWORD;
            if (str.matches(".*弓 [0-9]+以上.*")) return  WeaponType.BOW;
            if (str.matches(".*魔法 [0-9]+以上.*")) return WeaponType.MAGIC;
        }

        //loreから判断できなかった場合はItemIDで判断
        String itemId = item.getUnlocalizedName();
        if (itemId.contains("sword")) return WeaponType.SWORD;
        if (itemId.equals("item.bow") || itemId.equals("item.banner")) return WeaponType.BOW;
        if (itemId.contains("hoe")) return WeaponType.MAGIC;

        //当てはまらなかった場合はnull
        return null;
    }

    public static double getParkGainByWeaponType(WeaponType type) {
        if (type == null) return 0d;
        switch (type) {
            case SWORD:
                return TheLowTooltipModConfig.overStrengthSwordValue;
            case BOW:
                return TheLowTooltipModConfig.overStrengthBowValue;
            case MAGIC:
                return TheLowTooltipModConfig.overStrengthMagicValue;
            default:
                return 0d;
        }
    }

    public static double getParkGainByItem(ItemStack item) {
        List<String> lore = ItemNBTUtil.getItemLore(item);

        if (lore == null || !lore.get(1).matches(".*による攻撃力増加\\+[0-9.]{1,7}%")) return 0d;

        try {
            return Double.parseDouble(lore.get(1).replaceAll(".*による攻撃力増加\\+|%", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0d;
    }
}
