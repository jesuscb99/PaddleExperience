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
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import vistaPistes.FXMLVistaPistesController;

/**
 *
 * @author 2jesu
 */
public class FXMLPaddleController implements Initializable {
    

    @FXML
    private BorderPane borderPane;
 
    
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
    
}
