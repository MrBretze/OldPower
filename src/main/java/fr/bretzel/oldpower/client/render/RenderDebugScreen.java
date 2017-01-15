package fr.bretzel.oldpower.client.render;

import fr.bretzel.oldpower.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RenderDebugScreen {

    private static final DecimalFormat df;
    private static final DecimalFormatSymbols dfs;
    public static UUID localPlayerUUID;
    public static List<UUID> players = new ArrayList<>();
    public static double MS_TPS = 00.00D;
    private static HashMap<UUID, Integer> integerHashMap = new HashMap<>();

    static {
        df = new DecimalFormat("###.###");
        dfs = DecimalFormatSymbols.getInstance();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onDebugRender(RenderGameOverlayEvent.Text event) {
        if (Minecraft.getMinecraft().gameSettings.showDebugInfo) {

        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!players.contains(event.player.getPersistentID())) {
            localPlayerUUID = event.player.getPersistentID();
        }
    }

    @SubscribeEvent
    public void onTickEvent(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
        }
    }

    private String getColorTPS(double i) {

        if (i >= 15.5)
            return TextFormatting.GREEN + String.valueOf(i) + TextFormatting.RESET;

        if (i <= 15.5 && i >= 10.5)
            return TextFormatting.GOLD + String.valueOf(i) + TextFormatting.RESET;

        if (i <= 10.5 && i >= 5.5)
            return TextFormatting.RED + String.valueOf(i) + TextFormatting.RESET;

        if (i <= 5.5)
            return TextFormatting.DARK_RED + String.valueOf(i) + TextFormatting.RESET;

        Logger.info(i);
        return TextFormatting.WHITE + String.valueOf(i);
    }
}
