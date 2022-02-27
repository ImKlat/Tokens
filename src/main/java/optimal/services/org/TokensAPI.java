package optimal.services.org;

/*
 * Created by ImKlat
 * Project: Tokens-Hooker-MySQL
 * Date: 26/2/2022 @ 07:54
 */

import optimal.services.org.utils.PlayerData;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TokensAPI {
    public int getTokens(Player player, UUID uuid){
        return PlayerData.getTokens(Main.getInstance().getMySQl(), uuid);
    }
}
