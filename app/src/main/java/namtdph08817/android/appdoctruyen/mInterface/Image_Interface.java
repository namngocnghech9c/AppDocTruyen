package namtdph08817.android.appdoctruyen.mInterface;

import namtdph08817.android.appdoctruyen.models.Image_Response;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Image_Interface {
    @Multipart
    @POST("uploads")
    Call<Image_Response> uploadImage(@Part MultipartBody.Part image);
}
