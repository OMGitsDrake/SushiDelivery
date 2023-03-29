/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.sushideliveryservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.unipi.mainproject.sushideliveryservice.beans.HttpConnector;
import it.unipi.mainproject.sushideliveryservice.beans.JSONReader;
import it.unipi.mainproject.sushideliveryservice.exceptions.ParameterException;
import it.unipi.mainproject.sushideliveryservice.beans.Registrazione;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class RegisterController {
    private static final Logger logger = LogManager.getLogger(RegisterController.class);
    
    @FXML private TextField userReg;
    @FXML private TextField userMail;
    @FXML private TextField pswdReg;
    @FXML private TextField rePswdReg;
    @FXML private Label titleLabel;
    @FXML private Label errMsg;
    @FXML private Button regConfirmBtn;
    @FXML private Button backBtn;
    @FXML private DatePicker datePicker;
    
    @FXML
    private void initialize(){
        if(App.currentLang == 1){
            backBtn.setText(App.lang_en.BACK);
            datePicker.setPromptText(App.lang_en.BDATE);
            rePswdReg.setPromptText(App.lang_en.RE_PASSWORD);
            titleLabel.setText(App.lang_en.RE_TITLE);
        } else {
            backBtn.setText(App.lang_it.BACK);
            datePicker.setPromptText(App.lang_it.BDATE);
            rePswdReg.setPromptText(App.lang_it.RE_PASSWORD);
            titleLabel.setText(App.lang_it.RE_TITLE);
        }
    }
    
    @FXML
    public void register() throws IOException{
        if(userReg.getText().isBlank() || userMail.getText().isBlank() || pswdReg.getText().isBlank() || rePswdReg.getText().isBlank() || datePicker.getValue() == null){
            errMsg.setText(App.currentLang == 1 ? "All fields are required" : "Tutti i campi sono obbligatori");
            errMsg.setVisible(true);
            return;
        }

        Task t = new Task<Void>(){
            @Override
            public Void call(){
                try{
                    // Registrazione, interazione con il servizio
                    logger.info("Registrazione");

                    // Nome utente tra 4 e 10 caratteri alfabetici
                    Pattern p = Pattern.compile("^[a-zA-Z0-9]{4,10}$", 0);
                    Matcher m = p.matcher(userReg.getText());
                    if(!m.find())
                        throw new ParameterException(2, App.currentLang == 1 ? "Wrong username format" : "Formato del nome utente errato");

                    // E-mail
                    p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", 0);
                    m = p.matcher(userMail.getText());
                    if(!m.find())
                        throw new ParameterException(3, App.currentLang == 1 ? "Wrong email format" : "Formato della mail errato");

                    // Passowrd almeno un numero, una minuscola e una maiuscola, da 8 caratteri in poi
                    p = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", 0);
                    m = p.matcher(pswdReg.getText());
                    if(!m.find())
                        throw new ParameterException(4, App.currentLang == 1 ? "Wrong password format" : "Formato della password errato");
                    m = p.matcher(rePswdReg.getText());
                    if(!m.find())
                        throw new ParameterException(4, App.currentLang == 1 ? "Wrong password format" : "Formato della password errato");

                    // Password hash
                    String hash = BCrypt.hashpw(pswdReg.getText(), BCrypt.gensalt());

                    // Connessione
                    HttpURLConnection con = HttpConnector.connectPOST("http://localhost:8080/user/sign");

                    Gson gson = new Gson();

                    String jsonCredentials = gson.toJson(new Registrazione(userReg.getText(), userMail.getText(), hash, datePicker.getValue().toString()));

                    // invio
                    try(OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonCredentials.getBytes("utf-8");
                        os.write(input, 0, input.length);			
                    }
                    
                    // ricezione e parsing
                    JsonObject response = JSONReader.read(con);

                    if(response.get("ok").getAsBoolean()){
                        logger.info(response.get("msg"));
                        logger.debug(datePicker.getValue());
                        return null;
                    } else {
                        logger.error(response.get("msg"));
                        errMsg.setText(App.currentLang == 1 ? "Something went wrong, try again later" : "Qualcosa è andato storto, riprova più tardi");
                        errMsg.setVisible(true);
                    }
                } catch(ParameterException pe){
                    // error handling
                    errMsg.setText(pe.getMessage());
                    errMsg.setVisible(true);
                } catch (IOException ioe){
                    logger.error(ioe.getMessage());
                }
                return null;
            }
        };
        t.setOnSucceeded(e -> {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) userReg.getScene().getWindow();
                stage.setScene(scene);
            } catch(IOException ioe){
                logger.error(ioe.getMessage());
            }
        });
        new Thread(t).start();
    }
    
    @FXML
    private void back() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) userReg.getScene().getWindow();
        stage.setScene(scene);
    }
}
