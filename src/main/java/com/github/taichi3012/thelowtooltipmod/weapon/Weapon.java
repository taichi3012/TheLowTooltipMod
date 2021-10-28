package com.github.taichi3012.thelowtooltipmod.weapon;

import com.github.taichi3012.thelowtooltipmod.api.TheLowAPI;
import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.github.taichi3012.thelowtooltipmod.api.TheLowAPI.updatePlayerStatus;

public class Weapon {
    protected final WeaponData weaponData;

    public Weapon(WeaponData weaponData) {
        this.weaponData = weaponData;
    }
    public Weapon(ItemStack item) {
        this(new WeaponData(item));
    }

    public Map<ResultCategoryType, Double> getCategorizedDamage() {
        Map<ResultCategoryType, Double> result = new HashMap<>();
        JobType job = TheLowAPI.getPlayerJobByUUID(Minecraft.getMinecraft().thePlayer.getUniqueID().toString());
        //武器の種類がnullの場合は"剣"として処理する
        WeaponType weaponType = Objects.nonNull(weaponData.getWeaponType()) ? weaponData.getWeaponType() : WeaponType.SWORD;
        double parkGain = TheLowUtil.getParkGainByWeaponType(weaponType);
        double jobGain = job.getGainByWeaponType(weaponType);
        updatePlayerStatus(false);

        for (ResultCategoryType categoryType : ResultCategoryType.values()) {
            double resultDamage = weaponData.getDamage();

            resultDamage += weaponData.getSpecialDamage(categoryType);
            resultDamage *= weaponData.getMSMultiply(categoryType);
            resultDamage *= (100d + parkGain + jobGain) / 100d;

            result.put(categoryType, resultDamage);
        }
        return result;
    }

    public List<String> getContext() {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages = DamageCalcUtil.removeAllRedundancy(getCategorizedDamage());
        Comparator<ResultCategoryType> comparator = Comparator.comparingDouble(damages::get);

        damages.keySet().stream().sorted(comparator.reversed())
                .forEach(category -> {
                    double normalDmg = Math.round(damages.get(category) * 1000d) / 1000d;
                    double criticalDmg = Math.round(damages.get(category) * 1.15 * 1000d) / 1000d;

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§6+%s§c§o(+%s)", normalDmg, criticalDmg));
                });

        return result;
    }
}
