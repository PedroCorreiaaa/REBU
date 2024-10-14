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
public class RegistarCondutorController {

    @GetMapping("/home")
    public String showHomePage() {
        return "helloworld"; // nome da página helloworld.html
    }

    @GetMapping("/voltar-condutor")
    public String voltarParaPaginaAnteriorCondutor() {
        return "helloworld";
    }

    @GetMapping("/registar-condutor")
    public String showRegisterForm(Model model) {
        List<String> generos = Arrays.asList("M", "F");
        List<String> nacionalidades = Nacionalidadebll.listar();

        model.addAttribute("generos", generos);
        model.addAttribute("nacionalidades", nacionalidades);

        return "registar-condutor";
    }

    @PostMapping("/conduzir")
    public String handleRegister(
            @RequestParam("nome") String nome,
            @RequestParam("dataNascimento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNascimento,
            @RequestParam("genero") Character genero,
            @RequestParam("nacionalidade") String nacionalidade,
            @RequestParam("nCartaoCidadao") String nCartaoCidadao,
            @RequestParam("password") String password,
            @RequestParam("nCartaConducao") String nCartaConducao,
            @RequestParam("nCertificadoTVDE") String nCertificadoTVDE,
            Model model) {

        int flag = 0;

        for (CondutorEntity c : Condutorbll.listar()) {
            if (c.getnCc().equals(nCartaoCidadao)) {
                flag = 1;
                break;
            }
        }

        if (flag == 0) {
            CondutorEntity condutor = new CondutorEntity();
            condutor.setnCc(nCartaoCidadao);
            condutor.setNome(nome);
            LocalDate hoje = LocalDate.now();
            condutor.setIdade(Period.between(dataNascimento, hoje).getYears());
            condutor.setGenero(genero);
            condutor.setPassword(password);
            condutor.setnConducao(nCartaConducao);
            condutor.setnCertificadoTvde(nCertificadoTVDE);
            condutor.setIdNacionalidade(Nacionalidadebll.getidNacionalidade(nacionalidade));
            condutor.setIdEstado(1);
            Condutorbll.criar(condutor);
            model.addAttribute("successMessage", "Conta criada com sucesso.");
            return "helloworld";
        } else {
            model.addAttribute("errorMessage", "O condutor com este número de Cartão de Cidadão já está registado.");
            return "registar-condutor";
        }
    }
}
