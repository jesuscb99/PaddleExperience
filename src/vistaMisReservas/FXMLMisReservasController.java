/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaMisReservas;

import DBAcess.ClubDBAccess;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import funcions.Adapter;
import funcions.OrdenarBookings;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import model.Booking;
import model.Member;
import paddleexperience.FXMLPaddleController;

/**
 * FXML Controller class
 *
 * @author 2jesu
 */
public class FXMLMisReservasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ClubDBAccess club;
    @FXML
    private ToggleGroup ordenar;
    @FXML
    private Button prev;
    @FXML
    private Button next;
    @FXML
    private ListView<Booking> listView;
    @FXML
    private Button elimBoto;
    private IntegerProperty pag;
    private int maxPag;
    private FXMLPaddleController main;
    Member member;
    
    List<Booking> totesReserves;
    List<List<Booking>> enPantalla;
    ObservableList<Booking> dades;
    @FXML
    private RadioButton anticsPrimer;
    @FXML
    private RadioButton postPrimer;
    

    private BooleanProperty noPagades;
    private BooleanProperty pendentsDeJoc;
    private BooleanProperty anticsFirst;
    private BooleanProperty selecPagat;
    
    private BooleanProperty finalPag;
    
    @FXML
    private CheckBox noPagBut;
    @FXML
    private CheckBox pendJocBut;
    @FXML
    private Label paginaText;
    @FXML
    private VBox menu;
    @Override
    
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
     
    }    
    
    public void setConfig(boolean postPrimer_, boolean noPagades, boolean actives) {
        if(postPrimer_) {
            postPrimer.selectedProperty().setValue(true);
        }
        
        if(noPagades) {
            noPagBut.selectedProperty().setValue(true);
        
        }   
        if(actives) {
            pendJocBut.selectedProperty().setValue(true);
        }
        
    }
    public void init(FXMLPaddleController main, Member member) {
        club = DBAcess.ClubDBAccess.getSingletonClubDBAccess();
        this.main = main;
        this.member = member;
        totesReserves = club.getUserBookings(member.getLogin());
        //List<Booking> misdades = new ArrayList<Booking>();
        dades = FXCollections.observableArrayList(totesReserves);
        listView.setItems(dades);
        
        
        
        
        
        
        noPagades = new SimpleBooleanProperty(false);
        pendentsDeJoc = new SimpleBooleanProperty(false);
        anticsFirst = new SimpleBooleanProperty(true);
        selecPagat = new SimpleBooleanProperty(true);
        finalPag = new SimpleBooleanProperty();
        pag = new SimpleIntegerProperty(0);
        
        anticsFirst.bind(anticsPrimer.selectedProperty());
        noPagades.bind(noPagBut.selectedProperty());
        pendentsDeJoc.bind(pendJocBut.selectedProperty());
        
        listView.disableProperty().bind(main.cargant);
        menu.disableProperty().bind(main.cargant);
        maxPag = (totesReserves.size() / 10) + 1;
        
        
        
        next.disableProperty().bind(finalPag);
        
        prev.disableProperty().bind(Bindings.equal(pag, 1));
        
       elimBoto.disableProperty().bind(Bindings.or(selecPagat,Bindings.equal(listView.getSelectionModel().selectedIndexProperty(), -1)));
       
      listView.getSelectionModel().selectedItemProperty().addListener((object, oldv, newv) -> {
        
            Booking b = newv;
            if(b == null) { 
                selecPagat.setValue(false);
                    
            } else {
                  if(b.getPaid()) {
                      selecPagat.setValue(true);
                  } else {
                      selecPagat.setValue(false);
                  }
            }
        });
        
        
        
        
        class BookingListCell extends ListCell<Booking>
        {
            @Override
            protected void updateItem(Booking item, boolean empty) {
                super.updateItem(item, empty);
                
                
                if(item == null || empty) {
                    setText(null);
                } else {
                    aplicarCss(this);
                    Adapter adapter = new Adapter(item.getMadeForDay(), item.getFromTime());
                    String pagat = "";
                    LocalTime fin = item.getFromTime().plusMinutes(90);
                   
                    if(item.getPaid()) {
                        
                        pagat = "Sí";
                    } else {
                        pagat = "No";
                    }
                    
                    String jugat = "";
                    
                    if(LocalDateTime.of(item.getMadeForDay(),item.getFromTime()).isBefore(LocalDateTime.now())) {
                        jugat += "Sí";
                    } else {
                        jugat += "No";
                    }
                    
                    String data = adapter.data;
                  // String l1 =  "       "+"DIA" + "       "+  "HORA" + "       "+"PISTA" + "       "+"PAGAT"+ "       "+ "JUGAT\n";
                  // String l2 =  "       "+adapter.data  + "       "+ item.getFromTime().toString() + "       "+ item.getCourt().getName() + "       "+ pagat+ "        \n";
                   String l1 = String.format("%-32s%44s%32s%32s%32s\n", "DIA", "HORA", "PISTA", "PAGAT", "JUGAT  ");
                   String l2 = String.format("%-32s%32s%32s%32s%32s\n",  data, item.getFromTime().toString() + " - " + fin, item.getCourt().getName(), pagat, jugat);
                 
                 // String l1 = String.format("{0,32}{1,32}{2,32}{3,32}{4,32}\n","DIA", "HORA", "PISTA", "PAGAT", "JUGAT");
                 // String l2 = String.format("{0,32} + {1,32} + {2,32} + {3,32} + {4,32}\n",adapter.data, item.getFromTime().toString() + " - " + fin, item.getCourt().getName(), pagat, jugat);
                    //setText("DIA: " + adapter.data + "\nHORA: " + item.getFromTime().toString() + " - " + fin + "\nPISTA: " + item.getCourt().getName() + "\nPAGAT: " +  pagat);
                    setText(l1 + l2);
                }
            }
        }
         listView.setFixedCellSize(120);
         listView.setCellFactory(c -> new BookingListCell());
         actualitzarListView();
    }
    
    private void aplicarCss(ListCell<Booking> cell) {
        int index = cell.getIndex();
     
        if(index >= 0) {
           
            Booking b = cell.getItem();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime date = LocalDateTime.of(b.getMadeForDay(),b.getFromTime());
            if(date.isBefore(now)) {
                cell.getStyleClass().set(0, "celda-jugada");
            } else if(b.getPaid()) {
                if(index % 2 == 0) {
                    cell.getStyleClass().set(0, "celda-pagada-par");
                } else {
                     cell.getStyleClass().set(0, "celda-pagada-impar");
                }
               
            } else {
                if(index % 2 == 0) {
                    cell.getStyleClass().set(0, "celda-nopagada-par");
                } else {
                     cell.getStyleClass().set(0, "celda-nopagada-impar");
                }
            }
           cell.getStyleClass().set(1, "celda-general"); 
        }
    }
    private void updateTexPagina() {
        if(finalPag.getValue()) {
                int ultPos = enPantalla.get(pag.getValue() - 1).size() + (pag.getValue()-1) * 10;
                paginaText.setText("Mostrant pàgina " + pag.getValue() + " de " + maxPag + " (" + (((pag.getValue() - 1) * 10) + 1) + " - " + ultPos + ")");
       } else {
                int ultPos = (pag.getValue() * 10);
                 paginaText.setText("Mostrant pàgina " + pag.getValue() + " de " + maxPag + " (" + (((pag.getValue() - 1) * 10) + 1) + " - " + ultPos + ")");
            }
    }
    @FXML
    private void ordenar(ActionEvent event) {
        actualitzarListView();
    }


    @FXML
    private void actives(ActionEvent event) {
        actualitzarListView();
    }
    
    private void actualitzarListView() {
        
        List<Booking> actual = null;
        
        if(ordenar.getSelectedToggle() == anticsPrimer) {
            actual = totesReserves;
           
            
        } else {
            actual = OrdenarBookings.getPosteriorsPrimer((ArrayList<Booking>) totesReserves);
            
            
        }
        
        if(noPagades.getValue()) {
            actual = OrdenarBookings.getNoPagades(actual);
            
           
        }
        
        if(pendentsDeJoc.getValue()) {
            actual = OrdenarBookings.getActives(actual);
            
            
        }
        
       
        enPantalla = new ArrayList<List<Booking>>();
        
        
        
        maxPag = (actual.size() / 10) + 1;
        
        pag.setValue(1);
        
         
        for(int j = 0; j < maxPag; j++) {
            List<Booking> misdades = new ArrayList<>();
            for(int i = j * 10; i < (j+1) * 10 && i < actual.size(); i++) {
                misdades.add(actual.get(i));
            }
            enPantalla.add(misdades);
        }
       
        finalPag.setValue(maxPag == pag.getValue());
        updateTexPagina();
      
        dades = FXCollections.observableArrayList(enPantalla.get(0));
        listView.setItems(dades);
        System.out.println(listView.getItems().size());
        
        
       
            
       
    }
     
    
   
    @FXML
    private void tornar(ActionEvent event) {
        main.entrar(member);
    }

    @FXML
    private void noPagades(ActionEvent event) {
        actualitzarListView();
    }

    @FXML
    private void prevPag(ActionEvent event) {
        pag.setValue(pag.getValue() - 1);
        finalPag.setValue(maxPag == pag.getValue());
                
        updateTexPagina();
        dades = FXCollections.observableArrayList(enPantalla.get(pag.getValue() - 1));
        listView.setItems(dades);
    }

    @FXML
    private void nextPag(ActionEvent event) {
        pag.setValue(pag.getValue() + 1);
      finalPag.setValue(maxPag == pag.getValue());
      
      updateTexPagina();
        dades = FXCollections.observableArrayList(enPantalla.get(pag.getValue() - 1));
        listView.setItems(dades);
    }

    @FXML
    private void eliminar(ActionEvent event) {
        
        Booking item = listView.getSelectionModel().getSelectedItem();
        
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         
         ButtonType buttonSi = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
         ButtonType buttonCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
         alert.getButtonTypes().setAll(buttonSi, buttonCancel);
       alert.setTitle("Confirmació de eliminació de reserva");
       
      
       
        Adapter adapter = new Adapter(item.getMadeForDay(), item.getFromTime());
          
        LocalTime fin = item.getFromTime().plusMinutes(90);
                    
        alert.setHeaderText("DIA: " + adapter.data + "\nHORA: " + item.getFromTime().toString() + " - " + fin + "\nPISTA: " + item.getCourt().getName() + "\nPAGAT: No");
        alert.setContentText("Estàs segur que vols eliminar la reserva?");
       
        
       alert.showAndWait();
       
       ButtonType res = alert.getResult();
       
       if(res == buttonSi) {
          club.getBookings().remove(item); 
           
         
            //main.cargant.setValue(false);
            totesReserves = club.getUserBookings(member.getLogin());
            actualitzarListView();
    

            
       }
       
    }
    
    
}
