/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funcions;

import java.time.LocalDate;
import java.time.LocalTime;
import model.DateTimeAdapter;
import model.LocalDateAdapter;
import model.LocalTimeAdapter;

/**
 *
 * @author 2jesu
 */
public class Adapter {
    public int any;
    public int mes;
    public int dia;
    public String hora;
    public String diaSemana;
    public String data;
    
    public Adapter(LocalDate date) {
        
        
        any = date.getYear();
        mes = date.getMonth().getValue();
      
        dia = date.getDayOfMonth();
      
        diaSemana = date.getDayOfWeek().toString();
        
        switch(diaSemana) {
            case "MONDAY" :
                diaSemana = "Dilluns";
                break;
            case "TUESDAY" :
                diaSemana = "Dimarts";
                break;
            case "WEDNESDAY" :
                diaSemana = "Dimecres";
                break;
            case "THURSDAY" :
                diaSemana = "Dijous";
                break;
            case "FRIDAY" :
                diaSemana = "Divendres";
                break;
            case "SATURDAY" :
                diaSemana = "Dissabte";
                break;
            case "SUNDAY" :
                diaSemana = "Diumenge";
                break;
            default:
            
        }
        String mesS = Integer.toString(mes);
        if(mesS.length() == 1) {mesS = "0" + mesS;}
        
        String diaS = Integer.toString(dia);
        if(diaS.length() == 1) {diaS = "0" + diaS;}
        
        data = diaSemana + ". " + diaS + "-" + mesS + "-" + any;
      
        
    }
    
    public Adapter(LocalDate date, LocalTime hora) {
        LocalTimeAdapter timeAdapter = new LocalTimeAdapter();
        try {
        this.hora = timeAdapter.marshal(hora);
        } catch(Exception e){}
        
       any = date.getYear();
      
        mes = date.getMonth().getValue();
        
        
        
        dia = date.getDayOfMonth();
       
        diaSemana = date.getDayOfWeek().toString();
        
        
        diaSemana = date.getDayOfWeek().toString();
        
        switch(diaSemana) {
            case "MONDAY" :
                diaSemana = "Dilluns";
                break;
            case "TUESDAY" :
                diaSemana = "Dimarts";
                break;
            case "WEDNESDAY" :
                diaSemana = "Dimecres";
                break;
            case "THURSDAY" :
                diaSemana = "Dijous";
                break;
            case "FRIDAY" :
                diaSemana = "Divendres";
                break;
            case "SATURDAY" :
                diaSemana = "Dissabte";
                break;
            case "SUNDAY" :
                diaSemana = "Diumenge";
                break;
            default:
            
        }
        
        String mesS = Integer.toString(mes);
        if(mesS.length() == 1) {mesS = "0" + mesS;}
        
        String diaS = Integer.toString(dia);
        if(diaS.length() == 1) {diaS = "0" + diaS;}
        
        data = diaSemana + ". " + diaS + "-" + mesS + "-" + any;
        
    }
    
    
}
