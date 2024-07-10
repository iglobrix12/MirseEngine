package rus.iglo.MirseEngine.Engine.Dialog;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class DialogGUI extends Screen {
    private final Rest[] benches;
    private final String title;

    public DialogGUI(String title, Rest[] benches) {
        super(Component.nullToEmpty(title));
        this.title = title;
        this.benches = benches;
    }

    @Override
    protected void init() {
        int buttonWidth = 100;
        int buttonHeight = 20;
        int x = (this.width - buttonWidth) / 2;
        int y = (this.height - (buttonHeight * benches.length)) / 2;

        for (int i = 0; i < benches.length; i++) {
            Rest bench = benches[i];

            this.addRenderableWidget(new Button(x, y + i * buttonHeight, buttonWidth, buttonHeight,
                    Component.literal(bench.getLabel()),
                    (button) -> {
                        onClose();
                        bench.getAction().run();
                    }));
        }
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);

        int titleWidth = this.font.width(this.title);

        int buttonHeight = 20;
        int y = (this.height - (buttonHeight * benches.length)) / 2;
        int titleX = (this.width - titleWidth) / 2;
        int titleY = y - 20;

        this.font.draw(poseStack, this.title, titleX, titleY, 0xFFFFFF);
    }


    public static void open(DialogGUI dialog) {
        Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(dialog));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
