/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import DBAcess.ClubDBAccess;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Booking;
import model.Member;
import vistaLogin.FXMLLoginController;
import vistaMisReservas.FXMLMisReservasController;
import vistaPistes.FXMLVistaPistesController;
import vistaPrincipal.FXMLPrincipalController;
import vistaRegistre.FXMLRegistreController;

/**
 *
 * @author 2jesu
 */
public class FXMLPaddleController implements Initializable {
    

    @FXML
    private BorderPane borderPane;
    public BooleanProperty logged;
    @FXML
    private ToolBar toolbarNoLogged;
    @FXML
    private HBox toolbarLogged;
    @FXML
    private ImageView fotoPerfil;
    @FXML
    private Label usuariText;
    private Member member;
    
    public BooleanProperty cargant;
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logged = new SimpleBooleanProperty(false);
       cargant = new SimpleBooleanProperty(false);
        toolbarLogged.visibleProperty().bind(logged);
         toolbarNoLogged.visibleProperty().bind(Bindings.not(logged));
         toolbarLogged.disableProperty().bind(cargant);
         toolbarNoLogged.disableProperty().bind(cargant);
         FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistaPistes/FXMLVistaPistes.fxml"));
         try {
             
             //AnchorPane vistaPistes = (AnchorPane) FXMLLoader.load(getClass().getResource("/vistaPistes/FXMLVistaPistes.fxml"));
             //hola
            BorderPane root = (BorderPane) miCargador.load();
            FXMLVistaPistesController controlador = miCargador.<FXMLVistaPistesController>getController();
            controlador.init(member, this);
             System.out.println("yeeee");
             borderPane.setCenter(root);
             
         } catch(IOException e) {
             System.err.println(e.toString());
         }
         
    }    

   private void updatePaid() {
       ClubDBAccess club = DBAcess.ClubDBAccess.getSingletonClubDBAccess();
       List<Booking> bookings = club.getUserBookings(member.getLogin());
       LocalDateTime now = LocalDateTime.now();
       
       
       for(Booking b : bookings) {
           LocalDateTime date = b.getBookingDate();
           
           LocalDateTime unDiaAbans = date.minusDays(1);
          
           if(now.isAfter(unDiaAbans)) {
               b.setPaid(true);
           }
           
       }
   }
   
    @FXML
    private void login(ActionEvent event) {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistaLogin/FXMLLogin.fxml"));
         try {
             
        
             
            VBox root = (VBox) miCargador.load();
            FXMLLoginController controlador = miCargador.<FXMLLoginController>getController();
             controlador.init(this);
             
             borderPane.setCenter(root);
             
         } catch(IOException e) {
             System.err.println(e.toString());
         }
    }

    @FXML
    private void reg(ActionEvent event) {
        register();
    }

    @FXML
    private void pistes(ActionEvent event) {
        reservarPista();
    }
    
    public void reservarPista() {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistaPistes/FXMLVistaPistes.fxml"));
         try {
             
            
            BorderPane root = (BorderPane) miCargador.load();
            FXMLVistaPistesController controlador = miCargador.<FXMLVistaPistesController>getController();
            controlador.init(member, this);
           
             borderPane.setCenter(root);
             
         } catch(IOException e) {
             System.err.println(e.toString());
         }
    }
    public void updatePerfil(Member mem) {
        System.out.println("Estic diiiins");
        member = mem;
        fotoPerfil.setImage(member.getImage());
        usuariText.setText("Usuari: " + member.getLogin());
        
        updatePaid();
    }
    public void entrar(Member mem) {
        System.out.println("Estic diiiins");
        member = mem;
        Image imatge = member.getImage();
        
        if(imatge != null) {
            fotoPerfil.setImage(member.getImage());
        }
        usuariText.setText("Usuari: " + member.getLogin());
       
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistaPrincipal/FXMLPrincipal.fxml"));
         try {
             
            
            VBox root = (VBox) miCargador.load();
            FXMLPrincipalController controlador = miCargador.<FXMLPrincipalController>getController();
             controlador.init(member, this);
             System.out.println("yeeee");
             borderPane.setCenter(root);
             
         } catch(IOException e) {
             System.err.println(e.toString());
         }
        updatePaid();
        logged.setValue(true);
    }
    
    public void register() {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistaRegistre/FXMLRegistre.fxml"));
         try {
             
            
            BorderPane root = (BorderPane) miCargador.load();
            
          
           borderPane.setAlignment(root, Pos.TOP_CENTER);
           //borderPane.setMinHeight(root.getHeight() + 1000);
           
            FXMLRegistreController controlador = miCargador.<FXMLRegistreController>getController();
            controlador.init(this, false);
            
             borderPane.setCenter(root);
             
         } catch(IOException e) {
             System.err.println(e.toString());
         }
    }
    
    public void misReservas(boolean postPrimer, boolean noPag, boolean actives) {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistaMisReservas/FXMLMisReservas.fxml"));
         try {
             
        
             
            BorderPane root = (BorderPane) miCargador.load();
            FXMLMisReservasController controlador = miCargador.<FXMLMisReservasController>getController();
           
            controlador.setConfig(postPrimer, noPag, actives);
            controlador.init(this, member);
            
            borderPane.setCenter(root);
           
             
         } catch(IOException e) {
             System.err.println(e.toString());
         }
    }

    @FXML
    private void meuesReserves(ActionEvent event) {
        misReservas(false, false, false);
    }

    @FXML
    private void eliminarReserves(ActionEvent event) {
        misReservas(false, true, false);
    }

    @FXML
    private void veurePistes(ActionEvent event) {
        reservarPista();
    }
}
