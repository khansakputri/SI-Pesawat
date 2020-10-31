package apap.tugas.sipes.service;

import apap.tugas.sipes.model.TipeModel;

import java.util.List;

public interface TipeService {
    void addTipe(TipeModel tipe);
    List<TipeModel> getListTipe();
    TipeModel getTipeById(Long id);
}
