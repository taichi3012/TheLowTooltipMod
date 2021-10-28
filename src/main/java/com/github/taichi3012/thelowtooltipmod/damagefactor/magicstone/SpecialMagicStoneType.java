package com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.SpecialDamageAvailable;

import java.util.function.Predicate;

public enum SpecialMagicStoneType implements MagicStone, SpecialDamageAvailable {
    SKELETON_MAGIC_STONE("スケルトン特攻", "skeleton_attack", c -> c == ResultCategoryType.SKELETON_CATEGORY),
    ZOMBIE_MAGIC_STONE("ゾンビ特攻", "zombie_attack", SpecialMagicStoneType::isZombie),
    LIVING_MAGIC_STONE("生物特攻", "living_attack", SpecialMagicStoneType::isLiving);

    private final String name;
    private final String id;
    private final Predicate<ResultCategoryType> isAvailable;

    SpecialMagicStoneType(String name, String id, Predicate<ResultCategoryType> availableCategory) {
        this.name = name;
        this.id = id;
        this.isAvailable = availableCategory;
    }

    private static boolean isZombie(ResultCategoryType categoryType) {
        switch (categoryType) {
            case ZOMBIE_CATEGORY:
            case PIG_ZOMBIE_CATEGORY:
                return true;
            default:
                return false;
        }
    }

    private static boolean isLiving(ResultCategoryType categoryType) {
        switch (categoryType) {
            case SPIDER_CATEGORY:
            case INSECT_CATEGORY:
            case ANIMAL_CATEGORY:
            case LIVING_CATEGORY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getID() {
        return id;
    }

    public Predicate<ResultCategoryType> getIsAvailable() {
        return isAvailable;
    }

    public boolean isAvailable(ResultCategoryType type) {
        return isAvailable.test(type);
    }
}
