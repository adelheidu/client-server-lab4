package org.example.server;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.ApiPaths;
import org.example.model.ObjectListModel;
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

    @Operation(description = "Добавление клиента в список клиентов")
    @PostMapping(ApiPaths.CLIENT)
    public Integer addClient() {
        return service.addClient();
    }

    @Operation(description = "Получение списка клиентов")
    @GetMapping(ApiPaths.CLIENT)
    public List<Integer> getClients() {
        return service.getClients();
    }

    @Operation(description = "Удаление клиента из списка клиентов")
    @DeleteMapping(ApiPaths.CLIENT_ID)
    public void removeClient(@PathVariable Integer clientId) {
        service.removeClient(clientId);
    }

    @Operation(description = "Отправка объекта клиентам")
    @PostMapping(ApiPaths.OBJECT)
    public void sendObject(@RequestBody ObjectToClientsModel object) {
        service.sendObject(object);
    }

    @Operation(description = "Получение объекта от клиента")
    @GetMapping(ApiPaths.OBJECT_ID)
    public ObjectModel getObject(@PathVariable Integer clientId) {
        return service.getObject(clientId);
    }

    @Operation(description = "Добавление объекта в список объектов")
    @PostMapping(ApiPaths.OBJECT_ID)
    public void addObject(@PathVariable Integer clientId, @RequestBody ObjectModel obj) {
        service.addObject(clientId, obj.object());
    }

    @Operation(description = "Удаление объекта из списка клиентов")
    @DeleteMapping(ApiPaths.OBJECT_LIST_ID)
    public void deleteObjectList(@PathVariable Integer clientId) {
        service.deleteObjectList(clientId);
    }

    @Operation(description = "Получение всех объектов клиента")
    @GetMapping(ApiPaths.OBJECT_LIST_ID)
    public ObjectListModel getObjectList(@PathVariable Integer clientId) {
        return service.getObjectList(clientId);
    }

}
