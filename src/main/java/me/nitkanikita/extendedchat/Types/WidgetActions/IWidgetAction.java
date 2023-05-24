package me.nitkanikita.extendedchat.Types.WidgetActions;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public interface IWidgetAction {
   TextComponent Register(TextComponent var1);

   TextComponent ParseAndRegister(TextComponent var1, String var2, Player var3, String var4);
}
