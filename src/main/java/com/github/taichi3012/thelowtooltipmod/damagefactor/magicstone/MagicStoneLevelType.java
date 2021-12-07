package com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone;

public enum MagicStoneLevelType {
    LEVEL1(1.1d, 0.95d),
    LEVEL2(1.15d, 0.9d),
    LEVEL3(1.23d, 0.84d),
    LEVEL4(1.35d, 0.77d),
    LEVEL4_5(1.4d, 0.72d),
    LEVEL5(1.55d, 0.6d),
    LEGEND(1.55d, 0.6d),
    UNKNOWN_LEVEL(1.0d, 1.0d);

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
