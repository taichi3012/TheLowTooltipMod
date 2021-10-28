package com.github.taichi3012.thelowtooltipmod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ItemNBTUtil {
    public static List<String> getItemLore(ItemStack item) {
        List<String> result = new ArrayList<>();

        NBTTagCompound tags = item.getTagCompound();
        if (tags == null) return null;
        NBTTagCompound display = tags.getCompoundTag("display");
        if (display == null) return null;
        NBTTagList lore = display.getTagList("Lore", Constants.NBT.TAG_STRING);
        if (lore == null) return null;

        for (int i = 0; i < lore.tagCount(); i++ )
            result.add(lore.getStringTagAt(i));

        return result;
    }
}
