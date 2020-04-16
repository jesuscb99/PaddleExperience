/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaRegistre;


import DBAcess.ClubDBAccess;
import com.sun.glass.ui.Application;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;



import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Member;
import paddleexperience.FXMLPaddleController;
import vistaPistes.FXMLVistaPistesController;

/**
 *
 * @author almagar3
 */
public class FXMLRegistreController implements Initializable {

    @FXML
    private Label errorUsuari;
    @FXML
    private Label errorPassword;
    @FXML
    private TextField nom;
    @FXML
    private TextField cognoms;
    @FXML
    private TextField usuari;
    @FXML
    private TextField contra;
    @FXML
    private TextField numCard;
    @FXML
    private TextField telf;
    @FXML
    private TextField svc;
    @FXML
    private Button selecImage;
    @FXML
    private Button regButton;
    
   private boolean consume = false;
   private BooleanProperty totCorrecte;
   ClubDBAccess club;
   private FXMLPaddleController main;
   private Member nuevo;
   
   private FXMLVistaPistesController pistaController;
   private FileChooser fileChooser;
    private BooleanProperty nomCorrecte;
    private BooleanProperty usuariCorrecte;
    private BooleanProperty contraCorrecte;
    private BooleanProperty telfCorrecte;
    private BooleanProperty numCardCorrecte;
    private BooleanProperty svcCorrecte;
    private BooleanProperty imgCorrecte;
    private BooleanProperty carregat;

    
    private boolean modal;
    
    @FXML
    private Label errorTelf;
    @FXML
    private Label errNumCard;
    @FXML
    private Label errSvc;
    @FXML
    private Label rutaImatge;
 
    private Image imatge;
    @FXML
    private ProgressIndicator iconoCarga;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      nom.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        KeyCode tecla = event.getCode();
                        
                        if(!tecla.isLetterKey()) {
                            if(!tecla.equals(KeyCode.SPACE) && !tecla.equals(KeyCode.SHIFT)) {
                                 consume = true;
                            }
                        }
                      
                    }
                  
              }
              );
      
      
      ////////////
      contra.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        KeyCode tecla = event.getCode();
                        
                        if(!tecla.isLetterKey()) {
                            if(!tecla.equals(KeyCode.SPACE) && !tecla.equals(KeyCode.SHIFT)) {
                                 consume = true;
                            }
                        }
                      
                    }
                  
              }
              );
      /////////
      usuari.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        KeyCode tecla = event.getCode();
                        
                        if(tecla.equals(KeyCode.SPACE)) {
                            consume = true;
                        }
                      
                    }
                  
              }
              );
      ///////////////
      telf.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        KeyCode tecla = event.getCode();
                        
                        if(!tecla.isDigitKey() || telf.getText().length() >= 9) {
                            
                            consume = true;
                        }
                      
                    }
                  
              }
              );
      //////////
      svc.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        KeyCode tecla = event.getCode();
                        
                        if(!tecla.isDigitKey() || svc.getText().length() >= 3) {
                            
                            consume = true;
                        }
                      
                    }
                  
              }
              );
      
      numCard.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        KeyCode tecla = event.getCode();
                        
                        if(!tecla.isDigitKey() || numCard.getText().length() >= 16) {
                           
                            consume = true;
                        }
                      
                    }
                  
              }
              );
      ///////////////////
    }    

    
     public void init(FXMLPaddleController main,FXMLVistaPistesController pistaController, boolean modal) {
         this.pistaController = pistaController;
     init(main, modal);
     }
    public void init(FXMLPaddleController main, boolean modal) {
        
        fileChooser = new FileChooser();
       
        ////////StackOverFlow
        FileChooser.ExtensionFilter imageFilter
             = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        //////////////////////
        
        this.main = main;
        totCorrecte = new SimpleBooleanProperty(false);
        this.modal = modal;
        
        usuariCorrecte = new SimpleBooleanProperty(false);
        contraCorrecte = new SimpleBooleanProperty(false);
        nomCorrecte = new SimpleBooleanProperty(false);
        telfCorrecte = new SimpleBooleanProperty(false);
        numCardCorrecte = new SimpleBooleanProperty(false);
        svcCorrecte = new SimpleBooleanProperty(false);
        imgCorrecte = new SimpleBooleanProperty(false);
        
        
        //carregant = new SimpleBooleanProperty(false);
        
        
        
        
        
        usuari.textProperty().addListener((a, b, c) -> {
                comprovarUsuari();
        }
        );
        
        contra.textProperty().addListener((a, b, c) -> {
                comprovarContra();
        }
        );
       
       ChangeListener<String> listenerNom = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                comprovarNomComplet();
            }
        
        };
       nom.textProperty().addListener(listenerNom);
       cognoms.textProperty().addListener(listenerNom);
       
       telf.textProperty().addListener((a, b, c) -> {
                comprovarTelf();
        }
        );
       
        numCard.textProperty().addListener((a, b, c) -> {
                comprovarNumCard();
        }
        );
        
        svc.textProperty().addListener((a, b, c) -> {
                comprovarSvc();
        }
        );
        /**
         * COMPROVAR
         */
        totCorrecte.bind(Bindings.and(nomCorrecte, Bindings.and(usuariCorrecte, Bindings.and(telfCorrecte,
                Bindings.and(numCardCorrecte, Bindings.and(svcCorrecte, contraCorrecte))))));
        
        regButton.disableProperty().bind(Bindings.or(main.cargant, Bindings.not(totCorrecte)));
        
        club = DBAcess.ClubDBAccess.getSingletonClubDBAccess();
    }
    @FXML
    private void completarReg(ActionEvent event) throws InterruptedException {
        Member actual = null;
        
        if(imgCorrecte.getValue()) {
            actual = new Member(nom.getText(), cognoms.getText(), telf.getText(), 
                usuari.getText(), contra.getText(), numCard.getText(), svc.getText(), imatge);
        } else {
            
                 actual = new Member(nom.getText(), cognoms.getText(), telf.getText(), 
                usuari.getText(), contra.getText(), numCard.getText(), svc.getText(), null);
          
           
        }
        
        club.getMembers().add(actual);
        
        nuevo = actual;
        
      
  
        main.logged.setValue(true);
   
        if (modal) {
            Stage stage = (Stage) regButton.getScene().getWindow();
            pistaController.setMember(nuevo);
            main.updatePerfil(nuevo);
            stage.close();

        } else {

            main.entrar(nuevo);
        }
       
        
    }

  
    private void consumirEvent(KeyEvent event) {
        if(consume) {
            consume = false;
            event.consume();
            
        }
    }
    @FXML
    private void usuariCorrecte(KeyEvent event) {
      
         consumirEvent(event);
         
    }
    
    private void comprovarUsuari() {
        String user = usuari.getText();
  
        
        if(user.isEmpty()) {
            usuariCorrecte.setValue(false);
            errorUsuari.visibleProperty().setValue(false);
        }
        else if(club.existsLogin(user)) { 
             usuariCorrecte.setValue(false);
            errorUsuari.visibleProperty().setValue(true);
        } else {
             usuariCorrecte.setValue(true);
             errorUsuari.visibleProperty().setValue(false);
        }
        
    }
    
    private void comprovarContra() {
       
        String pass = contra.getText();
        if(pass.length() < 6) {
            if(pass.isEmpty()) {
                errorPassword.visibleProperty().setValue(false);
            } else {
                 errorPassword.visibleProperty().setValue(true);
            }
            
            contraCorrecte.setValue(false);
        } else {
            contraCorrecte.setValue(true);
            errorPassword.visibleProperty().setValue(false);
        }
    }
    
    private void comprovarNomComplet() {
        if(!nom.getText().isEmpty() &&
           !cognoms.getText().isEmpty()) {
                nomCorrecte.setValue(true);
        } else {
            nomCorrecte.setValue(false);
        }
    }
    
    private void comprovarTelf() {
        String telef = telf.getText();
        if(telef.length() < 9) {
            if(telef.isEmpty()) {
                
                errorTelf.visibleProperty().setValue(false);
            } else {
                errorTelf.visibleProperty().setValue(true);
            }
            telfCorrecte.setValue(false);
            
        } else {
            errorTelf.visibleProperty().setValue(false);
            telfCorrecte.setValue(true);
        }
        
    }
    private void comprovarNumCard() {
        if(numCard.getText().length() < 16) {
            if(numCard.getText().isEmpty()) {
                errNumCard.visibleProperty().setValue(false);
            
            } else {
                errNumCard.visibleProperty().setValue(true);
            }
            numCardCorrecte.setValue(false);
        } else {
            errNumCard.visibleProperty().setValue(false);
            numCardCorrecte.setValue(true);
        }
        
    }
    private void comprovarSvc() {
        if(svc.getText().length() < 3) {
            if(svc.getText().isEmpty()) {
                errSvc.visibleProperty().setValue(false);
            } else {
                errSvc.visibleProperty().setValue(true);
            }
            svcCorrecte.setValue(false);
        } else {
            errSvc.visibleProperty().setValue(false);
            svcCorrecte.setValue(true);
        }
    }
    @FXML
    private void nomCorrecte(KeyEvent event) {
    
        consumirEvent(event);
       
    }

    @FXML
    private void numCardCorrecte(KeyEvent event) {
       
        consumirEvent(event);
    }

    @FXML
    private void telfCorrecte(KeyEvent event) {
      
        consumirEvent(event);
    }

    @FXML
    private void svcCorrecte(KeyEvent event) {
        consumirEvent(event);
    }

    @FXML
    private void selecImatge(ActionEvent event) {
        
        Button select = (Button) event.getSource();
        Window ownerWindow = (Window) select.getScene().getWindow();
        
        
       
        File file = fileChooser.showOpenDialog(ownerWindow);
        if(file != null) {
            String url = file.toPath().toString();
                    
            //Codi per a importar la imatge es de una pagina web (Inlcuit file chooser)
            try {
                imatge = new Image(new FileInputStream(url));
                
                
                
                imgCorrecte.setValue(true);
                rutaImatge.setText(url);
            } catch(FileNotFoundException e) {
                System.err.println(e.getCause());
                imgCorrecte.setValue(false);
                rutaImatge.setText("Error obrint la imatge");
            }
        }
      
        
    }

    
}
