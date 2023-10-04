package alquilafacil.exception;

public class ClienteRegistrado extends  RuntimeException{

    //Constructor

    public ClienteRegistrado(String msg) {
        super(msg);
    }
}
