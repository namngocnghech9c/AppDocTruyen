package namtdph08817.android.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NoiDungTruyenActivity extends AppCompatActivity {
    private TextView tv_tenchap,tv_noiDung;
    private Button btn_BackTop,btn_NextTop,btn_BackBottom,btn_NextBottom;
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

        Intent intent = getIntent();
        String noiDung = intent.getStringExtra("noiDung");
        String tenChap = intent.getStringExtra("tenChap");
        tv_noiDung.setText(noiDung);
        tv_tenchap.setText(tenChap);

        btn_NextTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}