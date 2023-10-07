package namtdph08817.android.appdoctruyen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import namtdph08817.android.appdoctruyen.APIClass;
import namtdph08817.android.appdoctruyen.MainActivity;
import namtdph08817.android.appdoctruyen.R;
import namtdph08817.android.appdoctruyen.models.TruyenModel;

public class TruyenAdapter extends BaseAdapter {
    private ArrayList<TruyenModel> list;
    private Context context;
    private LifecycleOwner lifecycleOwner;

    public TruyenAdapter(ArrayList<TruyenModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TruyenModel model = list.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_truyen, parent, false);
        }

        TextView tvname = (TextView) convertView.findViewById(R.id.item_tv_nametruyen);
        tvname.setText(model.getNameTruyen());

        ImageView img = convertView.findViewById(R.id.item_img_anhbia_truyen);
        String url = MainActivity.mURL+ "uploads/"+model.getAnhBia();
        Glide.with(context).load(url).placeholder(R.drawable.logo).into(img);

        return convertView;
    }
}
