package com.example.projetoiiweb;

import baseDados.bll.Condutorbll;
import baseDados.bll.Passageirobll;
import baseDados.bll.Staffbll;
import baseDados.entity.CondutorEntity;
import baseDados.entity.PassageiroEntity;
import baseDados.entity.StaffEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @PostMapping("/iniciar-sessao")
    public String handleLogin(
            @RequestParam("nCartaoCidadao") String nCartaoCidadao,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) { // Injete a HttpSession aqui

        for (PassageiroEntity p : Passageirobll.listar()) {
            if (p.getNCc().equals(nCartaoCidadao) && p.getPassword().equals(password)) {
                // Se for um passageiro, você pode decidir o que fazer aqui se necessário
            }
        }

        for (CondutorEntity c : Condutorbll.listar()) {
            if (c.getnCc().equals(nCartaoCidadao) && c.getPassword().equals(password) && c.getIdEstado() == 2) {
                session.setAttribute("condutor", c); // Armazene o condutor na sessão
                return "menu-condutor"; // Redirecione para a página do menu do condutor
            }
        }

        for (StaffEntity s : Staffbll.listar()) {
            if (s.getNumero().equals(nCartaoCidadao) && s.getPassword().equals(password)) {
                // Se for um staff, você pode decidir o que fazer aqui se necessário
            }
        }

        model.addAttribute("errorMessage", "Não existe nenhum utilizador com os dados introduzidos.");
        return "login";
    }

}


