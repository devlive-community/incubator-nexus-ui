package org.devlive.nexus.ui.component.button;

import javafx.animation.RotateTransition;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.devlive.nexus.ui.core.Component;

public class NexusButton
        extends Component
{
    private final String text;
    private Runnable clickHandler;
    private ButtonType type = ButtonType.DEFAULT;
    private ButtonSize size = ButtonSize.DEFAULT;
    private boolean disabled = false;
    private boolean loading = false;

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

    public NexusButton disabled(boolean disabled)
    {
        this.disabled = disabled;
        return this;
    }

    public NexusButton loading(boolean loading)
    {
        this.loading = loading;
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
        Button button = new Button();
        HBox content = new HBox(5);

        // 添加加载图标
        // Add loading icon
        if (loading) {
            Text loadingIcon = new Text("⟳");
            setupLoadingAnimation(loadingIcon);
            content.getChildren().add(loadingIcon);
        }

        // 添加文本
        // Add text
        Text textNode = new Text(text);
        content.getChildren().add(textNode);

        button.setGraphic(content);

        // 应用基础样式
        // Apply base styles
        button.getStyleClass().add("nexus-ui-button");

        // 根据类型应用不同的样式
        // Apply styles based on type
        applyStyle(button);

        // 应用禁用状态
        // Apply disabled state
        button.setDisable(disabled);

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
        Color disabledBgColor = Color.rgb(229, 231, 235); // Gray-200
        Color disabledTextColor = Color.rgb(156, 163, 175); // Gray-400

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
        // Combine base styles and size styles
        String sizeStyle = getSizeStyle();
        StringBuilder styleBuilder = new StringBuilder()
                .append("-fx-background-radius: 6px;")
                .append(sizeStyle)
                .append("-fx-cursor: ").append((disabled || loading) ? "cursor-not-allowed" : "hand").append(";")
                .append("-fx-background-color: ").append(toCssColor(bgColor)).append(";")
                .append("-fx-text-fill: ").append(toCssColor(textColor)).append(";");

        // 添加禁用状态的透明度
        // Add opacity for disabled state
        if (disabled || loading) {
            styleBuilder.append("-fx-opacity: 0.4;");
        }

        String baseStyle = styleBuilder.toString();
        button.setStyle(baseStyle);
        button.setDisable(disabled);

        // 只在非禁用状态下添加鼠标悬停效果
        // Add hover effect only when not disabled
        if (!disabled && !loading) {
            button.setOnMouseEntered(e -> {
                String hoverStyle = baseStyle.replace(
                        toCssColor(bgColor),
                        toCssColor(hoverBgColor)
                );
                button.setStyle(hoverStyle);
            });

            button.setOnMouseExited(e -> button.setStyle(baseStyle));
        }
        else {
            // 清除鼠标事件处理器
            // Clear mouse event handlers
            button.setOnMouseEntered(null);
            button.setOnMouseExited(null);
        }
    }

    private String getSizeStyle()
    {
        return switch (size) {
            case SMALL -> "-fx-padding: 4px 8px; -fx-font-size: 12px;";
            case LARGE -> "-fx-padding: 12px 20px; -fx-font-size: 16px;";
            default -> "-fx-padding: 8px 16px; -fx-font-size: 14px;";
        };
    }

    private void setupLoadingAnimation(Text loadingIcon)
    {
        RotateTransition rotation = new RotateTransition(Duration.seconds(1), loadingIcon);
        rotation.setByAngle(360);
        rotation.setCycleCount(RotateTransition.INDEFINITE);
        rotation.play();
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
