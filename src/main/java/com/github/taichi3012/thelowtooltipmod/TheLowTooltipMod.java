package com.github.taichi3012.thelowtooltipmod;

import com.github.taichi3012.thelowtooltipmod.api.TheLowAPI;
import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.gui.UpdatePark;
import com.github.taichi3012.thelowtooltipmod.weapon.TooltipChanger;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = TheLowTooltipMod.MOD_ID, version = TheLowTooltipMod.VERSION, name = TheLowTooltipMod.MOD_NAME, guiFactory = "com.github.taichi3012.thelowtooltipmod.gui.TheLowTooltipModGuiFactory")
public class TheLowTooltipMod {
    public static final String MOD_ID = "thelowtooltipmod";
    public static final String VERSION = "1.0.0";
    public static final String MOD_NAME = "TheLowTooltipMod";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        TheLowTooltipModConfig.loadConfig(event);
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new UpdatePark());
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        try {
            if (TheLowAPI.processResponse(event))
                event.setCanceled(true);
        } catch (RuntimeException e) {
            logger.warn("APIの解析に失敗しました。");
            e.printStackTrace();
        }

        if (event.message.getUnformattedText().matches("職業「.*」を選択しました。")) TheLowAPI.updatePlayerStatus(true);
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(TheLowTooltipMod.MOD_ID))
            TheLowTooltipModConfig.syncConfig();
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (TheLowTooltipModConfig.isTooltipEnable && GuiScreen.isCtrlKeyDown()) {
            TooltipChanger.addResult(event);
        }
    }
}
