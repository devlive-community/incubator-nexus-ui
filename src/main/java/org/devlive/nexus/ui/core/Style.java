package org.devlive.nexus.ui.core;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;

public class Style
{
    private Color backgroundColor;
    private Color textColor;
    private Insets padding;
    private Insets margin;
    private double width = -1;
    private double height = -1;

    public Style backgroundColor(Color color)
    {
        this.backgroundColor = color;
        return this;
    }

    public Style textColor(Color color)
    {
        this.textColor = color;
        return this;
    }

    public Style width(double width)
    {
        this.width = width;
        return this;
    }

    public Style height(double height)
    {
        this.height = height;
        return this;
    }

    public Style padding(double value)
    {
        this.padding = new Insets(value);
        return this;
    }

    public Style margin(double value)
    {
        this.margin = new Insets(value);
        return this;
    }

    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    public Color getTextColor()
    {
        return textColor;
    }

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }

    public Insets getPadding()
    {
        return padding;
    }

    public Insets getMargin()
    {
        return margin;
    }
}
