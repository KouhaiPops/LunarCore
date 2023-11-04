package emu.lunarcore;

import java.io.*;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.Logger;
import emu.lunarcore.command.CommandManager;
import emu.lunarcore.data.ResourceLoader;
import emu.lunarcore.database.DatabaseManager;
import emu.lunarcore.server.game.GameServer;
import emu.lunarcore.server.http.HttpServer;
import emu.lunarcore.util.Handbook;
import emu.lunarcore.util.JsonUtils;
import lombok.Getter;

public class LunarCore {
    private static final Logger log = (Logger) LoggerFactory.getLogger(LunarCore.class);
    private static File configFile = new File("./config.json");
    private static Config config;

    @Getter private static DatabaseManager accountDatabase;
    @Getter private static DatabaseManager gameDatabase;

    @Getter private static HttpServer httpServer;
    @Getter private static GameServer gameServer;

    @Getter private static CommandManager commandManager;
    @Getter private static ServerType serverType = ServerType.BOTH;
    
    private static LineReaderImpl reader;
    @Getter private static boolean usingDumbTerminal;

    static {
        // Setup console reader
        try {
            reader = (LineReaderImpl) LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.builder().dumb(true).build())
                    .build();
            
            usingDumbTerminal = Terminal.TYPE_DUMB.equals(reader.getTerminal().getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Load config
        LunarCore.loadConfig();
    }

    public static void main(String[] args) {
        // Start Server
        LunarCore.getLogger().info("Starting Lunar Core...");
        LunarCore.getLogger().info("Made for game version " + GameConstants.VERSION);
        boolean generateHandbook = true;
        
        // Load commands
        LunarCore.commandManager = new CommandManager();

        // Parse arguments
        for (String arg : args) {
            switch (arg) {
            case "-dispatch":
                serverType = ServerType.DISPATCH;
                break;
            case "-game":
                serverType = ServerType.GAME;
                break;
            case "-nohandbook":
            case "-skiphandbook":
                generateHandbook = false;
                break;
            case "-database":
                // Database only
                DatabaseManager databaseManager = new DatabaseManager();
                databaseManager.startInternalMongoServer(LunarCore.getConfig().getInternalMongoServer());
                LunarCore.getLogger().info("Running local mongo server at " + databaseManager.getServer().getConnectionString());
                // Console
                LunarCore.startConsole();
                return;
            }
        }
        
        // Skip these if we are only running the http server in dispatch mode
        if (serverType.runGame()) {
            // Load resources
            ResourceLoader.loadAll();

            // Build handbook
            if (generateHandbook) {
                Handbook.generate();
            }
        }

        // Start Database(s)
        LunarCore.initDatabases();

        // Always run http server as it is needed by for dispatch and gateserver
        httpServer = new HttpServer(serverType);
        httpServer.start();

        // Start game server
        if (serverType.runGame()) {
            gameServer = new GameServer(getConfig().getGameServer());
            gameServer.start();
        }

        // Start console
        LunarCore.startConsole();
    }

    public static Config getConfig() {
        return config;
    }

    public static Logger getLogger() {
        return log;
    }
    
    public static LineReaderImpl getLineReader() {
        return reader;
    }

    // Database

    private static void initDatabases() {
        if (LunarCore.getConfig().useSameDatabase) {
            // Setup account and game database
            accountDatabase = new DatabaseManager(LunarCore.getConfig().getAccountDatabase(), serverType);
            // Optimization: Dont run a 2nd database manager if we are not running a gameserver
            if (serverType.runGame()) {
                gameDatabase = accountDatabase;
            }
        } else {
            // Run separate databases
            accountDatabase = new DatabaseManager(LunarCore.getConfig().getAccountDatabase(), ServerType.DISPATCH);
            // Optimization: Dont run a 2nd database manager if we are not running a gameserver
            if (serverType.runGame()) {
                gameDatabase = new DatabaseManager(LunarCore.getConfig().getGameDatabase(), ServerType.GAME);
            }
        }
    }

    // Config

    public static void loadConfig() {
        try (FileReader file = new FileReader(configFile)) {
            config = JsonUtils.loadToClass(file, Config.class);
        } catch (Exception e) {
            LunarCore.config = new Config();
        }
        saveConfig();
    }

    public static void saveConfig() {
        try (FileWriter file = new FileWriter(configFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            file.write(gson.toJson(config));
        } catch (Exception e) {
            getLogger().error("Config save error");
        }
    }

    // Server console
    
    private static void startConsole() {
        try {
            while (true) {
                String input = reader.readLine("> ");
                if (input == null || input.length() == 0) {
                    continue;
                }
                
                LunarCore.getCommandManager().invoke(null, input);
            } 
        } catch (UserInterruptException | EndOfFileException e) {
            // CTRL + C / CTRL + D
            System.exit(0);
        } catch (Exception e) {
            LunarCore.getLogger().error("Terminal error: ", e);
        }
    }

    // Server enums

    public enum ServerType {
        DISPATCH    (0x1),
        GAME        (0x2),
        BOTH        (0x3);

        private final int flags;

        private ServerType(int flags) {
            this.flags = flags;
        }

        public boolean runDispatch() {
            return (this.flags & 0x1) == 0x1;
        }

        public boolean runGame() {
            return (this.flags & 0x2) == 0x2;
        }
    }
}
