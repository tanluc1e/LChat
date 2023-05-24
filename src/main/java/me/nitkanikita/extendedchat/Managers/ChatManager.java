package me.nitkanikita.extendedchat.Managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.logging.Logger;
import me.nitkanikita.extendedchat.Main;
import me.nitkanikita.extendedchat.Managers.MessageStorage.MessageStorage;
import me.nitkanikita.extendedchat.Types.Chat;
import me.nitkanikita.extendedchat.Types.Message.Message;
import org.bukkit.entity.Player;

public class ChatManager {
   private ConfigManager configManager;
   private Random rnd = new Random();
   private HashMap<String, Chat> chats;

   public ChatManager(ConfigManager configManager) {
      this.configManager = configManager;
      this.chats = configManager.LoadChats();
   }

   public void Load() {
      this.chats = this.configManager.LoadChats();
   }

   public Chat getChat(String prefix) {
      Chat result = null;
      Iterator var3 = this.chats.entrySet().iterator();

      Entry chat;
      while(var3.hasNext()) {
         chat = (Entry)var3.next();
         if (((Chat)chat.getValue()).getPrefix().equals("")) {
            result = (Chat)chat.getValue();
         }
      }

      var3 = this.chats.entrySet().iterator();

      while(var3.hasNext()) {
         chat = (Entry)var3.next();
         if (((Chat)chat.getValue()).getPrefix().equals(prefix)) {
            result = (Chat)chat.getValue();
         }
      }

      return result;
   }

   public void Message(String content, Player player) {
      Chat chat = this.getChat(content.substring(0, 1));
      if (chat != null) {
         String msgId = UUID.randomUUID().toString();
         msgId = msgId.substring(msgId.length() - 12);
         String msg = content.replace(chat.getPrefix(), "");
         Message message = new Message(player, msg, chat, msgId);
         chat.Send(player, chat.Parse(msg, player, msgId));
         MessageStorage.AddMessageToStorage(message);
      }
   }


   public void PrintChats() {
      Logger log = Main.i.getLogger();
      log.info(" ");
      log.info("CHATS");
      Iterator var2 = this.chats.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<String, Chat> chat = (Entry)var2.next();
         log.info(((Chat)chat.getValue()).toString());
      }

   }
}
