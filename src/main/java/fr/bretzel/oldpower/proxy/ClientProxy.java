package fr.bretzel.oldpower.proxy;

import fr.bretzel.oldpower.util.CommonRegistry;
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

        addBlockRender(CommonRegistry.blockLamp, 0, OldPower.MODID + ":un_lit/white", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 1, OldPower.MODID + ":un_lit/orange", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 2, OldPower.MODID + ":un_lit/magenta", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 3, OldPower.MODID + ":un_lit/lightBlue", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 4, OldPower.MODID + ":un_lit/yellow", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 5, OldPower.MODID + ":un_lit/lime", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 6, OldPower.MODID + ":un_lit/pink", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 7, OldPower.MODID + ":un_lit/gray", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 8, OldPower.MODID + ":un_lit/silver", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 9, OldPower.MODID + ":un_lit/cyan", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 10, OldPower.MODID + ":un_lit/purple", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 11, OldPower.MODID + ":un_lit/blue", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 12, OldPower.MODID + ":un_lit/brown", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 13, OldPower.MODID + ":un_lit/green", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 14, OldPower.MODID + ":un_lit/red", "inventory");
        addBlockRender(CommonRegistry.blockLamp, 15, OldPower.MODID + ":un_lit/black", "inventory");

        addBlockRender(CommonRegistry.blockLitLamp, 0, OldPower.MODID + ":lit/white", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 1, OldPower.MODID + ":lit/orange", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 2, OldPower.MODID + ":lit/magenta", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 3, OldPower.MODID + ":lit/lightBlue", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 4, OldPower.MODID + ":lit/yellow", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 5, OldPower.MODID + ":lit/lime", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 6, OldPower.MODID + ":lit/pink", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 7, OldPower.MODID + ":lit/gray", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 8, OldPower.MODID + ":lit/silver", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 9, OldPower.MODID + ":lit/cyan", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 10, OldPower.MODID + ":lit/purple", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 11, OldPower.MODID + ":lit/blue", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 12, OldPower.MODID + ":lit/brown", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 13, OldPower.MODID + ":lit/green", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 14, OldPower.MODID + ":lit/red", "inventory");
        addBlockRender(CommonRegistry.blockLitLamp, 15, OldPower.MODID + ":lit/black", "inventory");

        addBlockRender(CommonRegistry.blockDecorativeLamp, 0, OldPower.MODID + ":lit/white", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 1, OldPower.MODID + ":lit/orange", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 2, OldPower.MODID + ":lit/magenta", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 3, OldPower.MODID + ":lit/lightBlue", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 4, OldPower.MODID + ":lit/yellow", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 5, OldPower.MODID + ":lit/lime", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 6, OldPower.MODID + ":lit/pink", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 7, OldPower.MODID + ":lit/gray", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 8, OldPower.MODID + ":lit/silver", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 9, OldPower.MODID + ":lit/cyan", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 10, OldPower.MODID + ":lit/purple", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 11, OldPower.MODID + ":lit/blue", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 12, OldPower.MODID + ":lit/brown", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 13, OldPower.MODID + ":lit/green", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 14, OldPower.MODID + ":lit/red", "inventory");
        addBlockRender(CommonRegistry.blockDecorativeLamp, 15, OldPower.MODID + ":lit/black", "inventory");
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
