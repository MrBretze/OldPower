package fr.bretzel.oldpower.util;

import fr.bretzel.oldpower.api.LampType;
import fr.bretzel.oldpower.block.*;
import net.minecraft.block.Block;

public class CommonRegistry {

    public static BlockBase blockLamp = new BlockLamp("lamp", LampType.LAMP);
    public static BlockBase blockLitLamp = new BlockLamp("lamp_lit", LampType.LAMP_LIT);
    public static BlockBase blockDecorativeLamp = new BlockDecorativeLamp("lamp_decorative");
    public static BlockBase blockBasalt = new BlockBasalt("basalt");
    public static Block blockTephra = new BlockTephra("tephra");
    public static Block blockCharredLog = new BlockCharredLog("charred_log");
}
