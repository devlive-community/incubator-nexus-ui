package org.devlive.nexus.ui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.devlive.nexus.ui.component.NexusContainer;
import org.devlive.nexus.ui.component.NexusButton;
import org.devlive.nexus.ui.core.Component;

public class NexusApplication
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
                .addChild(new NexusButton("Primary").type(NexusButton.ButtonType.PRIMARY))
                .addChild(new NexusButton("Success").type(NexusButton.ButtonType.SUCCESS))
                .addChild(new NexusButton("Warning").type(NexusButton.ButtonType.WARNING))
                .addChild(new NexusButton("Danger").type(NexusButton.ButtonType.DANGER))
                .addChild(new NexusButton("Info").type(NexusButton.ButtonType.INFO))
                .addChild(new NexusButton("Text").type(NexusButton.ButtonType.TEXT))
                .addChild(new NexusButton("Default").type(NexusButton.ButtonType.DEFAULT))
                .addChild(new NexusButton("Link"));

        Scene scene = new Scene((Parent) root.render());

        stage.setTitle("Nexus UI Demo");
        stage.setScene(scene);
        stage.show();
    }
}
