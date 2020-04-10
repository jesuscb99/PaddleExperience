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
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
/**
 * FXML Controller class
 *
 * @author 2jesu
 */
public class FXMLVistaPistesController implements Initializable {

    private ClubDBAccess club;
   
    
    @FXML
    private TableView<?> tableView;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tableView.widthProperty().addListener((object, oldValue, newValue) -> {
            
            col1.setMaxWidth(tableView.getWidth() / 4.0);
            col1.setMinWidth(tableView.getWidth() / 4.0);
            
            col2.setMinWidth(tableView.getWidth() / 4.0);
            col2.setMaxWidth(tableView.getWidth() / 4.0);
            
            col3.setMinWidth(tableView.getWidth() / 4.0);
            col3.setMaxWidth(tableView.getWidth() / 4.0);
            
            col4.setMinWidth(tableView.getWidth() / 4.0);
            col4.setMaxWidth(tableView.getWidth() / 4.0);
        });
        
        selecDia.valueProperty().set(LocalDate.now());
        club = ClubDBAccess.getSingletonClubDBAccess();
        
       
        configTaula();
        cargaTaula();
        
        
        
    }   
    
    
    private void configTaula() {
        
        col1.setCellValueFactory(cellData1 -> cellData1.getValue().bookingProperty(1));
        
        col1.setCellFactory(v -> {
                return new TableCell<FilaReserves, Booking>(){

                    @Override
                    public void updateItem(Booking item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item == null || empty) {
                            setText("");
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
                            setText("");
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
                            setText("");
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
                            setText("");
                        } else {
                           setText(item.getMember().getName());
                        }
                    }
                };
                
        });
        
    }
    private void cargaTaula() {
        LocalDate dia = selecDia.getValue();
        
        
       //int length = bookings.size();
        
        FilaReserves[] files = new FilaReserves[8];
        
        
        for(int i = 1; i <= 4; i++) {
          ArrayList<Booking> bookings = club.getCourtBookings("Pista " + i, dia);
          
        }
        
                
    }
    
}
