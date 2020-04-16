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
import funcions.Adapter;
import java.io.IOException;

import model.*;
import vistaPistes.files.FilaReserves;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;

import javafx.beans.property.SimpleBooleanProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.scene.control.TableCell;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import paddleexperience.FXMLPaddleController;
import vistaLogin.FXMLAuxiliarController;
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
    private EventHandler<MouseEvent>[] events;
    private EventHandler<MouseEvent> eventExited;
    private BooleanProperty diaActual;
    FXMLPaddleController main;
    Member member;
    
    FilaReserves[] files;
    String[] hores;
    /**
     * Initializes the controller class.
     */
    private ObservableList<FilaReserves> dades;
    @FXML
    private Label preview;
    @FXML
    private Button tornarBoto;
    @FXML
    private Button dMenysBot;
    @FXML
    private Button dMesBot;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    
    
   
    public void init(Member m, FXMLPaddleController main) {
        // TODO
        events = (EventHandler<MouseEvent>[]) new EventHandler[4];
        
        eventExited = filterExited();
        
        tornarBoto.visibleProperty().bind(main.logged);
        tornarBoto.disableProperty().bind(main.cargant);
        tableView.disableProperty().bind(main.cargant);
        selecDia.disableProperty().bind(main.cargant);
        
        diaActual = new SimpleBooleanProperty(true);
        dMenysBot.disableProperty().bind(diaActual);
   
        member = m;
        this.main = main;
       
       
        /**
         * Esta funcio es de stackOverFlow per a desactivar els dies passats.
         */
        selecDia.setDayCellFactory(picker -> new DateCell(){
           
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                LocalDate today = LocalDate.now();
                
                setDisable(empty || item.compareTo(today) < 0 );
            }
        });
        /**
         * ///////////////////////////////
         */
                
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.heightProperty().addListener((object, oldValue, newValue) -> {
         tableView.setFixedCellSize((tableView.getHeight() - 60) / 9.0);
         
        });
       tableView.widthProperty().addListener((object, oldValue, newValue) -> {
           
          
            col0.setMaxWidth(tableView.getWidth() * 0.1);
            col0.setMinWidth(tableView.getWidth() * 0.1);
            
            col1.setMaxWidth((tableView.getWidth() - 5) * 0.225);
            col1.setMinWidth((tableView.getWidth() - 5) * 0.225);
            
            col2.setMinWidth((tableView.getWidth() - 5) * 0.225);
            col2.setMaxWidth((tableView.getWidth() - 5) * 0.225);
            
            col3.setMinWidth((tableView.getWidth() - 5) * 0.225);
            col3.setMaxWidth((tableView.getWidth() - 5) * 0.225);
            
            col4.setMinWidth((tableView.getWidth() - 5) * 0.225);
            col4.setMaxWidth((tableView.getWidth() - 5) * 0.225);
        });
        
        selecDia.valueProperty().set(LocalDate.now());
        club = ClubDBAccess.getSingletonClubDBAccess();
        
        List<FilaReserves> dadesmy = new ArrayList<>(); 
        dades = FXCollections.observableArrayList(dadesmy);
        tableView.setItems(dades);
        
        
        
        hores = new String[]{"09:00", "10:30", "12:00", "13:30", "15:00", "16:30", "18:00", "19:30", "21:00", "22:30"};
       
        files = new FilaReserves[9];
        
        for(int i = 0; i < 9; i++) {
            files[i] = new FilaReserves(hores[i], hores[i+1]);
            files[i].setLocalDate(selecDia.getValue());
        }
        
        configTaula();
        cargaTaula();
    }   
    
    
    private void configTaula() {
        
        col0.setCellValueFactory(
                 new PropertyValueFactory<FilaReserves, String>("hora")
        );
        
        col0.setCellFactory((TableColumn<FilaReserves, String> v) -> {
                TableCell<FilaReserves, String> tableCell = null;
                
                tableCell = new TableCell<FilaReserves, String>(){
                
                    
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        aplicarCellCssHores(this);
                        if(item == null || empty) {
                            
                                setText("");
                           
                        } else {
                           setText(item);
                        }
                    }
                    
                   
                };
               
                 return tableCell;
        });
       col1.setCellValueFactory(cellData1 -> cellData1.getValue().bookingProperty(1));
        
        col1.setCellFactory((TableColumn<FilaReserves, Booking> v) -> {
                TableCell<FilaReserves, Booking> tableCell = null;
                
                tableCell = new TableCell<FilaReserves, Booking>(){
                
                    
                    @Override
                    public void updateItem(Booking item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        aplicarCellCss(this, false);
                        if(item == null || empty) {
                            if(esPasada(this)) {
                                 setText("Fora d'horari");
                            } else if(empty) {
                                setText("");
                            } else {
                                setText("Lliure");
                            }
                        } else {
                           setText(item.getMember().getLogin());
                        }
                    }
                };
                
              
                if(!esPasada(tableCell)) {
                    tableCell.addEventFilter(MouseEvent.MOUSE_ENTERED, filterEntered(1));
                    tableCell.addEventFilter(MouseEvent.MOUSE_EXITED, filterExited());    
                }
                    
                return tableCell;
                
        });;
        
        col2.setCellValueFactory(cellData2 -> cellData2.getValue().bookingProperty(2));
        col2.setCellFactory((TableColumn<FilaReserves, Booking> v) -> {
                TableCell<FilaReserves, Booking> tableCell = null;
                
                tableCell = new TableCell<FilaReserves, Booking>(){
                 
                    
                    @Override
                    public void updateItem(Booking item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        aplicarCellCss(this, true);
                        if(item == null || empty) {
                            if(esPasada(this)) {
                                 setText("Fora d'horari");
                            } else if(empty) {
                                setText("");
                            } else {
                                setText("Lliure");
                            }
                        } else {
                           setText(item.getMember().getLogin());
                        }
                    }
                };
                
              
                if(!esPasada(tableCell)) {
                    tableCell.addEventFilter(MouseEvent.MOUSE_ENTERED, filterEntered(2));
                    tableCell.addEventFilter(MouseEvent.MOUSE_EXITED, filterExited());    
                }
                    
                return tableCell;
                
        });
        
        
        col3.setCellValueFactory(cellData3 -> cellData3.getValue().bookingProperty(3));
        
        col3.setCellFactory((TableColumn<FilaReserves, Booking> v) -> {
                TableCell<FilaReserves, Booking> tableCell = null;
                
                tableCell = new TableCell<FilaReserves, Booking>(){
                
                    
                    @Override
                    public void updateItem(Booking item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        aplicarCellCss(this, false);
                        if(item == null || empty) {
                            if(esPasada(this)) {
                                 setText("Fora d'horari");
                            } else if(empty) {
                                setText("");
                            } else {
                                setText("Lliure");
                            }
                        } else {
                           setText(item.getMember().getLogin());
                        }
                    }
                };
                
              
                if(!esPasada(tableCell)) {
                    tableCell.addEventFilter(MouseEvent.MOUSE_ENTERED, filterEntered(3));
                    tableCell.addEventFilter(MouseEvent.MOUSE_EXITED, filterExited());    
                }
                    
                return tableCell;
                
        });
        
        col4.setCellValueFactory(cellData4 -> cellData4.getValue().bookingProperty(4));
        col4.setCellFactory((TableColumn<FilaReserves, Booking> v) -> {
                TableCell<FilaReserves, Booking> tableCell = null;
                
                tableCell = new TableCell<FilaReserves, Booking>(){
                
                    
                    @Override
                    public void updateItem(Booking item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        aplicarCellCss(this, true);
                        if(item == null || empty) {
                            if(esPasada(this)) {
                                 setText("Fora d'horari");
                            } else if(empty) {
                                setText("");
                            } else {
                                setText("Lliure");
                            }
                        } else {
                           setText(item.getMember().getLogin());
                        }
                    }
                };
                
               
                if(!esPasada(tableCell)) {
                    tableCell.addEventFilter(MouseEvent.MOUSE_ENTERED, filterEntered(4));
                    tableCell.addEventFilter(MouseEvent.MOUSE_EXITED, filterExited());    
                }
                
                
                
                return tableCell;
                
        });
        
        
       
       
       
        ObservableList<TableColumn<FilaReserves, ?>> columnes = tableView.getColumns();
        
    }
    private boolean esPasada(TableCell<FilaReserves, Booking> cell) {
        boolean res = false;
        if(cell.getIndex() < 0 || cell.getIndex() > 8)return false;
        FilaReserves fila = files[cell.getIndex()];
        
        LocalDateTime date = LocalDateTime.of(fila.getLocalDate(), fila.getLocalTime());
        if(date.isBefore(LocalDateTime.now())) {
            res = true;
            
        }
       
        return res;
    }
     private EventHandler<MouseEvent> filterEntered(int i) {
         EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 TableCell<FilaReserves, Booking> cell = ( TableCell) event.getSource();
                        if(esPasada(cell)) return;
                       if(!cell.isEmpty()) {
                            
                            updatePreview(i, cell.getIndex());
                        }
                        if(cell.getItem() == null && !cell.isEmpty()) {
                            //tableView.refresh();
                            cell.setText("Reservar");
                           
                            
                        }
             }
             
         };
         
         return handler;
     }
     
     private EventHandler<MouseEvent> filterExited() {
         EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 TableCell<FilaReserves, Booking> cell = ( TableCell) event.getSource();
                        if(esPasada(cell)) return;
                        if(cell.getItem() == null && !cell.isEmpty()) {
                            
                            cell.setText("Lliure");
                            
                        }
             }
             
         };
         
         return handler;
     }
     
     public void aplicarCellCssHores(TableCell<FilaReserves, String> tableCell) {
         int fila = tableCell.getIndex();
        
        if(fila < 0 || fila > 8) return;
        
        LocalDateTime data = LocalDateTime.of(files[fila].getLocalDate(), files[fila].getLocalTime());
        
        if(data.isBefore(LocalDateTime.now())) {
            tableCell.getStyleClass().set(0, "celda-passada");
            
           
        } else {
            tableCell.getStyleClass().set(0, "columna-hores");
        }
        
        tableCell.getStyleClass().set(1, "celda-general");
     }
    public void aplicarCellCss(TableCell<FilaReserves, Booking> tableCell, boolean par) {
        
        Booking b = tableCell.getItem();
       
      
        int fila = tableCell.getIndex();
        
        if(fila < 0 || fila > 8) return;
         LocalDateTime data = LocalDateTime.of(files[fila].getLocalDate(), files[fila].getLocalTime());
                 
        if(data.isBefore(LocalDateTime.now())) {
            tableCell.getStyleClass().set(0, "celda-passada");
          
            
          
           
        } else {
                        
            if(b != null) {
                if(member != null) {
                    if(b.getMember().getLogin().equals(member.getLogin())) {
                        tableCell.getStyleClass().set(0, "celda-meua");
                    }
                    else {
                         tableCell.getStyleClass().set(0, "celda-ocupada");
                    }
                } else {
                    tableCell.getStyleClass().set(0, "celda-ocupada");
                }
                
               
            } else {
                if(par) {
                    tableCell.getStyleClass().set(0, "celda-lliure-par");
                } else {
                    tableCell.getStyleClass().set(0, "celda-lliure-impar");
                }
               
            }
            
            
        }
      
        tableCell.getStyleClass().set(1, "celda-general");    
       
    }
    private void cargaTaula() {
       
         for(int i = 0; i < 9; i++) {
           files[i].reset();
       } 
       actualitzarFiles(); 
       dades.clear();
       tableView.refresh();
       for(int i = 0; i < 9; i++) {
           dades.add(files[i]);
       }    
       
    }
    
    public void setMember(Member m) {
        member = m;
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
                DateTimeAdapter adapterDate = new DateTimeAdapter();
               
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
                case "21:00":
                    files[8].setBooking(pista, b);   
                    break;
                default:
                 }
        
    }
    
    
    @FXML
    private void canviarDia(ActionEvent event) {
        if(selecDia.getValue().isEqual(LocalDate.now())) {
            diaActual.setValue(true);
        } else {
            diaActual.setValue(false);
        }
        
        
        for(int i = 0; i < 9; i++) {
            files[i].setLocalDate(selecDia.getValue());
        }
        cargaTaula();
       
    }

    private void obrirIniciSessio() {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/vistaLogin/FXMLAuxiliar.fxml"));
        
        try {
            AnchorPane root = (AnchorPane) miCargador.load();
            FXMLAuxiliarController controlador = miCargador.<FXMLAuxiliarController>getController();
          
          //init
            
            Scene scene = new Scene(root,455 , 557);
            Stage stage = new Stage();
            controlador.init(main, this, stage);
            stage.setScene(scene);
            stage.setTitle("Iniciar sessio");
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.showAndWait();       
        } catch(IOException e) {System.out.print("eeeeeeeeeeeeeeee");}
    }
    
    private boolean esPasada(Booking b) {
        if(b == null) return false;
       if(b.getBookingDate().isBefore(LocalDateTime.now())) {
           return true;
       } else {
           return false;
       }
    }
    private boolean esOcupada(int row, int column) {
        FilaReserves act = files[row];
        LocalDateTime data = LocalDateTime.of(act.getLocalDate(), act.getLocalTime());
         if(data.isBefore(LocalDateTime.now())) { return true; }
        Booking book = act.bookingProperty(column).getValue();
        
        return book != null;
    }
    @FXML
    private void selecReserva(MouseEvent event) throws InterruptedException {
        
       ObservableList<TablePosition> cells = tableView.getSelectionModel().getSelectedCells();
       if(cells.size() == 0) return;
       TablePosition cellSelected = cells.get(0);
       
      
       int columna = cellSelected.getColumn();
       int fila = cellSelected.getRow();
       
       if( columna== 0 || esOcupada(fila, columna)) return;
       if(!main.logged.getValue()) {
           obrirIniciSessio();
           if(!main.logged.getValue()) {
               return;
           }
       }
           
       
       
       FilaReserves filaReserva = files[fila]; 
       String horaS = filaReserva.horaProperty().getValue();
       
       
       Alert alert = new Alert(AlertType.CONFIRMATION);
       alert.setTitle("Confirmació de reserva");
       alert.setHeaderText("Pista: " + columna + "\n" + "Dia: " + selecDia.getValue().toString() + "\n" + "Hora: " + horaS);
       alert.setContentText("Estàs segur que vols reservar la pista?");
       
       alert.showAndWait();
       ButtonType res = alert.getResult();
       
       if(res == ButtonType.OK) {
           
           DateTimeAdapter adapter = new DateTimeAdapter();
           
           LocalDate date= selecDia.getValue();
           int any = date.getYear();
           int mes = date.getMonthValue();
           int dia = date.getDayOfMonth();
           
           LocalTime time = files[fila].getLocalTime();
           int hora = time.getHour();
           int minut = time.getMinute();
           
          // LocalDateTime cita = LocalDateTime.of(any, mes, dia, hora, minut);
         
           Court pista = new Court("Pista " + columna);
           
           boolean pagado = false;
           
           LocalDateTime comprovarPagado = LocalDateTime.of(any, mes, dia - 1, hora, minut);
           
           
           if(LocalDateTime.now().compareTo(comprovarPagado) >= 0) {
               pagado = true;
           }
          
           Booking novaReserva = new Booking(LocalDateTime.now(), date, time, pagado, pista, member);
           
           club.getBookings().add(novaReserva);


            cargaTaula();
       }
       
       
    }
       
    private void updatePreview(int pista, int sessio) {
       
        Adapter adapter = new Adapter(selecDia.getValue(), files[sessio].getLocalTime());
        LocalTimeAdapter adaptertime = new LocalTimeAdapter();
        String nextHora = "Errror";
        try {
            nextHora = adaptertime.marshal(files[sessio].getLocalTime().plusMinutes(90));
        }catch(Exception e) {}
        
        preview.setText(adapter.data + " Hora: " + adapter.hora + " - " + nextHora + ". Pista: " + pista);
        
    }

    @FXML
    private void tornar(ActionEvent event) {
        main.entrar(member);
    }

    @FXML
    private void diaMenys(ActionEvent event) {
        selecDia.setValue(selecDia.getValue().minusDays(1));
        canviarDia(event);
    }

    @FXML
    private void diaMes(ActionEvent event) {
        selecDia.setValue(selecDia.getValue().plusDays(1));
        canviarDia(event);
    }
       
        
      
   
}
