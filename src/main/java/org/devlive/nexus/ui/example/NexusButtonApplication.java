package org.devlive.nexus.ui.example;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.devlive.nexus.ui.component.NexusContainer;
import org.devlive.nexus.ui.component.button.ButtonSize;
import org.devlive.nexus.ui.component.button.ButtonType;
import org.devlive.nexus.ui.component.button.NexusButton;
import org.devlive.nexus.ui.core.Component;

public class NexusButtonApplication
        extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        Component root = new NexusContainer()
                .style(s -> s
                        .backgroundColor(Color.WHITE)
                        .padding(16)
                        .width(400)
                        .height(300)
                )
                .addChild(NexusButton.create("Primary").type(ButtonType.PRIMARY))
                .addChild(NexusButton.create("Success").type(ButtonType.SUCCESS))
                .addChild(NexusButton.create("Warning").type(ButtonType.WARNING))
                .addChild(NexusButton.create("Danger").type(ButtonType.DANGER))
                .addChild(NexusButton.create("Info").type(ButtonType.INFO))
                .addChild(NexusButton.create("Text").type(ButtonType.TEXT))
                .addChild(NexusButton.create("Default").type(ButtonType.DEFAULT))
                .addChild(NexusButton.create("Small").size(ButtonSize.SMALL))
                .addChild(NexusButton.create("Default").size(ButtonSize.DEFAULT))
                .addChild(NexusButton.create("Large").size(ButtonSize.LARGE));

        Scene scene = new Scene((Parent) root.render());

        stage.setTitle("Nexus UI Button Demo");
        stage.setScene(scene);
        stage.show();
    }
}
