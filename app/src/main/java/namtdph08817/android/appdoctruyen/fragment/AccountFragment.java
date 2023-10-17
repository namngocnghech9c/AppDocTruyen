package namtdph08817.android.appdoctruyen.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import namtdph08817.android.appdoctruyen.InformationActivity;
import namtdph08817.android.appdoctruyen.LoginActivity;
import namtdph08817.android.appdoctruyen.MainActivity;
import namtdph08817.android.appdoctruyen.R;


public class AccountFragment extends Fragment {
    private Button btn_info,btn_logout;
    private TextView tv_name;
    private ImageView img_avatar;
    public AccountFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_info = view.findViewById(R.id.btn_info);
        btn_logout = view.findViewById(R.id.btn_logout);
        tv_name = view.findViewById(R.id.tv_nameuser_account);
        img_avatar = view.findViewById(R.id.img_avatar);

        //load avatar
        SharedPreferences preferences = getContext().getSharedPreferences("User.txt", Context.MODE_PRIVATE);
        String avatar = preferences.getString("avatar","");
        String url = MainActivity.mURL+"uploads/"+avatar;
        Log.i("url",url);
        if (img_avatar!=null) {
            try {
                Glide.with(getContext()).load(url).placeholder(R.drawable.logo).into(img_avatar);
            } catch (Exception e) {
                Log.e("loi", e.toString());
            }
        }else {
            Log.i("img_avatar","null");
        }

        //get fullname
        String fullname = preferences.getString("fullname","");
        Log.i("fullname",fullname);
        tv_name.setText(fullname);

        //chuyen trang infor
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InformationActivity.class));
            }
        });
        // dang xuat
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
    }
}