package org.example.server;

import lombok.RequiredArgsConstructor;
import org.example.ApiPaths;
import org.example.model.ObjectModel;
import org.example.model.ObjectToClientsModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ServerController {

    private final ServerService service;

    @PostMapping(ApiPaths.CLIENT)
    public Integer addClient() {
        return service.addClient();
    }

    @GetMapping(ApiPaths.CLIENT)
    public List<Integer> getClients() {
        return service.getClients();
    }

    @DeleteMapping(ApiPaths.CLIENT_ID)
    public void removeClient(@PathVariable Integer clientId) {
        service.removeClient(clientId);
    }

    @PostMapping(ApiPaths.OBJECT)
    public void sendObject(@RequestBody ObjectToClientsModel object) {
        service.sendObject(object);
    }

    @GetMapping(ApiPaths.OBJECT_ID)
    public ObjectModel getObject(@PathVariable Integer clientId) {
        return service.getObject(clientId);
    }

}
