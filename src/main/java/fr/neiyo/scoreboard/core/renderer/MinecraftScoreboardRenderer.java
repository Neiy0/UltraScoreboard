package fr.neiyo.scoreboard.core.renderer;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import fr.neiyo.scoreboard.api.Scoreboard;
import fr.neiyo.scoreboard.api.line.LineAlignment;
import fr.neiyo.scoreboard.api.line.ScoreboardLine;
import fr.neiyo.scoreboard.api.renderer.IScoreboardRenderer;

import javax.annotation.Nonnull;
import java.util.List;

public final class MinecraftScoreboardRenderer implements IScoreboardRenderer {

    public static final MinecraftScoreboardRenderer INSTANCE = new MinecraftScoreboardRenderer();

    @Override
    public void render(@Nonnull UICommandBuilder builder, @Nonnull Scoreboard scoreboard) {
        scoreboard.reset();
        scoreboard.setLines();

        List<ScoreboardLine> lines = scoreboard.getLines();
        ScoreboardLine title = scoreboard.getTitle();

        int titleHeight = title == null ? 0 : (int) Math.ceil(title.fontSize() * 1.8);
        int totalHeight = titleHeight + 20;

        for (ScoreboardLine line : lines) {
            totalHeight += (int) (line.fontSize() * 1.8);
        }

        int totalWidth = calculateRequiredWidth(title, lines);

        String titleSection = title != null
                ? """
                  Group #TitleBackground {
                      Anchor: (Left: 0, Right: 0, Height: %d);
                      Background: (Color: #000000(0.3));
                      Padding: (Top: 6);
                      Label #Title { }
                  }
                  """.formatted(titleHeight)
                : "";

        String uiStructure = """
                Group {
                    Anchor: (Right: 0, Top: 0, Bottom: 0, Width: %d);
                    LayoutMode: Center;
                    Group #BoardPanel {
                        Anchor: (Width: %d, Height: %d);
                        Background: (Color: #434343(0.5));
                        LayoutMode: Top;
                        %sGroup #LinesContainer {
                            Anchor: (Left: 0);
                            Padding: (Left: 10, Right: 10, Top: 4, Bottom: 4);
                            LayoutMode: Top;
                        }
                    }
                }
                """.formatted(totalWidth, totalWidth, totalHeight, titleSection);

        builder.appendInline(null, uiStructure);

        if (title != null) {
            builder.set("#Title.TextSpans", title.value());
            builder.set("#Title.Style.FontSize", title.fontSize());
            if (title.alignment() != LineAlignment.LEFT) {
                builder.set("#Title.Style.Alignment", title.alignment().toString());
            }
        }

        for (int i = 0; i < lines.size(); i++) {
            int lineNum = i + 1;
            ScoreboardLine line = lines.get(i);
            int lineHeight = (int) Math.ceil(line.fontSize() * 1.8);

            builder.appendInline("#LinesContainer",
                    "Label #Line" + lineNum + " { " +
                            "Anchor: (Left: 0, Right: 0, Height: " + lineHeight + "); " +
                            "Padding: (Top: 4, Bottom: 0); " +
                            "}"
            );

            builder.set("#Line" + lineNum + ".TextSpans", line.value());
            builder.set("#Line" + lineNum + ".Style.FontSize", line.fontSize());

            if (line.alignment() != LineAlignment.LEFT) {
                builder.set("#Line" + lineNum + ".Style.Alignment", line.alignment().toString());
            }
        }
    }

    private int calculateRequiredWidth(ScoreboardLine title, List<ScoreboardLine> lines) {
        int maxWidth = 240;

        if (title != null) {
            int titleWidth = estimateTextWidth(title.value(), title.fontSize());
            maxWidth = Math.max(maxWidth, titleWidth);
        }

        for (ScoreboardLine line : lines) {
            int lineWidth = estimateTextWidth(line.value(), line.fontSize());
            maxWidth = Math.max(maxWidth, lineWidth);
        }

        return maxWidth;
    }

    private int estimateTextWidth(Message message, int fontSize) {
        String text = extractTextFromMessage(message);

        double charWidth = fontSize * 0.55;

        return (int) Math.ceil(text.length() * charWidth);
    }

    private String extractTextFromMessage(Message message) {
        if (message == null) return "";

        StringBuilder text = new StringBuilder();

        String rawText = message.getRawText();
        if (rawText != null) {
            text.append(rawText);
        }

        List<Message> children = message.getChildren();
        for (Message child : children) {
            text.append(extractTextFromMessage(child));
        }

        return text.toString();
    }
}