package com.github.taichi3012.thelowtooltipmod.damagefactor;

import java.util.function.Predicate;

public enum UniqueSpecialType implements SpecialAttackable {
    ZOMBIE_SPECIAL_DAMAGE("ゾンビ特攻", c -> c == ResultCategoryType.ZOMBIE_CATEGORY),
    SKELETON_SPECIAL_DAMAGE("スケルトン特攻", c -> c == ResultCategoryType.SKELETON_CATEGORY),
    SPIDER_SPECIAL_DAMAGE("クモ特攻", c -> c == ResultCategoryType.SPIDER_CATEGORY),
    PIG_ZOMBIE_SPECIAL_DAMAGE("ピグ特攻", c -> c == ResultCategoryType.PIG_ZOMBIE_CATEGORY),
    IRON_GOLEM_SPECIAL_DAMAGE("ゴーレム特攻", c -> c == ResultCategoryType.IRON_GOLEM_CATEGORY),
    UNDEAD_SPECIAL_DAMAGE("アンデット特攻", UniqueSpecialType::isUndead),
    INSECT_SPECIAL_DAMAGE("虫特攻", UniqueSpecialType::isInsect),
    GUARDIAN_SPECIAL_DAMAGE("ガーディアン特攻", c -> c == ResultCategoryType.GUARDIAN_CATEGORY),
    ANIMAL_SPECIAL_DAMAGE("動物特攻", c -> c == ResultCategoryType.ANIMAL_CATEGORY),
    WITHER_SPECIAL_DAMAGE("ウィザー特攻", c -> c == ResultCategoryType.WITHER_CATEGORY),
    GIANT_SPECIAL_DAMAGE("ジャイアント特攻", c -> c == ResultCategoryType.GIANT_CATEGORY),
    UNKNOWN_SPECIAL_DAMAGE("Unknown", c -> false);

    private final String name;
    private final Predicate<ResultCategoryType> isAvailable;

    UniqueSpecialType(String name, Predicate<ResultCategoryType> availableCategory) {
        this.name = name;
        this.isAvailable = availableCategory;
    }

    private static boolean isUndead(ResultCategoryType categoryType) {
        switch (categoryType) {
            case SKELETON_CATEGORY:
            case ZOMBIE_CATEGORY:
            case PIG_ZOMBIE_CATEGORY:
            case WITHER_CATEGORY:
            case UNDEAD_CATEGORY:
                return true;
            default:
                return false;
        }
    }

    private static boolean isInsect(ResultCategoryType categoryType) {
        switch (categoryType) {
            case SPIDER_CATEGORY:
            case INSECT_CATEGORY:
                return true;
            default:
                return false;
        }
    }

    public static UniqueSpecialType getTypeByNBTID(String id) {
        switch (id) {
            case "ZOMBIE":
                return ZOMBIE_SPECIAL_DAMAGE;
            case "SKELETON":
                return SKELETON_SPECIAL_DAMAGE;
            case "SPIDER":
                return SPIDER_SPECIAL_DAMAGE;
            case "PIG_ZOMBIE":
                return PIG_ZOMBIE_SPECIAL_DAMAGE;
            case "IRON_GOLEM":
                return IRON_GOLEM_SPECIAL_DAMAGE;
            case "UNDEAD":
                return UNDEAD_SPECIAL_DAMAGE;
            case "INSECT":
                return INSECT_SPECIAL_DAMAGE;
            case "GUARDIAN":
                return GUARDIAN_SPECIAL_DAMAGE;
            case "ANIMAL":
                return ANIMAL_SPECIAL_DAMAGE;
            case "WITHER":
                return WITHER_SPECIAL_DAMAGE;
            case "GIANT":
                return GIANT_SPECIAL_DAMAGE;
            default:
                return UNKNOWN_SPECIAL_DAMAGE;
        }
    }

    public String getName() {
        return name;
    }

    public Predicate<ResultCategoryType> getIsAvailable() {
        return isAvailable;
    }

    public boolean isAvailable(ResultCategoryType type) {
        return getIsAvailable().test(type);
    }
}
