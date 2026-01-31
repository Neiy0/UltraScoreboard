package fr.neiyo.scoreboard;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import fr.neiyo.scoreboard.api.ScoreboardProvider;
import fr.neiyo.scoreboard.core.component.ScoreboardComponent;
import fr.neiyo.scoreboard.core.ScoreboardSystem;

import javax.annotation.Nonnull;

public final class UltraScoreboard {

    public static void initialize(@Nonnull JavaPlugin plugin) {
        ComponentType<EntityStore, ScoreboardComponent> scoreboardComponentType = plugin.getEntityStoreRegistry()
                .registerComponent(ScoreboardComponent.class, ScoreboardComponent::new);

        ScoreboardSystem scoreboardSystem = new ScoreboardSystem(scoreboardComponentType);
        plugin.getEntityStoreRegistry().registerSystem(scoreboardSystem);

        ScoreboardProvider.register(scoreboardSystem);
    }

}