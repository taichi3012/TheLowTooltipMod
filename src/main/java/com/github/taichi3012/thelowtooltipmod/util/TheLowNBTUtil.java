package com.github.taichi3012.thelowtooltipmod.util;

import com.github.taichi3012.thelowtooltipmod.damagefactor.UniqueSpecialType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.MagicStoneData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;

public class TheLowNBTUtil {
    public static double getDamage(ItemStack stack) {
        final String NBTKey = "thelow_item_damage";
        NBTTagCompound itemNBT = stack.getTagCompound();

        if (itemNBT == null || !itemNBT.hasKey(NBTKey)) {
            return 0.0d;
        }

        return stack.getTagCompound().getDouble(NBTKey);
    }

    public static boolean hasDamage(ItemStack stack) {
        NBTTagCompound itemNBT = stack.getTagCompound();

        return itemNBT != null && itemNBT.hasKey("thelow_item_damage");
    }

    public static boolean hasSeed(ItemStack stack) {
        NBTTagCompound itemNBT = stack.getTagCompound();

        return itemNBT != null && itemNBT.hasKey("thelow_item_seed_value");
    }

    public static long getSeed(ItemStack stack) {
        NBTTagCompound itemNBT = stack.getTagCompound();
        if (itemNBT == null) {
            return 0L;
        }

        return itemNBT.getLong("thelow_item_seed_value");
    }

    public static Map<UniqueSpecialType, Double> getSpecialDamage(ItemStack stack) {
        final String NBTTypeKey = "thelow_item_special_attack_type";
        final String NBTValueKey = "thelow_item_special_attack_value";
        NBTTagCompound itemNBT = stack.getTagCompound();
        Map<UniqueSpecialType, Double> specialDamage = new HashMap<>();

        if (itemNBT == null) {
            return specialDamage;
        }

        for (int i = 1; i <= 2; i++) {
            if (!(itemNBT.hasKey(NBTTypeKey + i) && itemNBT.hasKey(NBTValueKey + i))) {
                continue;
            }

            specialDamage.put(
                    UniqueSpecialType.getTypeByNBTID(itemNBT.getString(NBTTypeKey + i)),
                    itemNBT.getDouble(NBTValueKey + i)
            );
        }

        return specialDamage;
    }

    public static String[] getMagicStoneString(ItemStack stack) {
        final String NBTKey = "thelow_item_slot_list";
        NBTTagCompound itemNBT = stack.getTagCompound();

        if (itemNBT == null || itemNBT.getString(NBTKey).isEmpty()) {
            return new String[0];
        }

        return itemNBT.getString(NBTKey).split(",");
    }

    public static List<MagicStoneData> getMagicStoneData(ItemStack stack) {
        List<MagicStoneData> result = new ArrayList<>();
        String[] msArray = getMagicStoneString(stack);

        Arrays.asList(msArray)
                .forEach(str -> result.add(MagicStoneUtil.getDataByNBTValue(str)));
        return result;
    }

    public static String getSkillSetID(ItemStack stack) {
        final String NBTKey = "thelow_item_weapon_skill_set_id";
        NBTTagCompound itemNBT = stack.getTagCompound();

        if (itemNBT == null || itemNBT.getString(NBTKey).isEmpty()) {
            return null;
        }

        return itemNBT.getString(NBTKey);
    }

    public static String getTheLowID(ItemStack stack) {
        final String NBTKey = "thelow_item_id";
        NBTTagCompound itemNBT = stack.getTagCompound();

        if (itemNBT == null || itemNBT.getString(NBTKey).isEmpty()) {
            return null;
        }

        return itemNBT.getString(NBTKey);
    }

    public static String[] getNormalSkillList(ItemStack stack) {
        final String NBTKey = "thelow_nbttag_weaponskilllist";
        NBTTagCompound itemNBT = stack.getTagCompound();

        if (itemNBT == null || itemNBT.getString(NBTKey).isEmpty()) {
            return new String[0];
        }

        return itemNBT.getString(NBTKey).split(",");
    }
}
