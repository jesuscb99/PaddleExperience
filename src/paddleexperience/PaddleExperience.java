/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import DBAcess.ClubDBAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vistaPistes.FXMLVistaPistesController;

/**
 *
 * @author 2jesu
 */
public class PaddleExperience extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLPaddle.fxml"));
       
        ClubDBAccess club = DBAcess.ClubDBAccess.getSingletonClubDBAccess();
        
        stage.setOnCloseRequest((WindowEvent event) ->{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            
                    alert.setTitle(club.getClubName());
                    alert.setHeaderText("Saving data in DB");
                    alert.setContentText("La aplicació està guardant la informació en la base de dades. Pot tardar uns minuts.");
                    alert.show();
                    club.saveDB();
                  
                   });
        
         //hola
            
            
            
        Scene scene = new Scene(root);
        ClubDBAccess clubDBAcess = DBAcess.ClubDBAccess.getSingletonClubDBAccess();
        stage.setScene(scene);
        
        
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
