package fr.bretzel.oldpower.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.render.RenderDecoLamp;
import fr.bretzel.oldpower.render.RenderLamp;
import fr.bretzel.oldpower.tiles.TileDecoLamp;
import fr.bretzel.oldpower.tiles.TileLamp;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        addBlockRender(CommonProxy.blockLamp, 0, OldPower.MODID + ":lamp_off_white", "inventory");
        addBlockRender(CommonProxy.blockLamp, 1, OldPower.MODID + ":lamp_off_orange", "inventory");
        addBlockRender(CommonProxy.blockLamp, 2, OldPower.MODID + ":lamp_off_magenta", "inventory");
        addBlockRender(CommonProxy.blockLamp, 3, OldPower.MODID + ":lamp_off_lightBlue", "inventory");
        addBlockRender(CommonProxy.blockLamp, 4, OldPower.MODID + ":lamp_off_yellow", "inventory");
        addBlockRender(CommonProxy.blockLamp, 5, OldPower.MODID + ":lamp_off_lime", "inventory");
        addBlockRender(CommonProxy.blockLamp, 6, OldPower.MODID + ":lamp_off_pink", "inventory");
        addBlockRender(CommonProxy.blockLamp, 7, OldPower.MODID + ":lamp_off_gray", "inventory");
        addBlockRender(CommonProxy.blockLamp, 8, OldPower.MODID + ":lamp_off_silver", "inventory");
        addBlockRender(CommonProxy.blockLamp, 9, OldPower.MODID + ":lamp_off_cyan", "inventory");
        addBlockRender(CommonProxy.blockLamp, 10, OldPower.MODID + ":lamp_off_purple", "inventory");
        addBlockRender(CommonProxy.blockLamp, 11, OldPower.MODID + ":lamp_off_blue", "inventory");
        addBlockRender(CommonProxy.blockLamp, 12, OldPower.MODID + ":lamp_off_brown", "inventory");
        addBlockRender(CommonProxy.blockLamp, 13, OldPower.MODID + ":lamp_off_green", "inventory");
        addBlockRender(CommonProxy.blockLamp, 14, OldPower.MODID + ":lamp_off_red", "inventory");
        addBlockRender(CommonProxy.blockLamp, 15, OldPower.MODID + ":lamp_off_black", "inventory");

        addBlockRender(CommonProxy.blockDecorativeLamp, 0, OldPower.MODID + ":lamp_on_white", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 1, OldPower.MODID + ":lamp_on_orange", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 2, OldPower.MODID + ":lamp_on_magenta", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 3, OldPower.MODID + ":lamp_on_lightBlue", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 4, OldPower.MODID + ":lamp_on_yellow", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 5, OldPower.MODID + ":lamp_on_lime", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 6, OldPower.MODID + ":lamp_on_pink", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 7, OldPower.MODID + ":lamp_on_gray", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 8, OldPower.MODID + ":lamp_on_silver", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 9, OldPower.MODID + ":lamp_on_cyan", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 10, OldPower.MODID + ":lamp_on_purple", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 11, OldPower.MODID + ":lamp_on_blue", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 12, OldPower.MODID + ":lamp_on_brown", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 13, OldPower.MODID + ":lamp_on_green", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 14, OldPower.MODID + ":lamp_on_red", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 15, OldPower.MODID + ":lamp_on_black", "inventory");
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);

        ClientRegistry.bindTileEntitySpecialRenderer(TileLamp.class, new RenderLamp());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDecoLamp.class, new RenderDecoLamp());
    }

    private static void addBlockRender(net.minecraft.block.Block block, int metadata, String blockString, String location) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(blockString, location));
    }

    private static void addItemRender(net.minecraft.item.Item item, int metadata, String blockString, String location) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(blockString, location));
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
