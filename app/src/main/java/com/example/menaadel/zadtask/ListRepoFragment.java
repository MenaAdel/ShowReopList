package com.example.menaadel.zadtask;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.menaadel.zadtask.caching.DatabaseOperations;
import com.example.menaadel.zadtask.data.GetReposOperation;
import com.example.menaadel.zadtask.data.RequestsObserver;
import com.example.menaadel.zadtask.data.model.Owner;
import com.example.menaadel.zadtask.data.model.Repository;
import com.example.menaadel.zadtask.network_connection.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListRepoFragment extends Fragment implements RequestsObserver,OnLongClick{

    @BindView(R.id.rcl_repo_names)
    RecyclerView rclRepoNames;
    @BindView(R.id.progress2)
    ProgressBar loader;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    ListRepoAdapter listRepoAdapter;
    public static List<Repository> data = new ArrayList<>();
    List<Repository> temp_data = new ArrayList<>();
    int page=1;
    int itemVisible=0;
    ConnectionDetector cd;
    DatabaseOperations db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_list_repo, container, false);
        ButterKnife.bind(this,view);
        cd=new ConnectionDetector(getContext());
        init();
        return view;
    }

    private void init(){
        db = new DatabaseOperations(getContext());
        initiateRclView();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void initiateRclView(){
        RecyclerView.LayoutManager gridLayout = new GridLayoutManager(getActivity(), 1);
        rclRepoNames.setHasFixedSize(true);
        rclRepoNames.setLayoutManager(gridLayout);
        if (cd.IsConnectingToInternet())
        {
            //in online connection
            getNextRepos();
            loader.setVisibility(View.VISIBLE);
        }else
        {
            //in offline connection
            getNextReposOffLine();
            data=temp_data;
        }
        initializeAdapter();

        // On Scroll add 10 items
        rclRepoNames.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

                if (lastVisiblePosition + 1 ==itemVisible+10) {
                    getNextRepos();
                    itemVisible += 10;
                }
            }

        });
    }

    /*
    * This method to initialize the recycler adapter
     */
    private void initializeAdapter()
    {
        listRepoAdapter = new ListRepoAdapter(data,ListRepoFragment.this,getContext());
        rclRepoNames.setAdapter(listRepoAdapter);
    }

    /*
    * This method used to Request only 10 repos at a time.
     */
    private void getNextRepos(){
        GetReposOperation.getInstance().startOperation(page++, 10,this);
    }

    /*
    * This method used to get data if there is no connection.
     */
    private void getNextReposOffLine(){
        if(!data.isEmpty()) {
            Cursor cr = db.SELECT(db);
            cr.moveToFirst();
            do {
                Repository repository = new Repository();
                Owner owner = new Owner();
                owner.setHtml_url(cr.getString(2));
                repository.setRepository(cr.getString(0), cr.getString(1), owner, cr.getString(3), cr.getString(4), cr.getString(5));
                temp_data.add(repository);
            } while (cr.moveToNext());
        }
    }

    /*
    * This method to get data from API if request success
     */
    @Override
    public void onSuccess(Object response) {
        loader.setVisibility(View.INVISIBLE);
        data.addAll((List<Repository>)response);
        DatabaseOperations db = new DatabaseOperations(getContext());
        for(int i=0;i<((List<Repository>) response).size();i++){
            db.INSERT(((List<Repository>) response).get(i));
        }

        listRepoAdapter.notifyDataSetChanged();
    }

    /*
    * This method to show error message if request failed
     */
    @Override
    public void onError(String message) {
        Log.d("errorResponse",message);
    }

    /*
    * This method used to show a dialog in long click on the item
     */
    @Override
    public void showDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("");
        builder.setMessage("visit user profile or repo");
        builder.setPositiveButton("Repo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ListRepoFragment.data.get(position).getHtml_url()));
                startActivity(intent);            }
        });
        builder.setNegativeButton("Profile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ListRepoFragment.data.get(position).getOwner().getHtml_url()));
                startActivity(intent);
            }
        });
        builder.show();
    }

    /*
    * This method used to refresh the data on item
     */
    public void refreshContent(){
        data.clear();
        page = 1;
        itemVisible = 0;
        getNextRepos();
        listRepoAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
