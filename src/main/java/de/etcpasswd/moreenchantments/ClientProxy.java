package de.etcpasswd.moreenchantments;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy {

    private KeyBinding keyNightVision;

    @Override
    public void preInit() {
        keyNightVision = new KeyBinding("Night Vision", Keyboard.KEY_I, "More Enchantments");
        ClientRegistry.registerKeyBinding(keyNightVision);
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent key) {
        if (keyNightVision.isPressed()) {
            Registry.hasNightVisionEnabled = !Registry.hasNightVisionEnabled;
        }
    }

}
