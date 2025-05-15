package org.example.client;

import org.example.ApiPaths;
import org.example.model.ObjectModel;
import org.example.model.ObjectToClientsModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface APIService {

    @POST(ApiPaths.CLIENT)
    Call<Integer> addClient();

    @GET(ApiPaths.CLIENT)
    Call<List<Integer>> getClients();

    @DELETE(ApiPaths.CLIENT_ID)
    Call<Void> removeClient(@Path("clientId") Integer id);

    @POST(ApiPaths.OBJECT)
    Call<Void> sendObject(@Body ObjectToClientsModel object);

    @GET(ApiPaths.OBJECT_ID)
    Call<ObjectModel> getObject(@Path("clientId") Integer id);

}
