package org.devlive.nexus.ui.component;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.devlive.nexus.ui.core.Component;

public class NexusContainer
        extends Component
{
    @Override
    public Node render()
    {
        VBox box = new VBox();

        // Apply styles
        if (style.getBackgroundColor() != null) {
            box.setStyle("-fx-background-color: " +
                    toCssColor(style.getBackgroundColor()));
        }

        if (style.getWidth() >= 0) {
            box.setPrefWidth(style.getWidth());
        }
        if (style.getHeight() >= 0) {
            box.setPrefHeight(style.getHeight());
        }
        if (style.getPadding() != null) {
            box.setPadding(style.getPadding());
        }
        if (style.getMargin() != null) {
            VBox.setMargin(box, style.getMargin());
        }

        // Add children
        for (Component child : children) {
            box.getChildren().add(child.render());
        }

        return box;
    }

    private String toCssColor(Color color)
    {
        return String.format(
                "#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255)
        );
    }
}
