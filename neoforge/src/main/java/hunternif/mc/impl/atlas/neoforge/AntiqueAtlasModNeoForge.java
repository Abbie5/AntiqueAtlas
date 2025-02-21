package hunternif.mc.impl.atlas.neoforge;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import hunternif.mc.impl.atlas.AntiqueAtlasMod;
import hunternif.mc.impl.atlas.AntiqueAtlasModClient;
import hunternif.mc.impl.atlas.client.gui.neoforge.AntiqueAtlasConfigMenu;
import net.neoforged.fml.common.Mod;

@Mod(AntiqueAtlasMod.ID)
public class AntiqueAtlasModNeoForge
{
    public AntiqueAtlasModNeoForge()
    {
        // Submit our event bus to let architectury register our content on the right time
        // todo
//        EventBuses.registerModEventBus(AntiqueAtlasMod.ID,
//                FMLJavaModLoadingContext.get().getModEventBus());

        AntiqueAtlasMod.init();

        if (Platform.getEnvironment() == Env.CLIENT)
        {
            AntiqueAtlasModClient.init();
            AntiqueAtlasConfigMenu.init();
        }
    }
}
