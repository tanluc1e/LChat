package me.nitkanikita.extendedchat;

import java.io.File;
import java.util.logging.Logger;
import me.nitkanikita.extendedchat.Listeners.ChatListener;
import me.nitkanikita.extendedchat.Managers.ChatManager;
import me.nitkanikita.extendedchat.Managers.CommandManager;
import me.nitkanikita.extendedchat.Managers.ChatCooldown;
import me.nitkanikita.extendedchat.Managers.ConfigManager;
import me.nitkanikita.extendedchat.Managers.WidgetManagers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
   public static Main i;
   public static Logger console;
   public ChatManager chatManager;
   public ConfigManager configManager;
   public WidgetManagers widgetManagers;

   public void onEnable() {
      Updater.init();
      FileConfiguration config = this.getConfig();
      if (!(new File(this.getDataFolder(), "config.yml")).exists()) {
         this.saveDefaultConfig();
         config = this.getConfig();
      }

      i = this;
      console = this.getLogger();
      this.configManager = new ConfigManager(config);
      this.chatManager = new ChatManager(this.configManager);
      this.widgetManagers = new WidgetManagers(this.configManager);
      this.configManager.LoadReplySettings();
      this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
      this.getCommand("exchat").setExecutor(new CommandManager());
      this.chatManager.PrintChats();
      this.widgetManagers.PrintWidgets();

      ChatCooldown.initialize();
   }
}
