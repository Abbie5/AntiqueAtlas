package hunternif.mc.impl.atlas.mixin;

import hunternif.mc.impl.atlas.mixinhooks.EntityHooksAA;
import net.minecraft.entity.Entity;
import net.minecraft.world.dimension.PortalManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public class MixinEntity implements EntityHooksAA {
    @Shadow @Nullable public PortalManager portalManager;

    @Override
    public boolean antiqueAtlas_isInPortal() {
        return portalManager != null && portalManager.isInPortal();
    }
}
