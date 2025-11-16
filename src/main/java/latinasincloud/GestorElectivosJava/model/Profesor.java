package latinasincloud.GestorElectivosJava.model;


import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad que representa a un Profesor, puede dictar múltiples Electivos.
 */
@Entity
@Table(name = "profesores")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String especialidad;

    @Column(nullable = false, length = 50)
    private String rol = "Profesor"; // Valor por defecto

    // Relación Bidireccional: Un Profesor puede tener muchos Electivos
    // 'mappedBy' indica el nombre del campo en la entidad Electivo que es la FK.
    @OneToMany(mappedBy = "profesor", fetch = FetchType.LAZY)
    private List<Electivo> electivosDictados;

    // Constructor sin argumentos requerido por JPA
    public Profesor() {
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<Electivo> getElectivosDictados() {
        return electivosDictados;
    }

    public void setElectivosDictados(List<Electivo> electivosDictados) {
        this.electivosDictados = electivosDictados;
    }
}