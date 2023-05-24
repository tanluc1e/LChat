package me.nitkanikita.extendedchat.Listeners;

import me.nitkanikita.extendedchat.Main;
import me.nitkanikita.extendedchat.Managers.ChatCooldown;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ChatListener implements Listener {

   @EventHandler
   public void onChatMessage(AsyncPlayerChatEvent event) {
      Player player = event.getPlayer();
      if (ChatCooldown.hasCooldown(player)) {
         long secondsLeft = ChatCooldown.getCooldownSeconds(player);
         String message = ChatCooldown.getRandomMessage(secondsLeft);
         if (message != null) {
            player.sendMessage(message);
         }
         event.setCancelled(true);
      } else {
         String content = event.getMessage();
         Main.i.chatManager.Message(content, player);
         event.setCancelled(true);
         ChatCooldown.setCooldown(player);
      }
   }

   private long getSecondsLeft(Player player) {
      if (ChatCooldown.hasCooldown(player)) {
         long currentTime = System.currentTimeMillis();
         long cooldownTime = ChatCooldown.getCooldownSeconds(player);
         long elapsedTime = currentTime - cooldownTime;
         long secondsLeft = ChatCooldown.getCooldownSeconds(player) - (elapsedTime / 1000);
         return secondsLeft > 0 ? secondsLeft : 0;
      }
      return 0;
   }




   @EventHandler
   public void OnJoin(PlayerJoinEvent e) {
      e.setJoinMessage("Aboba hehe");
   }
}
