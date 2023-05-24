package me.nitkanikita.extendedchat.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import me.nitkanikita.extendedchat.Types.Chat;
import me.nitkanikita.extendedchat.Types.Widget;
import me.nitkanikita.extendedchat.Types.WidgetActions.Actions.ActionType;
import me.nitkanikita.extendedchat.Types.WidgetActions.Actions.PasteCommandAction;
import me.nitkanikita.extendedchat.Types.WidgetActions.Actions.RunCommandAction;
import me.nitkanikita.extendedchat.Types.WidgetActions.Actions.ShowTooltipsAction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
   private FileConfiguration config;

   public ConfigManager(FileConfiguration config) {
      this.config = config;
   }

   public HashMap<String, Chat> LoadChats() {
      HashMap<String, Chat> chats = new HashMap();
      Iterator var2 = this.config.getConfigurationSection("chat").getKeys(false).iterator();

      while(var2.hasNext()) {
         String chatName = (String)var2.next();
         ConfigurationSection chatSettings = this.config.getConfigurationSection("chat." + chatName);
         String format = chatSettings.getString("format") != null ? chatSettings.getString("format") : "{nick} > {message}";
         String prefix = chatSettings.getString("prefix") != null ? chatSettings.getString("prefix") : "";
         boolean plots = chatSettings.getBoolean("plots");
         boolean reply = chatSettings.getBoolean("reply");
         int radius = chatSettings.getInt("radius");
         Chat chat = new Chat(chatName, format, prefix, plots, radius, reply);
         chats.put(chatName, chat);
      }

      return chats;
   }

   public void LoadReplySettings() {
      ConfigurationSection replySettings = this.config.getConfigurationSection("reply");
      ReplySettings.format = (ArrayList)replySettings.getStringList("format");
      ReplySettings.error_msg = replySettings.getString("error_msg");
   }

   public HashMap<String, Widget> LoadWidgets() {
      HashMap<String, Widget> widgets = new HashMap();

      String widgetName;
      Widget widget;
      for(Iterator var2 = this.config.getConfigurationSection("widgets").getKeys(false).iterator(); var2.hasNext(); widgets.put(widgetName, widget)) {
         widgetName = (String)var2.next();
         ConfigurationSection widgetSettings = this.config.getConfigurationSection("widgets." + widgetName);
         String text = widgetSettings.getString("text");
         widget = new Widget(widgetName, text);
         if (this.config.getConfigurationSection("widgets." + widgetName + ".actions") != null) {
            Iterator var7 = this.config.getConfigurationSection("widgets." + widgetName + ".actions").getKeys(false).iterator();

            while(var7.hasNext()) {
               String actionName = (String)var7.next();
               ActionType actionType = ActionType.valueOf(actionName);
               switch(actionType) {
               case RUN_COMMAND:
                  widget.AddWidgetAction(new RunCommandAction(widgetSettings.getString("actions." + actionName)));
                  break;
               case SUGGEST_COMMAND:
                  widget.AddWidgetAction(new PasteCommandAction(widgetSettings.getString("actions." + actionName)));
                  break;
               case TOOLTIP:
                  widget.AddWidgetAction(new ShowTooltipsAction((ArrayList)widgetSettings.getStringList("actions." + actionName)));
               }
            }
         }
      }

      return widgets;
   }
}
