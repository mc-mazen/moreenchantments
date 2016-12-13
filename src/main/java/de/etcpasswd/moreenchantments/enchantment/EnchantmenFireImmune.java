package de.etcpasswd.moreenchantments.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nonnull;

public class EnchantmenFireImmune extends Enchantment {
    public static final
    @Nonnull
    String NAME = "fireimmune";

    public EnchantmenFireImmune() {
        super(Rarity.RARE, EnumEnchantmentType.ARMOR_LEGS, new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS});
        setName(NAME);
        setRegistryName(NAME);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 25;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 60;
    }
}
