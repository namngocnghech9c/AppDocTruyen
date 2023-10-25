package namtdph08817.android.appdoctruyen.mInterface;

import namtdph08817.android.appdoctruyen.models.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface User_Interface {
    @POST("users")
    Call<UserModel> postUser(@Body UserModel data);
    @PUT("users/{id}")
    Call<UserModel> putUser(@Path("id") String id, @Body UserModel data);
}
