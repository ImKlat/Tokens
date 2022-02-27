package optimal.services.org.utils;

/*
 * Created by ImKlat
 * Project: Tokens-Hooker-MySQL
 * Date: 20/2/2022 @ 18:39
 */

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CC {

 public String LINE = "&7&m--------------------------";

 public String translate(String in) {
  return ChatColor.translateAlternateColorCodes('&', in);
 }

 public List<String> translate(List<String> in) {
  return in.stream().map(CC::translate).collect(Collectors.toList());
 }

 public String strip(String in) {
  return ChatColor.stripColor(in);
 }

 public void sender(CommandSender sender, String in) {
  sender.sendMessage(translate(in));
 }

 public void message(Player player, String in) {
  player.sendMessage(translate(in));
 }

 public void broadcast(String in) {
  Bukkit.broadcastMessage(translate(in));
 }

 public void log(String in) {
  Bukkit.getConsoleSender().sendMessage(translate(in));
 }

 public static Integer tryParseInt(String string) {
  try {
   return Integer.parseInt(string);
  } catch (IllegalArgumentException ex) {
   return null;
  }
 }

 public static void sendHelp(CommandSender sender) {
  String[] helpMessage = new String[]{
          CC.translate(" "),
          CC.translate("&6&lTokens Arguments"),
          CC.translate(" "),
          CC.translate("&e/tokens help &7- Show tokens help"),
          CC.translate("&e/tokens give <player> <amount> &7- Give tokens to player"),
          CC.translate("&e/tokens remove <player> <amount> &7- Remove tokens of player"),
          CC.translate("&e/tokens set <player> <amount> &7- Set tokens to player"),
          CC.translate("&e/tokens pay <player> <amount> &7- Send tokens to a player with yours tokens"),
          CC.translate(" "),
  };
  sender.sendMessage(helpMessage);
 }
}