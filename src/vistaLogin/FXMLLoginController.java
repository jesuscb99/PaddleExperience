/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaLogin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import paddleexperience.FXMLPaddleController;
import DBAcess.ClubDBAccess;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Member;
/**
 * FXML Controller class
 *
 * @author 2jesu
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField usuari;
    @FXML
    private Label errorLabel;
    private FXMLPaddleController main;
    @FXML
    private TextField pass;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void init(FXMLPaddleController main) {
        this.main = main;
        
    }
    
    private boolean login() {
        errorLabel.setText("");
        String user = usuari.getText();
        String password = pass.getText();
        
        ClubDBAccess club = ClubDBAccess.getSingletonClubDBAccess();
        Member membreAct = club.getMemberByCredentials(user, password);
        
        if(membreAct == null) {
            List<String> usuaris = new ArrayList();
            ArrayList<Member> membres = club.getMembers();
            
            for(Member m : membres) {
                
                usuaris.add(m.getLogin());
            }
            System.out.println(usuaris.toString());
           if(usuaris.remove(user)) {
               errorLabel.setText("La contrasenya introduida es incorrecta.");
           } else {
               errorLabel.setText("El usuari introduit no existeix.");
           }
           return false;
        } else {
            
            main.entrar(membreAct);
            return true;
        }
    }
    @FXML
    private void login(ActionEvent event) {
        login();
    }

    
    @FXML
    private void register(ActionEvent event) {
        main.register();
    }

    @FXML
    private void enterPressed(KeyEvent event) {
        KeyCode tecla = event.getCode();
        
        if(tecla.equals(KeyCode.ENTER)) {
            login();
        }
    }
    
    
}
