package me.nitkanikita.extendedchat.Managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Logger;
import me.nitkanikita.extendedchat.Main;
import me.nitkanikita.extendedchat.Types.Widget;

public class WidgetManagers {
   private ConfigManager configManager;
   private HashMap<String, Widget> widgets;

   public WidgetManagers(ConfigManager configManager) {
      this.configManager = configManager;
      this.widgets = configManager.LoadWidgets();
   }

   public void Load() {
      this.widgets = this.configManager.LoadWidgets();
   }

   public Widget getWidget(String name) {
      return (Widget)this.widgets.get(name);
   }

   public void PrintWidgets() {
      Logger log = Main.i.getLogger();
      log.info(" ");
      log.info("WIDGETS");
      Iterator var2 = this.widgets.entrySet().iterator();

      while(var2.hasNext()) {
         Entry<String, Widget> widget = (Entry)var2.next();
         log.info(((Widget)widget.getValue()).toString());
      }

   }
}
