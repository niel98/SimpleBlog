package first.com.interns.simpleblog;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mBlogList;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");

        mBlogList = findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Blog> options = new FirebaseRecyclerOptions.Builder<Blog>().setQuery(mDatabase, Blog.class).build();

        FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(options) {

            @Override
            public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.blog_row, parent, false);

                return new BlogViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull BlogViewHolder holder, int position, @NonNull Blog model) {

                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImage(getApplicationContext(), model.getImage());

            }

        };

        mBlogList.setAdapter(firebaseRecyclerAdapter);


    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setTitle(String title){

            TextView postTitle = mView.findViewById(R.id.post_title);
            postTitle.setText(title);

        }

        public void setDesc(String desc){

            TextView postDesc = mView.findViewById(R.id.post_desc);
            postDesc.setText(desc);

        }

        public void setImage(Context ctx, String image){

            ImageView post_image = mView.findViewById(R.id.post_image);
            Picasso.get().load(image).into(post_image);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add){

            startActivity(new Intent(this, PostActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
