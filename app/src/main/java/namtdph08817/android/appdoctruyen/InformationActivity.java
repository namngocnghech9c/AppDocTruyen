package namtdph08817.android.appdoctruyen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import namtdph08817.android.appdoctruyen.mInterface.Image_Interface;
import namtdph08817.android.appdoctruyen.mInterface.User_Interface;
import namtdph08817.android.appdoctruyen.models.Image_Response;
import namtdph08817.android.appdoctruyen.models.UserModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InformationActivity extends AppCompatActivity {
    private ImageView imgAvatar,img_edit_pass;
    private EditText ed_fullname,ed_email,ed_sdt,ed_pass;
    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;
//    private Image_Interface imgInterface;
    private User_Interface user_interface;
    private UserModel model = new UserModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        anhxa();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("User.txt",MODE_PRIVATE);
        String id = preferences.getString("_id","");
        String username = preferences.getString("username","");
        String pass = preferences.getString("passwd","");
        String avatar = preferences.getString("avatar","");
        String fullname = preferences.getString("fullname","");
        String email = preferences.getString("email","");
        String sdt = preferences.getString("sdt","");
        int vaitro = preferences.getInt("vaitro",0);


        ed_fullname.setText(fullname);
        ed_email.setText(email);
        ed_sdt.setText(sdt);
        ed_pass.setText("aksfgfea");
        String url = APIClass.URL_1+"uploads/"+avatar;
        if (imgAvatar!=null) {
            try {
                Glide.with(getApplicationContext()).load(url).placeholder(R.drawable.logo).into(imgAvatar);
            } catch (Exception e) {
                Log.e("loi", e.toString());
            }
        }else {
            Log.i("img_avatar","null");
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIClass.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        user_interface = retrofit.create(User_Interface.class);

        img_edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(InformationActivity.this);
                dialog.setContentView(R.layout.dialog_sua_mk);
                EditText ed_newpass = dialog.findViewById(R.id.ed_new_pass);
                EditText ed_re_newpass = dialog.findViewById(R.id.ed_re_newpass);
                EditText ed_oldpass = dialog.findViewById(R.id.ed_old_pass);
                Button btn_ok = dialog.findViewById(R.id.btn_ok_dialog);
                Button btn_huy = dialog.findViewById(R.id.btn_cancel_dialog);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!ed_oldpass.getText().toString().equals(pass)){
                            Toast.makeText(InformationActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        }else if(ed_newpass.getText().toString().equals("") || ed_re_newpass.getText().toString().equals("")){
                            Toast.makeText(InformationActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                        }else {
                            if(ed_re_newpass.getText().toString().equals(ed_newpass.getText().toString())){
                                model.setUsername(username);
                                model.setVaitro(vaitro);
                                model.set_id(id);
                                model.setAvatar(avatar);
                                model.setPasswd(ed_newpass.getText().toString());
                                model.setSdt(sdt);
                                model.setFullname(fullname);
                                model.setEmail(email);
                                Call<UserModel> call = user_interface.putUser(id,model);
                                call.enqueue(new Callback<UserModel>() {
                                    @Override
                                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(InformationActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }else {
                                            Toast.makeText(InformationActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UserModel> call, Throwable t) {
                                        Log.e("failed change pass !!!",t.toString());
                                    }
                                });
                            }else {
                                Toast.makeText(InformationActivity.this, "Nhập lại mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        //avatar
//        OkHttpClient client = new OkHttpClient.Builder().build();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(APIClass.URL_1)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        imgInterface = retrofit.create(Image_Interface.class);
//        imgAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(InformationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                        == PackageManager.PERMISSION_GRANTED) {
//                    // The permission is granted, start the activity to pick an image
//                    selectImageFromDevice();
//                } else {
//                    // The permission is not granted, request the permission
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//                    }
//                }
//            }
//        });
    }

    private void anhxa() {
        imgAvatar = findViewById(R.id.img_avatar_info);
        img_edit_pass = findViewById(R.id.img_edit_password);
        ed_fullname = findViewById(R.id.ed_fullname_info);
        ed_email = findViewById(R.id.ed_email_info);
        ed_sdt = findViewById(R.id.ed_sdt_info);
        ed_pass = findViewById(R.id.ed_passwd_info);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
//            if (data != null && data.getData() != null) {
//                Uri selectedImageUri = data.getData();
//                // Xử lý hình ảnh đã chọn
//                File imageFile = new File(getRealPathFromUri(selectedImageUri));
//                uploadImage(imageFile);
//            }
//        }
//    }

//    private void uploadImage(File imageFile) {
//        // Tạo RequestBody từ file hình ảnh
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
//        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
//
//        // Gửi yêu cầu upload hình ảnh
//        Call<Image_Response> call = imgInterface.uploadImage(imagePart);
//        call.enqueue(new Callback<Image_Response>() {
//            @Override
//            public void onResponse(Call<Image_Response> call, Response<Image_Response> response) {
//                Image_Response imageResponse = response.body();
//                String imageUrl = imageResponse.getImgUrl();
//                Log.i("imgUrl 1", imageUrl);
//                if (response.isSuccessful()) {
//                    Toast.makeText(InformationActivity.this, "gui thanh cong", Toast.LENGTH_SHORT).show();
//                    imageResponse = response.body();
//                    if (imageResponse != null) {
//                        imageUrl = imageResponse.getImgUrl();
//                        Log.i("imgUrl", imageUrl);
//                        // Lưu đường dẫn ảnh vào MongoDB hoặc thực hiện các xử lý khác
//                    }
//                }else {
//                    Toast.makeText(InformationActivity.this, "gui k thanh cong"+response.errorBody(), Toast.LENGTH_SHORT).show();
//                    Log.i("err res", response.errorBody().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Image_Response> call, Throwable t) {
//                Log.e("failed !!!", t.toString());
//            }
//        });
//    }
//
//    private String getRealPathFromUri(Uri uri) {
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
//        if (cursor != null) {
//            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            String filePath = cursor.getString(columnIndex);
//            cursor.close();
//            return filePath;
//        } else {
//            return uri.getPath();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, start the activity to pick an image
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
//            } else {
//                // Permission denied, show a message to the user
//                Toast.makeText(InformationActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//    private void selectImageFromDevice() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
//        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//    }
    

}