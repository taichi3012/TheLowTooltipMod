package com.github.taichi3012.thelowtooltipmod.weapon;

import com.github.taichi3012.thelowtooltipmod.util.TheLowNBTUtil;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class TooltipChanger {
    public static void addResult(ItemTooltipEvent event) {
        if (TheLowNBTUtil.hasDamage(event.itemStack)) {
            int cursorIndex = 0;
            //ダメージ表示の始まる行を決める。スロットに表記があればそれの1つ上に決める。
            for (String str : event.toolTip) {
                if (str.contains("[SLOT]")) {
                    cursorIndex--;
                    if (cursorIndex < 0) cursorIndex = 1;
                    break;
                }
                cursorIndex++;
            }

            Weapon weapon = new Weapon(event.itemStack);
            event.toolTip.add(++cursorIndex, "§4§l[ダメージ]");
            for (String str : weapon.getContext()) {
                event.toolTip.add(++cursorIndex, "§a" + str);
            }
            event.toolTip.add(++cursorIndex, "");
        }
    }
}
