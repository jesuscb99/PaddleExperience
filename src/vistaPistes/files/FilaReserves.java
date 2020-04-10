/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaPistes.files;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Booking;

/**
 *
 * @author 2jesu
 */
public class FilaReserves{
       private final ObjectProperty<Booking> bookPista1 = new SimpleObjectProperty<Booking>(); 
       private final ObjectProperty<Booking> bookPista2 = new SimpleObjectProperty<Booking>(); 
       private final ObjectProperty<Booking> bookPista3 = new SimpleObjectProperty<Booking>(); 
       private final ObjectProperty<Booking> bookPista4 = new SimpleObjectProperty<Booking>(); 
       
       public FilaReserves(Booking b1, Booking b2, Booking b3, Booking b4) {
           bookPista1.setValue(b1);
           bookPista2.setValue(b2);
           bookPista3.setValue(b3);
           bookPista4.setValue(b4);
       }
       
       public ObjectProperty<Booking> bookingProperty(int i) {
           switch(i) {
               case 1:
                   return bookPista1;
                  
               case 2:
                   return bookPista2;
                
               case 3:
                   return bookPista3;
           
               case 4:
                   return bookPista4;
                
               default:
                   return null;
           }
       }
       
       public void setBooking(int pista, Booking b) {
           switch(pista) {
               case 1:
                  bookPista1.setValue(b);
                  break;
               case 2:
                  bookPista2.setValue(b);
                  break;
                
               case 3:
                 bookPista3.setValue(b);
                  break;
           
               case 4:
                   bookPista4.setValue(b);
                   break;
                
               default:
                   
           }
       }
        
    }
