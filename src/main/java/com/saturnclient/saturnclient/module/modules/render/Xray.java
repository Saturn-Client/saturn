package com.saturnclient.saturnclient.module.modules.render;

import com.saturnclient.saturnclient.Saturn;
import com.saturnclient.saturnclient.interfaces.ISimpleOption;
import com.saturnclient.saturnclient.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;

public class Xray extends Module {
    public static ArrayList < Block > blocks = new ArrayList < Block > ();

    public Xray() {
        super("Xray", "See ores through the ground", GLFW.GLFW_KEY_UNKNOWN, Category.RENDER);
        initXRay();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.worldRenderer.reload();
        if (Saturn.getInstance().getModuleManager().isModuleEnabled("Fullbright")) return; //To make sure that it doesn't set the GAMMA to low when fullbright is enabled.
        ISimpleOption < Double > gamma =
                (ISimpleOption < Double > )(Object) mc.options.getGamma();
        gamma.forceSetValue(1.0);
        mc.worldRenderer.reload();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.worldRenderer.reload();
        @SuppressWarnings("unchecked")
        ISimpleOption < Double > gamma =
                (ISimpleOption < Double > )(Object) mc.options.getGamma();
        gamma.forceSetValue(10000.0);
    }

    public void initXRay() {
        blocks.add(Blocks.EMERALD_ORE);
        blocks.add(Blocks.EMERALD_BLOCK);
        blocks.add(Blocks.DIAMOND_ORE);
        blocks.add(Blocks.DIAMOND_BLOCK);
        blocks.add(Blocks.GOLD_ORE);
        blocks.add(Blocks.GOLD_BLOCK);
        blocks.add(Blocks.IRON_ORE);
        blocks.add(Blocks.IRON_BLOCK);
        blocks.add(Blocks.COAL_ORE);
        blocks.add(Blocks.COAL_BLOCK);
        blocks.add(Blocks.REDSTONE_BLOCK);
        blocks.add(Blocks.REDSTONE_ORE);
        blocks.add(Blocks.LAPIS_ORE);
        blocks.add(Blocks.LAPIS_BLOCK);
        blocks.add(Blocks.NETHER_QUARTZ_ORE);
        blocks.add(Blocks.MOSSY_COBBLESTONE);
        blocks.add(Blocks.COBBLESTONE);
        blocks.add(Blocks.STONE_BRICKS);
        blocks.add(Blocks.OAK_PLANKS);
        blocks.add(Blocks.DEEPSLATE_EMERALD_ORE);
        blocks.add(Blocks.DEEPSLATE_DIAMOND_ORE);
        blocks.add(Blocks.DEEPSLATE_GOLD_ORE);
        blocks.add(Blocks.DEEPSLATE_IRON_ORE);
        blocks.add(Blocks.DEEPSLATE_COAL_ORE);
    }

    public static boolean isXRayBlock(Block b) {
        if (Xray.blocks.contains(b)) {
            return true;
        }
        return false;
    }

}