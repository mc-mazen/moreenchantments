package de.etcpasswd.moreenchantments.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nonnull;

public class EnchantmentAntiWither extends Enchantment {
    public static final
    @Nonnull
    String NAME = "antiwither";

    public EnchantmentAntiWither() {
        super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR_LEGS, new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS});
        setName(NAME);
        setRegistryName(NAME);
    }
}
