package rus.iglo.MirseEngine.Engine.Dialog;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class Dialog extends Screen {
    private final Rest[] rests;
    private final String title;

    public Dialog(String title, Rest[] rests) {
        super(Component.nullToEmpty(title));
        this.title = title;
        this.rests = rests;
    }

    @Override
    protected void init() {
        int buttonHeight = 20;
        int y = (this.height - (buttonHeight * rests.length)) / 2;

        int maxButtonWidth = this.font.width(this.title) + 20;

        for (int i = 0; i < rests.length; i++) {
            Rest rest = rests[i];

            int x = (this.width - maxButtonWidth) / 2;

            this.addRenderableWidget(new Button(x, y + i * buttonHeight, maxButtonWidth, buttonHeight, Component.literal(rest.getLabel()),
                    (button) -> {
                        this.minecraft.setScreen(null);
                        rest.getAction().execute();
                    }));
        }
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);

        int titleWidth = this.font.width(this.title);

        int buttonHeight = 20;
        int y = (this.height - (buttonHeight * rests.length)) / 2;
        int titleX = (this.width - titleWidth) / 2;
        int titleY = y - 20;

        this.font.draw(poseStack, this.title, titleX, titleY, 0xFFFFFF);
    }

    public static void open(Dialog dialog) {
        Minecraft.getInstance().submit(() -> Minecraft.getInstance().setScreen(dialog));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
