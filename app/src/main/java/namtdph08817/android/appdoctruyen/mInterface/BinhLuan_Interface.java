package namtdph08817.android.appdoctruyen.mInterface;

import java.util.List;

import namtdph08817.android.appdoctruyen.models.BinhLuanModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BinhLuan_Interface {
    @GET("comments/{idTruyen}")
    Call<List<BinhLuanModel>> getAllByIdTruyen(@Path("idTruyen") String idTruyen);
    @POST("comments")
    Call<BinhLuanModel> postBinhLuan(@Body BinhLuanModel data);
}
