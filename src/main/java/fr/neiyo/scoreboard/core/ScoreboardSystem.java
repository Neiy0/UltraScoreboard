package fr.neiyo.scoreboard.core;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
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
import fr.neiyo.scoreboard.core.dependence.HudDependence;
import fr.neiyo.scoreboard.core.dependence.HudDependenceProvider;
import fr.neiyo.scoreboard.core.renderer.MinecraftScoreboardRenderer;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ScoreboardSystem extends DelayedEntitySystem<EntityStore> implements IScoreboardSystem {

    private final Map<UUID, ScoreboardHUD> scoreboards = new ConcurrentHashMap<>();
    private final HudDependence hudDependence;

    public ScoreboardSystem() {
        super(0.1f);
        this.hudDependence = HudDependenceProvider.get();
    }

    @Override
    public void setScoreboard(@Nonnull PlayerRef playerRef, @Nonnull Scoreboard scoreboard) {
        setScoreboard(playerRef, scoreboard, MinecraftScoreboardRenderer.INSTANCE);
    }

    @Override
    public void setScoreboard(@Nonnull PlayerRef playerRef, @Nonnull Scoreboard scoreboard, IScoreboardRenderer renderer) {
        ScoreboardHUD hud = new ScoreboardHUD(playerRef, scoreboard, renderer);

        Ref<EntityStore> ref = playerRef.getReference();
        if (ref == null) return;

        Store<EntityStore> store = ref.getStore();
        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) return;

        hudDependence.setCustomHud(player, playerRef, hud);

        scoreboards.put(playerRef.getUuid(), hud);
    }

    @Override
    public void removeScoreboard(@Nonnull PlayerRef playerRef) {
        scoreboards.remove(playerRef.getUuid());

        Ref<EntityStore> ref = playerRef.getReference();
        if (ref == null) return;

        Store<EntityStore> store = ref.getStore();
        World world = store.getExternalData().getWorld();

        world.execute(() -> {
            Player player = store.getComponent(ref, Player.getComponentType());
            if (player == null) return;
            hudDependence.removeCustomHud(player, playerRef);
        });
    }

    @Override
    public void tick(float deltaTime, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());

        if (playerRef == null) return;
        ScoreboardHUD hud = scoreboards.get(playerRef.getUuid());
        if (hud == null) return;

        hud.show();
    }

    @Override
    public @Nonnull Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}
