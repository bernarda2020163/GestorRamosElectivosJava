package latinasincloud.GestorElectivosJava.service;

import latinasincloud.GestorElectivosJava.model.Electivo;
import latinasincloud.GestorElectivosJava.model.Profesor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // ¡Esta anotación es la clave!
public class ElectivoService {

    private final List<Electivo> electivos = new ArrayList<>();
    private static int contadorId = 1;

    private ProfesorService profesorService;

    public ElectivoService(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    public Electivo crearElectivo(Electivo electivo, int idProfesor) {
        electivo.setId(idProfesor);

        Profesor profesor = profesorService.obtenerProfesorPorId(idProfesor);
        electivo.setProfesor(profesor);

        electivos.add(electivo);
        return electivo;
    }

    public List<Electivo> listaElectivos() {
        return electivos;
    }

    public Electivo obtenerElectivoPorId(int id) {
        for (Electivo electivo : electivos) {
            if (electivo.getId() == id) {
                return electivo;
            }
        }
        return null;
    }

    public Electivo actualizarElectivo(int id, Electivo electivoAc) {
        Electivo electivo = obtenerElectivoPorId(id);
        electivo.setNombre(electivoAc.getNombre() != null && !electivoAc.getNombre().isEmpty() ? electivoAc.getNombre() : electivo.getNombre());
        electivo.setDescripcion(electivoAc.getDescripcion() != null && !electivoAc.getDescripcion().isEmpty() ? electivoAc.getDescripcion() : electivo.getDescripcion());
        if (electivoAc.getCupos() >= 0) {
            electivo.setCupos(electivoAc.getCupos());
        }
        return electivo;
    }

    public boolean eliminarElectivoPorId(int electivoId) {
        Electivo electivo = obtenerElectivoPorId(electivoId);
        if (electivo != null) {
            electivos.remove(electivo);
            System.out.println("¡El electivo ha sido eliminado del registro exitosamente!");
            return true;
        }
        else{
            return false;
        }

    }

}