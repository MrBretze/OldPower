package fr.bretzel.oldpower.item;

import fr.bretzel.oldpower.OldPower;
import net.minecraft.item.Item;

public abstract class ItemBase extends Item {

    private String unlocalizedName = "";

    public ItemBase(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + OldPower.MODID + "." + unlocalizedName;
    }

    public String getOnlyUnlocalizedName() {
        return unlocalizedName;
    }

    public abstract int hasSubType();

    public String getSubTypeName(int damage) {
        return unlocalizedName;
    }
}
