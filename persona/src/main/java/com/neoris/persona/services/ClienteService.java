package com.neoris.persona.services;

import com.neoris.persona.entities.Cliente;
import com.neoris.persona.model.dto.ClienteRequest;
import com.neoris.persona.model.dto.ClienteResponse;
import com.neoris.persona.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ResponseEntity<Object> addCliente(ClienteRequest clienteRequest) {
        try {
            Optional<Cliente> res = Optional.ofNullable(clienteRepository.findByIdentificacion(clienteRequest.getIdentificacion()));
            if (res.isEmpty()) {
                var cliente = Cliente.builder()
                        .nombre(clienteRequest.getNombre())
                        .genero(clienteRequest.getGenero())
                        .edad(clienteRequest.getEdad())
                        .identificacion(clienteRequest.getIdentificacion())
                        .direccion(clienteRequest.getDireccion())
                        .telefono(clienteRequest.getTelefono())
                        .contrasenia(clienteRequest.getContrasenia())
                        .estado(clienteRequest.getEstado())
                        .build();

                clienteRepository.save(cliente);
                log.info("Cliente agregado " + cliente);
                return new ResponseEntity<>(
                        HttpStatus.CREATED
                );
            } else {
                log.info("Cliente ya registrado ");
                return new ResponseEntity<>(
                        HttpStatus.CONFLICT
                );
            }
        } catch (Exception e) {
            log.error("error al crear cliente " + e);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    public List<ClienteResponse> getAllClientes() {
        var clientes = clienteRepository.findAll();
        return clientes.stream().map(this::mapToClientesResponse).toList();
    }

    private ClienteResponse mapToClientesResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .genero((cliente.getGenero()))
                .edad((cliente.getEdad()))
                .identificacion(cliente.getIdentificacion())
                .telefono(cliente.getTelefono())
                .contrasenia(cliente.getContrasenia())
                .estado(cliente.getEstado())
                .direccion(cliente.getDireccion())
                .build();
    }

    public ResponseEntity<Object> update(Cliente cliente) {
        try {
            Optional<Cliente> res = Optional.ofNullable(clienteRepository.findByIdentificacion(cliente.getIdentificacion()));
            if (!res.isEmpty()) {
                log.info("Cliente modificado");
                clienteRepository.save(cliente);
                return new ResponseEntity<>(
                        HttpStatus.OK
                );
            } else {
                log.info("Cliente NO modificado");
                return new ResponseEntity<>(
                        HttpStatus.CONFLICT
                );
            }
        }catch (Exception e){
            log.error("No se pudo actualizar :" +e);
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public Cliente findByIdentificacion(String identificacion) {
        Cliente cliente = clienteRepository.findByIdentificacion(identificacion);
        return cliente;
    }
}
