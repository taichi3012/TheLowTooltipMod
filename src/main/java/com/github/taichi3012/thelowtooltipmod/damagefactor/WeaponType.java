package com.github.taichi3012.thelowtooltipmod.damagefactor;

public enum WeaponType {
    SWORD("剣"),
    BOW("弓"),
    MAGIC("魔法");

    private final String name;

    WeaponType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
