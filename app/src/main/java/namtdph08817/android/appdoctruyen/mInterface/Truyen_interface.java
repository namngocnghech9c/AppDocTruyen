package namtdph08817.android.appdoctruyen.mInterface;

import java.util.List;

import namtdph08817.android.appdoctruyen.models.TruyenModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Truyen_interface {
    @GET("truyen")
    Call<List<TruyenModel>> getAllTruyen();
    @GET("truyen/theloai/{theLoai}")
    Call<List<TruyenModel>> getTruyenByTheLoai(@Path("theLoai") String theLoai);
}
