package me.nitkanikita.extendedchat.Types.Message;

import me.nitkanikita.extendedchat.Types.Chat;
import org.bukkit.entity.Player;

public class Message {
   public Player author;
   public String content;
   public Chat chat;
   public String id;

   public Message(Player author, String content, Chat chat, String id) {
      this.author = author;
      this.content = content;
      this.chat = chat;
      this.id = id;
   }
}
