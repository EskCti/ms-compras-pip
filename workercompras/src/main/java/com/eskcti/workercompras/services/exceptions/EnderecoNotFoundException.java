package com.eskcti.workercompras.services.exceptions;

public class EnderecoNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EnderecoNotFoundException(String mensagem) {
        super(mensagem);
    }

    public EnderecoNotFoundException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
