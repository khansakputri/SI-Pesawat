package apap.tugas.sipes.controller;

import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.service.PenerbanganService;
import apap.tugas.sipes.service.PesawatService;
import apap.tugas.sipes.service.TeknisiService;
import apap.tugas.sipes.service.TipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PenerbanganController {
    @Qualifier("penerbanganServiceImpl")
    @Autowired
    private PenerbanganService penerbanganService;

    @Qualifier("pesawatServiceImpl")
    @Autowired
    private PesawatService pesawatService;

    @Qualifier("teknisiServiceImpl")
    @Autowired
    private TeknisiService teknisiService;

    @Qualifier("tipeServiceImpl")
    @Autowired
    private TipeService tipeService;

    @GetMapping("/penerbangan")
    public String viewPenerbangan(Model model){
        List<PenerbanganModel> listPenerbangan = penerbanganService.getPenerbanganList();
        model.addAttribute("listPenerbangan", listPenerbangan);
        return "view-penerbangan";
    }

    @GetMapping("/penerbangan/tambah")
    public String addPenerbanganFormPage(Model model){
        model.addAttribute("penerbangan",new PenerbanganModel());
        return "form-add-penerbangan";
    }

    @PostMapping("/penerbangan/tambah")
    public String addPenerbanganSubmit(
            @ModelAttribute PenerbanganModel penerbangan, Model model
    ){
        Boolean numberCheck = penerbanganService.getListNomorPenerbangan().contains(penerbangan.getNomor_penerbangan());
        if((penerbangan.getNomor_penerbangan().length() != 16) || numberCheck == true) {
            model.addAttribute("msg", "Nomor penerbangan harus berjumlah 16 digit dan unik");
            model.addAttribute("penerbangan", penerbangan);
            return "form-add-penerbangan";
        }else{
            penerbanganService.addPenerbangan(penerbangan);
            model.addAttribute("penerbangan", penerbangan);
        }
        return "add-penerbangan";
    }

    @GetMapping("/penerbangan/{id}")
    public String viewDetailPenerbangan(
            @PathVariable(value = "id") Long id, Model model){
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);
        if (penerbangan.getPesawat() == null){
            model.addAttribute("no_seri", "Belum diassign pesawat");
        } else{
            model.addAttribute("no_seri", penerbangan.getPesawat().getNomor_seri());
        }

        model.addAttribute("penerbangan", penerbangan);
//        model.addAttribute("no_seri", no_seri);

        return "detail-penerbangan";
    }


    @GetMapping("/penerbangan/ubah/{id}")
    private String changePenerbanganFormPage(
            @PathVariable Long id, Model model){
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);

        model.addAttribute("penerbangan", penerbangan);
        return "form-ubah-penerbangan";
    }

    @PostMapping("/penerbangan/ubah")
    private String changePenerbanganFormSubmit(
            @ModelAttribute PenerbanganModel penerbangan, Model model){
        PenerbanganModel updatedPenerbangan = penerbanganService.updatePenerbangan(penerbangan);
        model.addAttribute("penerbangan", updatedPenerbangan);
        return "ubah-penerbangan";
    }

    @GetMapping("/penerbangan/delete/{id}")
    public String deletepenerbangan(
            @PathVariable(value = "id") Long id, Model model){
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);

        // mau ngehapus jadwal penerbangan si pesawat
        penerbanganService.deletePenerbangan(id);
        model.addAttribute("penerbangan", penerbangan);
        return "delete-penerbangan";
    }
}
