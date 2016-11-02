package fr.bretzel.oldpower.util;

import net.minecraft.item.EnumDyeColor;

public class RGBColor {

    private int r, g, b;

    protected RGBColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getB() {
        return b;
    }

    public int getG() {
        return g;
    }

    public int getR() {
        return r;
    }

    public int getHex(EnumDyeColor d) {
        return Color.translateDyeColor(d).getHex();
    }

    public RGBColor mult(int m) {
        this.r = r * m;
        this.g = g * m;
        this.b = b * m;
        return this;
    }

    public RGBColor div(int d) {
        this.r = r / d;
        this.g = g / d;
        this.b = b / d;
        return this;
    }

    public static RGBColor getRGB(EnumDyeColor color) {
        switch (color) {
            case WHITE:
                return new RGBColor(255, 255, 255);
            case BLACK:
                return getRGB(EnumDyeColor.GRAY).div(5);
            case BLUE:
                return new RGBColor(0, 0, 255);
            case BROWN:
                return new RGBColor(139, 69, 19);
            case CYAN:
                return new RGBColor(0, 255, 255);
            case GRAY:
                return new RGBColor(128,128,128);
            case GREEN:
                return new RGBColor(0, 128, 0);
            case LIGHT_BLUE:
                return new RGBColor(173, 216, 230);
            case LIME:
                return new RGBColor(0, 255, 0);
            case MAGENTA:
                return new RGBColor(255, 0, 255);
            case ORANGE:
                return new RGBColor(255, 165, 0);
            case PINK:
                return new RGBColor(255, 192, 203);
            case PURPLE:
                return new RGBColor(128 , 0, 128);
            case RED:
                return new RGBColor(255, 0, 0);
            case SILVER:
                return new RGBColor(192, 192, 192);
            case YELLOW:
                return new RGBColor(255, 255, 0);
            default:
                return new RGBColor(0, 0, 0);
        }
    }

    public enum Color {

        WHITE(15461355, 15790320, 16777215, 'f', 0), //
        ORANGE(13399095, 15435844, 16228709, '6', 1), //
        MAGENTA(11551162, 12801229, 14645736, '1', 2), //
        LIGHT_BLUE(5075908, 6719955, 10862322, 'b', 3), //
        YELLOW(12234014, 14602026, 16443695, 'w', 4), //
        LIME(3913776, 4312372, 9433222, 'a', 5), //
        PINK(11561083, 14188952, 15250369, 'd', 6), //
        GRAY(2829099, 4408131, 5723991, '8', 7), //
        SILVER(16777215, 11250603, 16777215, '7', 8), //
        CYAN(1662072, 2651799, 4622499, '3', 9), //
        PURPLE(5840271, 8073150, 10248401, '5', 10), //
        BLUE(1515891, 2437522, 3425215, '1', 11), //
        BROWN(3021583, 5320730, 6502427, '4', 12), //
        GREEN(2437903, 3887386, 5732390, '2', 13), //
        RED(8857894, 11743532, 14695992, 'c', 14), //
        BLACK(263172, 1973019, 2829099, '0', 15), //
        NONE(-1, -1, -1, (char) -1, -1), //
        ANY(-1, -1, -1, (char) -1, -1);

        public static Color[] COLORS = {WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME, PINK, GRAY, SILVER, CYAN, PURPLE,
                BLUE, BROWN, GREEN, RED, BLACK};
        public static Color[] WIRE_COLORS = {WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME, PINK, GRAY, SILVER, CYAN, PURPLE,
                BLUE, BROWN, GREEN, RED, BLACK, NONE};

        private int hexDark, hex, hexLight, meta;
        private char chatColorChar;

        Color(int hexDark, int hex, int hexLight, char chatColorChar, int meta) {

            this.hexDark = hexDark;
            this.hex = hex;
            this.hexLight = hexLight;
            this.chatColorChar = chatColorChar;
        }

        public int getHexDark() {
            return hexDark;
        }

        public int getHex() {
            return hex;
        }

        public int getHexLight() {
            return hexLight;
        }

        public String getChatColor() {
            return String.valueOf('\u00a7') + chatColorChar;
        }

        public char getChatColorChar() {
            return chatColorChar;
        }

        public int getMetadata() {
            return meta;
        }

        public boolean matches(Color color) {
            if (this == ANY || color == ANY)
                return true;
            return this == color;
        }

        public boolean canConnect(Color color) {
            if (this == ANY || color == ANY || this == NONE || color == NONE)
                return true;

            return this == color;
        }

        public static Color translateDyeColor(EnumDyeColor color) {
            return COLORS[color.getMetadata()];
        }
    }
}
