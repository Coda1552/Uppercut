package coda.uppercut.registry;

import coda.uppercut.Uppercut;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Uppercut.MOD_ID);

//    public static final RegistryObject<Item> BOXING_GLOVE = ITEMS.register("boxing_glove", () -> new BoxingGloveItem(3, -2.4F, new Item.Properties().stacksTo(1).durability(250).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> BOXING_GLOVE = ITEMS.register("boxing_glove", () -> new Item(new Item.Properties().stacksTo(1).durability(250).tab(CreativeModeTab.TAB_COMBAT)));
}
