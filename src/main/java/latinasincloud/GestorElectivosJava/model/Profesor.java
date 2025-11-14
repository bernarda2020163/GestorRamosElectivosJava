package latinasincloud.GestorElectivosJava.model;

public class Profesor {

    // atributos
    private int id;
    private String nombre;
    private String especialidad;

    // constructor por defecto
    public Profesor() {}

    // constructor con parámetros
    public Profesor(int id, String nombre, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    // getters and setters encapsulamiento de los atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
