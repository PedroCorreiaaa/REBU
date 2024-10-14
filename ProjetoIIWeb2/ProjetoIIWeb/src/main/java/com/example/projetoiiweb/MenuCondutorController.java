package com.example.projetoiiweb;

import baseDados.bll.TipoVeiculobll;
import baseDados.bll.Veiculobll;
import baseDados.entity.CondutorEntity;
import baseDados.entity.VeiculoEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuCondutorController {

    @GetMapping("/registar-veiculo")
    public String registarVeiculo(Model model, HttpSession session) {
        CondutorEntity condutor = (CondutorEntity) session.getAttribute("condutor"); // Recupere o condutor da sessão
        List<String> tiposVeiculo = TipoVeiculobll.listar();
        model.addAttribute("tiposVeiculo", tiposVeiculo);

        return "registar-veiculo";
    }

    @GetMapping("/escolher-veiculo")
    public String escolherVeiculo(Model model, HttpSession session) {
        CondutorEntity condutor = (CondutorEntity) session.getAttribute("condutor"); // Recupere o condutor da sessão
        if (condutor != null) {
            List<String> veiculosCondutor = new ArrayList<>();
            for (VeiculoEntity v : Veiculobll.listarVeiculosRegistadosCondutor(condutor.getIdCondutor())) {
                veiculosCondutor.add(v.getMatricula());
            }
            model.addAttribute("veiculosCondutor", veiculosCondutor);
        } else {
            // Tratar o caso onde o condutor não está na sessão
            model.addAttribute("veiculosCondutor", new ArrayList<>());
        }
        return "escolher-veiculo";
    }
}
