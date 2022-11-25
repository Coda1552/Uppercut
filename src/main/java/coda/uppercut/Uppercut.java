package coda.uppercut;

import coda.uppercut.common.combo.PlayerCombo;
import coda.uppercut.common.combo.PlayerComboProvider;
import coda.uppercut.registry.UCItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Uppercut.MOD_ID)
public class Uppercut {
    public static final String MOD_ID = "uppercut";

    public Uppercut() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        UCItems.ITEMS.register(bus);

        forgeBus.addGenericListener(Entity.class, this::onAttachCapabilitiesPlayer);
        forgeBus.addListener(this::onPlayerCloned);
        bus.addListener(this::onRegisterCapabilities);
    }

    private void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !event.getObject().getCapability(PlayerComboProvider.PLAYER_COMBO).isPresent()) {
            event.addCapability(new ResourceLocation(MOD_ID, "properties"), new PlayerComboProvider());
        }
    }

    private void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerComboProvider.PLAYER_COMBO).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerComboProvider.PLAYER_COMBO).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    private void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerCombo.class);
    }
}
