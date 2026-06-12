package com.claudio.importcontrol.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException() {
        super("O usuário solicitado não foi encontrado no sistema.");
    }
}