import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author mercm
 */
public class Main extends Application{

    Stage window;
    String translatedText;
    translator perform = new translator();

    @Override
    public void start(Stage primaryStage) throws IOException {

        window = primaryStage;

        window.setTitle("SpanishTranslate");

        final GridPane grid = new GridPane();
        grid.setPadding(new Insets(50, 50, 50, 50));
        grid.getColumnConstraints().add(new ColumnConstraints(825));
        grid.setVgap(10);
        grid.setHgap(10);

        Label message = new Label("Translate English to Spanish");
        GridPane.setConstraints(message, 0, 0);
        message.setPrefSize(300, 10);

        final TextField input = new TextField();
        input.setPromptText("Enter English here");
        GridPane.setConstraints(input, 0, 1);
        input.setPrefSize(840, 450);
        input.setAlignment(Pos.CENTER);

        final TextField output = new TextField();
        output.setPromptText("Translated text will appear here");
        GridPane.setConstraints(output, 2, 1);
        output.setPrefSize(840, 450);
        output.setAlignment(Pos.CENTER);

        Button btn = new Button();
        btn.setText("Translate");
        GridPane.setConstraints(btn, 0, 2);

        btn.setOnAction(event -> {

            try {
                translatedText = perform.translate(input.getText());
                output.setText(translatedText);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        Label loadingMessage = new Label("Loading Stanford NLP. Please wait...");
        GridPane.setConstraints(loadingMessage, 2, 2);
        message.setPrefSize(300, 10);


        FadeTransition fade = new FadeTransition(Duration.seconds(20), loadingMessage);
        fade.setFromValue(1);
        fade.setToValue(0);

        grid.getChildren().addAll(input, output, btn, message, loadingMessage);

        final BooleanProperty firstTime = new SimpleBooleanProperty(true);

        input.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && firstTime.get()) {
                grid.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });




        Scene scene = new Scene(grid, 1775, 450);
        //scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        window.setScene(scene);
        window.setResizable(false);
        window.show();

        fade.play();

        String startProgram = perform.translate("Hi");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
