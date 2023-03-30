/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.sushideliveryservice;

import it.unipi.mainproject.sushideliveryservice.beans.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Lorenzo
 */
public class ConclusionController {
    private static final Logger logger = LogManager.getLogger(ConclusionController.class);

    @FXML private Label userLabel;
    @FXML private Label msgLabel;
    @FXML private Button exitBtn;
    
    private final Utente user;

    public ConclusionController(Utente user) {
        this.user = user;
    }
    
    @FXML
    private void initialize(){
        if(App.currentLang == 1){
            userLabel.setText(App.lang_en.THANKS + user.getUsername() + "!");
            msgLabel.setText(App.lang_en.CNC_MSG);
            exitBtn.setText(App.lang_en.EXIT);
        } else {
            userLabel.setText(App.lang_it.THANKS + user.getUsername() + "!");
            exitBtn.setText(App.lang_it.EXIT);
            msgLabel.setText(App.lang_it.CNC_MSG);
        }
    }
    
    @FXML
    private void quit(){
        Stage s = (Stage) exitBtn.getScene().getWindow();
        s.close();
    }
}
