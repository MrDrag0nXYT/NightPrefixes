package zxc.MrDrag0nXYT.nightPrefixes.util.config;

import org.bukkit.configuration.file.YamlConfiguration;
import zxc.MrDrag0nXYT.nightPrefixes.NightPrefixes;

import java.io.File;
import java.util.List;

public class Config {

    private final NightPrefixes plugin;
    private File file;
    private YamlConfiguration config;

    public Config(NightPrefixes plugin) {
        this.plugin = plugin;

        init();
        updateConfig();
    }

    private void init() {
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }
        try {
            config.load(file);
        } catch (Exception e) {
            plugin.getLogger().severe(String.valueOf(e));
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            plugin.getLogger().severe(String.valueOf(e));
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }



    /*
     * Checking config values
     */

    private void checkConfigValue(String key, Object defaultValue) {
        if (!config.contains(key)) {
            config.set(key, defaultValue);
        }
    }

    private void updateConfig() {
        checkConfigValue("enable-metrics", true);
        checkConfigValue("database.type", "SQLITE");
        checkConfigValue("default-prefix", "");
        checkConfigValue("max-length", 32);

        checkConfigValue("messages.no-permission", List.of("<#745c97>NightPrefixes <#c0c0c0>› <#fcfcfc>У вас <#d45079>недостаточно прав</#d45079> для выполнения этого действия!"));
        checkConfigValue("messages.command.reload.success", List.of("<#745c97>NightPrefixes <#c0c0c0>› <#fcfcfc>Плагин <#ace1af>успешно перезагружен</#ace1af>"));
        checkConfigValue("messages.command.blocked", List.of("<#745c97>NightPrefixes <#c0c0c0>› <#fcfcfc>Вы успешно заблокировали игроку <#745c97>%player%</#745c97> возможность менять префикс!"));
        checkConfigValue("messages.command.unblocked", List.of("<#745c97>NightPrefixes <#c0c0c0>› <#fcfcfc>Вы успешно разблокировали игроку <#745c97>%player%</#745c97> возможность менять префикс!"));
        checkConfigValue("messages.command.usage", List.of(
                "",
                "<#745c97>NightPrefixes <#c0c0c0>› <#fcfcfc>Информация",
                " <#c0c0c0>‣ <click:suggest_command:'/nightprefixes reload'><#745c97>/nightprefixes reload</click> <#c0c0c0>- <#fcfcfc>перезагрузить плагин",
                " <#c0c0c0>‣ <click:suggest_command:'/nightprefixes set'><#745c97>/nightprefixes set префикс</click> <#c0c0c0>- <#fcfcfc>установить префикс",
                " <#c0c0c0>‣ <click:suggest_command:'/nightprefixes reset'><#745c97>/nightprefixes reset</click> <#c0c0c0>- <#fcfcfc>сбросить префикс",
                ""
        ));
        checkConfigValue("messages.command.set", List.of("<#745c97>NightPrefixes <#c0c0c0>› <#fcfcfc>Префикс <#745c97>%prefix%</#745c97> был установлен"));
        checkConfigValue("messages.command.set-blocked", List.of("<#745c97>NightPrefixes <#c0c0c0>› <#fcfcfc>Функция установки сообщения <#d45079>заблокирована за нарушение правил</#d45079>"));
        checkConfigValue("messages.command.too-long", List.of("<#745c97>NightPrefixes <#c0c0c0>› <#fcfcfc>Префикс <#d45079>слишком длинный</#d45079>! Максимально допустимая длина: <#745c97>%prefix_max_length% символа</#745c97>"));
        checkConfigValue("messages.command.reset", List.of("<#745c97>NightPrefixes <#c0c0c0>› <#fcfcfc>Префикс был сброшен"));
        checkConfigValue("messages.command.admin-reset", List.of("<#745c97>NightPrefixes <#c0c0c0>› <#fcfcfc>Префикс игрока <#745c97>%player%</#745c97> был сброшен"));

        save();
    }

}
