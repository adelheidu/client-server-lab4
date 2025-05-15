package org.example.model;

import java.util.List;

public record ObjectToClientsModel(

        String object,

        List<Integer> clientIds

) {
}
