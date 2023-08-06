package com.saturnclient.saturnclient.mixin;

import com.saturnclient.saturnclient.Saturn;
import com.saturnclient.saturnclient.module.modules.render.Xray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

@Mixin(Block.class)
public abstract class BlockMixin implements ItemConvertible {

    @Inject(at = {
            @At("HEAD")
    }, method = {
            "shouldDrawSide(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/util/math/BlockPos;)Z"
    }, cancellable = true)
    private static void onShouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction direction,
                                         BlockPos blockPos, CallbackInfoReturnable < Boolean > cir) {
        if (Saturn.getInstance().getModuleManager().getModule("Xray").enabled) {
            boolean isXray = Xray.isXRayBlock(state.getBlock());
            cir.setReturnValue(isXray);
        }
    }

}