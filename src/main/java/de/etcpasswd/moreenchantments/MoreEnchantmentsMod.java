package de.etcpasswd.moreenchantments;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "moreenchantments", version = "0.1", name = "More Enchantments")
public class MoreEnchantmentsMod {

    @SidedProxy(clientSide = "de.etcpasswd.moreenchantments.ClientProxy", serverSide = "de.etcpasswd.moreenchantments.ServerProxy")
    public static CommonProxy proxy;


    @Mod.EventHandler()
    public void preInit(FMLPreInitializationEvent event) {

        Registry.loadConfigFromFile(event.getSuggestedConfigurationFile());


        EnchantmentHandler.registerEnchantments();
        MinecraftForge.EVENT_BUS.register(new EnchantmentHandler());
        MinecraftForge.EVENT_BUS.register(proxy);
        proxy.preInit();
    }

    @Mod.EventHandler()
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

}
