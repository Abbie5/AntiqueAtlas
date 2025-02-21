package hunternif.mc.impl.atlas.client.gui.neoforge;

import hunternif.mc.impl.atlas.AntiqueAtlasConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@OnlyIn(Dist.CLIENT)
public class AntiqueAtlasConfigMenu {
    public static void init() {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                () -> (mod, parent) -> AutoConfig.getConfigScreen(AntiqueAtlasConfig.class, parent).get()
        );
    }
}
