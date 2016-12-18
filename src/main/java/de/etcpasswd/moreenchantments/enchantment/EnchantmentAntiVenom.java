package de.etcpasswd.moreenchantments.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nonnull;

public class EnchantmentAntiVenom extends Enchantment {
    public static final
    @Nonnull
    String NAME = "antivenom";

    public EnchantmentAntiVenom() {
        super(Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR_LEGS, new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS});
        setName(NAME);
        setRegistryName(NAME);
    }
}
