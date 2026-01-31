package fr.neiyo.scoreboard.core.component;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import fr.neiyo.scoreboard.api.Scoreboard;
import fr.neiyo.scoreboard.api.renderer.IScoreboardRenderer;
import fr.neiyo.scoreboard.core.ScoreboardHUD;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ScoreboardComponent implements Component<EntityStore> {
    
    private final Scoreboard scoreboard;
    private final IScoreboardRenderer renderer;
    private ScoreboardHUD hud;

    public ScoreboardComponent() {
        this(null, null);
    }

    public ScoreboardComponent(@Nullable Scoreboard scoreboard, @Nullable IScoreboardRenderer renderer) {
        this.scoreboard = scoreboard;
        this.renderer = renderer;
        this.hud = null;
    }

    public ScoreboardComponent(@Nonnull ScoreboardComponent other) {
        this.scoreboard = other.scoreboard;
        this.renderer = other.renderer;
        this.hud = other.hud;
    }

    @Override
    public Component<EntityStore> clone() {
        return new ScoreboardComponent(this);
    }
    
    @Nullable
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
    
    @Nullable
    public IScoreboardRenderer getRenderer() {
        return renderer;
    }
    
    @Nullable
    public ScoreboardHUD getHud() {
        return hud;
    }
    
    public void setHud(@Nullable ScoreboardHUD hud) {
        this.hud = hud;
    }
}