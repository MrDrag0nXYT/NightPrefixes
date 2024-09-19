package zxc.MrDrag0nXYT.nightPrefixes.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Utilities {

    public static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static Component setColor(String from) {
        return miniMessage.deserialize(from);
    }
}
