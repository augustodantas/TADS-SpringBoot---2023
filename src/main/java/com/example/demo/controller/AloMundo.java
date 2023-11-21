package com.example.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AloMundo{
    
    @GetMapping("/")
    public String olaMundo(){
        return "Olá Mundo 33 !!!!";
    }

    @GetMapping("/rota2")
	public String mensagem2 () {
		return "Alô Mundo, pela rota 2!!!";
	}
	@GetMapping("/rota3/{valor}")
	public String mensagem3 (@PathVariable String valor) {
		return "Alô Mundo, pela rota 3 que receber como parâmetro o valor: " + valor +"!!!";
	}
	@GetMapping("/rota3")
	public String mensagem3 () {
		return "Alô Mundo, pela rota 3 sem parâmetro !!!";
	}
   
}
