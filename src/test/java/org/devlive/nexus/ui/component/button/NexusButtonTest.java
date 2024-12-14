package org.devlive.nexus.ui.component.button;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class NexusButtonTest
{
    private NexusButton button;

    @BeforeAll
    public static void setupHeadless()
    {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
    }

    @BeforeEach
    void setUp()
    {
        button = new NexusButton("Test Button");
    }

    @Test
    void testInitialState()
    {
        assertEquals("Test Button", button.getText());
        assertEquals(ButtonType.DEFAULT, button.getType());
        assertEquals(ButtonSize.DEFAULT, button.getSize());
        assertFalse(button.isDisabled());
        assertFalse(button.isLoading());
    }

    @Test
    void testButtonRender()
    {
        Parent parent = button.render();
        assertInstanceOf(Button.class, parent);
        Button fxButton = (Button) parent;

        // 验证基本样式
        // Verify base styles
        assertTrue(fxButton.getStyleClass().contains("nexus-ui-button"));

        // 验证内容结构
        // Verify content structure
        assertInstanceOf(HBox.class, fxButton.getGraphic());
        HBox content = (HBox) fxButton.getGraphic();
        assertEquals(1, content.getChildren().size());
        assertInstanceOf(Text.class, content.getChildren().get(0));
    }

    @Test
    void testLoadingState()
    {
        button.loading(true);
        Parent parent = button.render();
        Button fxButton = (Button) parent;
        HBox content = (HBox) fxButton.getGraphic();

        // 验证加载图标
        // Verify loading icon
        assertEquals(2, content.getChildren().size());
        assertInstanceOf(StackPane.class, content.getChildren().get(0));
        assertNotNull(button.getRotation());
    }

    @Test
    void testDisabledState()
    {
        button.disabled(true);
        Parent parent = button.render();
        Button fxButton = (Button) parent;

        assertTrue(fxButton.isDisabled());
        assertTrue(fxButton.getStyle().contains("-fx-opacity: 0.4"));
    }

    @Test
    void testButtonTypes()
    {
        ButtonType[] types = ButtonType.values();
        for (ButtonType type : types) {
            button.type(type);
            Parent parent = button.render();
            Button fxButton = (Button) parent;

            String style = fxButton.getStyle();

            switch (type) {
                case PRIMARY -> assertTrue(style.contains("#3B82F6"));
                case SUCCESS -> assertTrue(style.contains("#22C55E"));
                case WARNING -> assertTrue(style.contains("#F59E0B"));
                case DANGER -> assertTrue(style.contains("#EF4444"));
                case INFO -> assertTrue(style.contains("#6366F1"));
                case TEXT -> assertTrue(style.contains("#FFFFFF"));
                default -> assertTrue(style.contains("#F3F4F6"));
            }
        }
    }

    @Test
    void testButtonSizes()
    {
        ButtonSize[] sizes = ButtonSize.values();
        for (ButtonSize size : sizes) {
            button.size(size);
            Parent parent = button.render();
            Button fxButton = (Button) parent;
            String style = fxButton.getStyle();

            switch (size) {
                case SMALL -> assertTrue(style.contains("0.25em 0.5em"));
                case LARGE -> assertTrue(style.contains("0.75em 1.25em"));
                default -> assertTrue(style.contains("0.5em 1em"));
            }
        }
    }

    @Test
    void testClickHandler()
    {
        boolean[] clicked = {false};
        button.onClick(() -> clicked[0] = true);
        Parent parent = button.render();
        Button fxButton = (Button) parent;

        fxButton.fire();
        assertTrue(clicked[0]);
    }

    @Test
    void testResourceCleanup()
    {
        button.loading(true);
        button.render();
        assertNotNull(button.getRotation());

        button.dispose();
        assertNull(button.getRotation());
    }

    @Test
    void testDynamicStateChange()
    {
        Parent parent;
        Button fxButton;

        // 测试加载状态变化
        // Test loading state change
        button.loading(true);
        parent = button.render();
        fxButton = (Button) parent;
        HBox content = (HBox) fxButton.getGraphic();
        assertInstanceOf(StackPane.class, content.getChildren().get(0));

        // 测试禁用状态变化
        // Test disabled state change
        button.disabled(true);
        parent = button.render();
        fxButton = (Button) parent;
        assertTrue(fxButton.isDisabled());
    }

    @Test
    void testFocusStyle()
    {
        Parent parent = button.render();
        Button fxButton = (Button) parent;
        String style = fxButton.getStyle();

        assertTrue(style.contains("-fx-focus-color: transparent"));
        assertTrue(style.contains("-fx-faint-focus-color: transparent"));
    }

    @Test
    void testStaticFactoryMethod()
    {
        NexusButton factoryButton = NexusButton.create("Factory Test");
        assertEquals("Factory Test", factoryButton.getText());
        assertNotNull(factoryButton.render());
    }
}
