package com.github.taichi3012.thelowtooltipmod.util;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.SpecialDamageAvailable;
import com.github.taichi3012.thelowtooltipmod.damagefactor.UniqueSpecialType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.SpecialMagicStoneType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DamageCalcUtil {

    /**
     * 冗長な要素を削除する
     * @param damages カテゴライズ済みダメージ
     */
    public static Map<ResultCategoryType, Double> removeAllRedundancy(Map<ResultCategoryType, Double> damages) {
        //素ダメージとダメージが同じカテゴリーを削除
        List<ResultCategoryType> removeCategory = new ArrayList<>();
        damages.keySet().stream()
                .filter(category -> category != ResultCategoryType.NO_SPECIAL_CATEGORY)
                .filter(category -> Objects.equals(damages.get(ResultCategoryType.NO_SPECIAL_CATEGORY), damages.get(category)))
                .forEach(removeCategory::add);
        removeCategory.forEach(damages::remove);

        //ダメージがかぶっているカテゴリーを削除
        removeRedundancy(damages, UniqueSpecialType.UNDEAD_SPECIAL_DAMAGE, ResultCategoryType.UNDEAD_CATEGORY);
        removeRedundancy(damages, UniqueSpecialType.INSECT_SPECIAL_DAMAGE, ResultCategoryType.INSECT_CATEGORY);
        removeRedundancy(damages, SpecialMagicStoneType.ZOMBIE_MAGIC_STONE, ResultCategoryType.ZOMBIE_CATEGORY);
        removeRedundancy(damages, SpecialMagicStoneType.LIVING_MAGIC_STONE, ResultCategoryType.LIVING_CATEGORY);

        return damages;
    }

    private static void removeRedundancy(Map<ResultCategoryType, Double> damages, SpecialDamageAvailable eqAvailable, ResultCategoryType eqCategory) {
        List<ResultCategoryType> removeCategory = new ArrayList<>();

        //比べる基準となるカテゴリーが存在しない場合は返す
        if (!damages.containsKey(eqCategory)) return;

        damages.keySet().stream()
                .filter(type -> type != eqCategory)
                .filter(eqAvailable::isAvailable)
                .filter(category -> Objects.equals(damages.get(eqCategory), damages.get(category)))
                .forEach(removeCategory::add);

        //直接削除処理をすると例外が発生するため削除する要素をリスト化してから削除
        removeCategory.forEach(damages::remove);
    }

    public static double getRoundDamage(double damage) {
        return Math.round(damage * 1000d) / 1000d;
    }

    public static double getCriticalDamage(double damage) {
        return getRoundDamage(damage * 1.15);
    }

}
