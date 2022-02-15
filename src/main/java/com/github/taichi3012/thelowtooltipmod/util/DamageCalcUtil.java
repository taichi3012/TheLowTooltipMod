package com.github.taichi3012.thelowtooltipmod.util;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.SpecialAttackable;
import com.github.taichi3012.thelowtooltipmod.damagefactor.UniqueSpecialType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.SpecialMagicStoneType;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;

import java.util.*;
import java.util.stream.Collectors;

public class DamageCalcUtil {

    /**
     * 冗長な要素を削除する
     * @param damages カテゴライズ済みダメージ
     */
    public static Map<ResultCategoryType, Double> removeAllRedundancy(Map<ResultCategoryType, Double> damages) {
        //素ダメージと同じダメージのカテゴリの削除
        damages.keySet()
                .removeIf(key -> key != ResultCategoryType.NO_SPECIAL_CATEGORY && Objects.equals(damages.get(key), damages.get(ResultCategoryType.NO_SPECIAL_CATEGORY)));

        Iterator<ResultCategoryType> iterator = damages.keySet().iterator();
        while (iterator.hasNext()) {
            ResultCategoryType category = iterator.next();

            boolean isRedundancy = damages.keySet().stream()
                    .anyMatch(key -> key != category && Objects.equals(damages.get(key), damages.get(category)) && getAvailableSpecial(category).containsAll(getAvailableSpecial(key)));

            if (isRedundancy) {
                iterator.remove();
            }
        }

        return damages;
    }

    private static List<SpecialAttackable> getAvailableSpecial(ResultCategoryType type) {
        List<SpecialAttackable> specials = new ArrayList<>();
        specials.addAll(Arrays.asList(UniqueSpecialType.values()));
        specials.addAll(Arrays.asList(SpecialMagicStoneType.values()));

        return specials.stream()
                .filter(sp -> sp.isAvailable(type))
                .collect(Collectors.toList());
    }

    /*
    private static void removeRedundancy(Map<ResultCategoryType, Double> damages, SpecialAttackable eqFactor, ResultCategoryType eqCategory) {
        //比べる基準となるカテゴリーが存在しない場合は返す
        if (!damages.containsKey(eqCategory)) {
            return;
        }

        damages.keySet()
                .removeIf(next -> !eqCategory.equals(next) && eqFactor.isAvailable(next) && Objects.equals(damages.get(eqCategory), damages.get(next)));
    }
     */

    public static double roundDamage(double damage) {
        return JavaUtil.digitRound(damage, 3.0d);
    }

    public static double roundCriticalDamage(double damage) {
        return roundDamage(damage * 1.15d);
    }

    public static ResultCategoryType getSpecifiedRankResultType(WeaponData weaponData, int rank) {
        Map<ResultCategoryType, Double> damages = DamageCalcUtil.removeAllRedundancy((new WeaponBasic(weaponData)).generateCategorizedDamage(true));
        Comparator<ResultCategoryType> comparator = Comparator.comparingDouble(damages::get);
        List<ResultCategoryType> rankedResultTypeList = damages.keySet().stream().sorted(comparator.reversed()).collect(Collectors.toList());

        if (rank < 1 || rankedResultTypeList.size() < rank) {
            return null;
        }

        return rankedResultTypeList.get(rank - 1);
    }
}