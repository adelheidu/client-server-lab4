package org.example.server;

import lombok.Getter;
import org.example.model.ObjectModel;
import org.example.model.ObjectToClientsModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServerService {

    @Getter
    private List<Integer> clients;

    private Integer counter = 0;

    private List<ObjectToClient> objects;

    public ServerService() {
        clients = new ArrayList<>();
        objects = new ArrayList<>();
    }

    public Integer addClient() {
        clients.add(++counter);
        System.out.println(clients);
        return counter;
    }

    public void removeClient(Integer clientId) {
        clients.remove(clientId);
        System.out.println(clients);
    }

    public void sendObject(ObjectToClientsModel objectModel) {
        objectModel.clientIds().forEach(id -> {
            objects.add(new ObjectToClient(objectModel.object(), id));
        });
    }

    public ObjectModel getObject(Integer clientId) {
        ObjectToClient objectToClient = null;
        for (ObjectToClient object : objects) {
            if (object.clientId().equals(clientId)) {
                objectToClient = object;
                break;
            }
        }

        if (objectToClient != null) {
            objects.remove(objectToClient);
            return new ObjectModel(objectToClient.object());
        } else return new ObjectModel("none");
    }

}
