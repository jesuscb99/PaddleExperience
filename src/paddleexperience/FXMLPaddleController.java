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
        // TODO
         FXMLLoader cargadorPista = new FXMLLoader(getClass().getResource("/vistaPistes/FXMLVistaPistes.fxml"));
         
         try {
             
             VBox vistaPistes = (VBox) cargadorPista.load();
             FXMLVistaPistesController controlador = cargadorPista.<FXMLVistaPistesController>getController();
             
             borderPane.setCenter(vistaPistes);
             
         } catch(IOException e) {
             
         }
    }    
    
}
