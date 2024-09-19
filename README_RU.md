<center>
<img src="docs/NightPrefixes-Title.png">
<p><b>NightPrefixes</b> - плагин для Paper на кастомные префиксы!</p>

<b><a href="README.md">English</a></b> | <u>Russian</u>
</center>

***

# 🚀 Особенности

- **Кэширование** для заполнителей
- Администрация может **заблокировать** игрокам смену префиксов или сбросить его
- Лимит **по длине**
- **Настраиваемый** префикс если игрок не установил
- **Поддержка HEX цветов** через MiniMessage

# 💾 Требования

- Java **17+**
- Paper (или его форки, такие как Purpur) **1.18 или новее** <u>(Spigot и CraftBukkit НЕ ПОДДЕРЖИВАЮТСЯ И НЕ БУДУТ!!!)</u>

# ⚡ Команды и права

## /nightprefixes

> [!TIP]
> Сокращённые варианты: **/nightprefix**, **/nprefix**, **/np**, **/prefix**

#### Использование для администрации:
- **/nightprefixes admin reload** - Reload plugin
    - Право: `nightprefixes.admin.reload`
- **/nightprefixes admin reset player** - Reset player's prefix
    - Право: `nightprefixes.admin.prefix.reset`
- **/nightprefixes admin ban player** - Block player ability to change prefix
    - Право: `nightprefixes.admin.ban`
- **/nightprefixes admin unban player** - Block player ability to change prefix
    - Право: `nightprefixes.admin.unban`

#### Использование для игроков:
- **/nightprefixes set prefix** - Set prefix
    - Право: `nightprefixes.player.prefix.set`
- **/nightprefixes reset** - Reset prefix
    - Право: `nightprefixes.player.prefix.reset`

# 💫 Заполнители (плейсхолдеры)

#### %nightprefixes_prefix%
Возвращает префикс игрока, если не установлен строку `default-prefix` из `config.yml`

# 🌐 Локализация (#todo)

На данный момент плагин доступен только на русском языке

# 📜 Метрика

Вы можете посмотреть статистику использования плагина на серверах [here](https://bstats.org/plugin/bukkit/NightPrefixes/23404) и отключить отправку в `config.yml` установив `enable-metrics: false`



***



# ⚙ Дополнительно

### Если вы нашли баг или хотите помочь в разработке - не стесняйтесь обращаться ко мне
- Ссылки на контакты [тут](https://drakoshaslv.ru/)

### Также (по желанию) вы можете дать мне денег:
- [DonationAlerts](https://www.donationalerts.com/r/mrdrag0nxyt)
- TON:
  ```
  UQAwUJ_DWQ26_b94mFAy0bE1hrxVRHrq51umphFPreFraVL2
  ```
- ETH:
  ```
  0xf5D0Ab258B0f8EeA7EA07cF1050B35cc12E06Ab0
  ```



<center><h3>Сделано специально для <a href="https://nshard.ru">NightShard</a></h3></center>