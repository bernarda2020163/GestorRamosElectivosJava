package latinasincloud.GestorElectivosJava.model;

import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Usuario {
    // atributos
    private String curso;
    private List<Postulacion> postulaciones;

    // constructor por defecto
    public Estudiante() {}

    // constructor con parámetros
    public Estudiante(String curso) {
        this.curso = curso;
        this.postulaciones = postulaciones;
    }

    // Estudiante hereda usuario super
    public Estudiante(int id, String nombre, String email, String password, String rol, String curso) {
        super(id, nombre, email, password, rol);
        this.curso = curso;
        this.postulaciones = new ArrayList<>();
    }

    // getters and setters encapsulamiento de los atributos


    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public List<Postulacion> getPostulaciones() {
        return postulaciones;
    }

    public void setPostulaciones(List<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }
}
