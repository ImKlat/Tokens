package optimal.services.org.commands;

/*
 * Created by ImKlat
 * Project: Tokens-Hooker-MySQL
 * Date: 25/2/2022 @ 16:06
 */

import optimal.services.org.Main;
import optimal.services.org.listeners.ShopListener;
import optimal.services.org.utils.CC;
import optimal.services.org.utils.PlayerData;
import optimal.services.org.utils.command.BaseCommand;
import optimal.services.org.utils.command.Command;
import optimal.services.org.utils.command.CommandArgs;
import optimal.services.org.utils.config.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.UUID;

public class TokensCommand extends BaseCommand implements Listener {
    private final ConfigFile lang = Main.getInstance().getLangConfig();

    @Command(name = "tokens")

    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        UUID uuid = player.getUniqueId();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate(lang.getString("COMMANDS.TOKENS-BALANCE").replaceAll("%TOKENS%", Integer.toString(PlayerData.getTokens(Main.getInstance().getMySQl(), uuid))).replaceAll("%PLAYER%", player.getName())));
            return;
        }

        switch (args[0]) {
            default:
                CC.sendHelp(player);
                break;
            case "help":
                CC.sendHelp(player);
                break;
            case "give":
                if (!(player.hasPermission("tokens.give"))) {
                    player.sendMessage(CC.translate("&cNo permission."));
                    return;
                }

                if (args.length < 3 || args.length > 4) {
                    player.sendMessage(CC.translate("&cUsage: /tokens give <player> <amount>"));
                    return;
                }

                if (args.length == 3) {
                    Player target = Bukkit.getPlayer(args[1]);
                    int amount = Integer.parseInt(args[2]);
                    if (target == null) {
                        player.sendMessage(CC.translate(lang.getString("UTILS.TARGET-NOT-FOUND")));
                        return;
                    }
                    Integer f2 = CC.tryParseInt(args[2]);
                    if (f2 == null) {
                        player.sendMessage(CC.translate("&cUsage: /tokens give <player> <amount>"));
                        return;
                    }
                    int actualTokens = PlayerData.getTokens(Main.getInstance().getMySQl(), target.getUniqueId());
                    PlayerData.giveTokens(Main.getInstance().getMySQl(), target.getUniqueId(), actualTokens + amount);
                    player.sendMessage(CC.translate(lang.getString("COMMANDS.TOKENS-GIVE").replaceAll("%TOKENS%", Integer.toString(amount)).replaceAll("%PLAYER%", player.getName()).replaceAll("%TARGET%", target.getName())));
                    break;
                }
            case "remove":
                if (!(player.hasPermission("tokens.remove"))) {
                    player.sendMessage(CC.translate("&cNo permission."));
                    return;
                }

                if (args.length < 3 || args.length > 4) {
                    player.sendMessage(CC.translate("&cUsage: /tokens remove <player> <amount>"));
                    return;
                }

                if (args.length == 3) {
                    Player target = Bukkit.getPlayer(args[1]);
                    int amount = Integer.parseInt(args[2]);
                    if (target == null) {
                        player.sendMessage(CC.translate(lang.getString("UTILS.TARGET-NOT-FOUND")));
                        return;
                    }
                    Integer f2 = CC.tryParseInt(args[2]);
                    if (f2 == null) {
                        player.sendMessage(CC.translate("&cUsage: /tokens remove <player> <amount>"));
                        return;
                    }
                    int actualTokens = PlayerData.getTokens(Main.getInstance().getMySQl(), target.getUniqueId());
                    PlayerData.giveTokens(Main.getInstance().getMySQl(), target.getUniqueId(), actualTokens - amount);
                    player.sendMessage(CC.translate(lang.getString("COMMANDS.TOKENS-REMOVE").replaceAll("%TOKENS%", Integer.toString(amount)).replaceAll("%PLAYER%", player.getName()).replaceAll("%TARGET%", target.getName())));
                    break;
                }
            case "set":
                if (!(player.hasPermission("tokens.set"))) {
                    player.sendMessage(CC.translate("&cNo permission."));
                    return;
                }

                if (args.length < 3 || args.length > 4) {
                    player.sendMessage(CC.translate("&cUsage: /tokens set <player> <amount>"));
                    return;
                }

                if (args.length == 3) {
                    Player target = Bukkit.getPlayer(args[1]);
                    int amount = Integer.parseInt(args[2]);
                    if (target == null) {
                        player.sendMessage(CC.translate(lang.getString("UTILS.TARGET-NOT-FOUND")));
                        return;
                    }
                    Integer f2 = CC.tryParseInt(args[2]);
                    if (f2 == null) {
                        player.sendMessage(CC.translate("&cUsage: /tokens set <player> <amount>"));
                        return;
                    }
                    int actualTokens = PlayerData.getTokens(Main.getInstance().getMySQl(), target.getUniqueId());
                    PlayerData.giveTokens(Main.getInstance().getMySQl(), target.getUniqueId(), actualTokens - actualTokens + amount);
                    player.sendMessage(CC.translate(lang.getString("COMMANDS.TOKENS-SET").replaceAll("%TOKENS%", Integer.toString(amount)).replaceAll("%PLAYER%", player.getName()).replaceAll("%TARGET%", target.getName())));
                    break;
                }
            case "pay":
                if (!(player.hasPermission("tokens.pay"))) {
                    player.sendMessage(CC.translate("&cNo permission."));
                    return;
                }

                if (args.length < 3 || args.length > 4) {
                    player.sendMessage(CC.translate("&cUsage: /tokens pay <player> <amount>"));
                    return;
                }

                if (args.length == 3) {
                    Player target = Bukkit.getPlayer(args[1]);
                    int amount = Integer.parseInt(args[2]);
                    if (target == null) {
                        player.sendMessage(CC.translate(lang.getString("UTILS.TARGET-NOT-FOUND")));
                        return;
                    }
                    if (target == player) {
                        player.sendMessage(CC.translate("&cYou can't pay yourself"));
                        return;
                    }
                    Integer f2 = CC.tryParseInt(args[2]);
                    if (f2 == null) {
                        player.sendMessage(CC.translate("&cUsage: /tokens pay <player> <amount>"));
                        return;
                    }

                    int tActualTokens = PlayerData.getTokens(Main.getInstance().getMySQl(), target.getUniqueId());
                    int actualTokens = PlayerData.getTokens(Main.getInstance().getMySQl(), player.getUniqueId());
                    PlayerData.giveTokens(Main.getInstance().getMySQl(), player.getUniqueId(), actualTokens - amount);
                    PlayerData.giveTokens(Main.getInstance().getMySQl(), target.getUniqueId(), tActualTokens + amount);

                    player.sendMessage(CC.translate(lang.getString("COMMANDS.TOKENS-PAY").replaceAll("%TOKENS%", Integer.toString(amount)).replaceAll("%PLAYER%", player.getName()).replaceAll("%TARGET%", target.getName())));
                    break;
                }
            case "shop":
                if (!(player.hasPermission("tokens.shop"))) {
                    player.sendMessage(CC.translate("&cNo permission."));
                    return;
                }

                if (args.length > 2) {
                    player.sendMessage(CC.translate("&cUsage: /tokens shop "));
                    return;
                }

                if (args.length == 1) {
                    player.openInventory(ShopListener.getShop());
                    break;
                }
        }
    }
}

