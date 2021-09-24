package mappe.del3.addressregister.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main class. Sets the stage with elements from Factory class.
 * Shows the stage.
 *
 * @author Sindre Glomnes
 * @version 2021-05-14
 */
public class AddressRegisterApp extends Application {

    //launch
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Opens the primary stage.
     * Builds the borderPane root scene with nodes created in Factory class.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Creates an instance of Factory
        Factory factory = new Factory();

        // Creates a Borderpane as root
        BorderPane root = new BorderPane();
        VBox topContainer = new VBox();

        topContainer.getChildren().addAll(factory.createMenus(), factory.createButtons());
        topContainer.setMinHeight(105);

        // Sets the different containers of the BorderPane
        root.setTop(topContainer);
        root.setBottom(factory.createStatusBar());
        root.setCenter(factory.createCentreContent());

        // Sets the stage with the root
        Scene scene = new Scene(root, 715, 600);

        // Adds an icon to the application
        primaryStage.getIcons().add(new Image("file:src/main/resources/images/address.png"));
        // Sets the title to the application
        primaryStage.setTitle("Address Register v.0.1-SNAPSHOT");
        // Sets the scene to the stage
        primaryStage.setScene(scene);
        // Shows the stage
        primaryStage.show();
    }

    /**
     * Closes the application
     */
    @Override
    public void stop() {
        System.exit(0);
    }
}
