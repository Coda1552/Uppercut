package coda.uppercut.common;

import coda.uppercut.Uppercut;
import coda.uppercut.registry.UCItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Uppercut.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {

    @SubscribeEvent
    public static void attack(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getItemStack();
        Item heldItem = stack.getItem();
        Entity target = event.getTarget();
        Player player = event.getPlayer();

        if (heldItem == UCItems.BOXING_GLOVE.get()) {
            System.out.println(target);

            player.swing(event.getHand());
            player.attack(target);
        }
    }
}
