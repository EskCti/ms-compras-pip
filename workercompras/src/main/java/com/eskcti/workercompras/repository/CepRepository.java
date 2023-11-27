package com.eskcti.workercompras.repository;

import com.eskcti.workercompras.models.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "${viacep}")
public interface CepRepository {

    @GetMapping("/ws/{cep}/json/")
    Endereco buscarPorCep(@PathVariable("cep") String cep);

}
