package com.skyscape.demo.frame.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;

import com.skyscape.demo.frame.R;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.ViewHolder> {
    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<String> mDataList;

    public PicAdapter(Context mContext, List<String> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_pic, parent, false);
        return new ViewHolder(view);
    }
    OnclickLinstener onclickLinstener;
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String documentFile = mDataList.get(position);
        //InputStream is = getResources().openRawResource(R.drawable.t);

        Bitmap mBitmap = null;
        try {
            mBitmap = BitmapFactory.decodeStream(new FileInputStream(documentFile));
            holder.iv.setImageBitmap(mBitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }
    }
    public interface OnclickLinstener{
        void click(int p);
    }

    public void setOnclickLinstener(OnclickLinstener onclickLinstener) {
        this.onclickLinstener = onclickLinstener;
    }
//    public void refreshData(List<DocumentFile>files){
//        mDataList.clear();
//        mDataList.addAll(files);
//        notifyDataSetChanged();
//    }
}
