package com.eskcti.workercompras.services;

import com.eskcti.workercompras.models.Endereco;
import com.eskcti.workercompras.repository.CepRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CepService {

    private final CepRepository cepRepository;

    public Endereco buscarCep(String cep) {
        try {
            Endereco endereco = cepRepository.buscarPorCep(cep);
            log.info("Endereco montado com sucesso: {}", endereco);
            return endereco;
        } catch (Exception ex) {
            log.error("Não foi possivel buscar o cep {}", cep);
            return null;
        }
    }

}
