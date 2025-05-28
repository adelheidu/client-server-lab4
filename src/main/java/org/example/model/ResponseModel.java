package org.example.model;

public record ResponseModel(

        Integer sendToClient, // кому отправить объекты

        Integer sendFromClient // от кого получить объекты

) {
}
