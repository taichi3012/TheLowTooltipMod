package com.github.taichi3012.thelowtooltipmod.damagefactor;

import java.util.function.Function;

public enum JobType {
    NOVICE("ノービス", 0.0, 0.0, 0.0),
    SOLDIER("ソルジャー", JobType::getSoldierGain, i -> -2.0, i -> -2.0),
    ARCHER("アーチャー", -2.0, 5.0, -2.0),
    MAGICIAN("マジシャン", -2.0, -2.0, 5.0),
    WARRIOR("ウォーリア", JobType::getWarriorGain, i -> -5.0, i -> -5.0),
    BOW_MAN("ボウマン", -5.0, 10.0, -5.0),
    MAGE("メイジ", -5.0, -5.0, 10.0),
    RONIN("ロウニン", -4.0, -4.0, -4.0),
    DRAGON_KILLER("ドラゴンキラー", -2.0, 5.0, -2.0),
    PRIEST("プリースト", -10.0, -10.0, -10.0),
    SKIRMISHER("スカーミッシャー", 5.0, 0.0, 0.0),
    HAGURE_MONO("ハグレモノ", -7.0, -7.0, -7.0),
    RUNE_CASTER("ルーンキャスター", -7.0,-7.0, 7.0),
    SPELUNKER("スペランカー", 10.0, 10.0, 10.0),
    ALEISTER("アレイスター", -5.0, 0.0, 0.0),
    ARTHUR("アーサー", 5.0, -5.0, 5.0),
    SKULL_PIERCER("スカルピアサー", 0.0, 0.0, 0.0),
    DARK_BLASTER("ダークブラスター", 0.0, 0.0, 0.0),
    SEEKER("シーカー", -4.0, 5.0, -4.0),
    BUTTERFLY_SEEKER("バタフライシーカー", -7.0, 10.0, -7.0),
    UNKNOWN_JOB("未知の職業", 0.0, 0.0, 0.0);

    private final String name;
    private final Function<Integer, Double> swordFunc;
    private final Function<Integer, Double> bowFunc;
    private final Function<Integer, Double> magicFunc;

    JobType(String name, Function<Integer, Double> swordFunc, Function<Integer, Double> bowFunc, Function<Integer, Double> magicFunc) {
        this.name = name;
        this.swordFunc = swordFunc;
        this.bowFunc = bowFunc;
        this.magicFunc = magicFunc;
    }

    JobType(String name, double swordGain, double bowGain, double magicGain) {
        this(name, i -> swordGain, i -> bowGain, i -> magicGain);
    }

    public String getName() {
        return name;
    }

    public double getSwordGain(int lv) {
        return swordFunc.apply(lv);
    }

    public double getBowGain(int lv) {
        return bowFunc.apply(lv);
    }

    public double getMagicGain(int lv) {
        return magicFunc.apply(lv);
    }

    public double getGainByWeaponType(WeaponType type, int lv) {
        switch (type) {
            case SWORD:
                return getSwordGain(lv);
            case BOW:
                return getBowGain(lv);
            case MAGIC:
                return getMagicGain(lv);
            default:
                return 0.0d;
        }
    }

    public static JobType getJobByName(String name) {
        for (JobType job : values()) {
            if (job.getName().equals(name)) {
                return job;
            }
        }
        return UNKNOWN_JOB;
    }

    private static double getSoldierGain(int level) {
        return level < 60 ? 10.0 : 5.0;
    }

    private static double getWarriorGain(int level) {
        return level < 60 ? 15.0 : 10.0;
    }
}
