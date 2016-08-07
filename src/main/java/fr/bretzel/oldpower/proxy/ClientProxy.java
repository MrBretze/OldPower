package fr.bretzel.oldpower.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.render.RenderDecorativeLamp;
import fr.bretzel.oldpower.render.RenderLamp;
import fr.bretzel.oldpower.tiles.TileDecoLamp;
import fr.bretzel.oldpower.tiles.TileLamp;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        addBlockRender(CommonProxy.blockLamp, 0, OldPower.MODID + ":un_lit/white", "inventory");
        addBlockRender(CommonProxy.blockLamp, 1, OldPower.MODID + ":un_lit/orange", "inventory");
        addBlockRender(CommonProxy.blockLamp, 2, OldPower.MODID + ":un_lit/magenta", "inventory");
        addBlockRender(CommonProxy.blockLamp, 3, OldPower.MODID + ":un_lit/lightBlue", "inventory");
        addBlockRender(CommonProxy.blockLamp, 4, OldPower.MODID + ":un_lit/yellow", "inventory");
        addBlockRender(CommonProxy.blockLamp, 5, OldPower.MODID + ":un_lit/lime", "inventory");
        addBlockRender(CommonProxy.blockLamp, 6, OldPower.MODID + ":un_lit/pink", "inventory");
        addBlockRender(CommonProxy.blockLamp, 7, OldPower.MODID + ":un_lit/gray", "inventory");
        addBlockRender(CommonProxy.blockLamp, 8, OldPower.MODID + ":un_lit/silver", "inventory");
        addBlockRender(CommonProxy.blockLamp, 9, OldPower.MODID + ":un_lit/cyan", "inventory");
        addBlockRender(CommonProxy.blockLamp, 10, OldPower.MODID + ":un_lit/purple", "inventory");
        addBlockRender(CommonProxy.blockLamp, 11, OldPower.MODID + ":un_lit/blue", "inventory");
        addBlockRender(CommonProxy.blockLamp, 12, OldPower.MODID + ":un_lit/brown", "inventory");
        addBlockRender(CommonProxy.blockLamp, 13, OldPower.MODID + ":un_lit/green", "inventory");
        addBlockRender(CommonProxy.blockLamp, 14, OldPower.MODID + ":un_lit/red", "inventory");
        addBlockRender(CommonProxy.blockLamp, 15, OldPower.MODID + ":un_lit/black", "inventory");

        addBlockRender(CommonProxy.blockLitLamp, 0, OldPower.MODID + ":lit/white", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 1, OldPower.MODID + ":lit/orange", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 2, OldPower.MODID + ":lit/magenta", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 3, OldPower.MODID + ":lit/lightBlue", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 4, OldPower.MODID + ":lit/yellow", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 5, OldPower.MODID + ":lit/lime", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 6, OldPower.MODID + ":lit/pink", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 7, OldPower.MODID + ":lit/gray", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 8, OldPower.MODID + ":lit/silver", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 9, OldPower.MODID + ":lit/cyan", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 10, OldPower.MODID + ":lit/purple", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 11, OldPower.MODID + ":lit/blue", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 12, OldPower.MODID + ":lit/brown", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 13, OldPower.MODID + ":lit/green", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 14, OldPower.MODID + ":lit/red", "inventory");
        addBlockRender(CommonProxy.blockLitLamp, 15, OldPower.MODID + ":lit/black", "inventory");

        addBlockRender(CommonProxy.blockDecorativeLamp, 0, OldPower.MODID + ":lit/white", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 1, OldPower.MODID + ":lit/orange", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 2, OldPower.MODID + ":lit/magenta", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 3, OldPower.MODID + ":lit/lightBlue", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 4, OldPower.MODID + ":lit/yellow", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 5, OldPower.MODID + ":lit/lime", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 6, OldPower.MODID + ":lit/pink", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 7, OldPower.MODID + ":lit/gray", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 8, OldPower.MODID + ":lit/silver", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 9, OldPower.MODID + ":lit/cyan", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 10, OldPower.MODID + ":lit/purple", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 11, OldPower.MODID + ":lit/blue", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 12, OldPower.MODID + ":lit/brown", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 13, OldPower.MODID + ":lit/green", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 14, OldPower.MODID + ":lit/red", "inventory");
        addBlockRender(CommonProxy.blockDecorativeLamp, 15, OldPower.MODID + ":lit/black", "inventory");
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);

        ClientRegistry.bindTileEntitySpecialRenderer(TileLamp.class, new RenderLamp());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDecoLamp.class, new RenderDecorativeLamp());
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
