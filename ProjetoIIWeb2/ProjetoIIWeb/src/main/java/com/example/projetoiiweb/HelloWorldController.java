package com.example.projetoiiweb;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import baseDados.bll.*;
import baseDados.entity.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class HelloWorldController {

    @GetMapping("/")
    public String hello(Model model) {
        return "helloworld";
    }

    @GetMapping("/viajar")
    public String viajar(Model model) {
        List<String> generos = Arrays.asList("M", "F");
        List<String> nacionalidades = Nacionalidadebll.listar();

        model.addAttribute("generos", generos);
        model.addAttribute("nacionalidades", nacionalidades);
        return "registar-passageiro"; // This maps to registar-passageiro.html
    }

    @GetMapping("/conduzir")
    public String conduzir(Model model) {
        List<String> generos = Arrays.asList("M", "F");
        List<String> nacionalidades = Nacionalidadebll.listar();

        model.addAttribute("generos", generos);
        model.addAttribute("nacionalidades", nacionalidades);

        return "registar-condutor";
    }

    @GetMapping("/iniciar-sessao")
    public String login(Model model) {
        return "login";
    }

}
