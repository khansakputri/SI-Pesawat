package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.model.PesawatModel;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PenerbanganService {
    void addPenerbangan(PenerbanganModel penerbangan);

    PenerbanganModel updateObat(PenerbanganModel penerbangan);

    PenerbanganModel getPenerbanganById(Long id);

    List<PenerbanganModel> getPenerbanganList();

    void deletePenerbangan(Long id);

    PenerbanganModel updatePenerbangan(PenerbanganModel penerbangan);

    List<String> getListNomorPenerbangan();
}