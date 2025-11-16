package latinasincloud.GestorElectivosJava.model;
import java.time.LocalDateTime;

public class Postulacion {
    // atributos
    private int id;
    private Estudiante estudiante;
    private Electivo electivo;
    private LocalDateTime fecha;
    private Estado estado;
    private int prioridad; // <-- ¡NUEVO CAMPO!

    // constructor por defecto
    public Postulacion() {}

    // constructor con parámetros
    public Postulacion(int id, Estudiante estudiante, Electivo electivo, LocalDateTime fecha, Estado estado) {
        this.id = id;
        this.estudiante = estudiante;
        this.electivo = electivo;
        this.fecha = fecha;
        this.estado = estado;
        this.prioridad = prioridad; // <-- Inicializar prioridad
    }

    // 3. AÑADIR EL NUEVO CONSTRUCTOR DE 6 ARGUMENTOS (Este es el que falta)
    public Postulacion(int id, Estudiante estudiante, Electivo electivo, LocalDateTime fecha, Estado estado, int prioridad) {
        this.id = id;
        this.estudiante = estudiante;
        this.electivo = electivo;
        this.fecha = fecha;
        this.estado = estado;
        this.prioridad = prioridad;
    }

    // getters and setters encapsulamiento de los atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Electivo getElectivo() {
        return electivo;
    }

    public void setElectivo(Electivo electivo) {
        this.electivo = electivo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getPrioridad() { // <-- Nuevo Getter
        return prioridad;
    }

    public void setPrioridad(int prioridad) { // <-- Nuevo Setter
        this.prioridad = prioridad;
    }
}
