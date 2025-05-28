package org.example;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApiPaths {

    public static final String CLIENT = "/api/client";

    public static final String CLIENT_ID = "/api/client/{clientId}";

    public static final String OBJECT = "/api/object";

    public static final String OBJECT_ID = "/api/object/{clientId}";

    public static final String OBJECT_LIST_ID = "/api/objectList/{clientId}";

}
