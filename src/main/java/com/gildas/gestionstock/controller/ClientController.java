package com.gildas.gestionstock.controller;

import com.gildas.gestionstock.dto.ClientDTO;
import com.gildas.gestionstock.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/client")
@AllArgsConstructor
public class ClientController {

    ClientService clientService;

    @GetMapping
    public List<ClientDTO> findAll(){
        return clientService.findAll();
    }

    @PostMapping
    public ClientDTO save(@RequestBody ClientDTO dto){
        return clientService.save(dto);
    }

    @GetMapping("/{id}")
    public ClientDTO findById(@PathVariable("id") Integer id){
        return clientService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        clientService.delete(id);
    }
}
