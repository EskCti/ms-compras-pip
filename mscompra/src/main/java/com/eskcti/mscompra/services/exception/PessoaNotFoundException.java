package com.eskcti.mscompra.services.exception;

public class PessoaNotFoundException extends EntityNotFoundException {

    public PessoaNotFoundException(String mensagem) {
        super(mensagem);
    }

    public PessoaNotFoundException(Long id) {
        this(String.format("Não existe uma pessoa com o id: %s na base de dados!", id));
    }

}