package fr.bretzel.oldpower.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.block.BlockBase;

public class ItemBlockBase extends ItemBlock {

    private BlockBase blockBase;

    public ItemBlockBase(BlockBase block) {
        super(block);
        this.blockBase = block;

        if (blockBase.hasSubType() > 0) {
            setHasSubtypes(true);
            setMaxDamage(0);
        }
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "tile." + OldPower.MODID + "." + blockBase.getSubTypeName(stack.getItemDamage());
    }
}
