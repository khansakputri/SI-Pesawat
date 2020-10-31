package apap.tugas.sipes.repository;

import apap.tugas.sipes.model.PesawatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PesawatDb extends JpaRepository<PesawatModel, Long>{
    Optional<PesawatModel> findById(Long id);
    void deleteById(Long id);
    List<PesawatModel> findByOrderByIdAsc();
}