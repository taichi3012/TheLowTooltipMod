package com.github.taichi3012.thelowtooltipmod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ItemNBTUtil {
    public static List<String> getItemLore(ItemStack stack) {
        List<String> result = new ArrayList<>();

        if (stack == null) {
            return result;
        }

        NBTTagCompound tags = stack.getTagCompound();

        if (tags == null) {
            return result;
        }

        NBTTagCompound display = tags.getCompoundTag("display");

        if (display == null) {
            return result;
        }

        NBTTagList lore = display.getTagList("Lore", Constants.NBT.TAG_STRING);

        if (lore == null) {
            return result;
        }

        for (int i = 0; i < lore.tagCount(); i++) {
            result.add(lore.getStringTagAt(i));
        }

        return result;
    }

    public static String getStringTag(ItemStack stack, String key) {
        NBTTagCompound nbtTagComp = stack.getTagCompound();

        if (nbtTagComp == null) {
            return "";
        }

        return nbtTagComp.getString(key);
    }
}
