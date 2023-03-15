package com.github.taichi3012.thelowtooltipmod.weapon;

import java.util.List;
import java.util.Map;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;

public abstract class AbstractWeapon {

    public abstract List<String> generateResultContext();

    public abstract WeaponData getWeaponData();

    public abstract Map<ResultCategoryType, Double> generateCategorizedDamage(boolean includeUniqueSpecial);
}
