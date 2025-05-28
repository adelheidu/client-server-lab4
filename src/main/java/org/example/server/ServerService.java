package org.example.server;

import lombok.Getter;
import org.example.model.ObjectListModel;
import org.example.model.ObjectModel;
import org.example.model.ObjectToClientsModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ServerService {

    @Getter
    private List<Integer> clients;

    private Integer counter = 0;

    private List<ObjectToClient> objects;

    private HashMap<Integer, List<String>> allObjects;

    public ServerService() {
        clients = new ArrayList<>();
        objects = new ArrayList<>();
        allObjects = new HashMap<>();
    }

    public Integer addClient() {
        clients.add(++counter);
        System.out.println(clients);
        return counter;
    }

    public void removeClient(Integer clientId) {
        clients.remove(clientId);
        System.out.println("Clients: " + clients);
    }

    public void sendObject(ObjectToClientsModel objectModel) {
        objectModel.clientIds().forEach(id -> {
            objects.add(new ObjectToClient(objectModel.object(), id));
            List<String> list= allObjects.get(id);
            list.add(objectModel.object());
            allObjects.put(id, list);
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

    public void addObject(Integer clientId, String obj) {
        List<String> list;
        if (!allObjects.containsKey(clientId)) {
            list = new ArrayList<>();
        } else {
            list = allObjects.get(clientId);
        }
        list.add(obj);
        allObjects.put(clientId, list);
        System.out.println(allObjects);
    }

    public ObjectListModel getObjectList(Integer clientId) {
        return new ObjectListModel(allObjects.get(clientId));
    }

    public void deleteObjectList(Integer clientId) {
        allObjects.remove(clientId);
    }

}
