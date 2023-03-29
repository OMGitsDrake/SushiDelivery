/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.sushideliveryservice;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unipi.mainproject.sushideliveryservice.beans.Carrello;
import it.unipi.mainproject.sushideliveryservice.beans.HttpConnector;
import it.unipi.mainproject.sushideliveryservice.beans.JSONReader;
import it.unipi.mainproject.sushideliveryservice.beans.Piatto;
import it.unipi.mainproject.sushideliveryservice.beans.Preferito;
import it.unipi.mainproject.sushideliveryservice.beans.TipoMenu;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Lorenzo
 */
public class DishTableController {
    private static final Logger logger = LogManager.getLogger(DishTableController.class);
    @FXML TableView<Piatto> dishesTable = new TableView<>();
    @FXML TableView<Piatto> cartTable = new TableView<>();
    @FXML TableView<Piatto> starredTable = new TableView<>();
    @FXML private Label cartLabel;
    @FXML private Label userPtsLabel;
    @FXML private Label starredTitleLabel;
    @FXML private Label starredErrLabel;
    @FXML private Label maxStarredNmbrLabel;
    @FXML private Button backBtn;
    @FXML private Button orderBtn;
    @FXML private MenuItem cartRimuoviBtn;
    @FXML private MenuItem aggiungiStarredBtn;
    @FXML private MenuItem rimuoviStarredBtn;
    @FXML private MenuItem aggiungiPiattoBtn;
    @FXML private MenuItem makeStarredBtn;
    
    private ObservableList<Piatto> olMenu;
    private ObservableList<Piatto> olCart;
    private ObservableList<Piatto> olStarred;
    private final Carrello cart;
    private final String tipo;
    
    public DishTableController(Utente u, String tipo){
        this.cart = new Carrello(u, tipo);
        this.tipo = tipo;
    }
    
    @FXML
    private void initialize(){
        if(App.currentLang == 1){
            starredTitleLabel.setText(App.lang_en.STARRED);
            backBtn.setText(App.lang_en.BACK);
            orderBtn.setText(App.lang_en.ORDER);
            cartRimuoviBtn.setText(App.lang_en.CART_REMOVE);
            aggiungiStarredBtn.setText(App.lang_en.CART_ADD);
            aggiungiPiattoBtn.setText(App.lang_en.CART_ADD);
            rimuoviStarredBtn.setText(App.lang_en.STARRED_REMOVE);
            makeStarredBtn.setText(App.lang_en.MAKE_STARRED);
            maxStarredNmbrLabel.setText(App.lang_en.MAX_STARRED);
        } else {
            starredTitleLabel.setText(App.lang_it.STARRED);
            backBtn.setText(App.lang_it.BACK);
            orderBtn.setText(App.lang_it.ORDER);
            cartRimuoviBtn.setText(App.lang_it.CART_REMOVE);
            aggiungiStarredBtn.setText(App.lang_it.CART_ADD);
            aggiungiPiattoBtn.setText(App.lang_it.CART_ADD);
            rimuoviStarredBtn.setText(App.lang_it.STARRED_REMOVE);
            makeStarredBtn.setText(App.lang_it.MAKE_STARRED);
            maxStarredNmbrLabel.setText(App.lang_it.MAX_STARRED);
        }
        
        dishesTable.setPlaceholder(new Label(App.currentLang == 1 ? "Something went wrong" : "Qualcosa è andato storto"));
        cartTable.setPlaceholder(new Label(App.currentLang == 1 ? "Your cart is empty" : "Il carrello è vuoto"));
        starredTable.setPlaceholder(new Label(App.currentLang == 1 ? "Here you can see your favourite dishes" : "Qua puoi visualizzare i tuoi piatti preferiti"));
        
        // Observable List per il menu
        TableColumn idMenu = new TableColumn(App.currentLang == 1 ? App.lang_en.CODE : App.lang_it.CODE);
        idMenu.setCellValueFactory(new PropertyValueFactory("id_piatto"));
        
        TableColumn nomeMenu = new TableColumn(App.currentLang == 1 ? App.lang_en.NAME : App.lang_it.NAME);
        nomeMenu.setCellValueFactory(new PropertyValueFactory("nome"));
        
        TableColumn ingredientiMenu = new TableColumn(App.currentLang == 1 ? App.lang_en.INGREDIENTS : App.lang_it.INGREDIENTS);
        ingredientiMenu.setCellValueFactory(new PropertyValueFactory("ingredienti"));
        
        TableColumn prezzoMenu = new TableColumn(App.currentLang == 1 ? App.lang_en.PRICE : App.lang_it.PRICE);
        prezzoMenu.setCellValueFactory(new PropertyValueFactory("costo"));
        
        dishesTable.getColumns().addAll(idMenu, nomeMenu, ingredientiMenu, prezzoMenu);
        olMenu = FXCollections.observableArrayList();
        dishesTable.setItems(olMenu);
        
        // Observable List per il carrello
        TableColumn nomeCart = new TableColumn(App.currentLang == 1 ? App.lang_en.NAME : App.lang_it.NAME);
        nomeCart.setCellValueFactory(new PropertyValueFactory("nome"));
        
        TableColumn prezzoCart = new TableColumn(App.currentLang == 1 ? App.lang_en.PRICE : App.lang_it.PRICE);
        prezzoCart.setCellValueFactory(new PropertyValueFactory("costo"));
        
        cartTable.getColumns().addAll(nomeCart, prezzoCart);
        olCart = FXCollections.observableArrayList();
        cartTable.setItems(olCart);
        
        // Observable List per i preferiti
        TableColumn nomeStar = new TableColumn(App.currentLang == 1 ? App.lang_en.NAME : App.lang_it.NAME);
        nomeStar.setCellValueFactory(new PropertyValueFactory("nome"));
        
        TableColumn prezzoStar = new TableColumn(App.currentLang == 1 ? App.lang_en.PRICE : App.lang_it.PRICE);
        prezzoStar.setCellValueFactory(new PropertyValueFactory("costo"));
        
        starredTable.getColumns().addAll(nomeStar, prezzoStar);
        olStarred = FXCollections.observableArrayList();
        starredTable.setItems(olStarred);
        
        if(App.currentLang == 1)
            cartLabel.setText("Cart | Total: [" + cart.getTotale() + " €] | Points acquired: {" + cart.getTempPoints() + "}");
        else
            cartLabel.setText("Carrello | Totale: [" + cart.getTotale() + " €] | Punti acquisiti: {" + cart.getTempPoints() + "}");
        userPtsLabel.setText(cart.getUtente().getUsername() + "["+cart.getUtente().getPunti()+"]");
        
        Task t = new Task<Void>(){
            @Override
            public Void call(){
                try{
                    // Invio richiesta al server
                    HttpURLConnection con = HttpConnector.connectPOST("http://localhost:8080/dish/list");
                    
                    // codifica
                    Gson gson = new Gson();
                    String jsonTipoMenu = gson.toJson(new TipoMenu(tipo));
                    
                    // invio
                    try(OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonTipoMenu.getBytes("utf-8");
                        os.write(input, 0, input.length);			
                    }
                    
                    // Ricezione
                    JsonObject response = JSONReader.read(con);
                    
                    if(response.get("ok").getAsBoolean()){
                        logger.info(response.get("msg"));
                        JsonArray ja = response.get("menu").getAsJsonArray();
                        for(int i = 0; i < ja.size(); i++){
                            Piatto p = gson.fromJson(ja.get(i), Piatto.class);
                            olMenu.add(p);
                        }
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
        new Thread(t).start();
        
        Task t1 = new Task<Void>(){
            @Override
            public Void call(){
                try{
                    // Invio richiesta al server
                    HttpURLConnection con = HttpConnector.connectPOST("http://localhost:8080/dish/starred");
                    
                    // codifica
                    Gson gson = new Gson();
                    String jsonUsername = gson.toJson(cart);
                    
                    // invio
                    try(OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonUsername.getBytes("utf-8");
                        os.write(input, 0, input.length);			
                    }
                    
                    // Ricezione
                    JsonObject response = JSONReader.read(con);
                    
                    if(response.get("ok").getAsBoolean()){
                        logger.info(response.get("msg"));
                        JsonArray ja = response.get("menu").getAsJsonArray();
                        for(int i = 0; i < ja.size(); i++){
                            Piatto p = gson.fromJson(ja.get(i), Piatto.class);
                            olStarred.add(p);
                        }
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
        new Thread(t1).start();
    }
    
    @FXML
    private void aggiungiStarred(){
        orderBtn.setDisable(false);
        maxStarredNmbrLabel.setVisible(false);
        starredErrLabel.setVisible(false);
        cart.aggiungi(starredTable.getSelectionModel().getSelectedItem());
        cart.updateTotaleAdd(starredTable.getSelectionModel().getSelectedItem().getCosto());
        cart.updatePoints((int)cart.getTotale()/15);
        cartLabel.setText(App.currentLang == 1 ? "Cart | Total: [" + cart.getTotale() + " €] | Points acquired: {" + cart.getTempPoints() + "}" : 
                                                "Carrello | Totale: [" + cart.getTotale() + " €] | Punti acquisiti: {" + cart.getTempPoints() + "}");
        olCart.add(starredTable.getSelectionModel().getSelectedItem());
        logger.info(cart.toString());
    }
    
    @FXML
    private void makeStarred(){
        maxStarredNmbrLabel.setVisible(false);
        starredErrLabel.setVisible(false);
        Task t = new Task<Void>(){
            @Override
            public Void call(){
                try{
                    // Invio richiesta al server
                    HttpURLConnection con = HttpConnector.connectPOST("http://localhost:8080/dish/makestarred");
                    
                    // codifica
                    Gson gson = new Gson();
                    String jsonData = gson.toJson(new Preferito(dishesTable.getSelectionModel().getSelectedItem().getId_piatto(), cart.getUtente().getUsername()));
                    
                    // invio
                    try(OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonData.getBytes("utf-8");
                        os.write(input, 0, input.length);			
                    }
                    
                    // Ricezione
                    JsonObject response = JSONReader.read(con);
                    
                    if(response.get("ok").getAsBoolean()){
                        logger.info(response.get("msg"));
                        olStarred.add(dishesTable.getSelectionModel().getSelectedItem());
                    } else {
                        if(response.get("err").getAsInt() == 1)
                            starredErrLabel.setVisible(true);
                        else // 2
                            maxStarredNmbrLabel.setVisible(true);
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
    private void removeStarred(){
        maxStarredNmbrLabel.setVisible(false);
        starredErrLabel.setVisible(false);
        Task t = new Task<Void>(){
            @Override
            public Void call(){
                try{
                    // Invio richiesta al server
                    HttpURLConnection con = HttpConnector.connectDELETE("http://localhost:8080/dish/removestarred");
                    
                    // codifica
                    Gson gson = new Gson();
                    String jsonData = gson.toJson(new Preferito(starredTable.getSelectionModel().getSelectedItem().getId_piatto(), cart.getUtente().getUsername()));
                    
                    // invio
                    try(OutputStream os = con.getOutputStream()) {
                        byte[] input = jsonData.getBytes("utf-8");
                        os.write(input, 0, input.length);			
                    }
                    
                    // Ricezione
                    JsonObject response = JSONReader.read(con);
                    
                    if(response.get("ok").getAsBoolean()){
                        logger.info(response.get("msg"));
                        olStarred.remove(starredTable.getSelectionModel().getSelectedItem());
                    } else {
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
    private void aggiungiPiatto(){
        orderBtn.setDisable(false);
        maxStarredNmbrLabel.setVisible(false);
        starredErrLabel.setVisible(false);
        cart.aggiungi(dishesTable.getSelectionModel().getSelectedItem());
        cart.updateTotaleAdd(dishesTable.getSelectionModel().getSelectedItem().getCosto());
        cart.updatePoints((int)cart.getTotale()/15);
        cartLabel.setText(App.currentLang == 1 ? "Cart | Total: [" + cart.getTotale() + " €] | Points acquired: {" + cart.getTempPoints() + "}" : 
                                                "Carrello | Totale: [" + cart.getTotale() + " €] | Punti acquisiti: {" + cart.getTempPoints() + "}");
        olCart.add(dishesTable.getSelectionModel().getSelectedItem());
        logger.info(cart.toString());
    }
    
    @FXML
    private void rimuoviPiatto(){
        maxStarredNmbrLabel.setVisible(false);
        starredErrLabel.setVisible(false);
        cart.rimuovi(cartTable.getSelectionModel().getSelectedItem());
        cart.updateTotaleRemove(cartTable.getSelectionModel().getSelectedItem().getCosto());
        cart.updatePoints((int)cart.getTotale()/15);
        cartLabel.setText(App.currentLang == 1 ? "Cart | Total: [" + cart.getTotale() + " €] | Points acquired: {" + cart.getTempPoints() + "}" : 
                                                "Carrello | Totale: [" + cart.getTotale() + " €] | Punti acquisiti: {" + cart.getTempPoints() + "}");
        olCart.remove(cartTable.getSelectionModel().getSelectedItem());
        logger.info(cart.toString());
        if(cart.getCart().isEmpty())
            orderBtn.setDisable(true);
    }
    
    @FXML
    private void order() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("order.fxml"));
        OrderConfirmationController controller = new OrderConfirmationController(cart, cart.getUtente());
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
    }
    
    @FXML
    private void back() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        MenuController controller = new MenuController(cart.getUtente());
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
    }
}
