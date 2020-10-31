package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.repository.PenerbanganDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PenerbanganServiceImpl implements PenerbanganService {
    @Autowired
    PenerbanganDb penerbanganDb;

    @Override
    public void addPenerbangan(PenerbanganModel penerbangan) {
       penerbanganDb.save(penerbangan);
    }

    @Override
    public PenerbanganModel updateObat(PenerbanganModel penerbangan) {
        penerbanganDb.save(penerbangan);
        return penerbangan;
    }

    @Override
    public PenerbanganModel getPenerbanganById(Long id) {
        return penerbanganDb.findById(id).get();
    }

    @Override
    public List<PenerbanganModel> getPenerbanganList() {
        return penerbanganDb.findAll();
    }

    @Override
    public void deletePenerbangan(Long id) {
        penerbanganDb.deleteById(id);
    }

    @Override
    public PenerbanganModel updatePenerbangan(PenerbanganModel penerbangan) {
        penerbanganDb.save(penerbangan);
        return penerbangan;
    }

    @Override
    public List<String> getListNomorPenerbangan() {
        List<PenerbanganModel> listPenerbangan = penerbanganDb.findAll();
        List<String> nomor_penerbangan = new ArrayList<String>();
        for(PenerbanganModel penerbangan : listPenerbangan) {
            nomor_penerbangan.add(penerbangan.getNomor_penerbangan());
        }
        return nomor_penerbangan;
    }

}
