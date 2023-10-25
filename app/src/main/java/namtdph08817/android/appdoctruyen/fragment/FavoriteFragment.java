package namtdph08817.android.appdoctruyen.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import namtdph08817.android.appdoctruyen.ChiTietChuyen;
import namtdph08817.android.appdoctruyen.MainActivity;
import namtdph08817.android.appdoctruyen.R;
import namtdph08817.android.appdoctruyen.adapters.FavoriteAdapter;
import namtdph08817.android.appdoctruyen.adapters.TruyenAdapter;
import namtdph08817.android.appdoctruyen.mInterface.DataListener;
import namtdph08817.android.appdoctruyen.mInterface.Favorite_Interface;
import namtdph08817.android.appdoctruyen.models.FavoriteModel;
import namtdph08817.android.appdoctruyen.models.TruyenModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoriteFragment extends Fragment {
    private Favorite_Interface mInterface;
    private FavoriteAdapter adapter;
    private GridView gridView;
    private static ArrayList<TruyenModel> arrayList = new ArrayList<>();
    private static List<FavoriteModel> list;
    public static List<String> listTruyenID= new ArrayList<>();
    private DataListener dataListener;
    private ProgressBar progressBar;
    private ImageView img_refresh;

    public void setDataListener(DataListener listener) {
        this.dataListener = listener;
    }

    public FavoriteFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.id_grdV_Favorite);
        progressBar = view.findViewById(R.id.progress_yeuthich);
        img_refresh = view.findViewById(R.id.img_refresh_favorite);

        //click refresh trang
        progressBar.setVisibility(View.VISIBLE);
        img_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                loadData();
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), ""+arrayList.size(), Toast.LENGTH_SHORT).show();
            }
        });
        loadData();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("time","" +position+"  list :"+arrayList.size());
                TruyenModel model = arrayList.get(position);
                SharedPreferences preferences = getContext().getSharedPreferences("Truyen.txt", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("_id",model.get_id());
                editor.putString("nameTruyen",model.getNameTruyen());
                editor.putString("nameTacgia",model.getNameTacgia());
                editor.putInt("namXuatban",model.getNamXuatban());
                editor.putString("anhBia",model.getAnhBia());
                editor.putString("theLoai",model.getTheLoai());
                editor.putString("mota",model.getMota());
                editor.putBoolean("checked",true);
                editor.apply();
                startActivity(new Intent(getActivity(), ChiTietChuyen.class));
            }
        });
    }//end OncreatedView

    private void loadData(){
        arrayList.clear();
        SharedPreferences preferences = getContext().getSharedPreferences("User.txt", Context.MODE_PRIVATE);
        String idUser = preferences.getString("_id", "");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.mURL+"api/").addConverterFactory(GsonConverterFactory.create()).build();
        mInterface = retrofit.create(Favorite_Interface.class);
        Call<List<FavoriteModel>> call = mInterface.getTruyenyeuThich(idUser);
        call.enqueue(new Callback<List<FavoriteModel>>() {
            @Override
            public void onResponse(Call<List<FavoriteModel>> call, Response<List<FavoriteModel>> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    list = response.body();
                    for (FavoriteModel model : list){
                        listTruyenID.add(model.getTruyenModel().get_id());
                        TruyenModel truyenModel = new TruyenModel();
                        truyenModel.set_id(model.getTruyenModel().get_id());
                        truyenModel.setNameTruyen(model.getTruyenModel().getNameTruyen());
                        truyenModel.setNameTacgia(model.getTruyenModel().getNameTacgia());
                        truyenModel.setNamXuatban(model.getTruyenModel().getNamXuatban());
                        truyenModel.setAnhBia(model.getTruyenModel().getAnhBia());
                        truyenModel.setTheLoai(model.getTruyenModel().getTheLoai());
                        truyenModel.setMota(model.getTruyenModel().getMota());
                        arrayList.add(truyenModel);
                    }
                    adapter = new FavoriteAdapter(arrayList,getContext());
                    gridView.setAdapter(adapter);
                    Log.d("logd",listTruyenID.size()+"");
                }else {

                }
            }

            @Override
            public void onFailure(Call<List<FavoriteModel>> call, Throwable t) {

            }
        });
    }
    public void sendDataToListeners() {
        if (dataListener != null) {
            dataListener.onDataReceived(listTruyenID);
            Log.d("send", "da goi");
        }
    }
}