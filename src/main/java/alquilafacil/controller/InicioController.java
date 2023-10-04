package alquilafacil.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import alquilafacil.application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class InicioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRegistrarCliente;

    @FXML
    private Button btnRegistrarVehiculo;

    @FXML
    private Button btnVerLista;

    private Main main;
    ModelFactoryController modelFactoryController;

    @FXML
    void registrarClienteEvent(ActionEvent event) {
        registrarClienteAction();

    }

    private void registrarClienteAction() {
        this.main.mostrarRegistroCliente();
    }

    @FXML
    void registrarVehiculoEvent(ActionEvent event) {
        registrarVehiculoAction();

    }

    private void registrarVehiculoAction() {
        this.main.mostrarRegistroVehiculo();
    }

    @FXML
    void verListaEvent(ActionEvent event) {
        verListaAction();

    }

    private void verListaAction() {
        this.main.mostrarRegistroAlquiler();

    }

    @FXML
    void initialize() {
    }

    public void setApplication (Main main){
        this.main = main;
    }
}
