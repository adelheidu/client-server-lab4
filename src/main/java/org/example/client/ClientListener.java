package org.example.client;

import org.example.objects.GraphicObject;

import java.util.List;

public interface ClientListener {

    List<GraphicObject> getObjectList();
    void closeButtonAction();
    void addButtonAction();
    void clearButtonAction();

    void sendButtonAction(Integer client, GraphicObject object);

    void sendAllButtonAction(List<Integer> clients, GraphicObject object);

}
