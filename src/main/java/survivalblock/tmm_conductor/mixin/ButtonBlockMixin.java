package survivalblock.tmm_conductor.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.block.Block;
import net.minecraft.block.ButtonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.tmm_conductor.common.block.BroadcastButtonBlock;
import survivalblock.tmm_conductor.common.cca.BroadcastWorldComponent;

@Mixin(ButtonBlock.class)
public class ButtonBlockMixin {

    @WrapWithCondition(method = {"powerOn", "tryPowerWithProjectiles"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;scheduleBlockTick(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;I)V"))
    private boolean noSchedule(World instance, BlockPos blockPos, Block block, int delay) {
        if (block instanceof BroadcastButtonBlock) {
            return BroadcastWorldComponent.KEY.get(instance).isOnCooldown();
        }
        return true;
    }
}
