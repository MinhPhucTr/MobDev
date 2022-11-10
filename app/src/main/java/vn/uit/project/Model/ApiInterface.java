package vn.uit.project.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.uit.project.MapComponent.Map;

public interface ApiInterface {
    @GET("api/master/map")
    Call<Map> getMap();
}
