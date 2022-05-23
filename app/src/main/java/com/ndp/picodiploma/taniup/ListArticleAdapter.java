package com.ndp.picodiploma.taniup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListArticleAdapter extends RecyclerView.Adapter<ListArticleAdapter.ListViewHolder>{

    private ArrayList<Article> listArticle;

    public ListArticleAdapter(ArrayList<Article> list) {
        this.listArticle = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_article, parent, false);
        return new ListViewHolder(v);
    }

    private OnItemClickCallBack onItemClickCallBack;

    public void setOnItemClickCallBack(OnItemClickCallBack onItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Article article = listArticle.get(position);
        holder.ivItem.setImageResource(article.getPhoto());
        holder.tvTitle.setText(article.getTitle());
        holder.tvDesc.setText(article.getDescription());


        holder.itemView.setOnClickListener(view -> onItemClickCallBack.onItemClicked(listArticle.get(holder.getAdapterPosition())));

    }

    @Override
    public int getItemCount() {
        return listArticle.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView ivItem;
        TextView tvTitle, tvDesc;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.item_pics);
            tvTitle = itemView.findViewById(R.id.tv_artikel_title);
            tvDesc = itemView.findViewById(R.id.tv_artikel_desc);

        }
    }

    public interface OnItemClickCallBack {
        void onItemClicked(Article datas);
    }
}
