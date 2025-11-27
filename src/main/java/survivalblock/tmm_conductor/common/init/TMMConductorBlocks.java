package survivalblock.tmm_conductor.common.init;

import dev.doctor4t.ratatouille.util.registrar.BlockRegistrar;
import dev.doctor4t.trainmurdermystery.TMM;
import dev.doctor4t.trainmurdermystery.index.TMMBlocks;
import dev.doctor4t.trainmurdermystery.index.TMMItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import org.jetbrains.annotations.ApiStatus;
import survivalblock.tmm_conductor.common.block.BroadcastButtonBlock;

@SuppressWarnings("unchecked")
@ApiStatus.NonExtendable
public interface TMMConductorBlocks {

    BlockRegistrar registrar = new BlockRegistrar(TMM.MOD_ID);

    Block BROADCAST_BUTTON = registrar.createWithItem("broadcast_button", new BroadcastButtonBlock(AbstractBlock.Settings.copy(TMMBlocks.ELEVATOR_BUTTON)), TMMItems.DECORATION_GROUP);

    static void init() {
        registrar.registerEntries();
    }
}
