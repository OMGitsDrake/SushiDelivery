package it.unipi.mainproject.sushideliveryservice;

import it.unipi.mainproject.sushideliveryservice.beans.Utente;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuController {
    private static final Logger logger = LogManager.getLogger(MenuController.class);
    
    @FXML private Label welcomeLabel;
    @FXML private Button backBtn;
    @FXML private Button pranzoBtn;
    @FXML private Button cenaBtn;
    
    private final Utente user;
    
    public MenuController(Utente u){
        this.user = u;
    }
    
    @FXML
    private void initialize(){
        if(App.currentLang == 1){
            welcomeLabel.setText(App.lang_en.WELCOME + "\n" + user.getUsername() + "!\n" + App.lang_en.PUNTI + "[" + user.getPunti() + ']');
            backBtn.setText(App.lang_en.BACK);
            pranzoBtn.setText(App.lang_en.PRANZO);
            cenaBtn.setText(App.lang_en.CENA);
        } else {
            welcomeLabel.setText(App.lang_it.WELCOME + "\n" + user.getUsername() + "!\n" + App.lang_it.PUNTI + "[" + user.getPunti() + ']');
            backBtn.setText(App.lang_it.BACK);
            pranzoBtn.setText(App.lang_it.PRANZO);
            cenaBtn.setText(App.lang_it.CENA);
        }
        
        logger.info("Inizializzo la scene con utente: " + user.toString());
    }
    
    @FXML
    private void scegliPranzo(){
        logger.info("Scelto pranzo");
        cambiaScena("pranzo");
    }
    
    @FXML
    private void scegliCena(){
        logger.info("Scelta cena");
        cambiaScena("cena");
    }
    
    @FXML
    private void quit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.setScene(scene);
    }
    
    private void cambiaScena(String t){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"));
            DishTableController controller = new DishTableController(user, t);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch(IOException ioe){
            logger.error(ioe.getMessage());
        }
    }
}