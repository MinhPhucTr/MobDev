package vn.uit.project.APIComponent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.uit.project.AssetComponent.Asset;
import vn.uit.project.MapComponent.Map;

public interface ApiInterface {
    @GET("api/master/map")
    Call<Map> getMap();
    @GET("api/master/asset/{assetID}")
    Call<Asset> getAsset(@Path("assetID") String assetID);
    @GET("api/master/asset/user/current")
    Call<List<Asset>> getCurrentAsset();
}
