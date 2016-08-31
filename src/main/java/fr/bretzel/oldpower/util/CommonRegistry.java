package fr.bretzel.oldpower.util;

import fr.bretzel.oldpower.api.LampType;
import fr.bretzel.oldpower.block.BlockBase;
import fr.bretzel.oldpower.block.BlockDecorativeLamp;
import fr.bretzel.oldpower.block.BlockLamp;

public class CommonRegistry {

    public static BlockBase blockLamp = new BlockLamp("lamp", LampType.LAMP);
    public static BlockBase blockLitLamp = new BlockLamp("lamp_lit", LampType.LAMP_LIT);
    public static BlockBase blockDecorativeLamp = new BlockDecorativeLamp("lamp_decorative");

}
