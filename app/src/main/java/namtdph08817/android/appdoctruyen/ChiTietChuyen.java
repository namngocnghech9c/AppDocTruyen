package namtdph08817.android.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namtdph08817.android.appdoctruyen.adapters.BinhLuanAdapter;
import namtdph08817.android.appdoctruyen.adapters.ChaptersAdapter;
import namtdph08817.android.appdoctruyen.fragment.FavoriteFragment;
import namtdph08817.android.appdoctruyen.mInterface.BinhLuan_Interface;
import namtdph08817.android.appdoctruyen.mInterface.Chapter_Interface;
import namtdph08817.android.appdoctruyen.mInterface.DataListener;
import namtdph08817.android.appdoctruyen.mInterface.Favorite_Interface;
import namtdph08817.android.appdoctruyen.mInterface.Next_Interface;
import namtdph08817.android.appdoctruyen.models.BinhLuanModel;
import namtdph08817.android.appdoctruyen.models.ChapterID;
import namtdph08817.android.appdoctruyen.models.ChapterModel;
import namtdph08817.android.appdoctruyen.models.FavoriteModel;
import namtdph08817.android.appdoctruyen.models.TruyenModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChiTietChuyen extends AppCompatActivity implements DataListener {
    private ChaptersAdapter chaptersAdapter;
    private BinhLuanAdapter cmtAdapter;
    private Chapter_Interface chapInterface;
    private BinhLuan_Interface cmtInterface;
    private Favorite_Interface ytInterface;
    private TextView tv_nameTruyen, tv_nameTacgia, tv_theloai, tv_mota, tv_namXB;
    private RecyclerView cmtRecyclerView, chapRecyclerView;
    private ImageView img_anhBia, img_send;
    private EditText ed_cmt;
    private ToggleButton btn_toggle;
    private String idTruyen;
    private boolean check = false;
    public static ArrayList<ChapterID> arrListID = new ArrayList<>();
    private static List<String> listTruyenID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_chuyen);
        anhxa();

        //
        FavoriteFragment fragment = FavoriteFragment.newInstance();
        fragment.setDataListener(this);
        fragment.sendDataToListeners();

        //lay thoi gian hien tai
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String time = sdf.format(new Date());

        //lay du lieu truyen
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Truyen.txt", Context.MODE_PRIVATE);
        idTruyen = preferences.getString("_id", "");
        String nameTruyen = preferences.getString("nameTruyen", "");
        String nameTacgia = preferences.getString("nameTacgia", "");
        int namXuatban = preferences.getInt("namXuatban", 0);
        String anhBia = preferences.getString("anhBia", "");
        String theLoai = preferences.getString("theLoai", "");
        String mota = preferences.getString("mota", "");
        TruyenModel truyenModel = new TruyenModel();
        truyenModel.set_id(idTruyen);
        truyenModel.setNameTruyen(nameTruyen);
        truyenModel.setNameTacgia(nameTacgia);
        truyenModel.setNamXuatban(namXuatban);
        truyenModel.setAnhBia(anhBia);
        truyenModel.setTheLoai(theLoai);
        truyenModel.setMota(mota);
        //set du lieu tv
        tv_nameTruyen.setText(nameTruyen);
        tv_nameTacgia.setText("Tên tác giả : " + nameTacgia);
        tv_theloai.setText("Thể loại : " + theLoai);
        tv_namXB.setText("Năm xuất bản : " + namXuatban);
        tv_mota.setText("Mô tả : " + mota);
        //set anh
        Glide.with(this).load(APIClass.URL_1 + "uploads/" + anhBia).placeholder(R.drawable.logo).into(img_anhBia);


        //retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIClass.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chapInterface = retrofit.create(Chapter_Interface.class);
        cmtInterface = retrofit.create(BinhLuan_Interface.class);
        ytInterface = retrofit.create(Favorite_Interface.class);
        //load ds chapter
        Call<List<ChapterModel>> call = chapInterface.getAllChapterByIdTruyen(idTruyen);
        call.enqueue(new Callback<List<ChapterModel>>() {
            @Override
            public void onResponse(Call<List<ChapterModel>> call, Response<List<ChapterModel>> response) {
                List<ChapterModel> list = response.body();
                chaptersAdapter = new ChaptersAdapter(getApplicationContext(), new Next_Interface() {
                    @Override
                    public void nextActivity() {
                        startActivity(new Intent(ChiTietChuyen.this, NoiDungTruyenActivity.class));
                    }
                });
                chaptersAdapter.setData((ArrayList<ChapterModel>) list);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.VERTICAL, false);
                chapRecyclerView.setLayoutManager(manager);
                chapRecyclerView.setAdapter(chaptersAdapter);
                int nextID = 1;
                for (ChapterModel chapterModel : list) {
                    arrListID.add(new ChapterID(nextID++, chapterModel.get_id()));
                }
            }

            @Override
            public void onFailure(Call<List<ChapterModel>> call, Throwable t) {

            }
        });

        //load ds cmt
        Call<List<BinhLuanModel>> call1 = cmtInterface.getAllByIdTruyen(idTruyen);
        call1.enqueue(new Callback<List<BinhLuanModel>>() {
            @Override
            public void onResponse(Call<List<BinhLuanModel>> call, Response<List<BinhLuanModel>> response) {
                List<BinhLuanModel> list = response.body();
                cmtAdapter = new BinhLuanAdapter(getApplicationContext());
                cmtAdapter.setData((ArrayList<BinhLuanModel>) list);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.VERTICAL, false);
                cmtRecyclerView.setLayoutManager(manager);
                cmtRecyclerView.setAdapter(cmtAdapter);
            }

            @Override
            public void onFailure(Call<List<BinhLuanModel>> call, Throwable t) {

            }
        });
        SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("User.txt", MODE_PRIVATE);
        String idUser = preferences1.getString("_id", "");
        String fullname = preferences1.getString("fullname", "");
        //click send
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BinhLuanModel model = new BinhLuanModel();
                model.setIdTruyen(idTruyen);
                model.setIdUser(idUser);
                model.setDate(time);
                model.setFullname(fullname);
                model.setNoidung(ed_cmt.getText().toString());
                Call<BinhLuanModel> call2 = cmtInterface.postBinhLuan(model);
                call2.enqueue(new Callback<BinhLuanModel>() {
                    @Override
                    public void onResponse(Call<BinhLuanModel> call, Response<BinhLuanModel> response) {
                        Toast.makeText(ChiTietChuyen.this, "Thêm bình luận thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<BinhLuanModel> call, Throwable t) {
                        Toast.makeText(ChiTietChuyen.this, "add cmt failed !" + t, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //thêm vào favorite
        //lay listIDTruyenYeuthich
        for (String item : listTruyenID) {
            if (item.equals(idTruyen)) {
                check = true;
                Log.e("DataReceived", item);
                break;
            } else {
                check = false;
            }
        }
        if (check) {
            btn_toggle.setChecked(true);
        } else {
            btn_toggle.setChecked(false);
        }
        SharedPreferences.Editor editor = preferences.edit();
        btn_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btn_toggle.setBackgroundResource(R.drawable.icon_favorite_on);
                    FavoriteModel model = new FavoriteModel();
                    model.setIdUser(idUser);
                    model.setTruyenModel(truyenModel);
                    Toast.makeText(ChiTietChuyen.this, idTruyen, Toast.LENGTH_SHORT).show();
                    Call<Void> call3 = ytInterface.postYeuThich(model);
                    call3.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                // Xử lý khi thành công
                                check = true;
                                Toast.makeText(ChiTietChuyen.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                            } else {
                                // Xử lý khi có lỗi
                                Toast.makeText(ChiTietChuyen.this, "Thêm vào danh sách yêu thích không thành công", Toast.LENGTH_SHORT).show();
                                Log.e("yeuthichfailed", "loi");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });

                } else {
                    btn_toggle.setBackgroundResource(R.drawable.icon_favorite_off);
                    Call<Void> call4 = ytInterface.deleteYeuThich(idTruyen);
                    call4.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(ChiTietChuyen.this, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                    editor.putBoolean("checked", false);
                }
            }
        });

        editor.apply();
    }

    private void anhxa() {
        tv_nameTruyen = findViewById(R.id.tv_tenTruyen_ct);
        tv_nameTacgia = findViewById(R.id.tv_tenTacGia_ct);
        tv_theloai = findViewById(R.id.tv_theloai_ct);
        tv_namXB = findViewById(R.id.tv_namxuatban_ct);
        tv_mota = findViewById(R.id.tv_mota_ct);
        img_anhBia = findViewById(R.id.img_anhbia_ct);
        img_send = findViewById(R.id.img_send_cmt);
        ed_cmt = findViewById(R.id.ed_binhluan_ct);
        btn_toggle = findViewById(R.id.btn_toggle);
        cmtRecyclerView = findViewById(R.id.id_recyclerView_binhluan);
        chapRecyclerView = findViewById(R.id.id_recyclerView_chapters);
    }


    @Override
    public void onDataReceived(List<String> stringList) {
        if (stringList != null && !stringList.isEmpty()) {
            listTruyenID = stringList;
        } else {
            Log.e("DataReceived", "Empty list");
        }
    }
}