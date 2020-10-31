package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.repository.PesawatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class PesawatServiceImpl implements PesawatService{
    @Autowired
    PesawatDb pesawatDb;

    @Override
    public void addPesawat(PesawatModel pesawat) {
        pesawatDb.save(pesawat);
    }

    @Override
    public PesawatModel updatePesawat(PesawatModel pesawat) {
        pesawatDb.save(pesawat);
        return pesawat;
    }

    @Override
    public PesawatModel getPesawatById(Long id) {
        return pesawatDb.findById(id).get();
    }

    @Override
    public void deletePesawat(Long id) {
        pesawatDb.deleteById(id);
    }

    @Override
    public List<PesawatModel> getPesawatList() {
        return pesawatDb.findByOrderByIdAsc();
    }

    @Override
    public String generateNoSeri(PesawatModel pesawat){
        //GENERATE NOMOR SERI
        String no_seri = "";
        if (pesawat.getJenis_pesawat().equals("Komersial")){
            no_seri+="1";
        }
        else{
            no_seri+="2";
        }

        if(pesawat.getTipe().getNama().equals("ATR")){
            no_seri+="AT";
        }
        if(pesawat.getTipe().getNama().equals("Airbus")){
            no_seri+="AB";
        }
        if(pesawat.getTipe().getNama().equals("Bombardier")){
            no_seri+="BB";
        }
        if(pesawat.getTipe().getNama().equals("Boeing")){
            no_seri+="BO";
        }

        int getYear = pesawat.getTanggal_dibuat().getYear();
        String year = Integer.toString(getYear);
        StringBuilder sb = new StringBuilder(year);
        sb.reverse();
        String tahun = sb.toString();
        no_seri+=tahun;

        getYear +=8;
        String strGetYear = Integer.toString(getYear);
        no_seri+= strGetYear;

        Random random = new Random();
        char char1 = (char)(random.nextInt(26) + 'a');
        char char2 = (char)(random.nextInt(26) + 'a');
        String temp1 = String.valueOf(char1).toUpperCase();
        String temp2 = String.valueOf(char2).toUpperCase();
        no_seri+=temp1;
        no_seri+=temp2;

        return no_seri;
    }
}
