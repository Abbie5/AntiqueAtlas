package hunternif.mc.impl.atlas.mixin;

import hunternif.mc.impl.atlas.client.gui.ExportProgressOverlay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public class MixinInGameHud {
    @Inject(at = @At("TAIL"), method = "render")
    public void draw(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        ExportProgressOverlay.INSTANCE.draw(context, context.getScaledWindowWidth(), context.getScaledWindowHeight());
    }
}
