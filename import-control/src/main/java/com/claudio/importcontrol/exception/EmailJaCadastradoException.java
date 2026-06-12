package com.claudio.importcontrol.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException() {
        super("O e-mail informado já está cadastrado em nosso sistema.");
    }

    public EmailJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}