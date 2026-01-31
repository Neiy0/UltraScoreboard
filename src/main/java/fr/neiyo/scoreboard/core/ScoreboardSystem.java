package fr.neiyo.scoreboard.core;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.DelayedEntitySystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import fr.neiyo.scoreboard.api.Scoreboard;
import fr.neiyo.scoreboard.api.renderer.IScoreboardRenderer;
import fr.neiyo.scoreboard.api.system.IScoreboardSystem;
import fr.neiyo.scoreboard.core.component.ScoreboardComponent;
import fr.neiyo.scoreboard.core.dependence.HudDependence;
import fr.neiyo.scoreboard.core.dependence.HudDependenceProvider;
import fr.neiyo.scoreboard.core.renderer.MinecraftScoreboardRenderer;

import javax.annotation.Nonnull;

public final class ScoreboardSystem extends DelayedEntitySystem<EntityStore> implements IScoreboardSystem {

    private final ComponentType<EntityStore, ScoreboardComponent> scoreboardComponentType;
    private final HudDependence hudDependence;

    public ScoreboardSystem(@Nonnull ComponentType<EntityStore, ScoreboardComponent> scoreboardComponentType) {
        super(0.1f);
        this.scoreboardComponentType = scoreboardComponentType;
        this.hudDependence = HudDependenceProvider.get();
    }

    @Override
    public void setScoreboard(@Nonnull PlayerRef playerRef, @Nonnull Scoreboard scoreboard) {
        setScoreboard(playerRef, scoreboard, MinecraftScoreboardRenderer.INSTANCE);
    }

    @Override
    public void setScoreboard(@Nonnull PlayerRef playerRef, @Nonnull Scoreboard scoreboard, @Nonnull IScoreboardRenderer renderer) {
        Ref<EntityStore> ref = playerRef.getReference();
        if (ref == null) return;

        Store<EntityStore> store = ref.getStore();
        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) return;

        ScoreboardComponent scoreboardComponent = new ScoreboardComponent(scoreboard, renderer);

        ScoreboardHUD hud = new ScoreboardHUD(playerRef, scoreboard, renderer);
        scoreboardComponent.setHud(hud);

        hudDependence.setCustomHud(player, playerRef, hud);

        store.addComponent(ref, scoreboardComponentType, scoreboardComponent);
    }

    @Override
    public void removeScoreboard(@Nonnull PlayerRef playerRef) {
        Ref<EntityStore> ref = playerRef.getReference();
        if (ref == null) return;

        Store<EntityStore> store = ref.getStore();
        World world = store.getExternalData().getWorld();

        world.execute(() -> {
            ScoreboardComponent scoreboardComponent = store.getComponent(ref, scoreboardComponentType);
            Player player = store.getComponent(ref, Player.getComponentType());

            if (scoreboardComponent != null && player != null) {
                hudDependence.removeCustomHud(player, playerRef);
            }

            store.removeComponent(ref, scoreboardComponentType);
        });
    }

    @Override
    public void tick(float deltaTime, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        ScoreboardComponent scoreboardComponent = archetypeChunk.getComponent(index, scoreboardComponentType);
        if (scoreboardComponent == null) return;

        ScoreboardHUD hud = scoreboardComponent.getHud();
        if (hud == null) return;

        hud.show();
    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        return scoreboardComponentType;
    }
}