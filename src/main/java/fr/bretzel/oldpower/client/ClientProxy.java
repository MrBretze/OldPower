package fr.bretzel.oldpower.client;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.client.render.RenderLamp;
import fr.bretzel.oldpower.proxy.CommonProxy;
import fr.bretzel.oldpower.tiles.TileLamp;
import fr.bretzel.oldpower.util.CommonRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    private static void addBlockRender(Block block, int metadata, String blockString, String location) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(blockString, location));
    }

    private static void addItemRender(Item item, int metadata, String blockString, String location) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(blockString, location));
    }

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        addBlockRender(CommonRegistry.blockBasalt, 0, OldPower.MODID + ":basalt", "inventory");
        addBlockRender(CommonRegistry.blockTephra, 0, OldPower.MODID + ":tephra", "inventory");

        for (int i = 0; i < 16; i++) {
            addBlockRender(CommonRegistry.blockLamp, i, OldPower.MODID + ":un_lit/" + EnumDyeColor.byMetadata(i).getName().toLowerCase(), "inventory");
            addBlockRender(CommonRegistry.blockLitLamp, i, OldPower.MODID + ":lit/" + EnumDyeColor.byMetadata(i).getName().toLowerCase(), "inventory");
            addBlockRender(CommonRegistry.blockDecorativeLamp, i, OldPower.MODID + ":lit/" + EnumDyeColor.byMetadata(i).getName().toLowerCase(), "inventory");
        }

    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);

        ClientRegistry.bindTileEntitySpecialRenderer(TileLamp.class, new RenderLamp());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
