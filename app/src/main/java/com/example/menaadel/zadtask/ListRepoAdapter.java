package com.example.menaadel.zadtask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.menaadel.zadtask.data.model.Repository;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MenaAdel on 1/3/2018.
 */

public class ListRepoAdapter extends RecyclerView.Adapter<ListRepoAdapter.holder> {

    private List<Repository> repositories;
    private Context context;
    OnLongClick listener;

    public ListRepoAdapter(List<Repository> repositories, OnLongClick listener,Context context) {
        this.repositories = repositories;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.repo_name.setText(repositories.get(position).getRepo_name());
        holder.description.setText(repositories.get(position).getDescription());
        holder.username.setText(repositories.get(position).getUsername());
        if (repositories.get(position).isFork())
            holder.root_layout.setBackgroundColor(context.getResources().getColor(R.color.white));
        else
            holder.root_layout.setBackgroundColor(context.getResources().getColor(R.color.lightgreen));

    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public class holder extends RecyclerView.ViewHolder {

        @BindView(R.id.repo_name)
        TextView repo_name;

        @BindView(R.id.description)
        TextView description;

        @BindView(R.id.username)
        TextView username;

        @BindView(R.id.root_layout)
        RelativeLayout root_layout;

        OnLongClick onLongClick;

        public holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            onLongClick = listener;
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onLongClick.showDialog(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
