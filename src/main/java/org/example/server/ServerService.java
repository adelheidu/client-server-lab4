package org.example.server;

import lombok.Getter;
import org.example.model.ObjectListModel;
import org.example.model.ObjectModel;
import org.example.model.ObjectToClientsModel;
import org.example.model.RequestModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ServerService {

    @Getter
    private List<Integer> clients;

    private Integer counter = 0;

    private List<ObjectToClient> objects;

    private List<RequestModel> rowRequests;

    private List<RequestModel> readyRequests;

    private List<Integer> requestsIds;

    public ServerService() {
        clients = new ArrayList<>();
        objects = new ArrayList<>();
        rowRequests = new ArrayList<>();
        readyRequests = new ArrayList<>();
        requestsIds = new ArrayList<>();
    }

    public Integer addClient() {
        clients.add(++counter);
        return counter;
    }

    public void removeClient(Integer clientId) {
        clients.remove(clientId);
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

    public void addRequest(RequestModel requestModel) {
        rowRequests.add(requestModel);
        System.out.println(rowRequests);
    }

    public void checkRequest(Integer clientId, ObjectListModel objectListModel) {
        for (RequestModel request : rowRequests) {
            if (Objects.equals(request.fromClientId(), clientId)) {
                request.objects().addAll(objectListModel.objects());
                readyRequests.add(request);
                rowRequests.remove(request);
                break;
            }
        }
        System.out.println(rowRequests);
        System.out.println(readyRequests);
    }

    public ObjectListModel getAllObjects(Integer clientId) {
        for (RequestModel request : readyRequests) {
            if (Objects.equals(request.toClientId(), clientId)) {
                List<String> list = new ArrayList<>(request.objects());
                readyRequests.remove(request);
                return new ObjectListModel(list);
            }
        }
        return new ObjectListModel(null);
    }

}
