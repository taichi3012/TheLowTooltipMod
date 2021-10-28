package com.github.taichi3012.thelowtooltipmod.damagefactor;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;

import java.util.function.Supplier;

public enum WeaponType {
    SWORD("剣", () -> TheLowTooltipModConfig.overStrengthSwordValue),
    BOW("弓", () -> TheLowTooltipModConfig.overStrengthBowValue),
    MAGIC("魔法", () -> TheLowTooltipModConfig.overStrengthMagicValue);

    private final String name;
    private final Supplier<Double> overStrength;

    WeaponType(String name, Supplier<Double> os) {
        this.name = name;
        this.overStrength = os;
    }

    public String getName() {
        return name;
    }

    public double getOverStrength() {
        return overStrength.get();
    }
}
