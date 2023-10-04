package alquilafacil.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {


    private String nombre;
    private String cedula;
    private String correo;
    private String direccion;
    private String ciudad;
    private String telefono;

}