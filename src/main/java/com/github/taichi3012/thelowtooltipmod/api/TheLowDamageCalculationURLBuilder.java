package com.github.taichi3012.thelowtooltipmod.api;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.MagicStoneData;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.MagicStoneLevelType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.SpecialMagicStoneType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import java.util.*;

public class TheLowDamageCalculationURLBuilder {
    public static final String URL_BASE = "taichi3012.github.io/ThelowDamageCalculation/";
    public static final String URL_SHORT_BASE = "taichi3012.github.io/tldc/";

    public double weaponDamage;
    public double specialDamage;
    public double jobGain;
    public double equipGain;
    public double parkGain;
    public int legendValue;
    public final Map<MagicStoneLevelType, Boolean> magicStones;
    public String skillId;

    public TheLowDamageCalculationURLBuilder() {
        this.weaponDamage = 0.0d;
        this.specialDamage = 0.0d;
        this.jobGain = 0.0d;
        this.equipGain = 0.0d;
        this.parkGain = 0.0d;
        this.legendValue = 0;
        this.magicStones = new HashMap<>();
        this.skillId = "";

        Arrays.stream(MagicStoneLevelType.values())
                .filter(lv -> !lv.equals(MagicStoneLevelType.UNKNOWN_LEVEL))
                .forEach(lv -> this.magicStones.put(lv, false));
    }

    public static TheLowDamageCalculationURLBuilder getInstance(WeaponData weaponData, int rank) {
        if (weaponData == null) {
            return new TheLowDamageCalculationURLBuilder();
        }

        WeaponType weaponType = weaponData.getWeaponType();

        if (weaponType == null) {
            weaponType = WeaponType.SWORD;
        }

        ResultCategoryType targetResultType = DamageCalcUtil.getSpecifiedRankResultType(weaponData, rank);

        if (targetResultType == null) {
            targetResultType = ResultCategoryType.NO_SPECIAL_CATEGORY;
        }

        TheLowDamageCalculationURLBuilder instance = new TheLowDamageCalculationURLBuilder();

        instance.weaponDamage = weaponData.getDamage();
        instance.specialDamage = weaponData.getSpecialDamage(targetResultType);

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        if (player != null) {
            instance.jobGain = TheLowAPI.getPlayerJobByUUID(player.getUniqueID().toString()).getGainByWeaponType(weaponType);
        }

        instance.equipGain = TheLowUtil.getEquipAttackGain(weaponType);
        instance.parkGain = TheLowTooltipModConfig.getOSParkGainByWeaponType(weaponType);
        instance.legendValue = MagicStoneUtil.getLegendValue(weaponData);

        ResultCategoryType finalResultType = targetResultType;
        weaponData.getMagicStoneList().stream()
                .filter(MagicStoneData::isSpecialType)
                .filter(msData -> ((SpecialMagicStoneType) msData.getMagicStone()).isAvailable(finalResultType))
                .forEach(msData -> instance.magicStones.put(msData.getLevel(), true));

        return instance;
    }

    public String getAppliedParamUrl() {
        String query = getQuery();

        return !Objects.equals(query, "") ? URL_BASE + "?" + query
                : "";
    }

    public String getAppliedParamShortUrl() {
        String query = getQuery();

        return !Objects.equals(query, "") ? URL_SHORT_BASE + "?" + query
                : "";
    }

    public String getQuery() {
        Map<String, String> params = new LinkedHashMap<>();

        //武器の素ダメージ
        params.put("wd", getAlignedFormat(DamageCalcUtil.roundDamage(this.weaponDamage)));

        //武器固有特攻
        if (this.specialDamage != 0.0d) {
            params.put("sd", getAlignedFormat(DamageCalcUtil.roundDamage(this.specialDamage)));
        }

        //パーク火力補正
        if (this.parkGain != 0.0d) {
            params.put("pg", getAlignedFormat(this.parkGain));
        }

        //職業火力補正
        if (this.jobGain != 0.0d) {
            params.put("jg", getAlignedFormat(this.jobGain));
        }

        //装備火力補正
        if (this.equipGain != 0.0d) {
            params.put("eg", getAlignedFormat(this.equipGain));
        }

        //レジェンド魔法石個数
        if (this.legendValue != 0) {
            params.put("ns", String.valueOf(this.legendValue));
        }

        //特攻魔法石
        StringBuilder msParamValue = new StringBuilder("000000");
        this.magicStones.keySet().stream()
                .filter(this.magicStones::get)
                .forEach(lv -> {
                    switch (lv) {
                        case LEVEL1:
                            msParamValue.setCharAt(0, '1');
                            break;
                        case LEVEL2:
                            msParamValue.setCharAt(1, '1');
                            break;
                        case LEVEL3:
                            msParamValue.setCharAt(2, '1');
                            break;
                        case LEVEL4:
                            msParamValue.setCharAt(3, '1');
                            break;
                        case LEVEL4_5:
                            msParamValue.setCharAt(4, '1');
                            break;
                        case LEVEL5:
                        case LEGEND:
                            msParamValue.setCharAt(5, '1');
                            break;
                    }
                });
        if (!msParamValue.toString().equals("000000")) {
            params.put("ms", msParamValue.toString());
        }

        //スキル
        if (!this.skillId.isEmpty()) {
            params.put("sk", this.skillId);
        }

        StringJoiner query = new StringJoiner("&");
        params.forEach((name, value) -> query.add(name + "=" + value));

        return query.toString();
    }

    private String getAlignedFormat(double value) {
        if (value % 1.0d == 0.0d) {
            return Integer.toString((int) value, 36);
        }

        String str = String.valueOf(value);
        int decDigit = str.substring(str.indexOf(".") + 1).length();

        return Long.toString(Math.round(value * Math.pow(10.0d, decDigit)), 36) + "E" + Integer.toString(-decDigit, 36);
    }
}