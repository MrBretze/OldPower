package fr.bretzel.oldpower.proxy;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.client.render.RenderDebugScreen;
import fr.bretzel.oldpower.network.TPSNetwork;
import fr.bretzel.oldpower.util.CommonRegistry;
import fr.bretzel.oldpower.util.Register;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        Register.registerBlock(CommonRegistry.blockBasalt);
        Register.registerBlock(CommonRegistry.blockTephra);
        Register.registerBlock(CommonRegistry.blockLamp);
        Register.registerBlock(CommonRegistry.blockLitLamp);
        Register.registerBlock(CommonRegistry.blockDecorativeLamp);

        OldPower.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(OldPower.MODID);
        OldPower.networkWrapper.registerMessage(TPSNetwork.Handler.class, TPSNetwork.class, 1, Side.CLIENT);
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new RenderDebugScreen());
    }

    public void postInit(FMLPostInitializationEvent e) {

    }

}
