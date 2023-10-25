package namtdph08817.android.appdoctruyen.mInterface;

import java.util.List;

import namtdph08817.android.appdoctruyen.models.FavoriteModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Favorite_Interface {
    @POST("truyen/yeuthich")
    Call<Void> postYeuThich(@Body FavoriteModel data);
    @GET("truyen/yeuthich/{idUser}")
    Call<List<FavoriteModel>> getTruyenyeuThich(@Path("idUser") String idUser);
    @DELETE("truyen/yeuthich/{idTruyen}")
    Call<Void> deleteYeuThich(@Path("idTruyen") String idTruyen);
}
