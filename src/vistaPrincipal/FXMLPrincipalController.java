/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaPrincipal;

import DBAcess.ClubDBAccess;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Booking;
import model.Member;
import paddleexperience.FXMLPaddleController;

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
        
        
        
        
    }
    
    private void  updateProxReserva() {
        ArrayList<Booking> bookings = club.getUserBookings(member.getLogin());
        if(bookings.size() == 0) {
           proxReserv.setText("No tens reserves");
           return;
        }
        
        Booking proxima = bookings.get(0);
        LocalDateTime ara = LocalDateTime.now();
        
        for(Booking b : bookings) {
            LocalDateTime data = b.getBookingDate();
            if(data.compareTo(proxima.getBookingDate()) < 0 && data.compareTo(ara) > 0) {
                proxima = b;
            }
        }
        
        proxReserv.setText(proxima.getBookingDate().toString());
    }

    @FXML
    private void vorePistes(ActionEvent event) {
        main.reservarPista();
    }
}
