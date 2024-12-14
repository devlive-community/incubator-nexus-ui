package org.devlive.nexus.ui.component.button;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    private RotateTransition rotation;

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
        content.setAlignment(Pos.CENTER);

        // 添加加载图标
        // Add loading icon
        if (loading) {
            StackPane loadingIcon = createLoadingIcon();
            content.getChildren().add(loadingIcon);
        }

        // 创建文本节点并设置初始颜色
        // Create text node and set initial color
        Text textNode = new Text(text);
        textNode.setTextAlignment(TextAlignment.CENTER);
        Color textColor = getTextColor();
        textNode.setFill(textColor);
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
            textNode.setFill(style.getTextColor());
        }

        // 设置事件处理器
        // Set event handler
        if (clickHandler != null) {
            button.setOnAction(event -> clickHandler.run());
        }

        return button;
    }

    private Color getTextColor()
    {
        return switch (type) {
            case PRIMARY, SUCCESS, WARNING, DANGER, INFO -> Color.WHITE;
            case TEXT, DEFAULT -> Color.rgb(55, 65, 81);
        };
    }

    private void applyStyle(Button button)
    {
        Color bgColor;
        Color hoverBgColor;

        switch (type) {
            case PRIMARY -> {
                bgColor = Color.rgb(59, 130, 246);
                hoverBgColor = Color.rgb(37, 99, 235);
            }
            case SUCCESS -> {
                bgColor = Color.rgb(34, 197, 94);
                hoverBgColor = Color.rgb(22, 163, 74);
            }
            case WARNING -> {
                bgColor = Color.rgb(245, 158, 11);
                hoverBgColor = Color.rgb(217, 119, 6);
            }
            case DANGER -> {
                bgColor = Color.rgb(239, 68, 68);
                hoverBgColor = Color.rgb(220, 38, 38);
            }
            case INFO -> {
                bgColor = Color.rgb(99, 102, 241);
                hoverBgColor = Color.rgb(79, 70, 229);
            }
            case TEXT -> {
                bgColor = Color.WHITE;
                hoverBgColor = Color.WHITE;
            }
            default -> {
                bgColor = Color.rgb(243, 244, 246);
                hoverBgColor = Color.rgb(229, 231, 235);
            }
        }

        // 组合基础样式和尺寸样式
        // Combine base styles and size styles
        String sizeStyle = getSizeStyle();
        StringBuilder styleBuilder = new StringBuilder()
                .append("-fx-background-radius: 0.375em;")
                .append(sizeStyle)
                .append("-fx-cursor: ").append((disabled || loading) ? "not-allowed, cursor-not-allowed" : "hand, pointer").append(";")
                .append("-fx-background-color: ").append(toCssColor(bgColor)).append(";")
                .append("-fx-focus-color: transparent;")
                .append("-fx-faint-focus-color: transparent;");

        // 添加禁用状态的透明度和颜色
        // Add opacity and color for disabled state
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
            case SMALL -> "-fx-padding: 0.25em 0.5em; -fx-font-size: 0.875em;";
            case LARGE -> "-fx-padding: 0.75em 1.25em; -fx-font-size: 1.125em;";
            default -> "-fx-padding: 0.5em 1em; -fx-font-size: 1em;";
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

    private StackPane createLoadingIcon()
    {
        double size = 12;

        // 创建圆弧
        // Create an arc
        Arc arc = new Arc();
        arc.setRadiusX(size / 2);
        arc.setRadiusY(size / 2);
        arc.setStartAngle(0);
        // 画一个缺口的圆弧
        // Draw a gap in the arc
        arc.setLength(270);
        arc.setType(ArcType.OPEN);
        arc.setFill(null);
        arc.setStrokeWidth(2);

        // 设置圆弧颜色与文字颜色一致
        // Set arc color to match text color
        Color strokeColor = getTextColor();
        arc.setStroke(strokeColor);

        // 使用StackPane来居中显示圆弧
        // Use StackPane to center the arc
        StackPane container = new StackPane(arc);
        container.setMinSize(size, size);
        container.setMaxSize(size, size);
        container.setPrefSize(size, size);

        // 设置旋转动画
        // Set rotation animation
        this.rotation = new RotateTransition(Duration.seconds(1), container);
        this.rotation.setFromAngle(0);
        this.rotation.setToAngle(360);
        this.rotation.setCycleCount(RotateTransition.INDEFINITE);
        this.rotation.setInterpolator(Interpolator.LINEAR);
        this.rotation.play();

        return container;
    }

    private void cleanupResources()
    {
        if (rotation != null) {
            rotation.stop();
            rotation = null;
        }
    }

    @Override
    public void dispose()
    {
        cleanupResources();
        super.dispose();
    }
}
