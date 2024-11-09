package curriculo_documentado.com.InterfaceJpa;

import curriculo_documentado.com.Model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterfaceJpa extends JpaRepository<Docente, Long> {

    @Query(value = "SELECT * FROM docente ORDER BY id ASC LIMIT 1", nativeQuery = true)
    Optional<Docente> findFirst();
}
