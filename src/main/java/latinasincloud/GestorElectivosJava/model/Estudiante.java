package latinasincloud.GestorElectivosJava.model;


import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad que representa un Estudiante, asociado a múltiples Postulaciones.
 */
@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    //@Column(nullable = false, unique = true, length = 15)
    //private String rut;

    @Column(nullable = false, length = 50)
    private String rol = "Estudiante"; // Valor por defecto

    // Relación Bidireccional: Un Estudiante puede tener muchas Postulaciones
    @OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
    private List<Postulacion> postulaciones;

    // Constructor sin argumentos requerido por JPA
    public Estudiante() {
    }

    // Getters y Setters (Asegúrate de que existan)

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

    /*public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    } */

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<Postulacion> getPostulaciones() {
        return postulaciones;
    }

    public void setPostulaciones(List<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }
}