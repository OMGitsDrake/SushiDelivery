/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.sushideliveryservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.unipi.mainproject.sushideliveryservice.beans.Carrello;
import it.unipi.mainproject.sushideliveryservice.beans.HttpConnector;
import it.unipi.mainproject.sushideliveryservice.beans.JSONReader;
import it.unipi.mainproject.sushideliveryservice.beans.Orario;
import it.unipi.mainproject.sushideliveryservice.beans.Ordine;
import it.unipi.mainproject.sushideliveryservice.beans.Piatto;
import it.unipi.mainproject.sushideliveryservice.beans.Utente;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderConfirmationController {
    private static final Logger logger = LogManager.getLogger(OrderConfirmationController.class);
    
    private final Carrello cart;
    private final Utente user;
    private float sconto;
    private String orario = "";
    private String indirizzo;
    private String telefono;
    
    @FXML private Label pointsLabel;
    @FXML private Label subTotalLabel;
    @FXML private Label totalLabel;
    @FXML private Label orarioOkLabel;
    @FXML private Label orarioBadLabel;
    @FXML private Label fieldLabel;
    @FXML private Label discountLabel;
    @FXML private Label missingTimeLabel;
    @FXML private Label badDiscountValueLabel;
    @FXML private Label cartTitleLabel;
    @FXML private Label paymentQuestionLabel;
    @FXML private Label insertPointsLabel;
    @FXML private Button selectCardPayment;
    @FXML private Button selectCashPayment;
    @FXML private Button confirmBtn;
    @FXML private Button backBtn;
    @FXML private ComboBox orarioBox;
    @FXML private TextField cardNumber;
    @FXML private TextField cardCVV;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private TextField pointsField;
    @FXML TableView<Piatto> recapTable = new TableView<>();
    
    private ObservableList<String> options;
    private ObservableList<Piatto> olRecap;
    
    public OrderConfirmationController(Carrello cart, Utente user) {
        this.cart = cart;
        this.user = user;
    }
    
    @FXML
    private void initialize(){
        if(App.currentLang == 1){
            cartTitleLabel.setText(App.lang_en.CART_TITLE);
            orarioBox.setPromptText(App.lang_en.HOUR);
            addressField.setPromptText(App.lang_en.ADDR);
            phoneField.setPromptText(App.lang_en.PHONE);
            backBtn.setText(App.lang_en.BACK);
            subTotalLabel.setText(App.lang_en.SUBT);
            selectCardPayment.setText(App.lang_en.CARD);
            selectCashPayment.setText(App.lang_en.CASH);
            paymentQuestionLabel.setText(App.lang_en.PAY_QUEST);
            cardNumber.setPromptText(App.lang_en.CNUMBER);
            pointsLabel.setText(App.lang_en.USR_PTS);
            insertPointsLabel.setText(App.lang_en.INS_PTS);
            confirmBtn.setText(App.lang_en.CNF_ORD);
            orarioOkLabel.setText(App.lang_en.TIME_OK);
            orarioBadLabel.setText(App.lang_en.TIME_BAD);
            missingTimeLabel.setText(App.lang_en.TIME_NO);
            fieldLabel.setText(App.lang_en.FIELDS_NO);
            discountLabel.setText(App.lang_en.DISCOUNT);
            totalLabel.setText(App.lang_en.TOTAL);
        } else {
            cartTitleLabel.setText(App.lang_it.CART_TITLE);
            orarioBox.setPromptText(App.lang_it.HOUR);
            addressField.setPromptText(App.lang_it.ADDR);
            phoneField.setPromptText(App.lang_it.PHONE);
            backBtn.setText(App.lang_it.BACK);
            subTotalLabel.setText(App.lang_it.SUBT);
            selectCardPayment.setText(App.lang_it.CARD);
            selectCashPayment.setText(App.lang_it.CASH);
            paymentQuestionLabel.setText(App.lang_it.PAY_QUEST);
            cardNumber.setPromptText(App.lang_it.CNUMBER);
            pointsLabel.setText(App.lang_it.USR_PTS);
            insertPointsLabel.setText(App.lang_it.INS_PTS);
            confirmBtn.setText(App.lang_it.CNF_ORD);
            orarioOkLabel.setText(App.lang_it.TIME_OK);
            orarioBadLabel.setText(App.lang_it.TIME_BAD);
            missingTimeLabel.setText(App.lang_it.TIME_NO);
            fieldLabel.setText(App.lang_it.FIELDS_NO);
            discountLabel.setText(App.lang_it.DISCOUNT);
            totalLabel.setText(App.lang_it.TOTAL);
        }
        
        TableColumn nomeRecap = new TableColumn(App.currentLang == 1 ? App.lang_en.NAME: App.lang_it.NAME);
        nomeRecap.setCellValueFactory(new PropertyValueFactory("nome"));
        
        TableColumn prezzoRecap = new TableColumn(App.currentLang == 1 ? App.lang_en.PRICE: App.lang_it.PRICE);
        prezzoRecap.setCellValueFactory(new PropertyValueFactory("costo"));
        
        recapTable.getColumns().addAll(nomeRecap, prezzoRecap);
        olRecap = FXCollections.observableArrayList();
        recapTable.setItems(olRecap);
        olRecap.addAll(cart.getCart());
        
        if(cart.getTipo().equals("pranzo"))
            options = FXCollections.observableArrayList(
                    "12:00", //12
                    "12:15",
                    "12:30",
                    "12:45",
                    "13:00", //13
                    "13:15",
                    "13:30",
                    "13:45",
                    "14:00", //14
                    "14:15",
                    "14:30",
                    "14:45"
            );
        else
            options = FXCollections.observableArrayList(
                    "18:30", //18:30
                    "18:45",
                    "19:00", //19
                    "19:15",
                    "19:30",
                    "19:45",
                    "20:00", //20
                    "20:15",
                    "20:30",
                    "20:45",
                    "21:00", //21
                    "21:15",
                    "21:30",
                    "21:45",
                    "22:00"  //22
            );
        orarioBox.setItems(options);
        
        pointsLabel.setText(user.getPunti() + " " + pointsLabel.getText());
        subTotalLabel.setText(subTotalLabel.getText() + ' ' + cart.getTotale() + " €");
        totalLabel.setText(totalLabel.getText() + ' ' + cart.getTotale() + " €");
    }
    
    @FXML
    private void cashPayment(){
        selectCashPayment.setText("Ok");
        selectCashPayment.setDisable(true);
        selectCardPayment.setDisable(false);
        selectCardPayment.setText(App.currentLang == 1 ? "Credit Card" : "Carta");
        confirmBtn.setDisable(false);
        cardNumber.setDisable(true);
        cardNumber.clear();
        cardCVV.setDisable(true);
        cardCVV.clear();
    }
    
    @FXML
    private void cardPayment(){
        selectCardPayment.setText("Ok");
        selectCardPayment.setDisable(true);
        selectCashPayment.setDisable(false);
        selectCashPayment.setText(App.currentLang == 1 ? "On Delivery" : "Alla Consegna");
        confirmBtn.setDisable(false);
        cardNumber.setDisable(false);
        cardCVV.setDisable(false);
    }
    
    @FXML
    private void calcolaSconto(){
        badDiscountValueLabel.setVisible(false);
        try{
            if(Integer.parseInt(pointsField.getText()) > cart.getUtente().getPunti() || Integer.parseInt(pointsField.getText()) < 0){
                logger.info("Valore non valido inserito");
                badDiscountValueLabel.setText(App.currentLang == 1 ? "Invalid value" : "Valore non valido");
                badDiscountValueLabel.setVisible(true);
                return;
            }
        } catch(NumberFormatException e){
                logger.info("Valore non valido inserito");
                badDiscountValueLabel.setText(App.currentLang == 1 ? "Invalid value" : "Valore non valido");
                badDiscountValueLabel.setVisible(true);
                return;
        }
        
        if(cart.getTotale() - Integer.parseInt(pointsField.getText()) * .75f <= 0){
            logger.info("Valore troppo alto inserito");
            badDiscountValueLabel.setText(App.currentLang == 1 ? "Price cannot be zero" : "Lo sconto non può azzerare il prezzo");
            badDiscountValueLabel.setVisible(true);
            return;
        }
        sconto = Integer.parseInt(pointsField.getText()) * .75f;
        
        float diff = cart.getTotale() - sconto;
        discountLabel.setText((App.currentLang == 1 ? "Discount: " : "Sconto: ") + sconto + " €");
        totalLabel.setText((App.currentLang == 1 ? "Total: " : "Totale: ") + (float)(Math.round(diff*100))/100 + " €");
        cart.updatePoints(cart.getTempPoints() - Integer.parseInt(pointsField.getText()));
    }
    
    @FXML
    private void confirmOrder(){
        if((selectCardPayment.getText().equals("Ok") && (cardNumber.getText().isEmpty() || cardCVV.getText().isEmpty()))){
            fieldLabel.setVisible(true);
            return;
        }
        if(orario.isEmpty()){
            missingTimeLabel.setVisible(true);
            return;
        }
        
        if((addressField.getText().isEmpty() || phoneField.getText().isEmpty())){
            fieldLabel.setVisible(true);
            return;
        }
        indirizzo = addressField.getText();
        telefono = phoneField.getText();
        
        Task t = new Task<Void>(){
            @Override
            public Void call(){
                try{
                    // Invio richiesta al server
                    HttpURLConnection con = HttpConnector.connectPOST("http://localhost:8080/order/confirm");
                    
                    // codifica
                    Gson gson = new Gson();
                    String jsonTipoMenu = gson.toJson(new Ordine(cart.getUtente().getUsername(), orario, indirizzo, telefono, cart.getTotale() - sconto, cart.getTempPoints()));
                    
                    // invio
                    try(OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonTipoMenu.getBytes("utf-8");
                        os.write(input, 0, input.length);			
                    }
                    
                    // Ricezione
                    JsonObject response = JSONReader.read(con);
                    
                    if(response.get("ok").getAsBoolean()){
                        logger.info("Ordine registrato, punti acquisiti: " + cart.getTempPoints());
                    } else {
                        // handle
                        return null;
                    }
                } catch(IOException ioe){
                    logger.error(ioe.getMessage());
                }
                return null;
            }
        };
        t.setOnSucceeded(e -> {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("conclusion.fxml"));
                ConclusionController controller = new ConclusionController(cart.getUtente());
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) backBtn.getScene().getWindow();
                stage.setScene(scene);
            } catch(IOException ioe){
                logger.error(ioe.getMessage());
            }
        });
        new Thread(t).start();
    }
    
    @FXML
    private void scegliOrario(){
        Task t = new Task<Void>(){
            @Override
            public Void call(){
                try{
                    // Invio richiesta al server
                    HttpURLConnection con = HttpConnector.connectPOST("http://localhost:8080/order/choose");
                    
                    // codifica
                    Gson gson = new Gson();
                    String jsonTipoMenu = gson.toJson(new Orario(orarioBox.getSelectionModel().getSelectedItem().toString()));
                    
                    // invio
                    try(OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonTipoMenu.getBytes("utf-8");
                        os.write(input, 0, input.length);			
                    }
                    
                    // Ricezione
                    JsonObject response = JSONReader.read(con);
                    
                    if(response.get("ok").getAsBoolean()){
                        logger.info(response.get("msg") + " - " + orarioBox.getSelectionModel().getSelectedItem().toString());
                        orarioBadLabel.setVisible(false);
                        orarioOkLabel.setVisible(true);
                        missingTimeLabel.setVisible(false);
                        orario = orarioBox.getSelectionModel().getSelectedItem().toString();
                    } else {
                        // handle
                        orarioOkLabel.setVisible(false);
                        orarioBadLabel.setVisible(true);
                        orario = "";
                        return null;
                    }
                } catch(IOException ioe){
                    logger.error(ioe.getMessage());
                }
                return null;
            }
        };
        new Thread(t).start();
    }
    
    @FXML
    private void back() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"));
        DishTableController controller = new DishTableController(cart.getUtente(), cart.getTipo());
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
    }
}
