package namtdph08817.android.appdoctruyen.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import namtdph08817.android.appdoctruyen.fragment.AccountFragment;
import namtdph08817.android.appdoctruyen.fragment.FavoriteFragment;
import namtdph08817.android.appdoctruyen.fragment.HomeFragment;

public class AdapterViewpager extends FragmentStateAdapter {
    public AdapterViewpager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = HomeFragment.newInstance();
                break;
            case 1:
                fragment = FavoriteFragment.newInstance();
                break;
            case 2:
                fragment = AccountFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
