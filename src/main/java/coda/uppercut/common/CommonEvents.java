package coda.uppercut.common;

import coda.uppercut.Uppercut;
import coda.uppercut.common.combo.PlayerComboProvider;
import coda.uppercut.registry.UCItems;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Uppercut.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {

    // todo - make combos reset if you mess up the l-r-l-r sequence

    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();

        if (event.getTarget() instanceof LivingEntity target && player.getItemBySlot(EquipmentSlot.OFFHAND).is(UCItems.BOXING_GLOVE.get()) && player.getItemBySlot(EquipmentSlot.MAINHAND).is(UCItems.BOXING_GLOVE.get())) {
            player.swing(InteractionHand.OFF_HAND);

            player.getCapability(PlayerComboProvider.PLAYER_COMBO).ifPresent(combo -> {

                if (!combo.getWasPrevClickRight()) {
                    click(player, target);
                }

                combo.setPrevClickRight(true);
            });

        }
    }

    @SubscribeEvent
    public static void leftCLick(AttackEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getTarget();

        if (entity instanceof LivingEntity target && player.getItemBySlot(EquipmentSlot.OFFHAND).is(UCItems.BOXING_GLOVE.get()) && player.getItemBySlot(EquipmentSlot.MAINHAND).is(UCItems.BOXING_GLOVE.get())) {
            player.getCapability(PlayerComboProvider.PLAYER_COMBO).ifPresent(combo -> {

                if (combo.getWasPrevClickRight()) {
                    click(player, target);
                }

                combo.setPrevClickRight(false);
            });
        }
    }

    @SubscribeEvent
    public static void resetCombo(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        //if (player.getLastHurtMobTimestamp() < player.tickCount + 60) {
        //    player.getCapability(PlayerComboProvider.PLAYER_COMBO).ifPresent(PlayerCombo::resetCombo);
        //}
    }

    public static void click(Player player, Entity target) {
        player.getCapability(PlayerComboProvider.PLAYER_COMBO).ifPresent(combo -> {

            if (combo.getCombo() < 10) {
                combo.setCombo(combo.getCombo() + 1);
            }

            target.hurt(DamageSource.playerAttack(player), combo.getCombo());
        });
    }

    // TODO - remove before release
    @SubscribeEvent
    public static void health(RenderNameplateEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof LivingEntity target) {
            double health = target.getHealth();

            target.setCustomName(new TextComponent("" + (float)health));
        }
    }
}
