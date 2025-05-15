package org.example.client;

import okhttp3.OkHttpClient;
import org.example.model.ObjectToClientsModel;
import org.example.objects.GraphicObject;
import org.example.objects.ObjectList;
import org.example.objects.Serializer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client implements ClientListener {

    private ObjectList objectList;
    private Frame frame;

    private Serializer serializer;

    private static final String BASE_URL = "http://localhost:4567";
    private final APIService service;
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private Integer clientId;

    public static void main(String[] args) {
        Client client = new Client();
    }

    public Client() {
        objectList = new ObjectList();
        serializer = new Serializer();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        service = retrofit.create(APIService.class);

        try {
            clientId = service.addClient().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        frame = new Frame(this, clientId);
        startPolling();
    }

    public void startPolling() {
        executor.scheduleAtFixedRate(this::poll, 0, 1, TimeUnit.SECONDS);
    }

    private void poll() {
        try {
            List<Integer> clients = service.getClients().execute().body();
            clients.remove(clientId);
            frame.updateList(clients);

            String object = service.getObject(clientId).execute().body().object();
            if (!object.equals("none")) {
                objectList.add(serializer.deserializeXMLObject(object));
                frame.repaintDrawingPanel();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<GraphicObject> getObjectList() {
        return objectList.getObjects();
    }

    @Override
    public void sendButtonAction(Integer client, GraphicObject object) {
        try {
            List<Integer> clientList = new ArrayList<>();
            clientList.add(client);
            service.sendObject(new ObjectToClientsModel(serializer.serializeXMLObject(object), clientList)).execute();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void sendAllButtonAction(List<Integer> clients, GraphicObject object) {
        try {
            service.sendObject(new ObjectToClientsModel(serializer.serializeXMLObject(object), clients)).execute();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void closeButtonAction() {
        try {
            service.removeClient(clientId).execute();
            frame.dispose();
            System.exit(0);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void addButtonAction() {
        objectList.generateFigure();
        frame.repaintDrawingPanel();
    }

    @Override
    public void clearButtonAction() {
        objectList.clear();
        frame.repaintDrawingPanel();
    }

}
