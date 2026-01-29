package fr.neiyo.scoreboard.core.dependence;

import fr.neiyo.scoreboard.core.dependence.impl.BuzzMultipleHUDDependence;
import fr.neiyo.scoreboard.core.dependence.impl.DefaultHudDependence;
import fr.neiyo.scoreboard.core.dependence.impl.FlashMultipleHUDDependence;

import java.util.List;

public final class HudDependenceProvider {

    private static final List<Class<? extends HudDependence>> IMPLEMENTATIONS = List.of(
            BuzzMultipleHUDDependence.class,
            FlashMultipleHUDDependence.class
    );

    private static final HudDependence INSTANCE = create();

    private static HudDependence create() {
        HudDependence best = null;

        for (Class<? extends HudDependence> clazz : IMPLEMENTATIONS) {
            try {
                HudDependence dependence = clazz.getDeclaredConstructor().newInstance();

                if (!dependence.isAvailable()) {
                    continue;
                }

                if (best == null || dependence.getPriority() > best.getPriority()) {
                    best = dependence;
                }
            } catch (Throwable _) {}
        }

        return best != null ? best : new DefaultHudDependence();
    }

    public static HudDependence get() {
        return INSTANCE;
    }

    private HudDependenceProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
}