package optimal.services.org;

import lombok.Getter;
import lombok.Setter;
import optimal.services.org.commands.TokensCommand;
import optimal.services.org.listeners.JoinListener;
import optimal.services.org.utils.CC;
import optimal.services.org.utils.SQLUtil;
import optimal.services.org.utils.command.CommandManager;
import optimal.services.org.utils.config.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.ArrayList;

@Getter
@Setter
public final class Main extends JavaPlugin {
    private ConfigFile mainConfig;
    private ConfigFile langConfig;
    private CommandManager commandManager;
    private SQLUtil mySQLSetup;

    @Override
    public void onEnable() {
        mainConfig = new ConfigFile(this, "config.yml");
        loadConfigs();
        registerManagers();
        registerCommands();
        registerListeners();
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m--------------"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&eTokens &7| &fPlugin"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&r"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&fStatus: &aEnabled"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m--------------"));
        this.mySQLSetup = new SQLUtil("localhost", 3306, "tokens", "root", "");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands() {
        new TokensCommand();
    }

    public void registerListeners() {
        new JoinListener();
    }

    public void registerManagers() {
        this.commandManager = new CommandManager(this, new ArrayList<>());
    }

    public void loadConfigs() {
        this.langConfig = new ConfigFile(this, "lang.yml");
    }

    public Connection getMySQl() {
        return this.mySQLSetup.getConnection();
    }

    public static Main getInstance() {
        return getPlugin(Main.class);
    }
}
