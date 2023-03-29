package it.unipi.mainproject.sushideliveryservice;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import it.unipi.mainproject.sushideliveryservice.languages.Linguaggio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    static final XStream xstream = new XStream();
    static Linguaggio lang_en;
    static Linguaggio lang_it;
    static final String connectionURL = "jdbc:mysql://localhost:3306/620826";
    static final String user = "root";
    static final String pass = "Cic@da3310";
    static int currentLang = 0;
    
    @Override
    public void start(Stage stage) throws IOException {
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("lang", Linguaggio.class);
        lang_en = (Linguaggio) App.xstream.fromXML(getClass().getResource("languages/lang_en.xml"));
        lang_it = (Linguaggio) App.xstream.fromXML(getClass().getResource("languages/lang_it.xml"));
        scene = new Scene(loadFXML("login"));
        Image icon = new Image(getClass().getResourceAsStream("/it/unipi/mainproject/sushideliveryservice/img/app_icon.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Sushi Delivery");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}