package namtdph08817.android.appdoctruyen.mInterface;

import java.util.List;

import namtdph08817.android.appdoctruyen.models.TheLoaiModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Theloai_Interface {
    @GET("theloai")
    Call<List<TheLoaiModel>> getAllTheLoai();
}
