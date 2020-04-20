/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Booking;

/**
 *
 * @author 2jesu
 */
public class OrdenarBookings {
    public static List<Booking> getUltimesReserves(List<Booking> bookings) {
        
         List<Booking> copia = new ArrayList<>();
         List<Booking> aux = new ArrayList<>();
         LocalDateTime now = LocalDateTime.now(); 
         
        for(Booking b : bookings) {
            copia.add(b);
        }
        
        while(copia.size() > 0) {
            Booking max = copia.get(0);
            int pos = 0;
            for(int i = 0; i < copia.size(); i++) {
                if(copia.get(i).getBookingDate().isAfter(max.getBookingDate())) {
                    max = copia.get(i);
                    pos = i;
                }
            }
            
            aux.add(max);
            copia.remove(pos);
            
        }
        
        return aux;
    }
    public static List<Booking> getActives(List<Booking> bookings) {
       
         List<Booking> aux = new ArrayList<>();
         LocalDateTime now = LocalDateTime.now(); 
         
         
         for(Booking b : bookings) {
             LocalDateTime date = LocalDateTime.of(b.getMadeForDay(), b.getFromTime());
             if(date.compareTo(now) > 0) {
                 aux.add(b);
             }
            
         }
         
         return aux;
    }
    
    
    
    
    public static List<Booking> getPosteriorsPrimer(List<Booking> bookings) {
        
        
     
        List<Booking> aux = new ArrayList<>();
        for(int i = bookings.size() - 1; i >= 0; i--) {
            aux.add(bookings.get(i));
        }
        
        return aux;
    }
    
    /**
     * 
     * Precondició: bookings esta ordenat de mes velles a mes noves;
     * @return 
     */
    public static List<Booking> getNoPagades(List<Booking> bookings) {
        List<Booking> aux = new ArrayList<>();
        for(int i = 0; i < bookings.size(); i++) {
            Booking act = bookings.get(i);
            if(!act.getPaid()) {
                aux.add(bookings.get(i));
            }
        }
        
        return aux;
    }
}
