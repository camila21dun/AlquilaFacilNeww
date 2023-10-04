package alquilafacil.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import alquilafacil.application.Main;
import alquilafacil.exception.AlquilaException;
import alquilafacil.model.AlquilaFacil;

import alquilafacil.model.Vehiculo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class RegistroVehiculosController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnRegistrarVehiculo;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnCargar;

    @FXML
    private ImageView imagenClienteImageView;

    @FXML
    private ComboBox<String> cbTipoCaja;

    @FXML
    private TextField txtKilometraje;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtPuertas;

    @FXML
    private TextField txtReferencia;

    @FXML
    private TextField txtUrlFoto;

    @FXML
    void cargarImagenDesdeURLEvent(ActionEvent event) {
        cargarImagenDesdeURLAction();

    }




    private Main main;
    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();


    @FXML
    void registrarVehiculoEvent(ActionEvent event) throws IOException {
        registrarVehiculoAction();

    }

    private void registrarVehiculoAction() throws IOException {

        String placa = txtPlaca.getText();
        String referencia = txtReferencia.getText();
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();
        String foto =  txtUrlFoto.getText();
        String kilometraje = txtKilometraje.getText();
        String precio = txtPrecio.getText();
        String puertas =  txtPuertas.getText();
        boolean tipoCaja = cbTipoCaja.getValue().isEmpty();

        try {

            alquilaFacil.registrarVehiculo(placa, referencia, marca, modelo, foto, kilometraje, precio, tipoCaja, puertas);


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Se ha registrado correctamente el vehiculo de placa  " + placa.toLowerCase());
            alert.show();
            limpiarTexto();

        }
        catch(NumberFormatException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("El kilometraje y la cantidad de puertas deben ser números enteros, mientras que el precio debe ser un número decimal");
            alert.setHeaderText("");
            alert.show();
            AlquilaFacil.generarLoggerWarning("El kilometraje y la cantidad de puertas deben ser números enteros, mientras que el precio debe ser un número decimal"+kilometraje+" "+puertas+" "+precio);
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText("");
            alert.show();
        }

    }

    private void cargarImagenDesdeURLAction() {

        String imageUrl = txtUrlFoto.getText();
        try {
            Image image = new Image(imageUrl);
            imagenClienteImageView.setImage(image);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("la URL no es valida");
            alert.setHeaderText("");
            alert.show();
        }
    }


    @FXML
    void volverEvent(ActionEvent event) {
        this.main.mostrarInicio();

    }

    @FXML
    void initialize() {
        cbTipoCaja.getItems().addAll("Manual","Automatica");
        // Agregar un listener al TextField para detectar cambios en el texto.
        txtUrlFoto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    // Cuando el campo de texto cambia y no está vacío, intenta cargar la imagen automáticamente.
                    cargarImagenDesdeURLAction();
                }
            }
        });
    }

    public void setApplication(Main main) {
        this.main=main;
    }

    private void limpiarTexto() throws IOException {
        txtPuertas.setText("");
        txtKilometraje.setText("");
        txtUrlFoto.setText("");
        txtMarca.setText("");
        txtReferencia.setText("");
        txtModelo.setText("");
        txtPrecio.setText("");
        txtPlaca.setText("");
        cbTipoCaja.commitValue();
        AlquilaFacil.generarLoggerInformativo("campo limpiado correctamente en vehiculo");
    }
}