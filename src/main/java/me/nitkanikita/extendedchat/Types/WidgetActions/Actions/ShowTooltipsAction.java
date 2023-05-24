package me.nitkanikita.extendedchat.Types.WidgetActions.Actions;

import java.util.ArrayList;
import me.nitkanikita.extendedchat.Types.WidgetActions.IWidgetAction;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;

public class ShowTooltipsAction implements IWidgetAction {
   private ArrayList<String> lines;

   public ShowTooltipsAction(ArrayList<String> lines) {
      this.lines = lines;
   }

   public TextComponent Register(TextComponent source) {
      String[] arr = new String[this.lines.size()];
      arr = (String[])this.lines.toArray(arr);
      String tooltipText = String.join("\n", arr);
      source.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] { new TextComponent(tooltipText) }));
      return source;
   }

   public TextComponent ParseAndRegister(TextComponent source, String message, Player player, String id) {
      TextComponent component = (TextComponent) source.duplicate();
      ArrayList<String> _lines = new ArrayList<>(this.lines);

      for (int i = 0; i < _lines.size(); ++i) {
         String txt = _lines.get(i);
         txt = txt.replaceAll("\\{message\\}", message.replace("\\", "\\\\"));
         txt = txt.replaceAll("\\{nick\\}", player.getDisplayName());
         txt = txt.replaceAll("\\{message_id\\}", id);
         txt = PlaceholderAPI.setPlaceholders(player, txt);
         _lines.set(i, txt);
      }

      String[] arr = new String[_lines.size()];
      arr = _lines.toArray(arr);
      String tooltipText = String.join("\n", arr);
      component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] { new TextComponent(tooltipText) }));
      return component;
   }
}