package org.example.model;

import java.util.List;

public record RequestModel(

        Integer fromClientId,

        Integer toClientId,

        List<String> objects

) {
}
