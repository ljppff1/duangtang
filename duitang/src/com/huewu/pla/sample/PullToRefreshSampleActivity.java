package com.huewu.pla.sample;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.dodola.model.DuitangInfo;
import com.dodowaterfall.Helper;
import com.dodowaterfall.widget.ScaleImageView;
import com.example.android.bitmapfun.util.ImageFetcher;
import com.huewu.pla.AsyncImageLoader;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.ljppff.duitang.R;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PullToRefreshSampleActivity extends FragmentActivity implements IXListViewListener {
    private static final String STATE_ACTIVE_POSITION = "com.huewu.pla.sample.PullToRefreshSampleActivity.activePosition";
    private ImageFetcher mImageFetcher;
    private XListView mAdapterView = null;
    private StaggeredAdapter mAdapter = null;
    private int currentPage = 0;
    ContentTask task = new ContentTask(this, 2);
    private String albumId = "1608832";
    private AsyncImageLoader mAsyncImageLoader;

    private class ContentTask extends AsyncTask<String, Integer, List<DuitangInfo>> {

        private Context mContext;
        private int mType = 1;

        public ContentTask(Context context, int type) {
            super();
            mContext = context;
            mType = type;
        }

        @Override
        protected List<DuitangInfo> doInBackground(String... params) {
            try {
                return parseNewsJSON(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<DuitangInfo> result) {
            if (mType == 1) {

                mAdapter.addItemTop(result);
                mAdapter.notifyDataSetChanged();
                mAdapterView.stopRefresh();

            } else if (mType == 2) {
                mAdapterView.stopLoadMore();
                mAdapter.addItemLast(result);
                mAdapter.notifyDataSetChanged();
            }

        }

        @Override
        protected void onPreExecute() {
        }

        public List<DuitangInfo> parseNewsJSON(String url) throws IOException {
            List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
            String json = "";
            if (Helper.checkConnection(mContext)) {
                try {
                    json = Helper.getStringFromUrl(url);

                } catch (IOException e) {
                    Log.e("IOException is : ", e.toString());
                    e.printStackTrace();
                    return duitangs;
                }
            }
            Log.d("MainActiivty", "json:" + json);

            try {
                if (null != json) {
                	
                	
    				JSONObject jsonObject = new JSONObject(json);
					 JSONArray array = jsonObject.getJSONArray("data");

    		
    					  for (int i = 0; i < array.length(); i++) {
    				
                              DuitangInfo newsInfo1 = new DuitangInfo();
     						 JSONObject jsonObject2 = array.getJSONObject(i);

                              newsInfo1.setAlbid(jsonObject2.getString("ID"));
                              newsInfo1.setIsrc(jsonObject2.getString("CoverPic"));
                              newsInfo1.setMsg(jsonObject2.getString("Developer")+jsonObject2.getString("PropertyAddress"));
                              newsInfo1.setHeight(100+(int) (Math.random()*50));
                              duitangs.add(newsInfo1);
                              
    					}

                	
                	
                	
                	
                  /*  JSONObject newsObject = new JSONObject(json);
                    JSONObject jsonObject = newsObject.getJSONObject("data");
                    JSONArray blogsJson = jsonObject.getJSONArray("blogs");

                    for (int i = 0; i < blogsJson.length(); i++) {
                        JSONObject newsInfoLeftObject = blogsJson.getJSONObject(i);
                        DuitangInfo newsInfo1 = new DuitangInfo();
                        newsInfo1.setAlbid(newsInfoLeftObject.isNull("albid") ? "" : newsInfoLeftObject.getString("albid"));
                        newsInfo1.setIsrc(newsInfoLeftObject.isNull("isrc") ? "" : newsInfoLeftObject.getString("isrc"));
                        newsInfo1.setMsg(newsInfoLeftObject.isNull("msg") ? "" : newsInfoLeftObject.getString("msg"));
                        newsInfo1.setHeight(newsInfoLeftObject.getInt("iht"));
                        duitangs.add(newsInfo1);
                    }
                    
                    */
                    
                    
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return duitangs;
        }
    }


    private void AddItemToContainer(int pageindex, int type, String albumId) {
        if (task.getStatus() != Status.RUNNING) {
            String url = "http://www.duitang.com/album/"+albumId+"/masn/p/" + pageindex + "/24/";
            url ="http://www.hk-compass.com/json/firsthand.php?ShowType=1";
            Log.d("MainActivity", "current url:" + url);
            ContentTask task = new ContentTask(this, type);
            task.execute(url);

        }else if (task != null && task.getStatus() != AsyncTask.Status.FINISHED){
            task.cancel(true);
            String url = "http://www.duitang.com/album/"+albumId+"/masn/p/" + pageindex + "/24/";
            url ="http://www.hk-compass.com/json/firsthand.php?ShowType=1";

            Log.d("MainActivity", "current url:" + url);
            ContentTask task = new ContentTask(this, type);
            task.execute(url);
        }
    }

    public class StaggeredAdapter extends BaseAdapter {
        private Context mContext;

        public LinkedList<DuitangInfo> getmInfos() {
            return mInfos;
        }

        private LinkedList<DuitangInfo> mInfos;
        private XListView mListView;

        public StaggeredAdapter(Context context, XListView xListView) {
            mContext = context;
            mInfos = new LinkedList<DuitangInfo>();
            mListView = xListView;
            mAsyncImageLoader = AsyncImageLoader.getInstance();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            DuitangInfo duitangInfo = mInfos.get(position);

            if (convertView == null) {
                LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
                convertView = layoutInflator.inflate(R.layout.infos_list, null);
                holder = new ViewHolder();
                holder.imageView = (ScaleImageView) convertView.findViewById(R.id.news_pic);
                holder.contentView = (TextView) convertView.findViewById(R.id.news_title);
                
                convertView.setTag(holder);
            }

            holder = (ViewHolder) convertView.getTag();
            holder.imageView.setImageWidth(duitangInfo.getWidth());
            holder.imageView.setImageHeight(duitangInfo.getHeight());
            holder.contentView.setText(duitangInfo.getMsg());
//            Bitmap bitmap = mAsyncImageLoader.decodeSampledBitmapFromResource(
//                    duitangInfo.getIsrc(), duitangInfo.getWidth());
//            mAsyncImageLoader.addBitmapToMemoryCache(duitangInfo.getIsrc(), bitmap);
//            holder.imageView.setImageBitmap(bitmap);
            mImageFetcher.loadImage(duitangInfo.getIsrc(), holder.imageView);
            return convertView;
        }

        class ViewHolder {
            ScaleImageView imageView;
            TextView contentView;
            TextView timeView,news_time;
        }

        @Override
        public int getCount() {
            return mInfos.size();
        }

        @Override
        public Object getItem(int arg0) {
            return mInfos.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        public void addItemLast(List<DuitangInfo> datas) {
            mInfos.addAll(datas);
        }

        public void addItemTop(List<DuitangInfo> datas) {
            for (DuitangInfo info : datas) {
                mInfos.addFirst(info);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle inState) {
        super.onCreate(inState);

        setContentView(R.layout.act_pull_to_refresh_sample);
        mAdapterView = (XListView) findViewById(R.id.list);
        mAdapterView.setPullLoadEnable(true);
        mAdapterView.setXListViewListener(this);

        mAdapter = new StaggeredAdapter(this, mAdapterView);

        mImageFetcher = new ImageFetcher(this, 240);
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);

        mAdapterView.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent().setClass(PullToRefreshSampleActivity.this, ImageDetailActivity.class);
                in.putExtra(ImageDetailActivity.EXTRA_IMAGE,position-1);
                Bundle bu = new Bundle();
                ArrayList list = new ArrayList();
                list.add(mAdapter.getmInfos());
                bu.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);  //---
                in.putExtras(bu);
                startActivity(in);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
        mAdapterView.setAdapter(mAdapter);
        AddItemToContainer(currentPage, 2,albumId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRefresh() {
        AddItemToContainer(++currentPage, 1,albumId);

    }

    @Override
    public void onLoadMore() {
        AddItemToContainer(++currentPage, 2,albumId);

    }

}
