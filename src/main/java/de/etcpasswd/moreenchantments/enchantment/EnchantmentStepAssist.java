package de.etcpasswd.moreenchantments.enchantment;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nonnull;

public class EnchantmentStepAssist extends Enchantment {

    public static final
    @Nonnull
    String NAME = "stepassist";

    public EnchantmentStepAssist() {
        super(Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR_FEET, new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET});
        setName(NAME);
        setRegistryName(NAME);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 25;
    }
}
