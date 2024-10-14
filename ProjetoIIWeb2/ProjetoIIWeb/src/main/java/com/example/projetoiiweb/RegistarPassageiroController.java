package com.example.projetoiiweb;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import baseDados.bll.*;
import baseDados.entity.*;

@Controller
public class RegistarPassageiroController {

    @GetMapping("/registar-passageiro")
    public String showRegisterForm(Model model) {
        List<String> generos = Arrays.asList("M", "F");
        List<String> nacionalidades = Nacionalidadebll.listar();

        model.addAttribute("generos", generos);
        model.addAttribute("nacionalidades", nacionalidades);

        return "registar-passageiro";
    }

    @PostMapping("/viajar")
    public String handleRegister(
            @RequestParam("nome") String nome,
            @RequestParam("dataNascimento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNascimento,
            @RequestParam("genero") String genero,
            @RequestParam("nacionalidade") String nacionalidade,
            @RequestParam("nCartaoCidadao") String nCartaoCidadao,
            @RequestParam("password") String password,
            Model model) {

        int flag = 0;

        for (PassageiroEntity p : Passageirobll.listar()) {
            if (p.getNCc().equals(nCartaoCidadao)) {
                flag = 1;
                break;
            }
        }

        if (flag == 0) {
            PassageiroEntity passageiro = new PassageiroEntity();
            passageiro.setNCc(nCartaoCidadao);
            passageiro.setNome(nome);
            LocalDate hoje = LocalDate.now();
            passageiro.setIdade(Period.between(dataNascimento, hoje).getYears());
            passageiro.setGenero(genero.charAt(0));
            passageiro.setPassword(password);
            passageiro.setIdNacionalidade(Nacionalidadebll.getidNacionalidade(nacionalidade));
            Passageirobll.criar(passageiro);
            model.addAttribute("successMessage", "Conta criada com sucesso.");
            return "helloworld";
        } else {
            model.addAttribute("errorMessage", "O passageiro com este número de Cartão de Cidadão já está registado.");
            return "registar-passageiro";
        }
    }
}
