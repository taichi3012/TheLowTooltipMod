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

        if (itemNBT == null || !itemNBT.hasKey(NBTKey))
            return 0.0d;
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
        if (itemNBT == null)
            return 0;
        return itemNBT.getLong("thelow_item_seed_value");
    }

    public static Map<UniqueSpecialType, Double> getSpecialDamage(ItemStack stack) {
        final String NBTTypeKey = "thelow_item_special_attack_type";
        final String NBTValueKey = "thelow_item_special_attack_value";
        NBTTagCompound itemNBT = stack.getTagCompound();
        Map<UniqueSpecialType, Double> specialDamage = new HashMap<>();

        if (itemNBT == null)
            return null;

        if (itemNBT.hasKey(NBTTypeKey + "1") && itemNBT.hasKey(NBTValueKey + "1")) {
            specialDamage.put(UniqueSpecialType.getTypeByNBTID(itemNBT.getString(NBTTypeKey + "1")), itemNBT.getDouble(NBTValueKey + "1"));
        }

        if (itemNBT.hasKey(NBTTypeKey + "2") && itemNBT.hasKey(NBTValueKey + "2")) {
            specialDamage.put(UniqueSpecialType.getTypeByNBTID(itemNBT.getString(NBTTypeKey + "2")), itemNBT.getDouble(NBTValueKey + "2"));
        }

        if (specialDamage.isEmpty())
            return null;
        return specialDamage;
    }

    public static String[] getMagicStoneString(ItemStack stack) {
        final String NBTKey = "thelow_item_slot_list";
        NBTTagCompound itemNBT = stack.getTagCompound();

        if (itemNBT == null || itemNBT.getString(NBTKey).isEmpty())
            return null;
        return itemNBT.getString(NBTKey).split(",");
    }

    public static List<MagicStoneData> getMagicStoneData(ItemStack stack) {
        List<MagicStoneData> result = new ArrayList<>();
        String[] msArray = getMagicStoneString(stack);

        if (msArray == null)
            return null;

        Arrays.asList(msArray).forEach(str -> result.add(MagicStoneUtil.getDataByNBTValue(str)));

        if (result.size() == 0)
            return null;
        return result;
    }

    public static String getSkillSetID(ItemStack stack) {
        final String NBTKey = "thelow_item_weapon_skill_set_id";
        NBTTagCompound itemNBT = stack.getTagCompound();

        if (itemNBT == null || itemNBT.getString(NBTKey).isEmpty())
            return null;
        return itemNBT.getString(NBTKey);
    }

    public static String getTheLowID(ItemStack stack) {
        final String NBTKey = "thelow_item_id";
        NBTTagCompound itemNBT = stack.getTagCompound();

        if (itemNBT == null || itemNBT.getString(NBTKey).isEmpty())
            return null;
        return itemNBT.getString(NBTKey);
    }

    public static String[] getNormalSkillList(ItemStack stack) {
        final String NBTKey = "thelow_nbttag_weaponskilllist";
        NBTTagCompound itemNBT = stack.getTagCompound();

        if (itemNBT == null || itemNBT.getString(NBTKey).isEmpty())
            return null;
        return itemNBT.getString(NBTKey).split(",");
    }
}
