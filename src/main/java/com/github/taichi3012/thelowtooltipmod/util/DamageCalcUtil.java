package com.github.taichi3012.thelowtooltipmod.util;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.SpecialAttackable;
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

    private static void removeRedundancy(Map<ResultCategoryType, Double> damages, SpecialAttackable eqFactor, ResultCategoryType eqCategory) {
        //比べる基準となるカテゴリーが存在しない場合は返す
        if (!damages.containsKey(eqCategory)) {
            return;
        }

        damages.keySet().removeIf(next -> !eqCategory.equals(next) && eqFactor.isAvailable(next) && Objects.equals(damages.get(eqCategory), damages.get(next)));
    }

    public static double roundDamage(double damage) {
        return JavaUtil.digitRound(damage, 3.0d);
    }

    public static double roundCriticalDamage(double damage) {
        return roundDamage(damage * 1.15d);
    }
}
