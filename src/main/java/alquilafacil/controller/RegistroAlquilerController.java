package alquilafacil.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import alquilafacil.application.Main;
import alquilafacil.exception.AlquilaException;
import alquilafacil.model.AlquilaFacil;
import alquilafacil.model.Alquiler;
import alquilafacil.model.Cliente;
import alquilafacil.model.Vehiculo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.Setter;

public class RegistroAlquilerController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRegistrarAlquiler;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnBuscarInfo;

    @FXML
    private Button btnEliminarV;

    @FXML
    private DatePicker dateFinalInfo;

    @FXML
    private DatePicker dateInicioInfo;

    @FXML
    private TableColumn<Vehiculo, String> columModelo;

    @FXML
    private TableColumn<Vehiculo, String> columPlaca;

    @FXML
    private TableColumn<Vehiculo, Double> columPrecioDia;

    @FXML
    private TableColumn<Vehiculo, String> columReferencia;

    @FXML
    private DatePicker dateFinal;

    @FXML
    private DatePicker dateInicio;

    @FXML
    private TableView<Vehiculo> tableVehiculos;

    @FXML
    private TextField txtCedulaCliente;

    private AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    @Setter @Getter
    private Main application;


    @FXML
    void BuscarInfoEvent(ActionEvent event) {
        BuscarInfoAction();

    }
    @FXML
    private TableColumn<Alquiler, String> columCliente;

    @FXML
    private TableColumn<Alquiler, String> columVehiculo;

    @FXML
    private TableColumn<Alquiler, LocalDate> columFechaInicio;

    @FXML
    private TableColumn<Alquiler, LocalDate> columFechaFin;

    @FXML
    private TableView<Alquiler> tableAlquiladosFecha;

    @FXML
    private Button btnbuscar1;

    @FXML
    private Button mostrarMarcaCodicioada;

    @FXML
    private void buscar1Event(ActionEvent e) throws IOException {
        buscar1ActionEvent();
    }

    @FXML
    private DatePicker dateInicioInfo1;

    @FXML

    private DatePicker dateFinalInfo1;

    @FXML

    private Button gananciasTotales;

    @FXML

    private Button gananciasTotalesAqui;

    @FXML

    private void gananciasTotalEvent(ActionEvent e) {
        gananciasTotalActionEvent();
    }

    @FXML

    private void gananciasTotalActionEvent() {
        try
        {
            LocalDate fechaInicial=dateInicioInfo1.getValue();
            LocalDate fechaFinalizada=dateFinalInfo1.getValue();
            double gananciasTotalesRangoFecha=alquilaFacil.determinarTotalganado(fechaInicial,fechaFinalizada);
            gananciasTotalesAqui.setText(""+gananciasTotalesRangoFecha);
            AlquilaFacil.generarLoggerInformativo("estas son las ganancias totales en el rango de fechas que va desde "+fechaInicial+" hasta "+fechaFinalizada+"  "+gananciasTotalesRangoFecha);

        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText(null);
            alert.show();
        }
    }
    @FXML
    private void buscar1ActionEvent() throws IOException {
        String vehiculoMasalquilado=alquilaFacil.determinarVehimasAlquilado1();
        mostrarMarcaCodicioada.setText("La marca más codiciada por nuestros clientes es: "+vehiculoMasalquilado);
        AlquilaFacil.generarLoggerInformativo("La marca mas codiciada es "+vehiculoMasalquilado);
    }
    private void BuscarInfoAction() {
        LocalDate fechaInicioI=dateInicioInfo.getValue();
        LocalDate fechaFinI=dateFinalInfo.getValue();
        try
        {
            ArrayList<Alquiler> vehiculosFechas=alquilaFacil.determinarVehiculosAlquiladosFecha(fechaInicioI,fechaFinI);
            tableAlquiladosFecha.setItems(FXCollections.observableArrayList(vehiculosFechas));
            AlquilaFacil.generarLoggerInformativo("informacion dada a conocer con exito ");
        }
        catch(Exception e)
        {
            mostrarError("Error"+ e.getMessage());
        }

    }

    @FXML
    void eliminarEvent(ActionEvent event) {
        eliminarAction();

    }

    private void eliminarAction() {
        Vehiculo vehiculoSeleccionado = tableVehiculos.getSelectionModel().getSelectedItem();

        if (vehiculoSeleccionado != null) {
            // Eliminar el vehículo de la lista de vehículos
            AlquilaFacil.vehiculosRegistrados.remove(vehiculoSeleccionado);

            // Actualizar la tabla
            tableVehiculos.getItems().remove(vehiculoSeleccionado);

            // Limpiar la selección en la tabla
            tableVehiculos.getSelectionModel().clearSelection();

            // Opcional: Puedes mostrar un mensaje de confirmación o éxito
            mostrarMensaje("Vehículo eliminado con éxito");
        } else {
            // Si no se ha seleccionado ningún vehículo, muestra un mensaje de error
            mostrarError("Por favor, selecciona un vehículo para eliminar.");
        }


    }


    @FXML
    void registrarAlquilerEvent(ActionEvent event) {
        registrarAlquilerAction();

    }

    private void registrarAlquilerAction() {
        // Obtener los datos del formulario
        Vehiculo vehiculoSeleccionado = tableVehiculos.getSelectionModel().getSelectedItem();
        String cedulaCliente = txtCedulaCliente.getText();
        LocalDate fechaInicio = dateInicio.getValue();
        LocalDate fechaFinal = dateFinal.getValue();

        try {

            String factura=alquilaFacil.alquilarVheiculo(fechaFinal,fechaInicio,vehiculoSeleccionado,cedulaCliente);
            mostrarFactura(factura);
            AlquilaFacil.generarLoggerInformativo("la factura se ha mostrado con exito ");
            limpiarFormulario();




        }catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
        }
    }


    @FXML
    void volverEvent(ActionEvent event) {
        this.application.mostrarInicio();

    }


    @FXML
    void initialize() {

    }

    private void mostrarFactura(String factura) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Factura de Alquiler");
        alert.setHeaderText("Detalle del Alquiler");
        alert.setContentText(factura);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ha ocurrido un error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion");
        alert.setHeaderText("se ha eliminado correctamente");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarFormulario() throws IOException {
        // Limpia los campos del formulario
        txtCedulaCliente.clear();
        dateInicio.setValue(null);
        dateFinal.setValue(null);
        tableVehiculos.getSelectionModel().clearSelection();
        AlquilaFacil.generarLoggerInformativo("campos limpiados con exito en alquiler");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        columModelo.setCellValueFactory( new PropertyValueFactory<>("modelo"));
        columReferencia.setCellValueFactory( new PropertyValueFactory<>("referencia"));
        columPrecioDia.setCellValueFactory( new PropertyValueFactory<>("precioDia"));
        columPlaca.setCellValueFactory( new PropertyValueFactory<>("placa"));
        tableVehiculos.setItems(FXCollections.observableArrayList( AlquilaFacil.vehiculosRegistrados));

        columCliente.setCellValueFactory( new PropertyValueFactory<>("propietario"));
        columVehiculo.setCellValueFactory( new PropertyValueFactory<>("nombreVehiculo"));
        columFechaInicio.setCellValueFactory( new PropertyValueFactory<>("fechaInicio"));
        columFechaFin.setCellValueFactory( new PropertyValueFactory<>("fechaFin"));
    }
}