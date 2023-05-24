package me.nitkanikita.extendedchat.Managers;

import me.nitkanikita.extendedchat.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;

public class ChatCooldown {
    private static Map<Player, Long> cooldowns = new HashMap<>();
    private static int cooldownSeconds;
    private static List<String> messages;
    private static Random random;

    public static void initialize() {
        FileConfiguration config = Main.i.getConfig();
        cooldownSeconds = config.getInt("cooldown.seconds");
        messages = config.getStringList("cooldown.messages");
        random = new Random();
    }

    public static boolean hasCooldown(Player player) {
        if (cooldowns.containsKey(player)) {
            long secondsLeft = cooldownSeconds - ((System.currentTimeMillis() - cooldowns.get(player)) / 1000);
            return secondsLeft > 0;
        }
        return false;
    }

    public static void setCooldown(Player player) {
        cooldowns.put(player, System.currentTimeMillis());
    }

    public static String getRandomMessage(long secondsLeft) {
        if (messages != null && !messages.isEmpty()) {
            int index = random.nextInt(messages.size());
            String message = messages.get(index);
            message = ChatColor.translateAlternateColorCodes('&', message);
            message = message.replace("{time}", String.valueOf(secondsLeft));
            message = message.replace("\\n", "\n");
            return message;
        }
        return null;
    }

    public static long getCooldownSeconds(Player player) {
        if (cooldowns.containsKey(player)) {
            long secondsLeft = cooldownSeconds - ((System.currentTimeMillis() - cooldowns.get(player)) / 1000);
            return secondsLeft > 0 ? secondsLeft : 0;
        }
        return 0;
    }

}
