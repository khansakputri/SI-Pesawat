package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PesawatModel;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PesawatService {
    void addPesawat(PesawatModel pesawat);

    PesawatModel updatePesawat(PesawatModel pesawat);

    PesawatModel getPesawatById(Long id);

    void deletePesawat(Long id);

    List<PesawatModel> getPesawatList();

    String generateNoSeri(PesawatModel pesawat);
}
