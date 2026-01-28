package com.claudio.importcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class App {
	public static void main(String[] args) {
    System.out.println("📂 DIRETÓRIO DE EXECUÇÃO: " + System.getProperty("user.dir"));
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    String url = dotenv.get("DB_URL");
    
    if (url == null) {
        System.out.println("❌ ERRO: O .env não foi lido ou a variável DB_URL não existe nele.");
    } else {
        System.out.println("✅ SUCESSO: .env carregado! URL: " + url);
    }

    dotenv.entries().forEach(entry -> {
        System.setProperty(entry.getKey(), entry.getValue());
    });

    SpringApplication.run(App.class, args);
}
}