package com.example.projetoiiweb;

import baseDados.bll.*;
import baseDados.entity.VeiculoEntity;
import baseDados.entity.ViagemEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ViagensDisponiveisController {

    @GetMapping("/viagens-disponiveis")
    public String listarViagens(Model model, HttpSession session) {
        // Recuperar o veículo selecionado da sessão
        VeiculoEntity veiculoSelecionado = (VeiculoEntity) session.getAttribute("veiculoSelecionado");

        // Lista de viagens disponíveis para o veículo selecionado
        List<ViagemEntity> viagens = Viagembll.listarViagensPendentesTipoVeiculo(veiculoSelecionado.getIdTipoVeiculo());

        // Construir uma matriz de informações das viagens
        List<List<String>> matrizViagens = new ArrayList<>();
        for (ViagemEntity viagem : viagens) {
            List<String> infoViagem = new ArrayList<>();
            infoViagem.add(String.valueOf(viagem.getIdViagem()));
            infoViagem.add(Passageirobll.listarWithId(viagem.getIdPassageiro()).getNome());
            infoViagem.add(String.valueOf(Passageirobll.listarWithId(viagem.getIdPassageiro()).getGenero()));
            infoViagem.add(Nacionalidadebll.getNacionalidade(Passageirobll.listarWithId(viagem.getIdPassageiro()).getIdNacionalidade()));
            infoViagem.add(String.valueOf(Passageirobll.listarWithId(viagem.getIdPassageiro()).getIdade()));
            infoViagem.add(String.valueOf(Passageirobll.getAvaliacaoWithId(viagem.getIdPassageiro())));
            infoViagem.add(viagem.getOrigem());
            infoViagem.add(viagem.getDestino());
            infoViagem.add(String.valueOf(viagem.getDistancia()));
            infoViagem.add(TipoVeiculobll.getTipoVeiculo(viagem.getIdTipoVeiculo()));
            infoViagem.add(String.valueOf(viagem.getValor().multiply(viagem.getPercent_condutor())));
            matrizViagens.add(infoViagem);
        }

        model.addAttribute("matrizViagens", matrizViagens);

        return "viagens-disponiveis";
    }
}
