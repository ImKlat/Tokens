package optimal.services.org.listeners;

/*
 * Created by ImKlat
 * Project: Tokens-Hooker-MySQL
 * Date: 25/2/2022 @ 17:39
 */

import optimal.services.org.Main;
import optimal.services.org.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    private JoinListener plugin;

    public JoinListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!PlayerData.hasPlayedBefore(Main.getInstance().getMySQl(), uuid)) {
            PlayerData.makePlayer(Main.getInstance().getMySQl(), uuid, player.getName());
        }
    }
}
