package latinasincloud.GestorElectivosJava.model;

import jakarta.persistence.*; // Usar jakarta.persistence para Spring Boot 3+

/**
 * Entidad que representa a un Administrador en la base de datos.
 */
@Entity
@Table(name = "administradores")
public class Administrador {

    // Se cambia el tipo de generación de ID a IDENTITY (SERIAL en PostgreSQL)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String rol = "Administrador"; // Valor por defecto

    // Constructor sin argumentos requerido por JPA
    public Administrador() {
    }

    // Getters y Setters (Asegúrarse de que existan)

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}