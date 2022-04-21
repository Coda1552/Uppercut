package coda.uppercut;

import coda.uppercut.registry.UCItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Uppercut.MOD_ID)
public class Uppercut {
    public static final String MOD_ID = "uppercut";

    public Uppercut() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        UCItems.ITEMS.register(bus);
    }
}
