package com.example.projetoiiweb;

import baseDados.bll.Veiculobll;
import baseDados.entity.VeiculoEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class EscolherVeiculoController {
    @PostMapping("/escolher-veiculo")
    public String selecionarVeiculo(@RequestParam("veiculoCondutor") String veiculoCondutor, HttpSession session) {
        VeiculoEntity veiculo = Veiculobll.listarVeiculosMatricula(veiculoCondutor);
        session.setAttribute("veiculoSelecionado", veiculo);
        return "viagens-disponiveis";
    }
}
