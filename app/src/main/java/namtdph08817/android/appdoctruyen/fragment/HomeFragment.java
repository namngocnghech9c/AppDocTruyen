package namtdph08817.android.appdoctruyen.fragment;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import namtdph08817.android.appdoctruyen.APIClass;
import namtdph08817.android.appdoctruyen.MainActivity;
import namtdph08817.android.appdoctruyen.R;
import namtdph08817.android.appdoctruyen.adapters.TruyenAdapter;
import namtdph08817.android.appdoctruyen.mInterface.Theloai_Interface;
import namtdph08817.android.appdoctruyen.mInterface.Truyen_interface;
import namtdph08817.android.appdoctruyen.mInterface.User_Interface;
import namtdph08817.android.appdoctruyen.models.TheLoaiModel;
import namtdph08817.android.appdoctruyen.models.TruyenModel;
import namtdph08817.android.appdoctruyen.models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private GridView Gdview;
    private TruyenAdapter adapter;
    private Retrofit retrofit;
    private Truyen_interface tInterface;
    private ProgressBar progressBar;
    private Spinner spinner;
    private Theloai_Interface theloai_interface;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Gdview = view.findViewById(R.id.grdV_Truyen);
        progressBar = view.findViewById(R.id.progress_truyen);
        spinner = view.findViewById(R.id.spinner_locTruyen);

        //-----Spinner
        //+Lay ds theloai
        progressBar.setVisibility(View.VISIBLE);
        retrofit = new Retrofit
                .Builder()
                .baseUrl(MainActivity.mURL + "api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theloai_interface = retrofit.create(Theloai_Interface.class);
        tInterface = retrofit.create(Truyen_interface.class);
        Call<List<TheLoaiModel>> callTL = theloai_interface.getAllTheLoai();

        callTL.enqueue(new Callback<List<TheLoaiModel>>() {
            @Override
            public void onResponse(Call<List<TheLoaiModel>> call, Response<List<TheLoaiModel>> response) {
                List<TheLoaiModel> list = response.body();
                List<String> theLoaiNames = new ArrayList<>();
                theLoaiNames.add("Tất cả");
                for (TheLoaiModel theLoaiModel : list) {
                    theLoaiNames.add(theLoaiModel.getTenLoai());
                }
                ArrayAdapter<String> Aadapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, theLoaiNames);
                spinner.setAdapter(Aadapter);
            }

            @Override
            public void onFailure(Call<List<TheLoaiModel>> call, Throwable t) {
                Log.e("failed load category!!!", t.toString());
            }
        });
        //chon spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tLoai = (String) parent.getItemAtPosition(position);
                if (tLoai.equals("Tất cả")) {
                    //neu chon tat ca, lay ds tat ca truyen
                    Call<List<TruyenModel>> call = tInterface.getAllTruyen();
                    call.enqueue(new Callback<List<TruyenModel>>() {
                        @Override
                        public void onResponse(Call<List<TruyenModel>> call, Response<List<TruyenModel>> response) {
                            List<TruyenModel> list = response.body();
                            adapter = new TruyenAdapter((ArrayList<TruyenModel>) list, getContext());
                            progressBar.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Gdview.setAdapter(adapter);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }, 500);
                        }
                        @Override
                        public void onFailure(Call<List<TruyenModel>> call, Throwable t) {
                            Log.e("failed !!!", t.toString());
                        }
                    });
                } else {
                    //neu chon khac tat ca, loc theo ten loai da chon
                    progressBar.setVisibility(View.VISIBLE);
                    Call<List<TruyenModel>> call2 = tInterface.getTruyenByTheLoai(tLoai);
                    call2.enqueue(new Callback<List<TruyenModel>>() {
                        @Override
                        public void onResponse(Call<List<TruyenModel>> call, Response<List<TruyenModel>> response) {
                            List<TruyenModel> list = response.body();
                            adapter = new TruyenAdapter((ArrayList<TruyenModel>) list, getContext());
                            Gdview.setAdapter(adapter);
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<List<TruyenModel>> call, Throwable t) {
                            Log.e("log ne", "loi lay ds truyen: " + t.toString());
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //lấy ds truyen


    }

}