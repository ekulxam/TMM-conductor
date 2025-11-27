package survivalblock.tmm_conductor.common.init;

import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;
import survivalblock.tmm_conductor.common.cca.BroadcastWorldComponent;

public class TMMConductorWorldComponents implements WorldComponentInitializer {

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(BroadcastWorldComponent.KEY, BroadcastWorldComponent::new);
    }
}
