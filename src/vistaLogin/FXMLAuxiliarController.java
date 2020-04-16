/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaLogin;

import DBAcess.ClubDBAccess;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Member;
import paddleexperience.FXMLPaddleController;
import vistaPistes.FXMLVistaPistesController;
import vistaRegistre.FXMLRegistreController;

/**
 * FXML Controller class
 *
 * @author 2jesu
 */
public class FXMLAuxiliarController implements Initializable {

    @FXML
    private Label errLabel;
    
    private FXMLPaddleController main;
    FXMLVistaPistesController pistaController;
    private Stage stage;
    
    @FXML
    private TextField usuari;
    @FXML
    private PasswordField contra;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
 
    }    

    public void init(FXMLPaddleController main, FXMLVistaPistesController pistaController, Stage stage) {
        this.main = main;
        this.pistaController = pistaController;
        this.stage = stage;
    }
    @FXML
    private void login(ActionEvent event) {
        errLabel.setText("");
        
        String user = usuari.getText();
        String password = contra.getText();
        
        ClubDBAccess club = ClubDBAccess.getSingletonClubDBAccess();
        Member membreAct = club.getMemberByCredentials(user, password);
        
        if(membreAct == null) {
            List<String> usuaris = new ArrayList();
            ArrayList<Member> membres = club.getMembers();
            
            for(Member m : membres) {
                
                usuaris.add(m.getLogin());
            }
         
           if(usuaris.remove(user)) {
               errLabel.setText("La contrasenya introduida es incorrecta.");
           } else {
               errLabel.setText("El usuari introduit no existeix.");
           }
        
        } else {
            pistaController.setMember(membreAct);
            main.updatePerfil(membreAct);
            main.logged.setValue(Boolean.TRUE);
            
             Stage stage = (Stage) usuari.getScene().getWindow();
             stage.close();
           
        }
    }

    @FXML
    private void register(ActionEvent event) {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistaRegistre/FXMLRegistre.fxml"));
        
        try {
            BorderPane root = (BorderPane) miCargador.load();
            FXMLRegistreController controlador = miCargador.<FXMLRegistreController>getController();

            Scene scene = new Scene(root,800, 700); 
            controlador.init(main,pistaController, true);
            stage.setScene(scene);
            stage.setTitle("Registrar-se");
           
            
               
        } catch(IOException e) {System.out.print("eeeeeeeeeeeeeeee");}
    }
    
}
