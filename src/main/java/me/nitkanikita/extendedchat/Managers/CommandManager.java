package me.nitkanikita.extendedchat.Managers;

import java.util.Random;
import java.util.UUID;
import me.nitkanikita.extendedchat.Main;
import me.nitkanikita.extendedchat.Managers.MessageStorage.MessageStorage;
import me.nitkanikita.extendedchat.Types.Message.Message;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
   private Random rnd = new Random();

   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (strings.length < 1) {
         commandSender.sendMessage("§9§lExtendedChat");
         commandSender.sendMessage("§b§l - §3/exchat reply <mesage_id> <reply_text>");
         commandSender.sendMessage("§b§l - §3/exchat reload");
      } else {
         if (strings[0].equals("reply") && commandSender instanceof Player) {
            Player p = (Player)commandSender;
            Message messageReply = MessageStorage.GetPlayerMessage(strings[1]);
            if (ChatCooldown.hasCooldown(p)) {
               long secondsLeft = ChatCooldown.getCooldownSeconds(p);
               String cooldownMessage = ChatCooldown.getRandomMessage(secondsLeft);
               if (cooldownMessage != null) {
                  p.sendMessage(cooldownMessage);
               }
               return true; // Prevent further execution of the command
            }

            if (!messageReply.chat.reply) {
               p.spigot().sendMessage(new TextComponent("&с:("));
            } else if (strings.length < 3) {
               p.sendMessage("§cYou must provide a reply text.");
            } else {
               StringBuilder StrBuild = new StringBuilder();

               for (int i = 2; i < strings.length; ++i) {
                  StrBuild.append(strings[i]).append(" ");
               }

               String msgId = UUID.randomUUID().toString() + this.rnd.nextInt(999);
               msgId = msgId.substring(msgId.length() - 12);
               Message message = new Message(p, StrBuild.toString(), messageReply.chat, msgId);
               MessageStorage.AddMessageToStorage(message);
               messageReply.chat.Send(p, message.chat.Parse(StrBuild.toString(), p, msgId), messageReply);
               ChatCooldown.setCooldown(p);
            }
         }

         if (strings[0].equals("reload")) {
            Main.i.configManager.LoadReplySettings();
            Main.i.chatManager.Load();
            Main.i.widgetManagers.Load();
            commandSender.sendMessage("§9§lExtendedChat §7 - config reloaded");
         }
      }

      return true;
   }
}
