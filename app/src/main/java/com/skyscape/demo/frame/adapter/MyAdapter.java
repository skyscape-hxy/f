package com.skyscape.demo.frame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;

import com.skyscape.demo.frame.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<DocumentFile> mDataList;

    public MyAdapter(Context mContext, List<DocumentFile> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }
    OnclickLinstener onclickLinstener;
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        DocumentFile documentFile = mDataList.get(position);
        String name = documentFile.getName();


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean directory = documentFile.isDirectory();
                    if (directory){
                   onclickLinstener.click(position);
                    }
                }
            });

        holder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }
    public interface OnclickLinstener{
        void click(int p);
    }

    public void setOnclickLinstener(OnclickLinstener onclickLinstener) {
        this.onclickLinstener = onclickLinstener;
    }
    public void refreshData(List<DocumentFile>files){
        mDataList.clear();
        mDataList.addAll(files);
        notifyDataSetChanged();
    }
}
