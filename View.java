import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * TODO: Create and display directions.
 * TODO: Align the restart button.
 * Responsible for the main thread within the program.
 * Starts with a prompt screen that prompts the user to select within a combo-box
 * the number of players who will be playing. From there, a Track is created
 * with the respective amount of cars.
 */
public class View extends Application {

    /**
     * The parent scene of the pane's.
     */
    private Scene scene;

    /**
     * The main screen for the game.
     * Initialized in StartPrompt's event-handler for the start button to pass the selection for number of users.
     */
    private Track track;

    /**
     * Global reference to the restart button for the restart event-handler.
     */
    private Button restartButton;

    /**
     * Global reference to the start-prompt for the restart event-handler.
     */
    private StartPrompt prompt;
    /**
     * Handles restarting the game. Re-initiates the start prompt and sets it to the screen's root.
     */
    private EventHandler<MouseEvent> restart = mouseEvent -> {
        prompt = new StartPrompt();
        scene.setRoot(prompt);
    };

    /**
     * Starts the program.
     *
     * @param args Arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main running thread of the graphical interface.
     * Starts with a prompt and then ushers in the track.
     *
     * @param stage The parent stage of the scene.
     * @throws Exception
     */
    @Override
    public void start(final Stage stage) throws Exception {
        prompt = new StartPrompt();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        scene = new Scene(prompt, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight(), Color.WHITE);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Creates a prompt to allow users to choose how many players will be playing the game. Separated for clarity.
     */
    public class StartPrompt extends BorderPane {

        /**
         * A drop-down menu with a fixed set of selections.
         */
        private ComboBox<Integer> comboBox;

        /**
         * The start button. Separated for access by it's event handler.
         */
        private Button button;
        /**
         * Handles the start button
         * When the start button is clicked a new Track is created.
         * The value of the selection of the combo box is passed to the Track's setTrack method.
         * Then sets Scene's root to the track.
         * Creates a restart button, adds it to the track and adds an event handler for it.
         */
        private EventHandler<MouseEvent> event = mouseEvent -> {
//            track = new Track();
            comboBox.setDisable(true);
            button.setDisable(true);
            int i = comboBox.getValue() != null ? comboBox.getValue() : 2;
            track = new Track(i, scene.getWidth(),scene.getHeight());

            scene.setRoot(track);
            restartButton = new Button("Restart");
            restartButton.setOnMouseClicked(restart);

            track.getChildren().add(restartButton);
            setAlignment(restartButton,Pos.CENTER);
        };

        /**
         * Initializes the a title, a start button, and a ComboBox for the players to choose the amount of players.
         */
        public StartPrompt() {

            Text text = new Text("Car Race");
            text.setFill(Color.DARKBLUE);
            text.setFont(Font.font(100));

            button = new Button("Start");
            button.setStyle("-fx-background-color: black; -fx-text-fill: #fff");
            button.setOnMouseClicked(event);

            GridPane gp = addGridPane();

            this.setTop(new StackPane(text));
            this.getTop().setTranslateY(100);
            this.setCenter(gp);
            this.setBottom(new StackPane(button));
            this.getBottom().setTranslateY(-100);
            this.setStyle("-fx-background-color: #2c2c2c;");
        }

        /**
         * Creates a grid for layout purposes.
         *
         * @return gp a grid-pane with a label and a combo-box
         */
        private GridPane addGridPane() {
            Label players = new Label("Number of Players\t");
            players.setStyle("-fx-text-fill: #fff");

            Label l2 = new Label("Rules\n" +
                    "1. Each player is given a start and an end location.\n" +
                    "2. You must go to every location on your path from your start to end location. Order does not matter\n" +
                    "3. Each car has randomly assigned attributes that allow for it to travel faster in certain situations.\n" +
                    "Tires will affect your acceleration, engine will affect your top speed, and weight will affect both to a smaller degree.\n" +
                    "A higher acceleration means that you travel quicker between points that are closer together.\n" +
                    "A higher top speed means you will travel quicker between points that are further apart.\n\n" +
                    "4.Click each location to travel there. The car with the shortest time wins!");
            l2.setStyle("-fx-text-fill: #fff");


            comboBox = new ComboBox<Integer>();
            comboBox.getItems().addAll(2, 3, 4, 5, 15);
            comboBox.setEditable(false);
            comboBox.setValue(2);
            comboBox.setStyle("-fx-text-fill: #111111; -fx-border-color: #fff;");

            GridPane gp = new GridPane();


            gp.setAlignment(Pos.CENTER);
            gp.add(l2, 1,0);
            Rectangle rectangle = new Rectangle(100,100);
            rectangle.setFill(Color.TRANSPARENT);
            gp.add(rectangle, 0, 1);
            gp.add(players, 0, 2);
            gp.add(comboBox, 1, 2);

            return gp;
        }
    }


}
