/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaPistes.files;

import java.time.LocalTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Booking;

/**
 *
 * @author 2jesu
 */
public class FilaReserves{
       private final StringProperty hora = new SimpleStringProperty();
       private final ObjectProperty<Booking> bookPista1 = new SimpleObjectProperty<Booking>(); 
       private final ObjectProperty<Booking> bookPista2 = new SimpleObjectProperty<Booking>(); 
       private final ObjectProperty<Booking> bookPista3 = new SimpleObjectProperty<Booking>(); 
       private final ObjectProperty<Booking> bookPista4 = new SimpleObjectProperty<Booking>(); 
       
       public FilaReserves(Booking b1, Booking b2, Booking b3, Booking b4, String horaInici) {
           bookPista1.setValue(b1);
           bookPista2.setValue(b2);
           bookPista3.setValue(b3);
           bookPista4.setValue(b4);
           hora.setValue(horaInici);
           
       }
       
       public FilaReserves() {
           
           bookPista1.setValue(null);
           bookPista2.setValue(null);
           bookPista3.setValue(null);
           bookPista4.setValue(null);
           hora.setValue("");
       }
       public FilaReserves(String horaInici) {
           
           bookPista1.setValue(null);
           bookPista2.setValue(null);
           bookPista3.setValue(null);
           bookPista4.setValue(null);
           hora.setValue(horaInici);
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
       
       public final StringProperty horaProperty() {
		return this.hora;
	}
    }
