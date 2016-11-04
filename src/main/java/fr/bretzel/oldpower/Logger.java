package fr.bretzel.oldpower;

import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

public class Logger {

    private static String PREFIX = "[Old Power]: ";

    private static void log(Level level, String msg) {
        FMLLog.log(level, PREFIX + msg);
    }

    public static void info(String msg) {
        log(Level.INFO, msg);
    }

    public static void info(Object object) {
        log(Level.INFO, String.valueOf(object));
    }
}
