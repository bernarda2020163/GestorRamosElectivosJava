package latinasincloud.GestorElectivosJava.repository;


import latinasincloud.GestorElectivosJava.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Estudiante.
 */
@Repository
public interface IEstudianteRepository extends JpaRepository<Estudiante, Integer> {
}
