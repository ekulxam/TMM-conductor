package survivalblock.tmm_conductor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import dev.doctor4t.trainmurdermystery.cca.PlayerMoodComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.tmm_conductor.common.init.TMMConductorRoles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Mixin(value = PlayerMoodComponent.class, remap = false)
public class PlayerMoodComponentMixin {

    @Unique
    private boolean tmm_conductor$hasDoneAnnouncement = false;

    @Shadow
    @Final
    public Map<PlayerMoodComponent.Task, PlayerMoodComponent.TrainTask> tasks;

    @Shadow
    @Final
    private PlayerEntity player;

    @WrapOperation(method = "readFromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;contains(Ljava/lang/String;)Z"), remap = true)
    private boolean encodeProperly(NbtCompound instance, String key, Operation<Boolean> original) {
        if (instance.contains(key, NbtElement.STRING_TYPE)) {
            String maybe = instance.getString(key);
            if (maybe.equals("conductor")) {
                PlayerMoodComponent.Task typeEnum = TMMConductorRoles.BroadcastTask.taskType;
                this.tasks.put(typeEnum, typeEnum.setFunction.apply(instance));
            }
            return false;
        }
        return original.call(instance, key);
    }

    @Inject(method = "generateTask", at = @At(value = "INVOKE", target = "Ldev/doctor4t/trainmurdermystery/cca/PlayerMoodComponent$Task;values()[Ldev/doctor4t/trainmurdermystery/cca/PlayerMoodComponent$Task;"), cancellable = true)
    private void returnAnnouncementEarly(CallbackInfoReturnable<PlayerMoodComponent.TrainTask> cir) {
        if (GameWorldComponent.KEY.get(this.player.getWorld()).isRole(this.player, TMMConductorRoles.CONDUCTOR) && !this.tmm_conductor$hasDoneAnnouncement) {
            this.tmm_conductor$hasDoneAnnouncement = true;
            cir.setReturnValue(new TMMConductorRoles.BroadcastTask());
        }
    }

    @ModifyExpressionValue(method = "generateTask", at = @At(value = "INVOKE", target = "Ljava/util/Map;containsKey(Ljava/lang/Object;)Z"))
    private boolean neverFindNaturally(boolean original, @Local PlayerMoodComponent.Task task) {
        return original || Objects.equals(TMMConductorRoles.BroadcastTask.taskType, task);
    }

    @WrapMethod(method = "writeToNbt")
    private void addHasAnnounced(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup, Operation<Void> original) {
        original.call(tag, registryLookup);
        tag.putBoolean("tmm_conductor_hasDoneAnnouncement", this.tmm_conductor$hasDoneAnnouncement);
    }

    @WrapMethod(method = "readFromNbt")
    private void readHasAnnounced(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup, Operation<Void> original) {
        original.call(tag, registryLookup);
        if (tag.contains("tmm_conductor_hasDoneAnnouncement")) {
            this.tmm_conductor$hasDoneAnnouncement = tag.getBoolean("tmm_conductor_hasDoneAnnouncement");
        }
    }

    @WrapMethod(method = "reset")
    private void resetAnnouncementBool(Operation<Void> original) {
        original.call();
        this.tmm_conductor$hasDoneAnnouncement = false;
    }

    @Mixin(value = PlayerMoodComponent.Task.class, priority = 50000)
    public static class TaskMixin {

        @Shadow
        @Final
        private static PlayerMoodComponent.Task[] $VALUES;

        @SuppressWarnings("SameParameterValue")
        @Invoker("<init>")
        static PlayerMoodComponent.Task tmm_conductor$invokeInit(String name, int id, Function<NbtCompound, PlayerMoodComponent.TrainTask> function) {
            throw new UnsupportedOperationException();
        }

        static {
            List<PlayerMoodComponent.Task> list = new ArrayList<>(Arrays.asList($VALUES));
            int size = list.size();
            TMMConductorRoles.BroadcastTask.taskType = tmm_conductor$invokeInit("tmm_conductor$taskType", size, nbt -> new TMMConductorRoles.BroadcastTask());
            list.add(TMMConductorRoles.BroadcastTask.taskType);
            $VALUES = list.toArray(new PlayerMoodComponent.Task[size + 1]);
        }
    }
}
