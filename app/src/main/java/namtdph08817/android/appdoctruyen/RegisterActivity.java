package namtdph08817.android.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    EditText ed_ten, ed_pass, ed_email, ed_repass, ed_fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhxa();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.10.72:3000/users/register";

                String ten = ed_ten.getText().toString();
                String full = ed_fullname.getText().toString();
                String email = ed_email.getText().toString();
                String repass = ed_repass.getText().toString();
                String pass = ed_pass.getText().toString();

                if (ten.isEmpty() || full.isEmpty() || email.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "không được để trống", Toast.LENGTH_SHORT).show();
                }


                if (ten.length() < 5) {
                    Toast.makeText(RegisterActivity.this, "tên tài khoản >5", Toast.LENGTH_SHORT).show();
                } else {


                    JSONObject object = new JSONObject();
                    try {
                        object.put("username", ten);
                        object.put("pass", pass);
                        object.put("email", email);
                        object.put("full", full);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Xử lý lỗi (nếu có)
                                }
                            }

                    );
                    requestQueue.add(request);
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

        register = findViewById(R.id.btn_register);
    }
}
