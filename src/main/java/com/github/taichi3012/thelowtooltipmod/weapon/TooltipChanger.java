package com.github.taichi3012.thelowtooltipmod.weapon;

import com.github.taichi3012.thelowtooltipmod.util.TheLowNBTUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class TooltipChanger {
    public static void addResult(ItemTooltipEvent event) {
        if (TheLowNBTUtil.hasDamage(event.itemStack)) {
            Weapon weapon;
            ItemStack item = event.itemStack;
            int cursorIndex = event.showAdvancedItemTooltips ? event.toolTip.size() - 2 : event.toolTip.size();

            for (String str : event.toolTip) {
                if (str.contains("[SLOT]")) {
                    cursorIndex = event.toolTip.indexOf(str);
                    break;
                }
            }

            if (TheLowUtil.isMagicMixtureWeapon(event.itemStack)) {
                weapon = new MagicalMixtureWeapon(item);
            } else {
                weapon = new Weapon(item);
            }

            event.toolTip.add(cursorIndex++, "§4§l[ダメージ]");
            for (String str : weapon.getContext()) {
                event.toolTip.add(cursorIndex++, "§a" + str);
            }
            event.toolTip.add(cursorIndex, "");
        }
    }
}
