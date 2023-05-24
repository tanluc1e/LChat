package me.nitkanikita.extendedchat.Utils;

import org.bukkit.entity.Player;

public class IDUtils {
   public static int idGenPlayer(Player o) {
      int id = o.hashCode();
      int length = String.valueOf(id).length();
      int Max_Length = 5;
      if (String.valueOf(id).length() > Max_Length) {
         id = (int)((double)id / Math.pow(10.0D, (double)(length - Max_Length)));
      }

      return id;
   }

   public static int idGenString(String o) {
      int id = o.hashCode();
      int length = String.valueOf(id).length();
      int Max_Length = 5;
      if (String.valueOf(id).length() > Max_Length) {
         id = (int)((double)id / Math.pow(10.0D, (double)(length - Max_Length)));
      }

      return id;
   }
}
