package org.devlive.nexus.ui.core;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Component
{
    protected Style style = new Style();
    protected List<Component> children = new ArrayList<>();
    protected boolean disposed = false;
    protected Node renderedNode;

    public abstract Node render();

    public Component style(Consumer<Style> styleBuilder)
    {
        styleBuilder.accept(this.style);
        return this;
    }

    public Component addChild(Component child)
    {
        children.add(child);
        return this;
    }

    public Component onClick(Runnable action)
    {
        Node node = render();
        node.setOnMouseClicked(event -> action.run());
        return this;
    }

    /**
     * 释放组件资源
     * Release component resources
     */
    public void dispose()
    {
        if (disposed) {
            return;
        }

        // 先释放子组件
        // Dispose children first
        for (Component child : children) {
            child.dispose();
        }
        children.clear();

        // 清理事件监听器
        // Clean up event listeners
        if (renderedNode != null) {
            renderedNode.setOnMouseClicked(null);
            renderedNode = null;
        }

        disposed = true;
    }

    /**
     * 检查组件是否已被释放
     * Check if component is disposed
     */
    protected boolean isDisposed()
    {
        return disposed;
    }

    /**
     * 检查组件是否已渲染
     * Check if component is rendered
     */
    protected boolean isRendered()
    {
        return renderedNode != null;
    }

    /**
     * 重新渲染组件
     * Re-render component
     */
    protected Node rerender()
    {
        if (disposed) {
            throw new IllegalStateException("Cannot rerender disposed component");
        }
        renderedNode = render();
        return renderedNode;
    }
}
