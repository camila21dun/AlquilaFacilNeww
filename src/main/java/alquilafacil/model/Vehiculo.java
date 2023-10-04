package alquilafacil.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    private String placa;
    private String referencia;
    private String marca;
    private String modelo;
    private String foto;
    private int kilometraje;
    private double precioDia;
    private boolean esAutomatico;
    private int numPuertas;


}