package apap.tugas.sipes.service;

import apap.tugas.sipes.model.TeknisiModel;

import java.util.List;

public interface TeknisiService {
    void addTeknisi(TeknisiModel teknisi);
    List<TeknisiModel> getListTeknisi();
    TeknisiModel getTeknisiById(Long id);
}
