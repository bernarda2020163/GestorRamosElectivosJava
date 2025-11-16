package latinasincloud.GestorElectivosJava.repository;

import latinasincloud.GestorElectivosJava.model.Electivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Electivo.
 */
@Repository
public interface IElectivoRepository extends JpaRepository<Electivo, Integer> {
    // Si necesitas métodos de consulta personalizados, los defines aquí.
    // Ejemplo: List<Electivo> findByProfesorId(int profesorId);
}

