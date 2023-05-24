package me.nitkanikita.extendedchat.Managers.MessageStorage;

import java.util.HashMap;
import me.nitkanikita.extendedchat.Types.Message.Message;

public class MessageStorage {
   private static HashMap<String, Message> messages = new HashMap();

   public static void AddMessageToStorage(Message msg) {
      messages.put(msg.id, msg);
   }

   public static Message GetPlayerMessage(String messageId) {
      return (Message)messages.get(messageId);
   }
}
