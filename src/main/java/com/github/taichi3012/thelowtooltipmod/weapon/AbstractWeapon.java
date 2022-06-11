package com.github.taichi3012.thelowtooltipmod.weapon;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public abstract class AbstractWeapon {

    public abstract @NotNull List<String> generateResultContext();

    public abstract WeaponData getWeaponData();

    public abstract @NotNull Map<ResultCategoryType, Double> generateCategorizedDamage(boolean includeUniqueSpecial);
}
