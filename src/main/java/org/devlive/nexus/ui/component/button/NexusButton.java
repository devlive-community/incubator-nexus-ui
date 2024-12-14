package org.devlive.nexus.ui.component.button;

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
    private ButtonSize size = ButtonSize.DEFAULT;

    // 添加静态工厂方法
    // Add static factory method
    public static NexusButton create(String text)
    {
        return new NexusButton(text);
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

    public NexusButton size(ButtonSize size)
    {
        this.size = size;
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
        applyStyle(button);

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

    private void applyStyle(Button button)
    {
        Color textColor;
        Color bgColor;
        Color hoverBgColor;

        switch (type) {
            case PRIMARY -> {
                bgColor = Color.rgb(59, 130, 246);
                hoverBgColor = Color.rgb(37, 99, 235);
                textColor = Color.WHITE;
            }
            case SUCCESS -> {
                bgColor = Color.rgb(34, 197, 94);
                hoverBgColor = Color.rgb(22, 163, 74);
                textColor = Color.WHITE;
            }
            case WARNING -> {
                bgColor = Color.rgb(245, 158, 11);
                hoverBgColor = Color.rgb(217, 119, 6);
                textColor = Color.WHITE;
            }
            case DANGER -> {
                bgColor = Color.rgb(239, 68, 68);
                hoverBgColor = Color.rgb(220, 38, 38);
                textColor = Color.WHITE;
            }
            case INFO -> {
                bgColor = Color.rgb(99, 102, 241);
                hoverBgColor = Color.rgb(79, 70, 229);
                textColor = Color.WHITE;
            }
            case TEXT -> {
                bgColor = Color.WHITE;
                hoverBgColor = Color.WHITE;
                textColor = Color.rgb(55, 65, 81);
            }
            default -> {
                bgColor = Color.rgb(243, 244, 246);
                hoverBgColor = Color.rgb(229, 231, 235);
                textColor = Color.rgb(55, 65, 81);
            }
        }

        // 组合基础样式和尺寸样式
        String sizeStyle = getSizeStyle();
        String baseStyle = "-fx-background-radius: 6px;" +
                sizeStyle +
                "-fx-cursor: hand;" +
                "-fx-background-color: " + toCssColor(bgColor) + ";" +
                "-fx-text-fill: " + toCssColor(textColor) + ";";

        button.setStyle(baseStyle);

        button.setOnMouseEntered(e -> {
            String hoverStyle = baseStyle.replace(
                    toCssColor(bgColor),
                    toCssColor(hoverBgColor)
            );
            button.setStyle(hoverStyle);
        });

        button.setOnMouseExited(e -> button.setStyle(baseStyle));
    }

    private String getSizeStyle()
    {
        return switch (size) {
            case SMALL -> "-fx-padding: 4px 8px; -fx-font-size: 12px;";
            case LARGE -> "-fx-padding: 12px 20px; -fx-font-size: 16px;";
            default -> "-fx-padding: 8px 16px; -fx-font-size: 14px;";
        };
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
