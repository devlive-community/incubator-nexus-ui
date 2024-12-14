package org.devlive.nexus.ui.core;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Component
{
    protected Style style = new Style();
    protected List<Component> children = new ArrayList<>();

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
}
