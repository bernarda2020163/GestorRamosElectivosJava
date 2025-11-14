package latinasincloud.GestorElectivosJava.model;

import java.util.List;

public class Electivo{
    // atributos
    private int id;
    private String nombre;
    private String descripcion;
    private int cupos;
    private Profesor profesor;
    private List<Postulacion> postulaciones;

    // constructor por defecto
    public Electivo() {}

    // constructor con parámetros
    public Electivo(int id, String nombre, String descripcion, int cupos, Profesor profesor, List<Postulacion> postulaciones) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cupos = cupos;
        this.profesor = profesor;
        this.postulaciones = postulaciones;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Postulacion> getPostulaciones() {
        return postulaciones;
    }

    public void setPostulaciones(List<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }
}
