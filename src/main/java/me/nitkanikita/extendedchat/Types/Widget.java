package me.nitkanikita.extendedchat.Types;

import java.util.ArrayList;
import java.util.Iterator;
import me.nitkanikita.extendedchat.Types.WidgetActions.IWidgetAction;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class Widget {
   private String name;
   private String text;
   private ArrayList<IWidgetAction> actions;

   public Widget(String name, String text) {
      this.name = name;
      this.text = text;
      this.actions = new ArrayList();
   }

   public TextComponent Parse(String message, Player p, String id) {
      String txt = String.copyValueOf(this.text.toCharArray());
      txt = txt.replaceAll("\\{message\\}", message.replace("\\", "\\\\"));
      txt = txt.replaceAll("\\{nick\\}", p.getDisplayName());
      txt = PlaceholderAPI.setPlaceholders(p, txt);
      TextComponent textComponent = new TextComponent(txt);

      IWidgetAction action;
      for(Iterator var6 = this.actions.iterator(); var6.hasNext(); textComponent = action.ParseAndRegister(textComponent, message, p, id)) {
         action = (IWidgetAction)var6.next();
      }

      return textComponent;
   }

   public void AddWidgetAction(IWidgetAction action) {
      this.actions.add(action);
   }

   public String toString() {
      return '\n' + this.name + "{" + '\n' + ", text='" + this.text + "'" + '\n' + ", actions=" + this.actions.toString() + "'" + '\n' + '}';
   }
}
