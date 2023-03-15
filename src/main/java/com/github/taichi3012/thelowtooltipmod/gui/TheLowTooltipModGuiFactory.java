package com.github.taichi3012.thelowtooltipmod.gui;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

import com.github.taichi3012.thelowtooltipmod.TheLowTooltipMod;
import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;

public class TheLowTooltipModGuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraftInstance) {
    }


    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return TheLowTooltipModConfigGui.class;
    }


    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }


    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }


    public static class TheLowTooltipModConfigGui extends GuiConfig {
        public TheLowTooltipModConfigGui(GuiScreen parent) {
            super(
                    parent,
                    (new ConfigElement(TheLowTooltipModConfig.getConfig().getCategory(TheLowTooltipModConfig.GENERAL_CATEGORY))).getChildElements(),
                    TheLowTooltipMod.MOD_ID,
                    false,
                    false,
                    TheLowTooltipMod.MOD_NAME
            );
        }
    }
}
