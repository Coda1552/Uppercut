package coda.uppercut.common;

import coda.uppercut.Uppercut;
import coda.uppercut.registry.UCItems;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Uppercut.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {

    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();

        if (event.getTarget() instanceof LivingEntity target && target.getLastHurtByMob() != null && target.getLastHurtByMob().is(player) && player.getPersistentData().getInt("Clicks") > 0 && player.getItemBySlot(EquipmentSlot.OFFHAND).is(UCItems.BOXING_GLOVE.get()) && player.getItemBySlot(EquipmentSlot.MAINHAND).is(UCItems.BOXING_GLOVE.get())) {
            click(player, event.getTarget(), true);
        }


        /** idk what to do w this lol */
        //ItemStack stack = event.getItemStack();
        //Item heldItem = stack.getItem();
        //Entity target = event.getTarget();

        //if (heldItem == UCItems.BOXING_GLOVE.get()) {
        //    System.out.println(target);

        //    player.swing(event.getHand());
        //    player.attack(target);
        //}
    }

    @SubscribeEvent
    public static void leftCLick(AttackEntityEvent event) {
        Player player = event.getPlayer();

        if (event.getTarget() instanceof LivingEntity target && target.getLastHurtByMob() != null && target.getLastHurtByMob().is(player) && player.getPersistentData().getInt("Clicks") > 0 && player.getItemBySlot(EquipmentSlot.OFFHAND).is(UCItems.BOXING_GLOVE.get()) && player.getItemBySlot(EquipmentSlot.MAINHAND).is(UCItems.BOXING_GLOVE.get())) {
            click(player, event.getTarget(), false);
        }
    }

    public static void click(Player player, Entity target, boolean rightClick) {
        var tag = player.getPersistentData();
        var clicks = tag.getInt("Clicks");
        if (clicks % 2 == (rightClick ? 1 : 0)) {
            target.getPersistentData().putInt("ComboActive", 1);
            tag.putInt("Clicks", clicks + 1);
            target.hurt(DamageSource.playerAttack(player), clicks + 1);
        }
    }

    // TODO - remove before release
    @SubscribeEvent
    public static void health(RenderNameplateEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof LivingEntity target) {
            double health = target.getHealth();

            event.setContent(new TextComponent("" + (float)health));
        }
    }
}
