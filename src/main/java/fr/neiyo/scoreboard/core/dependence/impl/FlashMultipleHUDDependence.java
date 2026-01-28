package fr.neiyo.scoreboard.core.dependence.impl;

import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import fr.flash303.multiplehud.MultipleHud;
import fr.flash303.multiplehud.identifier.HudIdIdentifier;
import fr.flash303.multiplehud.identifier.HudIdentifier;
import fr.neiyo.scoreboard.core.ScoreboardHUD;
import fr.neiyo.scoreboard.core.dependence.HudDependence;

import javax.annotation.Nonnull;

public final class FlashMultipleHUDDependence implements HudDependence {

    private final HudIdentifier hudIdentifier = new HudIdIdentifier("UltraScoreboard");

    @Override
    public void setCustomHud(@Nonnull Player player, @Nonnull PlayerRef playerRef, @Nonnull ScoreboardHUD hud) {
        MultipleHud.displayCustomHud(player, playerRef, hudIdentifier, hud);
    }

    @Override
    public void removeCustomHud(@Nonnull Player player, @Nonnull PlayerRef playerRef) {
        MultipleHud.removeCustomHud(player, hudIdentifier);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean isAvailable() {
        try {
            Class.forName("fr.flash303.multiplehud.MultipleHud");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}