package com.claudio.importcontrol.exception;

public class CnpjNaoEncontradoException extends RuntimeException {

    public CnpjNaoEncontradoException() {
        super("O CNPJ informado não foi encontrado em nossa base de dados.");
    }

    public CnpjNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}