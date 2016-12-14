package de.etcpasswd.moreenchantments.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nonnull;

public class EnchantmentWaterBreathing extends Enchantment {

    public static final
    @Nonnull
    String NAME = "waterbreathing";

    public EnchantmentWaterBreathing() {
        super(Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR_HEAD, new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD});
        setName(NAME);
        setRegistryName(NAME);
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return !ench.equals(Enchantments.RESPIRATION);
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
