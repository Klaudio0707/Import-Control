package com.claudio.importcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class App {
	public static void main(String[] args) {
		try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            if (dotenv.get("DB_USER") != null) {
                System.setProperty("DB_USER", dotenv.get("DB_USER"));
                System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
            }
        } catch (Exception e) {
            System.out.println("⚠️ .env não encontrado, seguindo com variáveis de ambiente do sistema.");
        }
		SpringApplication.run(App.class, args);
		System.out.println("Servidor rodando em http://localhost:8080");
	}
}