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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import vistaLogin.FXMLLoginController;
import vistaPistes.FXMLVistaPistesController;
import vistaRegistre.FXMLRegistreController;

/**
 *
 * @author 2jesu
 */
public class FXMLPaddleController implements Initializable {
    

    @FXML
    private BorderPane borderPane;
    public boolean logged = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
      
         FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistaPistes/FXMLVistaPistes.fxml"));
         try {
             
             //AnchorPane vistaPistes = (AnchorPane) FXMLLoader.load(getClass().getResource("/vistaPistes/FXMLVistaPistes.fxml"));
             //hola
            VBox root = (VBox) miCargador.load();
            FXMLVistaPistesController controlador = miCargador.<FXMLVistaPistesController>getController();
            controlador.init();
             System.out.println("yeeee");
             borderPane.setCenter(root);
             
         } catch(IOException e) {
             System.err.println(e.toString());
         }
    }    

    @FXML
    private void login(ActionEvent event) {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistaLogin/FXMLLogin.fxml"));
         try {
             
        
             
            VBox root = (VBox) miCargador.load();
            FXMLLoginController controlador = miCargador.<FXMLLoginController>getController();
             controlador.init(this);
             System.out.println("yeeee");
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
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistaPistes/FXMLVistaPistes.fxml"));
         try {
             
            
            VBox root = (VBox) miCargador.load();
            FXMLVistaPistesController controlador = miCargador.<FXMLVistaPistesController>getController();
            controlador.init();
             System.out.println("yeeee");
             borderPane.setCenter(root);
             
         } catch(IOException e) {
             System.err.println(e.toString());
         }
    }
    
    public void entrar() {
        System.out.println("Estic diiiins");
    }
    
    public void register() {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistaRegistre/FXMLRegistre.fxml"));
         try {
             
            
            VBox root = (VBox) miCargador.load();
            
          
           borderPane.setAlignment(root, Pos.TOP_CENTER);
           //borderPane.setMinHeight(root.getHeight() + 1000);
           
            FXMLRegistreController controlador = miCargador.<FXMLRegistreController>getController();
            controlador.init();
            
             borderPane.setCenter(root);
             
         } catch(IOException e) {
             System.err.println(e.toString());
         }
    }
    
}
