package mod.hc.revive_plus;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class HCReviveGUI {

    private static final TextRenderer textRenderer;

    private static PlayerEntity player;

    public static void setPlayer(PlayerEntity player) {
        HCReviveGUI.player = player;
    }

    static {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        textRenderer = minecraft.textRenderer;
    }

    private static float knocked_draw = -1;

    public static void drawKnocked(float ticks) {
        knocked_draw = ticks;
    }

    public static void hideKnocked() {
        knocked_draw = -1;
    }

    public static void render(DrawContext context, float ticks) {

        if (knocked_draw > 0) {
            MatrixStack stack = context.getMatrices();
            stack.push();
            stack.scale(2,2,2);
            MutableText downedText = Text.translatable("hc_revive_plus.downed");
            MutableText downedSubText = Text.translatable("hc_revive_plus.downed_sub");
            int width = textRenderer.getWidth(downedText);
            int tW = textRenderer.getWidth(downedSubText);
            context.drawText(textRenderer, downedText, (context.getScaledWindowWidth()-width*2)/4,context.getScaledWindowHeight()/4 - 20,0xff0000,true);
            stack.pop();
            context.drawText(textRenderer, downedSubText, (context.getScaledWindowWidth()-tW)/2, context.getScaledWindowHeight()/2 - 70, 0xff0000, true);
            knocked_draw -= ticks;
        }

    }
}
