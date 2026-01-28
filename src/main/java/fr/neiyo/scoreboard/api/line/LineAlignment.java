package fr.neiyo.scoreboard.api.line;

public enum LineAlignment {

    LEFT("Left"),
    CENTER("Center"),
    ;

    private final String value;

    LineAlignment(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}