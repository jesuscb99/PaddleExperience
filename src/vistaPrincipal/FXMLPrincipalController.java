/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaPrincipal;

import DBAcess.ClubDBAccess;
import funcions.Adapter;
import funcions.OrdenarBookings;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Booking;
import model.LocalDateAdapter;
import model.Member;
import paddleexperience.FXMLPaddleController;
import vistaMisReservas.FXMLMisReservasController;

/**
 * FXML Controller class
 *
 * @author 2jesu
 */

public class FXMLPrincipalController implements Initializable {

    FXMLPaddleController main;
    Member member;
    @FXML
    private Label proxReserv;
    @FXML
    private Label reservFree;
    @FXML
    private Label reservNoPag;
    /**
     * Initializes the controller class.
     */
    ClubDBAccess club;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void init(Member m, FXMLPaddleController main) {
        club = DBAcess.ClubDBAccess.getSingletonClubDBAccess();
        member = m;
        this.main = main;
        
        /**
         * PROXIMES RESERVES
         */   
        updateProxReserva();
        updateReservNoPag();
        updateFree();
    }
    
    private void  updateProxReserva() {
        ArrayList<Booking> bookings = club.getUserBookings(member.getLogin());
       
        List<Booking> actives = OrdenarBookings.getActives(bookings);
        if(actives == null || actives.size() == 0) {
            proxReserv.setText("No tens reserves");
            return;
        } 
            
        Booking proxima = actives.get(0);
        Adapter adapter = new Adapter(proxima.getMadeForDay(), proxima.getFromTime());
        
         boolean pagat = proxima.getPaid();
        String pagatS = "";
        if(pagat) {
            pagatS = "Si";
        } else {
            pagatS = "No";
        }
        
        proxReserv.setText("Dia: " + adapter.data + "\nHora: " + adapter.hora + "\n"+ proxima.getCourt().getName() + "\nPagat: " + pagatS);
    }
    
     private void  updateReservNoPag() {
         
       
        reservNoPag.setText("No tens reserves pendents de pagament");
        
         ArrayList<Booking> bookings = club.getUserBookings(member.getLogin());
       
        List<Booking> actives = OrdenarBookings.getActives(bookings);
        
        if(actives == null || actives.size() == 0) {
            
            return;
        } 
            
        Booking proxima = null;
        
        
        for(Booking b : actives) {
            if(!b.getPaid()) {
                
                proxima = b;
                break;
            }
        }
        
        if(proxima == null) {
            return;
        }
        Adapter adapter = new Adapter(proxima.getMadeForDay(), proxima.getFromTime());
        reservNoPag.setText("Dia: " + adapter.data + "\nHora: " + adapter.hora + "\n"+ proxima.getCourt().getName() + "\nPagat: No");
    }
     
    private void updateFree() {
        LocalTime ara = LocalTime.now();
        if(ara.compareTo(LocalTime.of(21, 0)) >= 0) {
            reservFree.setText("No queden pistes disponibles per hui");
            return;
        }
        
        LocalTime[] horaris = getHoraris();
        
        LocalTime proxSessio = horaris[0];
        
        int i = -1; 
        
        do {
            i++;
            proxSessio = horaris[i];
            
        }while(horaris[i].isBefore(ara));
        
        
        
        i = 1;
        String pistesLliures = "";
        
        while(i <= 4) {
            if(pistaLliure("Pista " + i, proxSessio)) {
                pistesLliures += "  Pista " + i + "\n";
                
            }
            i++;
        }
        
        if(pistesLliures.isEmpty()) {
            reservFree.setText("No queden pistes disponibles per hui");
        } else {
            LocalTime fin = proxSessio.plusMinutes(90);
            System.out.println(fin.toString());
            reservFree.setText("Hora: " + proxSessio.toString() + "-" + fin + "\n" + pistesLliures);
        }
       
        
    }
    
    private boolean pistaLliure(String name, LocalTime sessio) {
        ArrayList<Booking> courtBookings = club.getCourtBookings(name, LocalDate.now());
        boolean res = true;
        
        for(Booking b : courtBookings) {
            LocalTime time = b.getFromTime();
            
            if(time.compareTo(sessio) == 0) {
               
               return false;
            }
        }
        
        return res;
    }
    private LocalTime[] getHoraris() {
        LocalTime[] horaris = new LocalTime[8];
        
        int hora = 9;
        int minut = 0;
        for(int i = 0; i < horaris.length; i++) {
  
            horaris[i] = LocalTime.of(hora, minut);
            hora++;
            minut += 30;
            if(minut == 60) {hora++; minut = 0;}
        }
        
        return horaris;
    }
    @FXML
    private void vorePistes(ActionEvent event) {
        main.reservarPista();
    }

    @FXML
    private void reserves(ActionEvent event) {
        main.misReservas(false, false, true);
    }

    @FXML
    private void elimReserves(ActionEvent event) {
        main.misReservas(false, true, false);
    }
}
