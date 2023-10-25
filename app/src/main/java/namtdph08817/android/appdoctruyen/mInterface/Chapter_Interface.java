package namtdph08817.android.appdoctruyen.mInterface;

import java.util.List;

import namtdph08817.android.appdoctruyen.models.ChapterModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Chapter_Interface {
    @GET("chapters/{idTruyen}")
    Call<List<ChapterModel>> getAllChapterByIdTruyen(@Path("idTruyen") String idTruyen);
    @GET("chapters/getall/{id}")
    Call<ChapterModel> get1Chapter(@Path("id") String id);
}
