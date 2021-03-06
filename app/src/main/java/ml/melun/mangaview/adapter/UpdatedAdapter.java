package ml.melun.mangaview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ml.melun.mangaview.Preference;
import ml.melun.mangaview.R;
import ml.melun.mangaview.mangaview.Manga;

public class UpdatedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Manga> mData;
    onclickListener olisten;
    Boolean save;
    Boolean dark;
    private LayoutInflater mInflater;

    public UpdatedAdapter(Context main) {
        super();
        context = main;
        mData = new ArrayList<>();
        save = new Preference().getDataSave();
        dark = new Preference().getDarkTheme();
        this.mInflater = LayoutInflater.from(main);
    }

    public void addData(ArrayList<Manga> data){
        int oSize = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(oSize,data.size());
    }

    public void setOnClickListener(onclickListener click){
        olisten = click;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_title, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        viewHolder h = (viewHolder) holder;
        Manga m = mData.get(position);
        h.text.setText(m.getName());
        if(m.getThumb().length()>1 && !save) Glide.with(context).load(m.getThumb()).into(h.thumb);
        else h.thumb.setImageBitmap(null);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView text;
        ImageView thumb;
        CardView card;
        public viewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.Title);
            card = itemView.findViewById(R.id.titleCard);
            thumb = itemView.findViewById(R.id.Thumb);
            if(dark){
                card.setBackgroundColor(ContextCompat.getColor(context, R.color.colorDarkBackground));
            }
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    olisten.onclick(mData.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface onclickListener {void onclick(Manga m);}
}
