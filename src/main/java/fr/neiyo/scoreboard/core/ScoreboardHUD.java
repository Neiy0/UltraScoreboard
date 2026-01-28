package fr.neiyo.scoreboard.core;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import fr.neiyo.scoreboard.api.Scoreboard;
import fr.neiyo.scoreboard.api.renderer.IScoreboardRenderer;

import javax.annotation.Nonnull;

public final class ScoreboardHUD extends CustomUIHud {

    private final Scoreboard scoreboard;
    private final IScoreboardRenderer renderer;

    public ScoreboardHUD(@Nonnull PlayerRef playerRef, @Nonnull Scoreboard scoreboard, @Nonnull IScoreboardRenderer renderer) {
        super(playerRef);
        this.scoreboard = scoreboard;
        this.renderer = renderer;
    }

    @Override
    protected void build(@Nonnull UICommandBuilder builder) {
        renderer.render(builder, scoreboard);
    }

}
