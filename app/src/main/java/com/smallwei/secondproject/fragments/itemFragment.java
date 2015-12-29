package com.smallwei.secondproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.smallwei.secondproject.R;
import com.smallwei.secondproject.adapters.ThingItemAdapeter;
import com.smallwei.secondproject.adapters.VideoAdapter;
import com.smallwei.secondproject.javabean.Response;
import com.smallwei.secondproject.javabean.VideoItem;
import com.smallwei.secondproject.utils.QsbkService;
import com.smallwei.secondproject.utils.VideoInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class itemFragment extends Fragment {

    ListView listView;
    ThingItemAdapeter adapeter;
    List<Response.ItemsEntity> items;
    VideoAdapter videoAdapter;
    List<VideoItem.ItemsEntity> videoItems;
    String type;

    public itemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_item, container, false);

        Bundle arguments = getArguments();
        String title = arguments.getString("title");
        Retrofit build = new Retrofit.Builder().baseUrl("http://m2.qiushibaike.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        listView= (ListView) view.findViewById(R.id.item_list_view);

        items=new ArrayList<>();
        adapeter=new ThingItemAdapeter(getContext(),items);
        listView.setAdapter(adapeter);



        switch (title){
            case "专享":
                type="suggest";
                break;
            case "视频":
                type="video";
                videoItems=new ArrayList<>();
                videoAdapter=new VideoAdapter(videoItems,getContext());
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
                return view;
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

        QsbkService service = build.create(QsbkService.class);
        Call<Response> call = service.getList(type, 1);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                adapeter.addAll(response.body().getItems());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        return view;
    }



}
