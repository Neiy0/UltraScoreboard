package fr.neiyo.scoreboard.core.dependence;

import fr.neiyo.scoreboard.core.dependence.impl.DefaultHudDependence;

import java.util.ServiceLoader;

public final class HudDependenceProvider {

    private static final HudDependence INSTANCE = create();

    private static HudDependence create() {
        ServiceLoader<HudDependence> loader = ServiceLoader.load(HudDependence.class);
        HudDependence best = null;
        for (HudDependence dependence : loader) {
            try {
                if (!dependence.isAvailable()) continue;
            } catch (Throwable throwable) {
                continue;
            }
            if (best == null || dependence.getPriority() > best.getPriority()) {
                best = dependence;
            }
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