package it.unipi.mainproject.sushideliveryservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.unipi.mainproject.sushideliveryservice.beans.Credenziali;
import it.unipi.mainproject.sushideliveryservice.beans.HttpConnector;
import it.unipi.mainproject.sushideliveryservice.beans.JSONReader;
import it.unipi.mainproject.sushideliveryservice.beans.Utente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Lorenzo
 */
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    
    @FXML private Button loginBtn;
    @FXML private Button registerBtn;
    @FXML private Label authErr;
    @FXML private TextField userText;
    @FXML private PasswordField passwordText;
    @FXML private MenuItem itemLangIT;
    @FXML private MenuItem itemLangEN;
    @FXML private MenuItem installDB;
    @FXML private Menu setLang;
    @FXML private Menu settings;
    
    @FXML
    private void initialize(){
        if(App.currentLang == 1)
            setLangEN();
        else
            setLangIT();
    }
    
    @FXML
    private void setLangEN(){
        setLang.setText(App.lang_en.LANG);
        itemLangIT.setText(App.lang_en.IT);
        itemLangEN.setText(App.lang_en.EN);
        installDB.setText(App.lang_en.INSTALL);
        settings.setText(App.lang_en.SETTINGS);
        loginBtn.setText(App.lang_en.LOGIN);
        registerBtn.setText(App.lang_en.REGISTER);
        authErr.setText(App.lang_en.AUTHERR);

        App.currentLang = 1;
        
        logger.info("Language swap to EN");
    }
    
    @FXML
    private void setLangIT(){
        setLang.setText(App.lang_it.LANG);
        itemLangIT.setText(App.lang_it.IT);
        itemLangEN.setText(App.lang_it.EN);
        installDB.setText(App.lang_it.INSTALL);
        settings.setText(App.lang_it.SETTINGS);
        loginBtn.setText(App.lang_it.LOGIN);
        registerBtn.setText(App.lang_it.REGISTER);
        authErr.setText(App.lang_it.AUTHERR);

        App.currentLang = 0;
        
        logger.info("Language swap to IT");
    }
    
    @FXML
    private void userLogin() throws IOException {
        logger.debug("login");
        
        if(userText.getText().isBlank() || passwordText.getText().isBlank()){
            authErr.setText(App.currentLang == 1 ? "All fields are required" : "Tutti i campi sono obbligatori");
            authErr.setVisible(true);
            return;
        }
        
        Task<Utente> t = new Task<Utente>(){
            @Override
            public Utente call(){
                try{
                    HttpURLConnection con = HttpConnector.connectPOST("http://localhost:8080/user/login");

                    Gson gson = new Gson();
                    String jsonCredentials = gson.toJson(new Credenziali(userText.getText(), passwordText.getText()));
                    
                    try(OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonCredentials.getBytes("utf-8");
                        os.write(input, 0, input.length);			
                    }
                    JsonObject response = JSONReader.read(con);
                    
                    if(response.get("ok").getAsBoolean()){
                        logger.debug(response.get("msg"));
                        return gson.fromJson(response.get("user").getAsJsonObject(), Utente.class);
                    } else {
                        authErr.setVisible(true);
                        return null;
                    }
                } catch (IOException ioe){
                    logger.error(ioe.getMessage());
                    authErr.setText("Server in manutenzione, riprovare più tardi.");
                    authErr.setVisible(true);
                }
                return null;
            }
        };
        
        t.setOnSucceeded(e -> {
            Utente u = t.getValue();
            if(u == null)
                return;
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                MenuController controller = new MenuController(u);
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) userText.getScene().getWindow();
                stage.setScene(scene);
            } catch(IOException ioe){
                logger.error(ioe.getMessage());
            }
        });
        new Thread(t).start();
    }
    
    @FXML
    private void install() throws SQLException, IOException{
        try ( // connessione DB e caricamento del file con l'inizializzazione
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", App.user, App.pass)) {
            InputStream inputStream = LoginController.class.getClassLoader().getResourceAsStream("buildDB.sql");
            
            // metto dentro content tutto il file riga per riga, grazie alla lambda function
            // e creo stringhe che rappresentano le query con split()
            String content = new BufferedReader(new InputStreamReader(inputStream)).lines().reduce("", (a, b) -> a + b + "\n");
            String[] queries = content.split(";");
            
            // creazione statement e esecuzione separata di ogni query 'raccolta' dal file
            Statement stmt = con.createStatement();
            for(String q : queries){
                // scarto eventuali stringhe vuote
                if(q.trim().isEmpty()){
                    continue;
                }
                stmt.execute(q.trim());
            }
        }
    }
    
    @FXML
    private void userRegister() throws IOException {
        logger.info("register");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) userText.getScene().getWindow();
        stage.setScene(scene);
    }
}
