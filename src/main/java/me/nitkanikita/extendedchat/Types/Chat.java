package me.nitkanikita.extendedchat.Types;

import java.util.Iterator;
import me.nitkanikita.extendedchat.Main;
import me.nitkanikita.extendedchat.Managers.ReplySettings;
import me.nitkanikita.extendedchat.Types.Message.Message;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class Chat {
   private String chatName;
   private String format;
   private String prefix;
   public boolean plots;
   public boolean reply;
   private int radius;

   public Chat(String chatName, String format, String prefix, boolean plots, int radius, boolean reply) {
      this.chatName = chatName;
      this.format = format;
      this.prefix = prefix;
      this.plots = plots;
      this.radius = radius;
      this.reply = reply;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public TextComponent Parse(String message, Player player, String msgId) {
      String txt = String.valueOf(this.format.toCharArray(), 0, this.format.length());
      txt = txt.replaceAll("\\{message\\}", message.replace("\\", "\\\\"));
      txt = txt.replaceAll("\\{player\\}", player.getDisplayName());
      txt = PlaceholderAPI.setPlaceholders(player, txt);
      TextComponent textComponent = new TextComponent();
      String[] strings = txt.split(" ");
      String[] var7 = strings;
      int var8 = strings.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String s = var7[var9];
         TextComponent parsed;
         if (s.startsWith("{wdg_")) {
            String widgetName = s.replaceAll("[\\{\\}]", "").replaceAll("wdg_", "");
            parsed = Main.i.widgetManagers.getWidget(widgetName).Parse(message, player, msgId);
         } else {
            parsed = new TextComponent(s);
         }

         textComponent.addExtra(parsed);
         textComponent.addExtra(" ");
      }

      return textComponent;
   }

   public void Send(Player sender, TextComponent msg) {
      Iterator var3;
      Player onlinePlayer;
      if (this.radius < 0) {
         var3 = Main.i.getServer().getOnlinePlayers().iterator();

         while(var3.hasNext()) {
            onlinePlayer = (Player)var3.next();
            onlinePlayer.spigot().sendMessage(msg);
         }
      }

      if (this.plots) {
      }

      if (this.radius > 0) {
         var3 = Main.i.getServer().getOnlinePlayers().iterator();

         while(var3.hasNext()) {
            onlinePlayer = (Player)var3.next();
            if (sender.getLocation().distance(onlinePlayer.getLocation()) < (double)this.radius) {
               onlinePlayer.spigot().sendMessage(msg);
            }
         }
      }

   }

   public void Send(Player sender, TextComponent msg, Message reply) {
      if (reply.chat.reply) {
         Iterator var4;
         Player onlinePlayer;
         Iterator var6;
         String string;
         String formatted;
         if (this.radius < 0) {
            var4 = Main.i.getServer().getOnlinePlayers().iterator();

            while(var4.hasNext()) {
               onlinePlayer = (Player)var4.next();
               var6 = ReplySettings.format.iterator();

               while(var6.hasNext()) {
                  string = (String)var6.next();
                  if (string.equals("{message_component}")) {
                     onlinePlayer.spigot().sendMessage(new TextComponent(msg));
                     return;
                  }

                  formatted = string.replaceAll("\\{reply_nick\\}", reply.author.getDisplayName()).replaceAll("\\{reply_msg\\}", reply.content.replaceAll("&", "§"));
                  onlinePlayer.spigot().sendMessage(new TextComponent(formatted));
               }
            }
         }

         if (this.plots) {
         }

         if (this.radius > 0) {
            var4 = Main.i.getServer().getOnlinePlayers().iterator();

            while(true) {
               do {
                  if (!var4.hasNext()) {
                     return;
                  }

                  onlinePlayer = (Player)var4.next();
               } while(!(sender.getLocation().distance(onlinePlayer.getLocation()) < (double)this.radius));

               var6 = ReplySettings.format.iterator();

               while(var6.hasNext()) {
                  string = (String)var6.next();
                  formatted = string.replaceAll("\\{reply_nick\\}", reply.author.getDisplayName()).replaceAll("\\{reply_msg\\}", reply.content.replaceAll("&", "§")).replaceAll("\\{message_component\\}", msg.toLegacyText());
                  onlinePlayer.spigot().sendMessage(new TextComponent(formatted));
               }
            }
         }
      } else {
         sender.spigot().sendMessage(new TextComponent(ReplySettings.error_msg.replaceAll("&", "§")));
      }

   }

   public String toString() {
      return '\n' + this.chatName + "{" + '\n' + ", format='" + this.format + "'" + '\n' + ", prefix='" + this.prefix + "'" + '\n' + ", plots=" + this.plots + '\n' + ", radius=" + this.radius + '\n' + '}';
   }
}
