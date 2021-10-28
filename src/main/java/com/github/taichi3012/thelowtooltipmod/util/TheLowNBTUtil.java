package com.github.taichi3012.thelowtooltipmod.util;

import com.github.taichi3012.thelowtooltipmod.damagefactor.UniqueSpecialType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import java.util.*;

public class TheLowNBTUtil {
    public static double getDamage(@Nonnull ItemStack item) {
        final String NBTKey = "thelow_item_damage";
        NBTTagCompound itemNBT = item.getTagCompound();

        if (itemNBT == null || !itemNBT.hasKey(NBTKey)) return -1d;
        return item.getTagCompound().getDouble(NBTKey);
    }

    public static boolean hasDamage(@Nonnull ItemStack item) {
        NBTTagCompound itemNBT = item.getTagCompound();

        return itemNBT != null && itemNBT.hasKey("thelow_item_damage");
    }

    public static Map<UniqueSpecialType, Double> getSpecialDamage(@Nonnull ItemStack item) {
        final String NBTTypeKey = "thelow_item_special_attack_type";
        final String NBTValueKey = "thelow_item_special_attack_value";
        NBTTagCompound itemNBT = item.getTagCompound();
        Map<UniqueSpecialType, Double> specialDamage = new HashMap<>();

        if (itemNBT == null) return null;

        if (itemNBT.hasKey(NBTTypeKey + "1") && itemNBT.hasKey(NBTValueKey + "1")) {
            specialDamage.put(UniqueSpecialType.getTypeByNBTID(itemNBT.getString(NBTTypeKey + "1")), itemNBT.getDouble(NBTValueKey + "1"));
        }

        if (itemNBT.hasKey(NBTTypeKey + "2") && itemNBT.hasKey(NBTValueKey + "2")) {
            specialDamage.put(UniqueSpecialType.getTypeByNBTID(itemNBT.getString(NBTTypeKey + "2")), itemNBT.getDouble(NBTValueKey + "2"));
        }

        if (specialDamage.isEmpty()) return null;
        return specialDamage;
    }

    public static String[] getMagicStoneToString(@Nonnull ItemStack item) {
        final String NBTKey = "thelow_item_slot_list";
        NBTTagCompound itemNBT = item.getTagCompound();

        if (itemNBT == null || itemNBT.getString(NBTKey).isEmpty()) return null;
        return itemNBT.getString(NBTKey).split(",");
    }

    public static List<MagicStoneData> getMagicStoneData(@Nonnull ItemStack item) {
        List<MagicStoneData> result = new ArrayList<>();
        String[] msArray = getMagicStoneToString(item);

        if (msArray == null) return null;

        Arrays.asList(msArray).forEach(str -> result.add(MagicStoneUtil.getDataByNBTValue(str)));

        if (result.size() == 0) return null;
        return result;
    }
}
