package namtdph08817.android.appdoctruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import namtdph08817.android.appdoctruyen.adapters.AdapterViewpager;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private AdapterViewpager adapter;
    public static final String mURL = APIClass.URL_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.id_viewpager2);
        tabLayout = findViewById(R.id.id_tablayout);

        adapter = new AdapterViewpager(this);
        viewPager2.setAdapter(adapter);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Home");
                        break;
                    case 1:
                        tab.setText("Favorite");
                        break;
                    case 2:
                        tab.setText("Account");
                        break;
                }
            }
        });
        mediator.attach();
        viewPager2.setUserInputEnabled(false);
    }
}