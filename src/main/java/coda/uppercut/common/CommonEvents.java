package coda.uppercut.common;

import coda.uppercut.Uppercut;
import coda.uppercut.registry.UCItems;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

        if (event.getTarget() instanceof LivingEntity target && target.getLastHurtByMob() != null && target.getLastHurtByMob().is(player) && player.getItemBySlot(EquipmentSlot.OFFHAND).is(UCItems.BOXING_GLOVE.get()) && player.getItemBySlot(EquipmentSlot.MAINHAND).is(UCItems.BOXING_GLOVE.get())) {
            click(player, event.getTarget(), true);

            //player.swing(event.getHand());
            //player.attack(target);
        }
    }

    @SubscribeEvent
    public static void leftCLick(AttackEntityEvent event) {
        Player player = event.getPlayer();

        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(UCItems.BOXING_GLOVE.get()) && player.getItemBySlot(EquipmentSlot.MAINHAND).is(UCItems.BOXING_GLOVE.get())) {
            click(player, event.getTarget(), false);
        }
    }

    @SubscribeEvent
    public static void removeCombo(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        LivingEntity target = player.getLastHurtMob();

        //if (target != null && target.getLastHurtByMob() != null && target.tickCount - target.getLastHurtMobTimestamp() >= 40) {
        //    System.out.println("should reset");
        //    player.getPersistentData().remove("ComboClicks");
        //}
    }

    public static void click(Player player, Entity target, boolean rightClick) {
        var tag = player.getPersistentData();
        var clicks = tag.getInt("ComboClicks");
        if (clicks % 2 == (rightClick ? 1 : 0)) {
            target.getPersistentData().putInt("ComboActive", 1);
            tag.putInt("ComboClicks", Math.min(clicks + 1, 8));

            //target.hurt(DamageSource.playerAttack(player), Math.min(clicks + 1, 8));
            System.out.println(clicks);

            var attributeInstance = player.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
            if (attributeInstance != null) {
                attributeInstance.addTransientModifier(new AttributeModifier("Combo damage", 1, AttributeModifier.Operation.ADDITION));
            }
        }

        System.out.println(clicks);

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
