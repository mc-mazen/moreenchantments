package de.etcpasswd.moreenchantments.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nonnull;

public class EnchantmentFlight extends Enchantment {

    public static final
    @Nonnull
    String NAME = "flight";

    public EnchantmentFlight() {
        super(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_CHEST, new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST});
        setName(NAME);
        setRegistryName(NAME);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 60;
    }
}
