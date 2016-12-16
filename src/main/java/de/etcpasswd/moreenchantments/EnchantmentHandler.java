package de.etcpasswd.moreenchantments;

import de.etcpasswd.moreenchantments.enchantment.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

public class EnchantmentHandler {

    private static final int AIR_MAX = 300;

    public static Map<String, Integer> curFlyingPlayers = new HashMap<String, Integer>();


    public static void registerEnchantments() {
        if (Registry.enableFlightEnchantment)
            Registry.enchantments.put(EnchantmentFlight.NAME, new EnchantmentFlight());
        if (Registry.enableWaterBreathing)
            Registry.enchantments.put(EnchantmentWaterBreathing.NAME, new EnchantmentWaterBreathing());
        if (Registry.enableStepAssistEnchantment)
            Registry.enchantments.put(EnchantmentStepAssist.NAME, new EnchantmentStepAssist());
        if (Registry.enableFireImmune)
            Registry.enchantments.put(EnchantmenFireImmune.NAME, new EnchantmenFireImmune());
        if (Registry.enableNightVisionEnchantment)
            Registry.enchantments.put(EnchantmentNightVision.NAME, new EnchantmentNightVision());
        if (Registry.enableSoulboundEnchantment)
            Registry.enchantments.put(EnchantmentSoulbound.NAME, new EnchantmentSoulbound());

        for (Enchantment ench : Registry.enchantments.values()) {
            GameRegistry.register(ench);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (Registry.enableFlightEnchantment && !player.isCreative() && event.side == Side.SERVER)
            handleFlight(player);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase != TickEvent.Phase.END) {
            return;
        }
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if(player == null) {
            return;
        }

        if (Registry.enableWaterBreathing)
            handleWaterBreathing(player);
        if (Registry.enableStepAssistEnchantment)
            handleStepAssist(player);
        if (Registry.enableNightVisionEnchantment)
            handleNightVision(player);
    }

    @SubscribeEvent
    public void onLivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntityLiving() == null || !(event.getEntityLiving() instanceof EntityPlayer) || event.isCanceled()) {
            return;
        }
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();

        if (Registry.enableFireImmune)
            handleFireImmune(player, event);
    }


    // The next 2 methods have been ported based on EnderIO's implementation of the Soulbound enchantment
    // https://github.com/SleepyTrousers/EnderIO
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDropsEvent event) {
        if (event.getEntityPlayer() == null || event.isCanceled() || !Registry.enableSoulboundEnchantment) {
            return;
        }
        if (event.getEntityPlayer().getEntityWorld().getGameRules().getBoolean("keepInventory")) {
            return;
        }

        ListIterator<EntityItem> iter = event.getDrops().listIterator();
        while (iter.hasNext()) {
            EntityItem ei = iter.next();
            ItemStack item = ei.getEntityItem();
            if (hasEnchantment(EnchantmentSoulbound.NAME, item)) {
                if (addToPlayerInventory(event.getEntityPlayer(), item)) {
                    iter.remove();
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerClone(PlayerEvent.Clone evt) {
        if (!evt.isWasDeath() || evt.isCanceled() || !Registry.enableSoulboundEnchantment) {
            return;
        }
        if (evt.getOriginal() == null || evt.getEntityPlayer() == null) {
            return;
        }
        if (evt.getEntityPlayer().getEntityWorld().getGameRules().getBoolean("keepInventory")) {
            return;
        }
        for (int i = 0; i < evt.getOriginal().inventory.armorInventory.size(); i++) {
            ItemStack item = evt.getOriginal().inventory.armorInventory.get(i);
            if (hasEnchantment(EnchantmentSoulbound.NAME, item)) {
                if (addToPlayerInventory(evt.getEntityPlayer(), item)) {
                    evt.getOriginal().inventory.armorInventory.set(i, ItemStack.EMPTY);
                }
            }
        }
        for (int i = 0; i < evt.getOriginal().inventory.mainInventory.size(); i++) {
            ItemStack item = evt.getOriginal().inventory.mainInventory.get(i);
            if (hasEnchantment(EnchantmentSoulbound.NAME, item)) {
                if (addToPlayerInventory(evt.getEntityPlayer(), item)) {
                    evt.getOriginal().inventory.mainInventory.set(i, ItemStack.EMPTY);
                }
            }
        }
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if (!Registry.allowEnchantmentStripping)
            return;

        if (event.getLeft().isEmpty() || event.getLeft().getEnchantmentTagList() == null) {
            return;
        }
        if (!(event.getRight().getItem() instanceof ItemBook)) {
            return;
        }

        ItemStack outputItem = new ItemStack(Items.ENCHANTED_BOOK);
        Map enchantments = EnchantmentHelper.getEnchantments(event.getLeft());
        EnchantmentHelper.setEnchantments(enchantments, outputItem);
        event.setOutput(outputItem);
        event.setCost(enchantments.size() * Registry.levelCostPerEnchantForStripping);

    }

    @SubscribeEvent
    public void onPlayerLogin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        if(curFlyingPlayers.containsKey(event.player.getGameProfile().getName())) {
            curFlyingPlayers.remove(event.player.getGameProfile().getName());
        }
    }

    @SubscribeEvent
    public void onPlayerLogout(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event) {
        if(curFlyingPlayers.containsKey(event.player.getGameProfile().getName())) {
            curFlyingPlayers.remove(event.player.getGameProfile().getName());
        }
    }



    private void handleNightVision(EntityPlayer player) {
        if (!hasEnchantment(EnchantmentNightVision.NAME, player)) {
            return;
        }

        if (Registry.hasNightVisionEnabled) {
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 210, 0, true, false));
        } else {
            player.removePotionEffect(MobEffects.NIGHT_VISION);
        }

    }

    private void handleFireImmune(EntityPlayer player, LivingHurtEvent event) {
        if (player.capabilities.isCreativeMode || !event.getSource().isFireDamage() || !hasEnchantment(EnchantmenFireImmune.NAME, player)) {
            return;
        }
        event.setCanceled(true);
        player.extinguish();
    }

    private void handleStepAssist(EntityPlayer player) {
        if (hasEnchantment(EnchantmentStepAssist.NAME, player) && !player.isSneaking()) {
            player.stepHeight = 1F;
        } else {
            player.stepHeight = 0.5F;
        }
    }

    private void handleWaterBreathing(EntityPlayer player) {
        if (hasEnchantment(EnchantmentWaterBreathing.NAME, player) && player.getAir() < AIR_MAX) {
            player.setAir(AIR_MAX);
        }
    }

    private void handleFlight(EntityPlayer player) {
        if(!(player instanceof EntityPlayerMP)) {
            return;
        }
        if (hasEnchantment(EnchantmentFlight.NAME, player) && !curFlyingPlayers.containsKey(player.getGameProfile().getName())) {
            player.capabilities.allowFlying = true;
            if (!player.onGround) {
                player.capabilities.isFlying = true;
            }
            player.sendPlayerAbilities();
            curFlyingPlayers.put(player.getGameProfile().getName(), 1);
        } else if (!hasEnchantment(EnchantmentFlight.NAME, player) && curFlyingPlayers.containsKey(player.getGameProfile().getName())) {
            player.capabilities.allowFlying = false;
            player.sendPlayerAbilities();
            curFlyingPlayers.remove(player.getGameProfile().getName());
        }
    }


    private boolean hasEnchantment(String key, ItemStack item) {
        return EnchantmentHelper.getEnchantmentLevel(Registry.enchantments.get(key), item) > 0;
    }

    private boolean hasEnchantment(String key, EntityPlayer player) {
        return EnchantmentHelper.getMaxEnchantmentLevel(Registry.enchantments.get(key), player) > 0;
    }

    private boolean addToPlayerInventory(EntityPlayer entityPlayer, ItemStack item) {
        if (item.isEmpty() || entityPlayer == null) {
            return false;
        }
        if (item.getItem() instanceof ItemArmor) {
            ItemArmor arm = (ItemArmor) item.getItem();
            int index = arm.armorType.getIndex();
            if (entityPlayer.inventory.armorInventory.get(index).isEmpty()) {
                entityPlayer.inventory.armorInventory.set(index, item);
                return true;
            }
        }

        InventoryPlayer inv = entityPlayer.inventory;
        for (int i = 0; i < inv.mainInventory.size(); i++) {
            if (inv.mainInventory.get(i).isEmpty()) {
                inv.mainInventory.set(i, item.copy());
                return true;
            }
        }

        return false;
    }
}
