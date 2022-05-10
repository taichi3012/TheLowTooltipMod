package com.github.taichi3012.thelowtooltipmod;

import com.github.taichi3012.thelowtooltipmod.api.TheLowAPI;
import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.listener.GuiParkSelectorListener;
import com.github.taichi3012.thelowtooltipmod.listener.TooltipListener;
import com.github.taichi3012.thelowtooltipmod.listener.WebCalcURLGenerateListener;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.SkillManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

@Mod(modid = TheLowTooltipMod.MOD_ID, version = TheLowTooltipMod.VERSION, name = TheLowTooltipMod.MOD_NAME, guiFactory = "com.github.taichi3012.thelowtooltipmod.gui.TheLowTooltipModGuiFactory")
public class TheLowTooltipMod {
    public static final String MOD_ID = "thelowtooltipmod";
    public static final String VERSION = "1.2.1";
    public static final String MOD_NAME = "TheLowTooltipMod";

    public static Logger logger;

    public static KeyBinding keyCopyWebCalculationURL = new KeyBinding("Webのダメージ計算器のURLをコピーする", Keyboard.KEY_G, MOD_NAME);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        TheLowTooltipModConfig.loadConfig(event);
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new GuiParkSelectorListener());
        MinecraftForge.EVENT_BUS.register(new TooltipListener());
        MinecraftForge.EVENT_BUS.register(new WebCalcURLGenerateListener());

        ClientRegistry.registerKeyBinding(keyCopyWebCalculationURL);

        SkillManager.registerAll();
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        try {
            if (TheLowAPI.processResponse(event)) {
                event.setCanceled(true);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        if (event.message.getUnformattedText().matches("職業「.*」を選択しました。")) {
            TheLowAPI.requestPlayerStatus(true);
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(TheLowTooltipMod.MOD_ID)) {
            TheLowTooltipModConfig.syncConfig();
        }
    }
}