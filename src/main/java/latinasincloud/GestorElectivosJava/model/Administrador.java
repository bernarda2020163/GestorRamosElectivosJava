package latinasincloud.GestorElectivosJava.model;

public class Administrador extends Usuario {

    // atributos
    private String cargo;

    // constructor por defecto
    public Administrador() {}

    // constructor con parámetros

    public Administrador(String cargo) {
        this.cargo = cargo;
    }

    // Administrador hereda usuario super
    public Administrador(int id, String nombre, String email, String password, String rol, String cargo) {
        super(id, nombre, email, password, rol);
        this.cargo = cargo;
    }

    // getters and setters encapsulamiento de los atributos


    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}