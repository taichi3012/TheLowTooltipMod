package com.github.taichi3012.thelowtooltipmod.weapon;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

abstract class AbstractWeapon {

    abstract @NotNull List<String> generateResultContext();

    abstract WeaponData getWeaponData();

    abstract @NotNull Map<ResultCategoryType, Double> generateCategorizedDamage(boolean includeUniqueSpecial);
}
