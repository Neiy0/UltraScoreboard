package fr.neiyo.scoreboard.core.dependence.impl;

import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import fr.neiyo.scoreboard.core.ScoreboardHUD;
import fr.neiyo.scoreboard.core.dependence.HudDependence;

import javax.annotation.Nonnull;

public final class DefaultHudDependence implements HudDependence {

    @Override
    public void setCustomHud(@Nonnull Player player, @Nonnull PlayerRef playerRef, @Nonnull ScoreboardHUD hud) {
        player.getHudManager().setCustomHud(playerRef, hud);
    }

    @Override
    public void removeCustomHud(@Nonnull Player player, @Nonnull PlayerRef playerRef) {
        player.getHudManager().resetHud(playerRef);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}