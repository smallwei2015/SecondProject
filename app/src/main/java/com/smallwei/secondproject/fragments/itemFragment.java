package com.smallwei.secondproject.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.smallwei.secondproject.MainActivity;
import com.smallwei.secondproject.R;
import com.smallwei.secondproject.activity.ItemDetailActivity;
import com.smallwei.secondproject.adapters.ThingItemAdapeter;
import com.smallwei.secondproject.adapters.VideoAdapter;
import com.smallwei.secondproject.javabean.Response;
import com.smallwei.secondproject.javabean.VideoItem;
import com.smallwei.secondproject.utils.QsbkService;
import com.smallwei.secondproject.utils.VideoInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class itemFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, Callback<Response> {

    ListView listView;
    ThingItemAdapeter adapeter;
    List<Response.ItemsEntity> items;
    VideoAdapter videoAdapter;
    List<VideoItem.ItemsEntity> videoItems;
    SwipeRefreshLayout swipeRefreshLayout;
    String type;
    private Call<Response> call;
    private QsbkService service;

    android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 22:
                    call = service.getList(type, 1);
                    call.enqueue(itemFragment.this);

                    break;
            }
        }
    };

    public itemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_item, container, false);

        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.thing_swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        //第一个参数是用来确定旋转的圈，是否逐渐变大，false表示一直以同样大小出现
        swipeRefreshLayout.setProgressViewOffset(true, 50, 150);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe);
        //swipeRefreshLayout.setSize(100);

        Bundle arguments = getArguments();
        String title = arguments.getString("title");
        Retrofit build = new Retrofit.Builder().baseUrl("http://m2.qiushibaike.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        listView= (ListView) view.findViewById(R.id.item_list_view);
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("item",videoItems.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        items=new ArrayList<>();
        adapeter=new ThingItemAdapeter(getContext(),items);
        adapeter.setOnClickListener(this);
        listView.setAdapter(adapeter);



        switch (title){
            case "专享":
                type="suggest";
                break;
            case "视频":
                type="video";
                /*videoItems=new ArrayList<>();
                videoAdapter=new VideoAdapter(videoItems,getContext(),getActivity());
                listView.setAdapter(videoAdapter);
                VideoInterface videoInterface = build.create(VideoInterface.class);
                Call<VideoItem> videoCall = videoInterface.getList(type, 1);
                videoCall.enqueue(new Callback<VideoItem>() {
                    @Override
                    public void onResponse(retrofit.Response<VideoItem> response, Retrofit retrofit) {
                        videoAdapter.addAll(response.body().getItems());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                });
                return view;*/
            case "文章":
                type="text";
                break;
            case "图片":
                type="image";
                break;
            case "精华":
                type="image";
                break;
            case "最新":
                type="latest";
                break;
        }

        service = build.create(QsbkService.class);
        call = service.getList(type, 1);
        call.enqueue(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        int position= (int) v.getTag();
        Intent intent=new Intent(getContext(), ItemDetailActivity.class);
        Bundle bundle=new Bundle();
        switch (id){
            case R.id.thing_fragment_user_comment_button:
                //bundle.putSerializable("item", items.get(position));
                bundle.putInt("userId",items.get(position).getId());
                break;
            case R.id.thing_fragment_user_content:
                bundle.putInt("userId", items.get(position).getId());
                break;
        }
        Response.ItemsEntity entity = items.get(position);
        bundle.putInt("id",entity.getId());
        bundle.putString("content", entity.getContent());
        bundle.getString("icon", entity.getUser().getIcon());
        bundle.getString("name", entity.getUser().getLogin());
        bundle.getString("type",entity.getType());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        //call.cancel();
    }

    @Override
    public void onRefresh() {
        //handler.sendEmptyMessage(22);
        call = service.getList(type, 1);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                items = response.body().getItems();
                adapeter.addAll(items);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
        adapeter.addAll(response.body().getItems());
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(),"freshed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }
}
