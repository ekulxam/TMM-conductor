package survivalblock.tmm_conductor.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.joml.Quaternionf;
import survivalblock.tmm_conductor.common.block.entity.BroadcastButtonBlockEntity;
import survivalblock.tmm_conductor.common.cca.BroadcastWorldComponent;

@SuppressWarnings("ClassCanBeRecord")
public class BroadcastButtonBlockEntityRenderer implements BlockEntityRenderer<BroadcastButtonBlockEntity> {

    protected final TextRenderer textRenderer;

    public BroadcastButtonBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.textRenderer = ctx.getTextRenderer();
	}

    @Override
    public void render(BroadcastButtonBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.multiply(this.getRotation(new Quaternionf()));
        matrices.translate(0, 1, 0);

        ClientWorld clientWorld = MinecraftClient.getInstance().world;
        final float lineHeight = 10;

        if (clientWorld != null) {
            BroadcastWorldComponent broadcast = BroadcastWorldComponent.KEY.get(clientWorld);
            int color = broadcast.getRenderColor();
            OrderedText orderedText = Text.literal(String.valueOf(broadcast.getTicksForRendering())).asOrderedText();
            float x = (float)(-this.textRenderer.getWidth(orderedText) / 2);
            float j = 4 * lineHeight / 2;
            this.textRenderer.draw(orderedText, x, -j, color, false, matrices.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.POLYGON_OFFSET, 0, 15728880);
        }

        matrices.pop();
    }

    protected static float getBackwardsYaw(Camera camera) {
        return camera.getYaw() - 180.0F;
    }

    protected Quaternionf getRotation(Quaternionf quaternionf) {
        Camera camera = MinecraftClient.getInstance().getEntityRenderDispatcher().camera;
        return quaternionf.rotationYXZ((float) (-Math.PI / 180.0) * getBackwardsYaw(camera), 0, 0.0F);
    }
}