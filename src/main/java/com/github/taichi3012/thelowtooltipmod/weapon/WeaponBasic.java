package com.github.taichi3012.thelowtooltipmod.weapon;

import java.util.*;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;

public class WeaponBasic extends AbstractWeapon {
    protected final WeaponData weaponData;

    public WeaponBasic(WeaponData weaponData) {
        this.weaponData = weaponData;
    }

    public WeaponBasic(ItemStack stack) {
        this(new WeaponData(stack));
    }

    @Override
    public WeaponData getWeaponData() {
        return weaponData;
    }

    //TODO ダメージを計算,保持するクラスを別途作成する
    public Map<ResultCategoryType, Double> generateCategorizedDamage(boolean isIncludeUniqueSpecial) {
        Map<ResultCategoryType, Double> result = new HashMap<>();
        //武器の種類がnullの場合は"剣"として処理する
        WeaponType weaponType = Objects.nonNull(weaponData.getWeaponType()) ? weaponData.getWeaponType() : WeaponType.SWORD;
        JobType job = TheLowUtil.getPlayerJobType();
        double abilityMultiply = 1.0d + TheLowUtil.getAttackMultiplyAbilityGain(weaponType) / 100.0d;
        double legendMultiply = Math.pow(1.06d, MagicStoneUtil.getLegendValue(weaponData));

        for (ResultCategoryType categoryType : ResultCategoryType.values()) {
            double resultDamage = weaponData.getDamage();

            resultDamage += isIncludeUniqueSpecial ? weaponData.getSpecialDamage(categoryType) : 0.0d;
            resultDamage *= weaponData.getMSMultiply(categoryType);
            resultDamage *= abilityMultiply;
            resultDamage *= legendMultiply;

            switch (categoryType) {
                case ZOMBIE_CATEGORY:
                    if (job.equals(JobType.SKULL_PIERCER))
                        resultDamage *= 0.9d;
                    break;
                case SKELETON_CATEGORY:
                    if (job.equals(JobType.DARK_BLASTER))
                        resultDamage *= 0.9d;
                    break;
            }

            result.put(categoryType, resultDamage);
        }
        return result;
    }

    @Override
    public List<String> generateResultContext() {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages = DamageCalcUtil.removeAllRedundancy(generateCategorizedDamage(true));
        Comparator<ResultCategoryType> comparator = Comparator.comparingDouble(damages::get);

        result.add("§4[ダメージ]");
        damages.keySet().stream().sorted(comparator.reversed())
                .forEach(category -> {
                    double normalDamage = damages.get(category);

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage), DamageCalcUtil.roundCriticalDamage(normalDamage)));
                });

        return result;
    }
}
