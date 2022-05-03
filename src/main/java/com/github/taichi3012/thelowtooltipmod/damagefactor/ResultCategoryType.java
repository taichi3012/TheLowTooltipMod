package com.github.taichi3012.thelowtooltipmod.damagefactor;

import net.minecraft.util.EnumChatFormatting;

public enum ResultCategoryType {
    SKELETON_CATEGORY("スケルトン", EnumChatFormatting.WHITE.toString()),
    SPIDER_CATEGORY("クモ", EnumChatFormatting.GRAY.toString()),
    ZOMBIE_CATEGORY("ゾンビ", EnumChatFormatting.LIGHT_PURPLE.toString()),
    PIG_ZOMBIE_CATEGORY("ピグ", EnumChatFormatting.RED.toString()),
    UNDEAD_CATEGORY("アンデット", EnumChatFormatting.DARK_GRAY.toString()),
    LIVING_CATEGORY("生物", EnumChatFormatting.GREEN.toString()),
    GUARDIAN_CATEGORY("ガーディアン", EnumChatFormatting.DARK_AQUA.toString()),
    IRON_GOLEM_CATEGORY("ゴーレム", EnumChatFormatting.GRAY.toString()),
    INSECT_CATEGORY("虫", EnumChatFormatting.DARK_GREEN.toString()),
    ANIMAL_CATEGORY("動物", EnumChatFormatting.GREEN.toString()),
    WITHER_CATEGORY("ウィザー", EnumChatFormatting.DARK_GRAY.toString()),
    GIANT_CATEGORY("ジャイアント", EnumChatFormatting.DARK_GREEN.toString()),
    NO_SPECIAL_CATEGORY("素ダメージ", EnumChatFormatting.AQUA.toString());

    private final String name;
    private final String displayColor;

    ResultCategoryType(String name, String displayColor) {
        this.name = name;
        this.displayColor = displayColor;
    }

    public String getDisplayColor() {
        return displayColor;
    }

    public String getName() {
        return name;
    }
}
