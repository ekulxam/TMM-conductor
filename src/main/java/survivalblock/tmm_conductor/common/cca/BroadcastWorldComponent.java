package survivalblock.tmm_conductor.common.cca;

import dev.doctor4t.trainmurdermystery.TMM;
import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;
import survivalblock.tmm_conductor.common.TMMConductor;

import java.util.UUID;

public class BroadcastWorldComponent implements ServerTickingComponent {

    public static final ComponentKey<BroadcastWorldComponent> KEY = ComponentRegistry.getOrCreate(TMMConductor.id("broadcast"), BroadcastWorldComponent.class);

    public static final int COOLDOWN_MULTIPLIER = 10;
    public static final int MAX_ANNOUNCEMENT_TICKS = 45 * 20;

    protected boolean broadcasting = true;
    protected int countdown = MAX_ANNOUNCEMENT_TICKS;
    protected int cooldown = 0;
    protected UUID announcerUuid = null;

    protected final World world;

    public BroadcastWorldComponent(World world) {
        this.world = world;
    }

    @Override
    public void serverTick() {
        if (broadcasting) {
            // decrement countdown if broadcasting, when countdown = 0, stop
            if (countdown > 0) {
                countdown--;
            } else {
                this.setBroadcasting(false);
                countdown = 0;
            }
        } else {
            // if not broadcasting, decrement cooldown
            if (cooldown > 0) {
                countdown--;
            } else {
                cooldown = 0;
            }
        }
    }

    public boolean isOnCooldown() {
        return this.cooldown > 0;
    }

    public void setBroadcasting(boolean broadcasting) {
        if (broadcasting) {
            if (this.isOnCooldown()) {
                return;
            }
            cooldown = 0;
        } else {
            cooldown = (MAX_ANNOUNCEMENT_TICKS - countdown) * COOLDOWN_MULTIPLIER;
            countdown = 0;
        }

        this.broadcasting = broadcasting;
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {

    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {

    }

    public void setAnnouncerUuid(UUID uuid) {
        this.announcerUuid = uuid;
    }

    public UUID getAnnouncerUuid() {
        return this.announcerUuid;
    }
}
