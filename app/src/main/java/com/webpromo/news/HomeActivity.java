package com.webpromo.news;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.webpromo.news.Helper.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyAdapter myAdapter;
    public static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    private String[] post_image;
    private String[] post_title;
    private String[] post_by;
    private String[] post_date;
    private String[] post_content;
    Call<List<Mydata>> call;
    SwipeRefreshLayout swipe;
    private  List<Mydata> heroList;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        swipe = findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar=findViewById(R.id.progressbar);


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        myAdapter = new MyAdapter((Callback<List<Mydata>>) call, post_image,
                post_title, post_by, post_date, post_content);
        fetchnews();

        /*recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                fetchnews();

            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });*/
//        MyAdapter myAdapter = new MyAdapter(getApplicationContext());


    }

    private void fetchnews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        call = api.getHeroes();

        call.enqueue(new Callback<List<Mydata>>() {
            @Override
            public void onResponse(Call<List<Mydata>> call, Response<List<Mydata>> response) {
                heroList= response.body();

//for using the pagination
                //Creating an String array for the ListView
                post_image = new String[heroList.size()];
                post_title = new String[heroList.size()];
                post_by = new String[heroList.size()];
                post_date = new String[heroList.size()];
                post_content = new String[heroList.size()];

                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            itemCount++;
                            Log.d("Itemcount", "run: " + itemCount);
                            *//*post_image[i] = heroList.get(i).getImg_link();
                            post_title[i] = heroList.get(i).getPost_title();
                            post_by[i] = heroList.get(i).getPost_author();
                            post_date[i] = heroList.get(i).getPost_date();
                            post_content[i] = heroList.get(i).getPost_content();*//*


                            List<Mydata> list = new ArrayList<>();
                            list.addAll();

                        }
                        if (currentPage != PAGE_START) myAdapter.removeLoading();
                        myAdapter.addAll(heroList);
                        swipe.setRefreshing(false);
                        if (currentPage < totalPage) myAdapter.addLoading();
                        else isLastPage = true;
                        isLoading = false;

                    }
                }, 2000);
*/
                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.size(); i++) {
                    post_image[i] = heroList.get(i).getImg_link();
                    post_title[i] = heroList.get(i).getPost_title();
                    post_by[i] = heroList.get(i).getPost_author();
                    post_date[i] = heroList.get(i).getPost_date();
                    post_content[i] = heroList.get(i).getPost_content();
                }

                MyAdapter myAdapter = new MyAdapter(this, post_image,
                        post_title, post_by, post_date, post_content);

                recyclerView.setAdapter(myAdapter);
                progressBar.setVisibility(View.GONE);
                //displaying the string array into listview
                /*recyclerView.setAdapter(new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1, heroes));*/

            }

            @Override
            public void onFailure(Call<List<Mydata>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        //myAdapter.clear();
        fetchnews();
    }
}
