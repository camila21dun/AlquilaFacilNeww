package alquilafacil.model;


import alquilafacil.exception.*;
import javafx.scene.control.Alert;
import lombok.Getter;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Getter
public class AlquilaFacil {

    private static final Logger LOGGER = Logger.getLogger(AlquilaFacil.class.getName());

    public static ArrayList<String> facturas = new ArrayList<String>();

    public static ArrayList<Cliente> clientesRegistrados = new ArrayList<Cliente>();

    public static ArrayList<Vehiculo> vehiculosRegistrados = new ArrayList<Vehiculo>();

    public static ArrayList<Alquiler> vehiculosAlquilados = new ArrayList<Alquiler>();

    private static AlquilaFacil alquilafacil;

    private static final String[] marcas = {"chevrolet", "nissan", "ferrari"};

    private static final String[] modelos = {"2000", "2001", "2002", "2003", "2004", "2005"};

    //Metodos clientes

    public void buscarCliente(String cedula) throws IOException {
        for (int i = 0; i < clientesRegistrados.size(); i++)
        {
            if (cedula.equals(clientesRegistrados.get(i).getCedula()))
            {
                generarLoggerWarning("Este usuario ya se encuentra registrado "+cedula);
                throw new ClienteRegistrado("Este usuario ya se encuentra registrado");
            }
        }
    }

    public void validarCedula(String cedula) throws IOException {

        boolean validacionCaracteres = validarCaracteres(cedula);

        if (cedula.isEmpty())
        {
            generarLoggerWarning("la cedula no puede estar vacia");
            throw new CedulaNovalida("la cedula no puede estar vacia");
        }
        else if (cedula == null)
        {
            generarLoggerWarning("la cedula no puede ser null");
            throw new CedulaNovalida("la cedula no puede ser null");
        }
        else if (cedula.length() != 8)
        {
            generarLoggerWarning("la cedula debe tener 8 caracteres exclusivamente");
            throw new CedulaNovalida("la cedula debe tener 8 caracteres exclusivamente");
        }
        else if (validacionCaracteres == true)
        {
            generarLoggerWarning("la cedula solo puede tener numeros");
            throw new CedulaNovalida("la cedula solo puede tener numeros");
        }
    }

    public boolean validarCaracteres(String cualquiera) {
        boolean tieneCaracteres = false;
        for (int i = 0; i < cualquiera.length(); i++)
        {
            if (cualquiera.charAt(i) < 48 || cualquiera.charAt(i) > 57)
            {
                tieneCaracteres = true;
            }
        }
        return tieneCaracteres;
    }

    public void validarTelefono(String telefono) throws IOException {
        if (telefono.length() != 10)
        {
            generarLoggerWarning("su telefono debe contener 10 caracteres exclusivamente");
            throw new TelefonoNovalido("su telefono debe contener 10 caracteres exclusivamente");
        }

        boolean caracteres = validarCaracteres(telefono);

        if (caracteres == true)
        {
            generarLoggerWarning("su telefono debe contener numeros unicamente");
            throw new TelefonoNovalido("su telefono debe contener numeros unicamente");
        }

    }

    public void validarVacio(String cualquiera, String msg) throws IOException {
        if (cualquiera.isEmpty() || cualquiera == null)
        {
            generarLoggerWarning(msg);
            throw new Vacio(msg);
        }

    }

    public void registrarCliente(String nombre, String cedula, String correo, String direccion, String ciudad, String telefono) throws IOException {
        validarCedula(cedula);
        validarVacio(telefono, "debe ingresar algun telefono");
        validarTelefono(telefono);
        validarVacio(nombre, "debe ingresar algun nombre");
        validarVacio(correo, "debe ingresar algun email");
        validarVacio(direccion, "debe ingresar alguna direccion");
        validarVacio(ciudad, "debe ingresar algun lugar de residencia");
        buscarCliente(cedula);
        validarNombre(nombre.toLowerCase());
        Cliente cliente = new Cliente.ClienteBuilder().cedula(cedula).nombre(nombre).correo(correo).direccion(direccion).ciudad(ciudad).telefono(telefono).build();
        clientesRegistrados.add(cliente);
        generarLoggerInformativo("cliente registrado con exito "+cliente.getCedula());


    }

    public void validarNombre(String nombre) throws IOException {
        for (int i = 0; i < nombre.length(); i++) {
            if (nombre.charAt(i) < 'a' || nombre.charAt(i) > 'z')
            {
                generarLoggerWarning("su nombre no puede tener numeros ");
                throw new MalNombre("su nombre no puede tener numeros ");
            }
        }
    }

    private AlquilaFacil() {

        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        LOGGER.log(Level.INFO, "Se crea una nueva instancia de AlquilaFacil");
    }

    /**
     * Método que se usará en las otras clases que requieran una instancia de esta clase
     *
     * @return Instancia del objeto AlquilaFacil
     */
    public static AlquilaFacil getInstance() {
        if (alquilafacil == null) {
            alquilafacil = new AlquilaFacil();
        }

        return alquilafacil;
    }

    //Metodos vehiculos

    public void buscarVehiculo(Vehiculo vehiculo) throws IOException {
        for (int i = 0; i < vehiculosRegistrados.size(); i++) {

            if (vehiculo.equals(vehiculosRegistrados.get(i)))
            {
                generarLoggerWarning("este vehiculo ya se encuentra registrado "+vehiculo.getPlaca());
                throw new VehiculoException("este vehiculo ya se encuentra registrado");
            }
        }
    }

    public void comprobarCaracteresPlaca(String primeraMitad, String segundaMitad) throws IOException {
        for (int i = 0; i < 3; i++) {
            if (primeraMitad.charAt(i) < 'a' || primeraMitad.charAt(i) > 'z') {
                generarLoggerWarning("los primeros 3 digitos deben contener exclusivamente letras"+primeraMitad);
                throw new PlacaNovalida("los primeros 3 digitos deben contener exclusivamente letras");
            }
            if (segundaMitad.charAt(i) < '0' || segundaMitad.charAt(i) > '9') {
                generarLoggerWarning("los 3 ultimos digitos deben tener unicamente numeros "+segundaMitad);
                throw new PlacaNovalida("los 3 ultimos digitos deben tener unicamente numeros ");
            }
        }
    }

    public void validarPlaca(String placa) throws IOException {
        if (placa.length() != 6) {
            generarLoggerWarning("su placa debe tener 6 digitos"+placa);
            throw new PlacaNovalida("su placa debe tener 6 digitos");
        }


        String primeraMitad = placa.substring(0, 3).toLowerCase();
        String segundaMitad = placa.substring(3, 6);

        System.out.println(primeraMitad + "  " + segundaMitad);
        comprobarCaracteresPlaca(primeraMitad, segundaMitad);

    }

    public void validarMarca(String marca) throws IOException {
        boolean apoyo = false;
        for (int i = 0; i < marcas.length; i++) {
            if (marca.equals(marcas[i])) {
                apoyo = true;
                i = marcas.length;
            }
        }

        if (apoyo == false)
        {
            generarLoggerWarning("ingrese una marca que manejemos en nuestra empresa. Manejamos, nissan, chvrolet y ferrari"+marca);
            throw new MarcaYmodeloNovalida("ingrese una marca que manejemos en nuestra empresa. Manejamos, nissan, chvrolet y ferrari");
        }
    }

    public void validarModelo(String modelo) throws IOException {
        boolean apoyo = false;
        for (int i = 0; i < modelos.length; i++) {
            if (modelo.equals(modelos[i])) {
                apoyo = true;
                i = modelos.length;
            }
        }

        if (apoyo == false) {
            generarLoggerWarning("ingrese un modelo valido. Solo admitimos modelos de 2000 a 2005"+modelo);
            throw new MarcaYmodeloNovalida("ingrese un modelo valido. Solo admitimos modelos de 2000 a 2005");
        }
    }

    public void verificarNumerosNegativos(double precioDia, int kilometraje, int numPuertas, String msg) throws IOException {
        if (precioDia < 0.0 || kilometraje < 0 || numPuertas < 0)
        {
            generarLoggerWarning(msg+"\n"+precioDia+"\n"+kilometraje+"\n"+numPuertas);
            throw new NoNumerosNegativos(msg);
        }
    }


    public void registrarVehiculo(String placa, String referencia, String marca, String modelo, String foto, String kilometraje, String precioDia, boolean esAutomatico, String numPuertas) throws IOException {
        validarVacio(placa, "su vehiculo debe tener placa");
        validarVacio(referencia, "su vehiculo debe tener un nombre");
        validarVacio(marca, "su vehiculo debe tener una marca asociada");
        validarVacio(modelo, "su vehiculo debe tener un modelo ");
        validarVacio(foto, "debe ingresar fotos del vehiculo");
        validarVacio(kilometraje, "su vehiculo debe tener kilometraje");
        validarVacio(precioDia, "su vehiculo debe tener precio por dia");
        validarVacio(numPuertas, "debe suministrar la cantidad de puertas que tiene su vehiculo");
        validarPlaca(placa);
        validarMarca(marca);
        validarModelo(modelo);
        verificarNumerosNegativos(Double.parseDouble(precioDia), Integer.parseInt(kilometraje), Integer.parseInt(numPuertas), "profavor el precio, el kilometraje, y la cantidad de puertas son valores que no pueden llegar a ser negativos");
        Vehiculo vehiculo = new Vehiculo.VehiculoBuilder().placa(placa).referencia(referencia).marca(marca).modelo(modelo).foto(foto).kilometraje(Integer.parseInt(kilometraje)).precioDia(Double.parseDouble(precioDia)).esAutomatico(esAutomatico).numPuertas(Integer.parseInt(numPuertas)).build();
        buscarVehiculo(vehiculo);
        vehiculosRegistrados.add(vehiculo);
        generarLoggerInformativo("vehiculo registrado "+vehiculo.getPlaca());
    }

    //Metodos alquiler

    public String alquilarVheiculo(LocalDate fechaFin, LocalDate fechaInicio, Vehiculo vehiculo, String cedula) throws IOException {
        buscarCedulaAlqui(cedula);
        String factura = "";
        Cliente cliente = buscarClienteAlqui(cedula);
        validarFechas(fechaFin, fechaInicio);
        LocalDateTime fechaRegisto=LocalDateTime.now();
        Alquiler alquilado = new Alquiler.AlquilerBuilder().
                cliente(cliente).
                vehiculo(vehiculo).
                fechaRegistro(LocalDateTime.now()).
                fechaInicio(fechaInicio).fechaFin(fechaFin).
                valorTotal(fechaInicio.until(fechaFin, ChronoUnit.DAYS) * vehiculo.getPrecioDia()).propietario(cliente.getNombre()).nombreVehiculo(vehiculo.getReferencia()).
                build();
        vehiculosAlquilados.add(alquilado);
        factura += "Nombre del cliente " + cliente.getNombre()+"\n"+
                "Cedula del cliente " +"\n"+
                cliente.getCedula()+"\n"+
                "Nombre del vehiculo "+"\n"+
                vehiculo.getReferencia()+"\n"+
                "Modelo del vehiculo " +"\n"+
                vehiculo.getModelo()+"\n" +
                "Marca del vehiculo "+"\n" +
                vehiculo.getMarca() +"\n"+
                "Fecha de inicio del alquiler "
                + fechaInicio +"\n"+
                "Fecha de entrega del vehciulo "
                + fechaFin+"\n"
                + "Fecha del registro del alquiler "+"\n"
                + fechaRegisto;
        facturas.add(factura);
        generarLoggerInformativo("vehiculo registrado "+vehiculo.getPlaca()+"\n"+"factura generada "+factura);
        return factura;

    }


    public String determinarVehimasAlquilado1() {
        String vehiculoMas = "";
        int centinela = 0;
        int vehiculoAlquiladoTotal = 0;
        for (int i = 0; i < marcas.length; i++) {
            centinela = 0;
            for (int j = 0; j < vehiculosRegistrados.size(); j++) {
                if (vehiculosRegistrados.get(j).getMarca().equals(marcas[i])) {
                    centinela++;
                }
            }

            if (centinela > vehiculoAlquiladoTotal) {
                vehiculoAlquiladoTotal = centinela;
                vehiculoMas = marcas[i];
            }
        }
        return vehiculoMas;
    }

    public double determinarTotalganado(LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
        double totalGanado = 0.0;
        validarFechas(fechaFin, fechaInicio);
        for (int i = 0; i < vehiculosAlquilados.size(); i++) {
            if (fechaInicio.equals(vehiculosAlquilados.get(i).getFechaInicio()) && fechaFin.equals(vehiculosAlquilados.get(i).getFechaFin())) {
                totalGanado += vehiculosAlquilados.get(i).getValorTotal();
            }
        }
        return totalGanado;
    }

    public ArrayList<Alquiler> determinarVehiculosAlquiladosFecha(LocalDate fechaInicio, LocalDate fechaFin) throws IOException {
        validarFechas(fechaFin, fechaInicio);
        ArrayList<Alquiler> alquilados = new ArrayList<Alquiler>();
        for (int i = 0; i < vehiculosAlquilados.size(); i++) {
            if (fechaInicio.equals(vehiculosAlquilados.get(i).getFechaInicio()) && fechaFin.equals(vehiculosAlquilados.get(i).getFechaFin())) {
                alquilados.add(vehiculosAlquilados.get(i));
            }
        }
        Collections.sort(alquilados);
        return alquilados;
    }

    public void validarFechas(LocalDate fechaFin, LocalDate fechaInicio) throws IOException {
        if (fechaInicio.isAfter(fechaFin)) {
            generarLoggerWarning("la fecha inicial no puede ser mayor que la fecha final"+fechaInicio+"\n"+fechaInicio);
            throw new MalasFechas("la fecha inicial no puede ser mayor que la fecha final");
        }
    }

    public void buscarCedulaAlqui(String cedula) throws IOException {
        boolean x = false;
        for (int i = 0; i < clientesRegistrados.size(); i++) {
            if (clientesRegistrados.get(i).getCedula().equals(cedula)) {
                x = true;
            }
        }
        if (x == false) {
            generarLoggerWarning("usted no se encuentra registrado"+cedula);
            throw new CedulaNovalida("usted no se encuentra registrado");
        }
    }

    public Cliente buscarClienteAlqui(String cedula) {
        for (int i = 0; i < clientesRegistrados.size(); i++) {
            if (cedula.equals(clientesRegistrados.get(i).getCedula())) {
                return clientesRegistrados.get(i);
            }
        }
        return null;
    }


    public static void generarLoggerInformativo(String msg) throws IOException {
        try {
            LOGGER.log(Level.INFO, msg);
        } catch (SecurityException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public static void generarLoggerWarning(String msg) throws IOException {
        try {
            LOGGER.log( Level.WARNING, msg);
        } catch (SecurityException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public static void generarLoggerSevere(String msg) throws IOException {
        try {
            LOGGER.log( Level.SEVERE, msg);
        } catch (SecurityException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
}