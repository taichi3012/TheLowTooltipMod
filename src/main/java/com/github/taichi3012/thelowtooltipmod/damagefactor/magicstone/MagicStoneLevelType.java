package com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone;

public enum MagicStoneLevelType {
    LEVEL1(1.1, 0.95),
    LEVEL2(1.15, 0.9),
    LEVEL3(1.23, 0.84),
    LEVEL4(1.35, 0.77),
    LEVEL4_5(1.4, 0.72),
    LEVEL5(1.55, 0.6),
    LEGEND(1.55, 0.6),
    UNKNOWN_LEVEL(1d, 1d);

    private final double damageMultiply;
    private final double coolTimeMultiply;

    MagicStoneLevelType(double damageMultiply, double coolTimeMultiply) {
        this.damageMultiply = damageMultiply;
        this.coolTimeMultiply = coolTimeMultiply;
    }

    public static MagicStoneLevelType getLevelByID(String id) {
        try {
            return valueOf(id);
        } catch (IllegalArgumentException e) {
            return UNKNOWN_LEVEL;
        }
    }

    public double getDamageMultiply() {
        return damageMultiply;
    }

    public double getCoolTimeMultiply() {
        return coolTimeMultiply;
    }
}
