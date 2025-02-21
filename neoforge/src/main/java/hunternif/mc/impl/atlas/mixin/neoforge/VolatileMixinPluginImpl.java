package hunternif.mc.impl.atlas.mixin.neoforge;


import net.neoforged.fml.loading.FMLEnvironment;

public class VolatileMixinPluginImpl
{
    public static boolean isDevelopmentEnvironment() {
        return !FMLEnvironment.production;
    }
}