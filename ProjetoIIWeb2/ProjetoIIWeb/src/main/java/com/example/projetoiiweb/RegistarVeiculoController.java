package com.example.projetoiiweb;

import baseDados.bll.TipoVeiculobll;
import baseDados.bll.Veiculobll;
import baseDados.entity.CondutorEntity;
import baseDados.entity.VeiculoEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;


@Controller
public class RegistarVeiculoController {

    @GetMapping("/menu-condutor")
    public String voltar(HttpSession session) {
        CondutorEntity condutor = (CondutorEntity) session.getAttribute("condutor"); // Recupere o condutor da sessão
        if (condutor == null) {
            // Se condutor for nulo, redirecione para a página de login ou faça outro tratamento adequado
            return "redirect:/login";
        }
        return "menu-condutor";
    }

    @PostMapping("/registar-veiculo")
    public String registarVeiculo(
            @RequestParam("marca") String marca,
            @RequestParam("modelo") String modelo,
            @RequestParam("anoFabrico") String anoFabrico,
            @RequestParam("matricula") String matricula,
            @RequestParam("tipoVeiculo") String tipoVeiculo,
            HttpSession session, // Injete a HttpSession aqui
            Model model) {

        CondutorEntity condutor = (CondutorEntity) session.getAttribute("condutor"); // Recupere o condutor da sessão
        if (condutor == null) {
            // Se condutor for nulo, redirecione para a página de login ou faça outro tratamento adequado
            return "login";
        }

        // Verifica se já existe um veículo com a mesma matrícula
        boolean veiculoExistente = Veiculobll.listar().stream()
                .anyMatch(v -> v.getMatricula().equals(matricula));

        if (veiculoExistente) {
            model.addAttribute("errorMessage", "O veículo com essa matrícula já está registrado.");
            return "registar-veiculo";
        }

        // Cria uma nova entidade de veículo
        VeiculoEntity veiculo = new VeiculoEntity();
        veiculo.setIdTipoVeiculo(TipoVeiculobll.getidTipoVeiculo(tipoVeiculo));
        veiculo.setIdEstado(1); // Defina o estado inicial do veículo conforme necessário
        veiculo.setAnoFabrico(anoFabrico);
        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setMatricula(matricula);
        veiculo.setIdCondutor(condutor.getIdCondutor()); // Define o condutor associado ao veículo

        // Salva o veículo no banco de dados
        Veiculobll.criar(veiculo);

        // Define a mensagem de sucesso e redireciona para a página de menu-condutor
        model.addAttribute("successMessage", "Aguarde enquanto o pedido de registo de veículo é avaliado!");
        model.addAttribute("redirectDelay", 3000);
        return "menu-condutor";
    }
}