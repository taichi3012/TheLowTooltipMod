package com.github.taichi3012.thelowtooltipmod.damagefactor;

public enum JobType {
    NOVICE("ノービス", 0.0, 0.0, 0.0),
    SOLDIER("ソルジャー", 5.0, -2.0, -2.0),
    ARCHER("アーチャー", -2.0, 5.0, -2.0),
    MAGICIAN("マジシャン", -2.0, -2.0, 5.0),
    WARRIOR("ウォーリア", 10.0, -5.0, -5.0),
    BOW_MAN("ボウマン", -5.0, 10.0, -5.0),
    MAGE("メイジ", -5.0, -5.0, 10.0),
    RONIN("ロウニン", -4.0, -4.0, -4.0),
    DRAGON_KILLER("ドラゴンキラー", -2.0, 5.0, -2.0),
    PRIEST("プリースト", -10.0, -10.0, -10.0),
    SKIRMISHER("スカーミッシャー", 5.0, 0.0, 0.0),
    HAGURE_MONO("ハグレモノ", -7.0, -7.0, -7.0),
    RUNE_CASTER("ルーンキャスター", -7.0,-7.0, 7.0),
    SPELUNKER("スペランカー", 10.0, 10.0, 10.0),
    ARTHUR("アーサー", 5.0, 0.0, 0.0),
    SEEKER("シーカー", -7.0, 10.0, -7.0),
    UNKNOWN_JOB("未知の職業", 0.0, 0.0, 0.0);

    private final String name;
    private final double swordGain;
    private final double bowGain;
    private final double magicGain;

    JobType(String name, double swordGain, double bowGain, double magicGain) {
        this.name = name;
        this.swordGain = swordGain;
        this.bowGain = bowGain;
        this.magicGain = magicGain;
    }

    public String getName() {
        return name;
    }

    public double getSwordGain() {
        return swordGain;
    }

    public double getBowGain() {
        return bowGain;
    }

    public double getMagicGain() {
        return magicGain;
    }

    public double getGainByWeaponType(WeaponType type) {
        switch (type) {
            case SWORD:
                return getSwordGain();
            case BOW:
                return getBowGain();
            case MAGIC:
                return getMagicGain();
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

}
