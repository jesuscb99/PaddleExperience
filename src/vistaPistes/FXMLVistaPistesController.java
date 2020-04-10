/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaPistes;

import static java.lang.Byte.MAX_VALUE;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author 2jesu
 */
public class FXMLVistaPistesController implements Initializable {

    @FXML
    private TableColumn<?, ?> col4;
    @FXML
    private TableView<?> tableView;
    @FXML
    private TableColumn<?, ?> col1;
    @FXML
    private TableColumn<?, ?> col2;
    @FXML
    private TableColumn<?, ?> col3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tableView.widthProperty().addListener((object, oldValue, newValue) -> {
         
            col1.setMinWidth(tableView.getWidth() / 4.0);
            col2.setMinWidth(tableView.getWidth() / 4.0);
            col3.setMinWidth(tableView.getWidth() / 4.0);
            col4.setMinWidth(tableView.getWidth() / 4.0);
        });
    }    
    
}
