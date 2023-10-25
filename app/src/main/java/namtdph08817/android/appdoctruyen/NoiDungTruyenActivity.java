package namtdph08817.android.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import namtdph08817.android.appdoctruyen.mInterface.Chapter_Interface;
import namtdph08817.android.appdoctruyen.models.ChapterID;
import namtdph08817.android.appdoctruyen.models.ChapterModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoiDungTruyenActivity extends AppCompatActivity {
    private TextView tv_tenchap, tv_noiDung;
    private Button btn_BackTop, btn_NextTop, btn_BackBottom, btn_NextBottom;
    private Chapter_Interface mInterface;
    private ArrayList<ChapterID> arrayList = ChiTietChuyen.arrListID;
    private String noidung = "",tenchap = "";
    private int vitri;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung_truyen);
        tv_tenchap = findViewById(R.id.tv_tenChuong);
        tv_noiDung = findViewById(R.id.tv_noidungTruyen);
        btn_BackTop = findViewById(R.id.btn_back_top);
        btn_NextTop = findViewById(R.id.btn_next_top);
        btn_BackBottom = findViewById(R.id.btn_back_bottom);
        btn_NextBottom = findViewById(R.id.btn_next_bottom);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Chap.txt", MODE_PRIVATE);
        vitri = preferences.getInt("position", 1);
        loadChapter(vitri);

        btn_NextTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vitri < arrayList.size() -1){
                    vitri++;
                    loadChapter(vitri);
                }else {
                    Toast.makeText(NoiDungTruyenActivity.this, "Không có chương tiếp theo", Toast.LENGTH_SHORT).show();
                    //xu ly vo hieu hoa nut button
                }
            }
        });
        btn_NextBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vitri < arrayList.size() -1 ){
                    vitri++;
                    loadChapter(vitri);
                }else {
                    Toast.makeText(NoiDungTruyenActivity.this, "Không có chương tiếp theo", Toast.LENGTH_SHORT).show();
                    //xu ly vo hieu hoa nut button
                }
            }
        });
        btn_BackTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vitri > 0){
                    vitri--;
                    loadChapter(vitri);
                }else {
                    Toast.makeText(NoiDungTruyenActivity.this, "Bạn đang ở chương đầu tiên", Toast.LENGTH_SHORT).show();
                    //xu ly vo hieu hoa nut button
                }
            }
        });
        btn_BackBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vitri > 0){
                    vitri--;
                    loadChapter(vitri);
                }else {
                    Toast.makeText(NoiDungTruyenActivity.this, "Bạn đang ở chương đầu tiên", Toast.LENGTH_SHORT).show();
                    //xu ly vo hieu hoa nut button
                }
            }
        });
    }


    private void loadChapter(int position) {
        ChapterID model = arrayList.get(position);
        String id = model.getIdChap();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIClass.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mInterface = retrofit.create(Chapter_Interface.class);
        Call<ChapterModel> call = mInterface.get1Chapter(id);
        call.enqueue(new Callback<ChapterModel>() {
            @Override
            public void onResponse(Call<ChapterModel> call, Response<ChapterModel> response) {
                if (response.isSuccessful()) {
                    ChapterModel chapterModel = response.body();
                    noidung = chapterModel.getNoiDung();
                    tenchap = chapterModel.getTenChap();
                    tv_noiDung.setText(noidung);
                    tv_tenchap.setText(tenchap);
                } else {
                    // Xử lý trường hợp không thành công
                    Log.e("failed get chapter","loi o day");
                }
            }

            @Override
            public void onFailure(Call<ChapterModel> call, Throwable t) {
                Log.e("failed get chapter",t.toString());
            }
        });

    }
}