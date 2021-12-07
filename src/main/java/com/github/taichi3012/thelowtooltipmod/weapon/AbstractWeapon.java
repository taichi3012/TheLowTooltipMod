package com.github.taichi3012.thelowtooltipmod.weapon;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;

import java.util.List;
import java.util.Map;

abstract class AbstractWeapon {

    abstract List<String> generateResultContext();

    abstract WeaponData getWeaponData();

    abstract Map<ResultCategoryType, Double> generateCategorizedDamage(boolean includeUniqueSpecial);
}
