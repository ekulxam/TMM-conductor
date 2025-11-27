package survivalblock.tmm_conductor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import dev.doctor4t.trainmurdermystery.game.GameFunctions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.tmm_conductor.common.ConductorVoicechatPlugin;
import survivalblock.tmm_conductor.common.TMMConductor;
import survivalblock.tmm_conductor.common.cca.BroadcastWorldComponent;
import survivalblock.tmm_conductor.common.init.TMMConductorRoles;

@Mixin(value = GameFunctions.class, remap = false)
public class GameFunctionsMixin {

	@Inject(method= "initializeGame", at = @At("RETURN"), remap = true)
	private static void initConductor(ServerWorld serverWorld, CallbackInfo ci) {
        ConductorVoicechatPlugin.reset();
        BroadcastWorldComponent.KEY.get(serverWorld).reset();
        GameWorldComponent gameWorldComponent = GameWorldComponent.KEY.get(serverWorld);

        for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
            if (TMMConductorRoles.CONDUCTOR.equals(gameWorldComponent.getRole(serverPlayer))) {
                continue;
            }
            ConductorVoicechatPlugin.addReceiver(serverPlayer);
        }
    }

    @WrapMethod(method = "finalizeGame", remap = true)
    private static void resetHaunters(ServerWorld world, Operation<Void> original) {
        original.call(world);
        ConductorVoicechatPlugin.reset();
        BroadcastWorldComponent.KEY.get(world).reset();
    }

    @ModifyExpressionValue(method = "startGame", at = @At(value = "FIELD", target = "Ldev/doctor4t/trainmurdermystery/api/GameMode;minPlayerCount:I", opcode = Opcodes.GETFIELD), remap = true)
    private static int setToZeroIfDevelopment(int original) {
        if (TMMConductor.DEVELOPMENT) {
            return 0;
        }
        return original;
    }
}