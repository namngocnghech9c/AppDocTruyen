package namtdph08817.android.appdoctruyen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import namtdph08817.android.appdoctruyen.R;
import namtdph08817.android.appdoctruyen.fragment.HomeFragment;
import namtdph08817.android.appdoctruyen.models.BinhLuanModel;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.BinhLuanHolder> {
    private ArrayList<BinhLuanModel> arrayList;
    private Context context;

    public BinhLuanAdapter(Context context) {
        this.context = context;
    }
    public void setData( ArrayList<BinhLuanModel> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BinhLuanAdapter.BinhLuanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false);
        return new BinhLuanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BinhLuanAdapter.BinhLuanHolder holder, int position) {
        BinhLuanModel model = arrayList.get(position);
        holder.tv_time.setText(model.getDate());
        holder.tv_name.setText(model.getFullname());
        holder.tv_noiDung.setText(model.getNoidung());
        holder.img_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList.size() != 0){
            return arrayList.size();
        }
        return 0;
    }

    public class BinhLuanHolder extends RecyclerView.ViewHolder {
        private TextView tv_noiDung,tv_name, tv_time;
        private ImageView img_option;
        public BinhLuanHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_fullname_item);
            tv_noiDung = itemView.findViewById(R.id.tv_noidungcmt_item);
            tv_time = itemView.findViewById(R.id.tv_time_item);
            img_option = itemView.findViewById(R.id.img_options_cmt);
        }
    }
}
