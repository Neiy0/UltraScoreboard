package fr.neiyo.scoreboard;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import fr.neiyo.scoreboard.api.ScoreboardProvider;
import fr.neiyo.scoreboard.core.ScoreboardSystem;

import javax.annotation.Nonnull;

public final class UltraScoreboard {

    public static void initialize(@Nonnull JavaPlugin plugin) {
        ScoreboardSystem scoreboardSystem = new ScoreboardSystem();
        plugin.getEntityStoreRegistry().registerSystem(scoreboardSystem);
        ScoreboardProvider.register(scoreboardSystem);
    }

}