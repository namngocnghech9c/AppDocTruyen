package namtdph08817.android.appdoctruyen.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import namtdph08817.android.appdoctruyen.R;
import namtdph08817.android.appdoctruyen.mInterface.Chapter_Interface;
import namtdph08817.android.appdoctruyen.mInterface.Next_Interface;
import namtdph08817.android.appdoctruyen.models.ChapterModel;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.CTViewHolder> {
    private Context context;
    private ArrayList<ChapterModel> arrayList;
    private Next_Interface mInterface;

    public ChaptersAdapter(Context context,Next_Interface mInterface) {
        this.context = context;
        this.mInterface = mInterface;
    }
    public void setData(ArrayList<ChapterModel> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
    public void setChapterClickListener(Next_Interface mInterface) {
        this.mInterface = mInterface;
    }
    @NonNull
    @Override
    public ChaptersAdapter.CTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter,parent,false);
        return new CTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChaptersAdapter.CTViewHolder holder, int position) {
        ChapterModel model = arrayList.get(position);
        holder.tv_tenChap.setText(model.getTenChap());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterface != null) {
                    mInterface.nextActivity(model);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrayList.size() != 0){
            return arrayList.size();
        }
        return 0;
    }

    public class CTViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_tenChap;
        public CTViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenChap = itemView.findViewById(R.id.tv_item_tenChapter);
        }
    }
}
