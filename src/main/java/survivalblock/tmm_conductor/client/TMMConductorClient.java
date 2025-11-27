package survivalblock.tmm_conductor.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import survivalblock.tmm_conductor.client.render.BroadcastButtonBlockEntityRenderer;
import survivalblock.tmm_conductor.common.init.TMMConductorBlockEntities;

public class TMMConductorClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(TMMConductorBlockEntities.BROADCAST_BUTTON, BroadcastButtonBlockEntityRenderer::new);
    }
}
