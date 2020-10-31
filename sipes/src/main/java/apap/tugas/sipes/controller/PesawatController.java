package apap.tugas.sipes.controller;

import apap.tugas.sipes.model.*;

import apap.tugas.sipes.service.PesawatService;
import apap.tugas.sipes.service.PenerbanganService;
import apap.tugas.sipes.service.TeknisiService;
import apap.tugas.sipes.service.TipeService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PesawatController {
    @Qualifier("pesawatServiceImpl")
    @Autowired
    private PesawatService pesawatService;

    @Qualifier("teknisiServiceImpl")
    @Autowired
    private TeknisiService teknisiService;

    @Qualifier("tipeServiceImpl")
    @Autowired
    private TipeService tipeService;

    @Qualifier("penerbanganServiceImpl")
    @Autowired
    private PenerbanganService penerbanganService;

    @GetMapping("/")
    private String home(){
        return "home";
    }

    @GetMapping("/pesawat")
    public String viewAllPesawat(Model model){
        List<PesawatModel> listPesawat = pesawatService.getPesawatList();
        model.addAttribute("listPesawat", listPesawat);
        return "view-pesawat";
    }

    @GetMapping("/pesawat/tambah")
    private String addpesawatFormPage(Model model){
        List<TeknisiModel> listTeknisi = teknisiService.getListTeknisi();
        List<TipeModel> listTipe = tipeService.getListTipe();
        model.addAttribute("pesawat", new PesawatModel());
        model.addAttribute("listTeknisi", listTeknisi);
        model.addAttribute("listTipe", listTipe);

        return "form-add-pesawat";
    }


    @PostMapping("/pesawat/tambah")
    private String addPesawatSubmit(
            @ModelAttribute PesawatModel pesawat,
            Model model
    ){
       pesawat.setTipe(tipeService.getTipeById(pesawat.getTipe().getId()));
       String noSeri = pesawatService.generateNoSeri(pesawat);
       pesawat.setNomor_seri(noSeri);
       pesawatService.addPesawat(pesawat);
    //    model.addAttribute("noSeri", noSeri);
        model.addAttribute("pesawat", pesawat);

       return "add-pesawat";
    }

    @PostMapping(value = "/pesawat/tambah", params = {"addTeknisi"})
    private String addPesawatTeknisi(
            @ModelAttribute PesawatModel pesawat,
            Model model
    ){
        if(pesawat.getListTeknisi() == null || pesawat.getListTeknisi().size() == 0){
            pesawat.setListTeknisi(new ArrayList<TeknisiModel>());
        }
        pesawat.getListTeknisi().add(new TeknisiModel());

        List<TeknisiModel> teknisiList = teknisiService.getListTeknisi();
        List<TipeModel> listTipe = tipeService.getListTipe();

        model.addAttribute("listTipe", listTipe);
        model.addAttribute("teknisiList", teknisiList);
        model.addAttribute("pesawat", pesawat);

        return "form-add-pesawat";
    }


    @GetMapping("/pesawat/hapus/{id}")
    public String deletePesawat(
            @PathVariable(value = "id") Long id, Model model){
        PesawatModel pesawat = pesawatService.getPesawatById(id);

        if(pesawatService.getPesawatById(id) == null){
            return "kosong";
        }else{
            pesawatService.deletePesawat(id);
            model.addAttribute("pesawat", pesawat);
            return "delete-pesawat";
        }
    }

    @GetMapping("/pesawat/{id}")
    public String viewDetailPesawat(
            @PathVariable(value = "id") Long id, Model model){
        PesawatModel pesawat = pesawatService.getPesawatById(id);
        List<TeknisiModel> listTeknisi = pesawat.getListTeknisi();
        List<PenerbanganModel> listPenerbangan = penerbanganService.getPenerbanganList();
        String namaTipe = pesawat.getTipe().getNama();
//        System.out.println("aaa " + listPenerbangan.size());

        model.addAttribute("berhasil", "");
        model.addAttribute("pesawatPenerbangan", pesawat.getListPenerbangan());
        model.addAttribute("namaTipe", namaTipe);
        model.addAttribute("pesawat", pesawat);
        model.addAttribute("listTeknisi", listTeknisi);
        model.addAttribute("listPenerbangan", listPenerbangan);
        return "detail-pesawat";
    }

    @PostMapping("/pesawat/{id}/tambah-penerbangan")
    public String tambahPenerbangan(
            @PathVariable(value = "id") Long id,
            @RequestParam(value="penerbanganId") Long penerbanganId,
            Model model
    ){
        PesawatModel pesawat = pesawatService.getPesawatById(id);
        List<TeknisiModel> listTeknisi = pesawat.getListTeknisi();
        String namaTipe = pesawat.getTipe().getNama();
        PenerbanganModel p = penerbanganService.getPenerbanganById(penerbanganId);

        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(p.getId());
        penerbangan.setPesawat(pesawat);

        model.addAttribute("berhasil", "Penerbangan dengan nomor" + penerbangan.getNomor_penerbangan() + "  berhasil ditambahkan");
        model.addAttribute("penerbangan", penerbangan);
        model.addAttribute("listTeknisi", listTeknisi);
        model.addAttribute("namaTipe", namaTipe);
        model.addAttribute("listPenerbangan", penerbanganService.getPenerbanganList());
        model.addAttribute("pesawatPenerbangan", pesawat.getListPenerbangan());
        model.addAttribute("pesawat", pesawat);
        return "detail-pesawat";
    }

    @GetMapping("/pesawat/ubah/{id}")
    private String changePenerbanganFormPage(
            @PathVariable Long id, Model model){
        PesawatModel pesawat = pesawatService.getPesawatById(id);
        model.addAttribute("pesawat", pesawat);

//        TipeModel tipe = pesawat.getTipe();
//        model.addAttribute("tipe", tipe);

        return "form-ubah-pesawat";
    }

    @PostMapping("/pesawat/ubah")
    private String changePenerbanganFormSubmit(
            @ModelAttribute PesawatModel pesawat, Model model
    ){
        pesawat.setTipe(tipeService.getTipeById(pesawat.getTipe().getId()));
        pesawat.setJenis_pesawat(pesawat.getJenis_pesawat());
        pesawat.setNomor_seri(pesawatService.generateNoSeri(pesawat));

        PesawatModel updatedPesawat = pesawatService.updatePesawat(pesawat);
        model.addAttribute("pesawat", updatedPesawat);
        return "ubah-pesawat";
    }

    @GetMapping("/pesawat/filter")
    private String filterPesawat(
            @RequestParam(value= "idPenerbangan", required = true) Optional<Long> idPenerbangan,
            @RequestParam(value= "idTipe", required = true) Optional<Long> idTipe,
            @RequestParam(value= "idTeknisi", required = true) Optional<Long> idTeknisi,
            Model model
    ){
        List<PenerbanganModel> listPenerbangan = penerbanganService.getPenerbanganList();
        List<TipeModel> listTipe = tipeService.getListTipe();
        List<TeknisiModel> listTeknisi = teknisiService.getListTeknisi();
        model.addAttribute("listPenerbangan", listPenerbangan);
        model.addAttribute("listTipe", listTipe);
        model.addAttribute("listTeknisi", listTeknisi);

        try {
            Long id_penerbangan = idPenerbangan.get();
            Long id_tipe = idTipe.get();
            Long id_teknisi = idTeknisi.get();

            if (id_penerbangan != 0 || id_tipe != 0 || id_teknisi != 0) {
                List<PesawatModel> listPesawat = pesawatService.getPesawatList();
                List<PesawatModel> pesawatList = new ArrayList<PesawatModel>();
                for (PesawatModel pesawat : listPesawat) {
                    pesawatList.add(pesawat);
                }

                for (PesawatModel pesawat : pesawatList) {
                    if (id_penerbangan != 0) {
                        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id_penerbangan);
                        List<PenerbanganModel> lst = pesawat.getListPenerbangan();
                        if (!lst.contains(penerbangan)) {
                            listPesawat.remove(pesawat);
                        }
                    }
                    if (id_tipe != 0) {
                        TipeModel tipe = tipeService.getTipeById(id_tipe);
                        TipeModel tipePesawat = pesawat.getTipe();
                        if (tipe.getId() != tipePesawat.getId()) {
                            listPesawat.remove(pesawat);
                        }
                    }
                    if (id_teknisi != 0) {
                        TeknisiModel teknisi = teknisiService.getTeknisiById(id_teknisi);
                        List<TeknisiModel> lst = pesawat.getListTeknisi();
                        if (!lst.contains(teknisi)) {
                            listPesawat.remove(pesawat);
                        }
                    }
                }
                model.addAttribute("listPesawat", listPesawat);
                model.addAttribute("size", listPesawat.size());
            }
            return "filter-pesawat";
        }catch (Exception e){
            return "filter-pesawat";
        }
    }

    @GetMapping("/pesawat/pesawat-tua")
    public String viewPesawatTua(Model model){
        List<PesawatModel> listPesawat = pesawatService.getPesawatList();
        List<PesawatModel> pesawatTua = new ArrayList<PesawatModel>();

        for (PesawatModel p : listPesawat){
            LocalDate hariIni = LocalDate.now();
            LocalDate pesawatDibuat = p.getTanggal_dibuat();
            Period usia = Period.between(pesawatDibuat, hariIni);
            if (usia.getYears() >= 10){
                pesawatTua.add(p);
            }
        }

        model.addAttribute("pesawatTua", pesawatTua);
        return "pesawat-tua";
    }

    @GetMapping("/bonus")
    private String countTeknisi(Model model) {
        List<PesawatModel> listPesawat = pesawatService.getPesawatList();
        model.addAttribute("listPesawat", listPesawat);

        return "bonus";
    }
    
}
