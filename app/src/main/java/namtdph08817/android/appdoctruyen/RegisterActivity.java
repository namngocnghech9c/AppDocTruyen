package namtdph08817.android.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import namtdph08817.android.appdoctruyen.mInterface.User_Interface;
import namtdph08817.android.appdoctruyen.models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private EditText ed_ten, ed_pass, ed_email, ed_repass, ed_fullname, ed_sdt;
    private Retrofit retrofit;
    private User_Interface interfaceU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhxa();

        //
        retrofit = new Retrofit
                .Builder()
                .baseUrl(APIClass.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaceU = retrofit.create(User_Interface.class);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_ten.getText().toString().isEmpty() || ed_pass.getText().toString().isEmpty() || ed_fullname.getText().toString().isEmpty()
                        || ed_email.getText().toString().isEmpty() || ed_sdt.getText().toString().isEmpty() || ed_repass.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }else {
                    if (ed_repass.getText().toString().equals(ed_pass.getText().toString())){
                        UserModel user = new UserModel();
                        user.setFullname(ed_fullname.getText().toString());
                        user.setUsername(ed_ten.getText().toString());
                        user.setPasswd(ed_pass.getText().toString());
                        user.setEmail(ed_email.getText().toString());
                        user.setSdt(ed_sdt.getText().toString());
                        user.setVaitro(1);
                        user.setAvatar("avatar.jpg");
                        Call<UserModel> call = interfaceU.postUser(user);
                        call.enqueue(new Callback<UserModel>() {
                            @Override
                            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                                Log.i("register : ",""+response);
                            }

                            @Override
                            public void onFailure(Call<UserModel> call, Throwable t) {
                                Log.e("loi ne : ",t.toString());
                            }
                        });
                    }else {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void anhxa() {
        ed_ten = findViewById(R.id.ed_username_register);
        ed_fullname = findViewById(R.id.ed_fullname_register);
        ed_pass = findViewById(R.id.ed_passwd_register);
        ed_email = findViewById(R.id.ed_email_register);
        ed_repass = findViewById(R.id.ed_Re_passwd_register);
        ed_sdt = findViewById(R.id.ed_sdt_register);
        register = findViewById(R.id.btn_register);
    }
}
