package de.etcpasswd.moreenchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.HashMap;

public class Registry {

    public static HashMap<String, Enchantment> enchantments = new HashMap<String, Enchantment>();

    public static boolean enableFlightEnchantment, enableNightVisionEnchantment, enableSoulboundEnchantment,
            enableStepAssistEnchantment, enableWaterBreathingEnchantment, enableFireImmuneEnchantment,
            allowEnchantmentStripping, enableAntiVenomEnchantment, enableAntiWitherEnchantment;

    public static int levelCostPerEnchantForStripping;


    public static void loadConfigFromFile(File configFile) {
        Configuration cfg = new Configuration(configFile);

        try {
            cfg.load();

            Registry.enableFlightEnchantment = cfg.getBoolean("flight", "enchantments", true, "Enable Flight");
            Registry.enableNightVisionEnchantment = cfg.getBoolean("nightvision", "enchantments", true, "Enable Night Vision");
            Registry.enableSoulboundEnchantment = cfg.getBoolean("soulbound", "enchantments", true, "Enable Soulbound");
            Registry.enableStepAssistEnchantment = cfg.getBoolean("stepassist", "enchantments", true, "Enable Step assist");
            Registry.enableWaterBreathingEnchantment = cfg.getBoolean("waterbreathing", "enchantments", true, "Enable Water Breathing");
            Registry.enableFireImmuneEnchantment = cfg.getBoolean("fireimmune", "enchantments", true, "Enable Fire Immunity");
            Registry.enableAntiVenomEnchantment = cfg.getBoolean("antivenom", "enchantments", true, "Enable Anti Venom");
            Registry.enableAntiWitherEnchantment = cfg.getBoolean("antiwither", "enchantments", true, "Enable Anti Wither");
            Registry.allowEnchantmentStripping = cfg.getBoolean("allowdisenchantwithbooks", "miscellaneous", true, "Allow stripping of enchanted items with books");
            Registry.levelCostPerEnchantForStripping = cfg.getInt("disenchantlevelcost", "miscellaneous", 5, 0, Integer.MAX_VALUE, "Number of levels per stripped enchantment");

        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }


    public static boolean hasNightVisionEnabled = false;

}
