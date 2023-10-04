package alquilafacil.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alquiler implements Comparable<Alquiler> {

    private Cliente cliente;
    private String propietario;
    private String nombreVehiculo;
    private Vehiculo vehiculo;
    private LocalDateTime fechaRegistro;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double valorTotal;


    @Override
    public int compareTo(Alquiler o) {
        if(o.getValorTotal()>valorTotal)
        {
            return-1;
        }

        else if(o.getValorTotal()>valorTotal)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
}
