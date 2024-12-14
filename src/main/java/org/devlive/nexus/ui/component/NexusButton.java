package org.devlive.nexus.ui.component;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import org.devlive.nexus.ui.core.Component;

public class NexusButton
        extends Component
{
    private final String text;
    private Runnable clickHandler;
    private ButtonType type = ButtonType.DEFAULT;

    // 按钮类型枚举
    // Button type enumeration
    public enum ButtonType
    {
        PRIMARY,
        SUCCESS,
        WARNING,
        DANGER,
        INFO,
        TEXT,
        DEFAULT
    }

    public NexusButton(String text)
    {
        this.text = text;
    }

    public NexusButton type(ButtonType type)
    {
        this.type = type;
        return this;
    }

    @Override
    public NexusButton onClick(Runnable action)
    {
        this.clickHandler = action;
        return this;
    }

    @Override
    public Parent render()
    {
        Button button = new Button(text);

        // 应用基础样式
        // Apply base styles
        button.getStyleClass().add("nexus-ui-button");

        // 根据类型应用不同的样式
        // Apply styles based on type
        applyTypeStyle(button);

        // 应用自定义样式
        // Apply custom styles
        if (style.getBackgroundColor() != null) {
            button.setStyle(button.getStyle() + "; -fx-background-color: " +
                    toCssColor(style.getBackgroundColor()));
        }

        if (style.getTextColor() != null) {
            button.setTextFill(style.getTextColor());
        }

        // 设置事件处理器
        // Set event handler
        if (clickHandler != null) {
            button.setOnAction(event -> clickHandler.run());
        }

        return button;
    }

    private void applyTypeStyle(Button button)
    {
        StringBuilder style = new StringBuilder();
        Color textColor;
        Color bgColor;
        Color hoverBgColor;

        switch (type) {
            case PRIMARY -> {
                bgColor = Color.rgb(59, 130, 246); // Blue-500
                hoverBgColor = Color.rgb(37, 99, 235); // Blue-600
                textColor = Color.WHITE;
            }
            case SUCCESS -> {
                bgColor = Color.rgb(34, 197, 94); // Green-500
                hoverBgColor = Color.rgb(22, 163, 74); // Green-600
                textColor = Color.WHITE;
            }
            case WARNING -> {
                bgColor = Color.rgb(245, 158, 11); // Yellow-500
                hoverBgColor = Color.rgb(217, 119, 6); // Yellow-600
                textColor = Color.WHITE;
            }
            case DANGER -> {
                bgColor = Color.rgb(239, 68, 68); // Red-500
                hoverBgColor = Color.rgb(220, 38, 38); // Red-600
                textColor = Color.WHITE;
            }
            case INFO -> {
                bgColor = Color.rgb(99, 102, 241); // Indigo-500
                hoverBgColor = Color.rgb(79, 70, 229); // Indigo-600
                textColor = Color.WHITE;
            }
            case TEXT -> {
                bgColor = Color.TRANSPARENT;
                hoverBgColor = Color.rgb(243, 244, 246); // Gray-100
                textColor = Color.rgb(55, 65, 81); // Gray-700
            }
            default -> { // DEFAULT
                bgColor = Color.rgb(243, 244, 246); // Gray-100
                hoverBgColor = Color.rgb(229, 231, 235); // Gray-200
                textColor = Color.rgb(55, 65, 81); // Gray-700
            }
        }

        // 基础样式
        // Base styles
        style.append("-fx-background-radius: 6px;")
                .append("-fx-padding: 8px 16px;")
                .append("-fx-cursor: hand;")
                .append("-fx-background-color: ").append(toCssColor(bgColor)).append(";")
                .append("-fx-text-fill: ").append(toCssColor(textColor)).append(";");

        button.setStyle(style.toString());

        // 鼠标悬停效果
        // Mouse hover effect
        button.setOnMouseEntered(e ->
                button.setStyle(style.toString().replace(
                        toCssColor(bgColor),
                        toCssColor(hoverBgColor)
                ))
        );

        button.setOnMouseExited(e ->
                button.setStyle(style.toString())
        );
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
