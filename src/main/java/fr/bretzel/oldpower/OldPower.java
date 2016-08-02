package fr.bretzel.oldpower;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import fr.bretzel.oldpower.proxy.CommonProxy;

@Mod(modid = OldPower.MODID, version = OldPower.VERSION, acceptedMinecraftVersions = OldPower.VERSION_MINECRAFT)
public class OldPower {

    public static final String MODID = "oldpower";

    public static final int VERSION_MAJOR = 0;
    public static final int VERSION_MINOR = 0;
    public static final int VERSION_FIX = 1;

    public static final String VERSION_MINECRAFT = "[1.10]";

    public static final String VERSION = "V-" + VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_FIX;

    public static final Logger logger = FMLLog.getLogger();

    @SidedProxy(serverSide = "fr.bretzel.oldpower.proxy.CommonProxy", clientSide = "fr.bretzel.oldpower.proxy.ClientProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    public static final CreativeTabs tabs = new CreativeTabs("OldPower") {

        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.COMMAND_BLOCK);
        }
    };
}
