package fr.bretzel.oldpower.proxy;

import fr.bretzel.oldpower.util.CommonRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import fr.bretzel.oldpower.util.Register;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        Register.registerBlock(CommonRegistry.blockLamp);
        Register.registerBlock(CommonRegistry.blockLitLamp);
        Register.registerBlock(CommonRegistry.blockDecorativeLamp);
    }

    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {

    }

}
