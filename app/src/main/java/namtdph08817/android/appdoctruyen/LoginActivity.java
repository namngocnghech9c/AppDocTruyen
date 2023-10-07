package namtdph08817.android.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import namtdph08817.android.appdoctruyen.models.UserModel;

public class LoginActivity extends AppCompatActivity {
    private EditText ed_username, ed_pass;
    private Button btnLogin;
    private CheckBox chkRemember;
    private TextView tv_register;
    private boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        checkRemember();

        //lấy dữ liệu user
        ArrayList<UserModel> arrayList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(APIClass.URL + "users", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsArray = response.getJSONArray("data");
                    for (int i = 0; i < jsArray.length(); i++) {
                        JSONObject object = jsArray.getJSONObject(i);
                        String username = object.getString("username");
                        String passwd = object.getString("passwd");
                        String fullname = object.getString("fullname");
                        String sdt = object.getString("sdt");
                        String email = object.getString("email");
                        String id = object.getString("_id");
                        int vaitro = object.getInt("vaitro");
                        String avatar = object.getString("avatar");
                        arrayList.add(new UserModel(id, username, passwd, email, fullname, sdt, vaitro, avatar));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Failed getting user", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Failed get user", error.toString());
            }
        });
        requestQueue.add(objectRequest);


        //click btn login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_username.getText().length() == 0 || ed_pass.getText().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Không được để trống username và password", Toast.LENGTH_SHORT).show();
                } else {
                    String user = "", pass = "";
                    for (int i = 0; i < arrayList.size(); i++) {
                        UserModel model = arrayList.get(i);

                        if (ed_username.getText().toString().equals(model.getUsername()) && ed_pass.getText().toString().equals(model.getPasswd())) {
                            user = model.getUsername();
                            pass = model.getPasswd();
                        }
                    }

                    if (ed_username.getText().toString().equals(user) && ed_pass.getText().toString().equals(pass)) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        SharedPreferences preferences = getSharedPreferences("User.txt",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        if (chkRemember.isChecked()){
                            editor.putString("username",user);
                            editor.putString("passwd",pass);
                            editor.putBoolean("check_rmb",true);

                        }else {
                            editor.putBoolean("check_rmb",false);
                        }
                        editor.apply();

                    } else {
                        Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public void anhxa() {
        ed_username = findViewById(R.id.ed_username);
        ed_pass = findViewById(R.id.ed_passwd);
        chkRemember = findViewById(R.id.checkBox);
        btnLogin = findViewById(R.id.btn_login);

        tv_register = findViewById(R.id.tv_register);
    }

    void checkRemember(){
        SharedPreferences preferences = getSharedPreferences("User.txt",Context.MODE_PRIVATE);
        check = preferences.getBoolean("check_rmb",true);
        if (check){
            String u = preferences.getString("username","");
            String p = preferences.getString("passwd","");
            ed_username.setText(u);
            ed_pass.setText(p);
            chkRemember.setChecked(true);
        }else {
            ed_username.setText("");
            ed_pass.setText("");
            chkRemember.setChecked(false);
        }
    }
}