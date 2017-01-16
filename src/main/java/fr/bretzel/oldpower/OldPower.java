package fr.bretzel.oldpower;

import fr.bretzel.oldpower.proxy.CommonProxy;
import fr.bretzel.oldpower.util.CommonRegistry;
import fr.bretzel.oldpower.util.RGBColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Random;

@Mod(modid = OldPower.MODID, version = OldPower.VERSION, acceptedMinecraftVersions = OldPower.VERSION_MINECRAFT)
public class OldPower {

    public static final String MODID = "oldpower";

    public static final int VERSION_MAJOR = 0;
    public static final int VERSION_MINOR = 0;
    public static final int VERSION_FIX = 1;
    public static final String VERSION_MINECRAFT = "1.9.4,1.10.2";
    public static final String VERSION = "V-" + VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_FIX;
    public static Random random = new Random();
    public static final CreativeTabs tabs = new CreativeTabs("OldPower") {

        int last_i = 0;
        boolean last_b;

        @Override
        public ItemStack getIconItemStack() {
            int i = (int)((System.currentTimeMillis() / 1000));

            if (i != last_i) {
                last_i = i;
                last_b = random.nextBoolean();
            }

            return new ItemStack(last_b ? CommonRegistry.blockDecorativeLamp : CommonRegistry.blockLamp, 1, (i % RGBColor.Color.COLORS.length));
        }

        @Override
        public Item getTabIconItem() {
            return null;
        }
    };
    public static boolean isClientSide = FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
    public static SimpleNetworkWrapper networkWrapper;
    @SidedProxy(serverSide = "fr.bretzel.oldpower.proxy.CommonProxy", clientSide = "fr.bretzel.oldpower.client.ClientProxy")
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
}
