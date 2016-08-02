package fr.bretzel.oldpower.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import fr.bretzel.oldpower.block.BlockBase;
import fr.bretzel.oldpower.block.BlockDecorativeLamp;
import fr.bretzel.oldpower.block.BlockLamp;
import fr.bretzel.oldpower.util.Register;

public class CommonProxy {

    public static BlockBase blockLamp = new BlockLamp("lamp", false);
    public static BlockBase blockLitLamp = new BlockLamp("lamp_lit", true);
    public static BlockBase blockDecorativeLamp = new BlockDecorativeLamp("lamp_decorative");

    public void preInit(FMLPreInitializationEvent e) {
        Register.registerBlock(blockLamp);
        Register.registerBlock(blockLitLamp);
        Register.registerBlock(blockDecorativeLamp);
    }

    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {

    }

}
