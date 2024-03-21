package com.neoris.persona.controllers;

import com.neoris.persona.entities.Cliente;
import com.neoris.persona.model.dto.ClienteRequest;
import com.neoris.persona.model.dto.ClienteResponse;
import com.neoris.persona.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.neoris.persona.services.ClienteService;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCliente(@RequestBody ClienteRequest clienteRequest) {
        this.clienteService.addCliente(clienteRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteResponse> getAllClientes() {
        return this.clienteService.getAllClientes();
    }

    @PutMapping
    public ResponseEntity<Object> updateCliente(@RequestBody Cliente cliente){
        return clienteService.update(cliente);
    }

}
