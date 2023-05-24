package me.nitkanikita.extendedchat.Types.WidgetActions.Actions;

import me.nitkanikita.extendedchat.Types.WidgetActions.IWidgetAction;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.entity.Player;

public class RunCommandAction implements IWidgetAction {
   private String cmd;

   public RunCommandAction(String command) {
      this.cmd = command;
   }

   public TextComponent Register(TextComponent source) {
      source.setClickEvent(new ClickEvent(Action.RUN_COMMAND, this.cmd));
      return source;
   }

   public TextComponent ParseAndRegister(TextComponent source, String message, Player player, String id) {
      TextComponent component = (TextComponent) source.duplicate();
      String txt = String.copyValueOf(this.cmd.toCharArray());
      txt = txt.replaceAll("\\{message\\}", message.replace("\\", "\\\\"));
      txt = txt.replaceAll("\\{nick\\}", player.getDisplayName());
      txt = txt.replaceAll("\\{message_id\\}", id);
      txt = PlaceholderAPI.setPlaceholders(player, txt);
      component.setClickEvent(new ClickEvent(Action.RUN_COMMAND, txt));
      return component;
   }
}
