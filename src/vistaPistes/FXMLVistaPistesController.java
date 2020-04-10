/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaPistes;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import DBAcess.ClubDBAccess;
import model.*;
import vistaPistes.files.FilaReserves;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
/**
 * FXML Controller class
 *
 * @author 2jesu
 */
public class FXMLVistaPistesController implements Initializable {

    private ClubDBAccess club;
   
    
    @FXML
    private TableView<FilaReserves> tableView;
     @FXML
    private TableColumn<FilaReserves, String> col0;
    @FXML
    private TableColumn<FilaReserves, Booking> col1;
    @FXML
    private TableColumn<FilaReserves, Booking> col2;
    @FXML
    private TableColumn<FilaReserves, Booking> col3;
    @FXML
    private TableColumn<FilaReserves, Booking> col4;
    @FXML
    private DatePicker selecDia;
    
    FilaReserves[] files;
    String[] hores;
    /**
     * Initializes the controller class.
     */
    private ObservableList<FilaReserves> dades;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
   
    public void init() {
        // TODO
        
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.heightProperty().addListener((object, oldValue, newValue) -> {
         tableView.setFixedCellSize((tableView.getHeight() - 60) / 8.0);
         
        });
       tableView.widthProperty().addListener((object, oldValue, newValue) -> {
           
            System.out.println(newValue);
            col0.setMaxWidth(tableView.getWidth() / 5.0);
            col0.setMinWidth(tableView.getWidth() / 5.0);
            
            col1.setMaxWidth(tableView.getWidth() / 5.0);
            col1.setMinWidth(tableView.getWidth() / 5.0);
            
            col2.setMinWidth(tableView.getWidth() / 5.0);
            col2.setMaxWidth(tableView.getWidth() / 5.0);
            
            col3.setMinWidth(tableView.getWidth() / 5.0);
            col3.setMaxWidth(tableView.getWidth() / 5.0);
            
            col4.setMinWidth(tableView.getWidth() / 5.0);
            col4.setMaxWidth(tableView.getWidth() / 5.0);
        });
        
        selecDia.valueProperty().set(LocalDate.now());
        club = ClubDBAccess.getSingletonClubDBAccess();
        
        List<FilaReserves> dadesmy = new ArrayList<>(); 
        dades = FXCollections.observableArrayList(dadesmy);
        tableView.setItems(dades);
        
        
        
        hores = new String[]{"09:00", "10:30", "12:00", "13:30", "15:00", "16:30", "18:00", "19:30"};
       
        files = new FilaReserves[8];
        for(int i = 0; i < 8; i++) {
            files[i] = new FilaReserves(hores[i]);
        }
        
        configTaula();
        cargaTaula();
    }   
    
    
    private void configTaula() {
        
        col0.setCellValueFactory(
                 new PropertyValueFactory<FilaReserves, String>("hora")
        );
        col1.setCellValueFactory(cellData1 -> cellData1.getValue().bookingProperty(1));
        
        col1.setCellFactory(v -> {
                return new TableCell<FilaReserves, Booking>(){

                    @Override
                    public void updateItem(Booking item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item == null || empty) {
                            setText("Lliure");
                        } else {
                           setText(item.getMember().getName());
                        }
                    }
                };
                
        });
        
        col2.setCellValueFactory(cellData2 -> cellData2.getValue().bookingProperty(2));
        col2.setCellFactory(v -> {
                return new TableCell<FilaReserves, Booking>(){

                    @Override
                    public void updateItem(Booking item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item == null || empty) {
                            setText("Lliure");
                        } else {
                           setText(item.getMember().getName());
                        }
                    }
                };
                
        });
        
        
        col3.setCellValueFactory(cellData3 -> cellData3.getValue().bookingProperty(3));
        col3.setCellFactory(v -> {
                return new TableCell<FilaReserves, Booking>(){

                    @Override
                    public void updateItem(Booking item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item == null || empty) {
                            setText("Lliure");
                        } else {
                           setText(item.getMember().getName());
                        }
                    }
                };
                
        });
        
        col4.setCellValueFactory(cellData4 -> cellData4.getValue().bookingProperty(4));
        col4.setCellFactory(v -> {
                return new TableCell<FilaReserves, Booking>(){

                    @Override
                    public void updateItem(Booking item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item == null || empty) {
                            setText("Lliure");
                        } else {
                           setText(item.getMember().getName());
                        }
                    }
                };
                
        });
        
    }
    private void cargaTaula() {
       
          
       actualitzarFiles(); 
       dades.clear();
       for(int i = 0; i < 8; i++) {
           dades.add(files[i]);
       }    
       
    }
    
    private void actualitzarFiles() {
       LocalTimeAdapter adapter = new LocalTimeAdapter();
        LocalDate dia = selecDia.getValue(); 
        
        for(int i = 1; i <= 4; i++) {
          String pistaAct = "Pista " + i;
          ArrayList<Booking> bookings = club.getCourtBookings(pistaAct, dia);
          
          int length = bookings.size();
          for(int j = 0; j < length; j++) { 
              
              try {
                 Booking act = bookings.get(j);
                 LocalTime horaBook = act.getFromTime();      
                 String horaAct = adapter.marshal(horaBook);
                 setBookingEnFila(i, horaAct, act);
                 
              } catch(Exception e) {System.err.println(e.getMessage());}
              
          }
          
        }
    }
    
    private void setBookingEnFila(int pista, String hora, Booking b) {
        switch(hora) {
                case "09:00":
                    files[0].setBooking(pista, b );
                    break;
                case "10:30":
                    files[1].setBooking(pista, b);
                     break;
                case "12:00":
                    files[2].setBooking(pista, b);   
                    break;
                case "13:30":
                    files[3].setBooking(pista, b);   
                    break;
                case "15:00":
                    files[4].setBooking(pista, b);   
                    break;
                case "16:30":
                    files[5].setBooking(pista, b);   
                    break;
                case "18:00":
                    files[6].setBooking(pista, b);   
                    break;
                case "19:30":
                    files[7].setBooking(pista, b);   
                    break;
                default:
                 }
        
    }
    
    
    @FXML
    private void canviarDia(ActionEvent event) {
        cargaTaula();
       
    }
    
}
