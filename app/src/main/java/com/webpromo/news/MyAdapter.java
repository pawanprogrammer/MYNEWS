package com.webpromo.news;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.webpromo.news.Helper.BaseViewHolder;

import java.util.List;

import retrofit2.Callback;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    String[] post_image;
    String[] post_title;
    String[] post_by;
    String[] post_date;
    String[] post_content;
    Context context;
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    public MyAdapter(Context c) {
        this.context = c;
    }

    public MyAdapter(Callback<List<Mydata>> callback, String[] post_image, String[] post_title, String[] post_by, String[] post_date, String[] post_content) {
        this.post_by = post_by;
        this.post_content = post_content;
        this.post_date = post_date;
        this.post_image = post_image;
        this.post_title = post_title;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_items, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {
        myHolder.post_title.setText(post_title[i]);
        myHolder.post_date.setText(post_date[i]);
        myHolder.post_content.setText(post_content[i]);
        myHolder.post_by.setText(post_by[i]);
        Picasso.with(myHolder.post_image.getContext()).load(post_image[i]).into(myHolder.post_image);
        myHolder.btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to send the manually data using android intent
                /*Intent intent = new Intent(myHolder.btn_read.getContext(),
                        DetailActivity.class);
                intent.putExtra("post_image", post_image);
                intent.putExtra("post_title", post_title);
                intent.putExtra("post_date", post_date);
                intent.putExtra("post_by", post_by);
                intent.putExtra("position", i);
                //intent.putExtra("post_content", post_content);
                myHolder.btn_read.getContext().startActivity(intent);*/

                //to send the large amount of data using android parceable
                Mydata mydata = new Mydata(post_title, post_by, post_date, post_content,post_image);
                Intent intent = new Intent(myHolder.btn_read.getContext(), DetailActivity.class);
                intent.putExtra("news data", mydata);
                myHolder.btn_read.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return post_title.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView post_title, post_by, post_date, post_content;
        ImageView post_image;
        TextView btn_read;

        public MyHolder(@NonNull View v) {
            super(v);
            post_by = v.findViewById(R.id.post_by);
            post_content = v.findViewById(R.id.post_content);
            post_date = v.findViewById(R.id.post_date);
            post_image = v.findViewById(R.id.post_image);
            post_title = v.findViewById(R.id.post_title);
            btn_read = v.findViewById(R.id.btn_read);
        }
    }
}


    /*private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    //private List<PostItem> mPostItems;

    *//*public PostRecyclerAdapter(List<PostItem> postItems) {
        this.mPostItems = postItems;
    }*//*


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.news_items, parent, false));
            case VIEW_TYPE_LOADING:
                return new FooterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == mPostItems.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mPostItems == null ? 0 : mPostItems.size();
    }

    public void add(PostItem response) {
        mPostItems.add(response);
        notifyItemInserted(mPostItems.size() - 1);
    }

    public void addAll(List<PostItem> postItems) {
        for (PostItem response : postItems) {
            add(response);
        }
    }


    private void remove(PostItem postItems) {
        int position = mPostItems.indexOf(postItems);
        if (position > -1) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoading() {
        isLoaderVisible = true;
        add(new PostItem());
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = mPostItems.size() - 1;
        PostItem item = getItem(position);
        if (item != null) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    PostItem getItem(int position) {
        return mPostItems.get(position);
    }


    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.textViewTitle)
        TextView textViewTitle;
        @BindView(R.id.textViewDescription)
        TextView textViewDescription;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            PostItem item = mPostItems.get(position);
            textViewTitle.setText(item.getTitle());
            textViewDescription.setText(item.getDescription());
        }
    }

    public class FooterHolder extends BaseViewHolder {

        ProgressBar mProgressBar;
        FooterHolder(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progressBar);
        }

        @Override
        protected void clear() {

        }
    }

}
*/