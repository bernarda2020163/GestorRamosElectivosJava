package latinasincloud.GestorElectivosJava.service;

import latinasincloud.GestorElectivosJava.model.Electivo;
import latinasincloud.GestorElectivosJava.model.Estado;
import latinasincloud.GestorElectivosJava.model.Estudiante;
import latinasincloud.GestorElectivosJava.model.Postulacion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service // Añadida la anotación para que Spring lo reconozca
public class PostulacionService {
    //agregar lista de postulaciones y contador Id incremental
    private final List<Postulacion> postulaciones = new ArrayList<>();
    private static int contadorId = 1;

    private EstudianteService estudianteService;
    private ElectivoService electivoService;


    public PostulacionService(EstudianteService estudianteService, ElectivoService electivoService) {
        this.estudianteService = estudianteService;
        this.electivoService = electivoService;
    }

    public Postulacion crearPostulacion(int estudianteId, int electivoId) {
        Estudiante estudiante = estudianteService.obtenerEstudiantePorId(estudianteId);
        Electivo electivo = electivoService.obtenerElectivoPorId(electivoId);

        Postulacion nueva = new Postulacion(
                contadorId++,
                estudiante,
                electivo,
                LocalDateTime.now(),
                Estado.PENDIENTE
        );

        postulaciones.add(nueva);
        estudiante.getPostulaciones().add(nueva);
        electivo.getPostulaciones().add(nueva);

        return nueva;
    }

    public Postulacion obtenerPostulacionPorId(int id) {
        for (Postulacion postulacion : postulaciones) {
            if (postulacion.getId() == id) {
                return postulacion;
            }
        }
        return null;
    }

    public List<Postulacion> listaPostulaciones (){
        return postulaciones;
    }

    public boolean eliminarPostulacionPorId(int postulacionId) {
        Postulacion postulacion = obtenerPostulacionPorId(postulacionId);
        if  (postulacion != null) {
            postulaciones.remove(postulacion);
            System.out.println("¡La postulacion ha sido eliminada del registro exitosamente!");
            return true;
        }
        else{
            return false;}

    }

}