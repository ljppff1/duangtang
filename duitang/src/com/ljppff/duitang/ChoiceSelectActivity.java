package com.ljppff.duitang;import java.io.IOException;import java.util.ArrayList;import java.util.LinkedList;import java.util.List;import org.json.JSONArray;import org.json.JSONException;import org.json.JSONObject;import me.maxwin.view.XListView;import me.maxwin.view.XListView.IXListViewListener;import com.czm.flowlayout.XCFlowLayout;import com.dodola.model.DuitangInfo;import com.dodowaterfall.Helper;import com.dodowaterfall.widget.ScaleImageView;import com.example.android.bitmapfun.util.ImageFetcher;import com.huewu.pla.AsyncImageLoader;import com.huewu.pla.lib.internal.PLA_AbsListView;import com.huewu.pla.lib.internal.PLA_AdapterView;import com.huewu.pla.sample.ImageDetailActivity;import com.huewu.pla.sample.PullToRefreshSampleActivity;import com.huewu.pla.sample.PullToRefreshSampleActivity.StaggeredAdapter;import android.annotation.SuppressLint;import android.app.Activity;import android.content.Context;import android.content.Intent;import android.graphics.Color;import android.os.AsyncTask;import android.os.Bundle;import android.os.Parcelable;import android.os.AsyncTask.Status;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.View.OnClickListener;import android.view.ViewGroup;import android.view.Window;import android.view.ViewGroup.LayoutParams;import android.view.ViewGroup.MarginLayoutParams;import android.widget.AbsListView;import android.widget.BaseAdapter;import android.widget.Button;import android.widget.RelativeLayout;import android.widget.TextView;import android.widget.AbsListView.OnScrollListener;@SuppressLint("NewApi")public class ChoiceSelectActivity extends Activity implements IXListViewListener{	private String nNamess[]=null;	private String mNames[] ={		"商品一","商品二","商品三",		};    private String mNames1[] = {            "#新品速递","#上衣","#内衣",            "#裙装","#裤子","#配饰",            "#鞋子","#包装","#型男",            "#美食","#日杂","#家居装饰",            "#阿良推荐"    };    private String mNames2[] = {            "#韩风","#日系","#欧美",            "#文艺","#简约","#森系"    };    private String mNames3[] = {            "#创意小物","#公仔","#蛋糕",            "#鲜花速递","#礼品包装"    };    private String mNames4[] = {            "#街拍","#秀场","#模特",            "#时尚博主","#男装","#杂志大片"    };    private String mNames5[] = {    		"#女生发型","#男生发型","#美甲","#香水","#纹身"    };    private String mNames6[] = {    		"#美妆心得","#约会妆","#日系妆","#烟熏妆","#底妆","#眼妆","#唇妆","#咬唇妆","#仿妆","#护肤","瘦身"    };    private String mNames7[] = {    		"#新娘造型","#伴娘造型","#婚纱礼服","#婚礼现场","#婚礼小物"    };    private String mNames8[] = {    		"#室内布置","#家具","#阳台庭院","#小户型","#工作间","#衣帽间","#植物多肉"    };    private String mNames9[] = {    		"#手工达人","#手帐","#废物利用","#编织","#绳线","#纸艺","#羊毛","#纽扣","#热缩片"    };    private String mNames10[] = {    		"#家常菜","#早餐","#日式料理","#西餐","#甜品","#饮品","#下厨房精选","#美食达人"    };    private String mNames11[] = {    		"#插画达人","#古风插画","#绘画教程","#手绘风格","#主题绘画"    };    private String mNames12[] = {    		"#萌宠达人","#喵星人","#汪星人","#动物星球","#萌娃"    };    private String mNames13[] = {    		"#女生","#男生","#情侣","#个性","#欧美","#卡通","#文字","#贱表情","#萌表情","#截图表情"    };    private String mNames14[] = {    		"#原创","#插画","#风景","#文字","#黑白","#平铺","#动漫","#电影","#人物","#萌物"    };    private String mNames15[] = {    		"#摄影达人","#摄影教程","#摄影作品","#Gif图片","#搞笑图片","#重口味图片","#十二星座"    };    private String mNames16[] = {    		"#电影","#电视剧","#综艺","#音乐","#书籍"    };    private String mNames17[] = {    		"#手写","#歌词","#英文","#备忘录"    };    private String mNames18[] = {    		"#今生必去","#旅行攻略","#目的地","#城堡","#岛屿","#教堂","#夜景","#森林","#赏花","温泉"    };    private String mNames19[] = {    		"#字体设计","#LOGO","#平面设计","#广告设计","#海报设计","#包装设计","#配色","#雕塑","#建筑",    };    private XCFlowLayout mFlowLayout;	private XListView mAdapterView;	private StaggeredAdapter mAdapter;	private ImageFetcher mImageFetcher;    private int currentPage = 0;    ContentTask task = new ContentTask(this, 2);    private String albumId = "1608832";	private Integer what;	private RelativeLayout task_linearlayout1;	private Button tdc_back;    private class ContentTask extends AsyncTask<String, Integer, List<DuitangInfo>> {        private Context mContext;        private int mType = 1;        public ContentTask(Context context, int type) {            super();            mContext = context;            mType = type;        }        @Override        protected List<DuitangInfo> doInBackground(String... params) {            try {                return parseNewsJSON(params[0]);            } catch (IOException e) {                e.printStackTrace();            }            return null;        }        @Override        protected void onPostExecute(List<DuitangInfo> result) {            if (mType == 1) {                mAdapter.addItemTop(result);                mAdapter.notifyDataSetChanged();                mAdapterView.stopRefresh();            } else if (mType == 2) {                mAdapterView.stopLoadMore();                mAdapter.addItemLast(result);                mAdapter.notifyDataSetChanged();            }        }        @Override        protected void onPreExecute() {        }        public List<DuitangInfo> parseNewsJSON(String url) throws IOException {            List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();            String json = "";            if (Helper.checkConnection(mContext)) {                try {                    json = Helper.getStringFromUrl(url);                } catch (IOException e) {                    Log.e("IOException is : ", e.toString());                    e.printStackTrace();                    return duitangs;                }            }            Log.d("MainActiivty", "json:" + json);            try {                if (null != json) {                	                	    				JSONObject jsonObject = new JSONObject(json);					 JSONArray array = jsonObject.getJSONArray("data");    		    					  for (int i = 0; i < array.length(); i++) {    				                              DuitangInfo newsInfo1 = new DuitangInfo();     						 JSONObject jsonObject2 = array.getJSONObject(i);                              newsInfo1.setAlbid(jsonObject2.getString("ID"));                              newsInfo1.setIsrc(jsonObject2.getString("CoverPic"));                              newsInfo1.setMsg(jsonObject2.getString("Developer")+jsonObject2.getString("PropertyAddress"));                              newsInfo1.setHeight(100+(int) (Math.random()*50));                              duitangs.add(newsInfo1);                                  					}                	                	                	                	                  /*  JSONObject newsObject = new JSONObject(json);                    JSONObject jsonObject = newsObject.getJSONObject("data");                    JSONArray blogsJson = jsonObject.getJSONArray("blogs");                    for (int i = 0; i < blogsJson.length(); i++) {                        JSONObject newsInfoLeftObject = blogsJson.getJSONObject(i);                        DuitangInfo newsInfo1 = new DuitangInfo();                        newsInfo1.setAlbid(newsInfoLeftObject.isNull("albid") ? "" : newsInfoLeftObject.getString("albid"));                        newsInfo1.setIsrc(newsInfoLeftObject.isNull("isrc") ? "" : newsInfoLeftObject.getString("isrc"));                        newsInfo1.setMsg(newsInfoLeftObject.isNull("msg") ? "" : newsInfoLeftObject.getString("msg"));                        newsInfo1.setHeight(newsInfoLeftObject.getInt("iht"));                        duitangs.add(newsInfo1);                    }                                        */                                                        }            } catch (JSONException e) {                e.printStackTrace();            }            return duitangs;        }    }    private void AddItemToContainer(int pageindex, int type, String albumId) {        if (task.getStatus() != Status.RUNNING) {            String url = "http://www.duitang.com/album/"+albumId+"/masn/p/" + pageindex + "/24/";            url ="http://www.hk-compass.com/json/firsthand.php?ShowType=1";            Log.d("MainActivity", "current url:" + url);            ContentTask task = new ContentTask(this, type);            task.execute(url);        }else if (task != null && task.getStatus() != AsyncTask.Status.FINISHED){            task.cancel(true);            String url = "http://www.duitang.com/album/"+albumId+"/masn/p/" + pageindex + "/24/";            url ="http://www.hk-compass.com/json/firsthand.php?ShowType=1";            Log.d("MainActivity", "current url:" + url);            ContentTask task = new ContentTask(this, type);            task.execute(url);        }    }	@Override	protected void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		requestWindowFeature(Window.FEATURE_NO_TITLE);		setContentView(R.layout.choiceselect);		task_linearlayout1 =(RelativeLayout)findViewById(R.id.task_linearlayout1);		tdc_back =(Button)this.findViewById(R.id.tdc_back);		tdc_back.setOnClickListener(new OnClickListener() {						@Override			public void onClick(View v) {				finish();				}		});	       what =(Integer) getIntent().getExtras().get("WHAT");	        mAdapterView = (XListView) findViewById(R.id.list);	        mAdapterView.setPullLoadEnable(true);	        mAdapterView.setXListViewListener(this);			View header = View.inflate(this, R.layout.stick_header, null);	        mFlowLayout = (XCFlowLayout) header.findViewById(R.id.flowlayout);	        MarginLayoutParams lp = new MarginLayoutParams(	                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);	        lp.leftMargin = 5;	        lp.rightMargin = 5;	        lp.topMargin = 5;	        lp.bottomMargin = 5;	        	        if(what==1){	        	nNamess =mNames;	        }else  if(what==2){	        	nNamess =mNames1;	        }else  if(what==3){	        	nNamess =mNames2;	        }else  if(what==4){	        	nNamess =mNames3;	        }else  if(what==5){	        	nNamess =mNames4;	        }else  if(what==6){	        	nNamess =mNames5;	        }else  if(what==7){	        	nNamess =mNames6;	        }else  if(what==8){	        	nNamess =mNames7;	        }else  if(what==9){	        	nNamess =mNames8;	        }else  if(what==10){	        	nNamess =mNames9;	        }else  if(what==11){	        	nNamess =mNames10;	        }else  if(what==12){	        	nNamess =mNames11;	        }else  if(what==13){	        	nNamess =mNames12;	        }else  if(what==14){	        	nNamess =mNames13;	        }else  if(what==15){	        	nNamess =mNames14;	        }else  if(what==16){	        	nNamess =mNames15;	        }else  if(what==17){	        	nNamess =mNames16;	        }else  if(what==18){	        	nNamess =mNames17;	        }else  if(what==19){	        	nNamess =mNames18;	        }else  if(what==20){	        	nNamess =mNames19;	        }	        for(int i = 0; i < nNamess.length; i ++){	            TextView view = new TextView(this);	            view.setText(nNamess[i]);	            view.setTextColor(Color.WHITE);	            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));	            mFlowLayout.addView(view,lp);	        }			mAdapterView.addHeaderView(header);			mAdapterView.setOnScrollListener(new  PLA_AbsListView.OnScrollListener() {								@Override				public void onScrollStateChanged(PLA_AbsListView view, int scrollState) {					// TODO Auto-generated method stub									}								@Override				public void onScroll(PLA_AbsListView view, int firstVisibleItem,						int visibleItemCount, int totalItemCount) {					if (firstVisibleItem >= 1) {					} else {					}									}			});				        mAdapter = new StaggeredAdapter(this, mAdapterView);	        mImageFetcher = new ImageFetcher(this, 240);	        mImageFetcher.setLoadingImage(R.drawable.empty_photo);	        mAdapterView.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {	            @Override	            public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {	                Intent in = new Intent().setClass(ChoiceSelectActivity.this, ImageDetailActivity.class);	                in.putExtra(ImageDetailActivity.EXTRA_IMAGE,position-1);	                Bundle bu = new Bundle();	                ArrayList list = new ArrayList();	                list.add(mAdapter.getmInfos());	                bu.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);  //---	                in.putExtras(bu);	                startActivity(in);	            }	        });	       			}	  public class StaggeredAdapter extends BaseAdapter {	        private Context mContext;	        public LinkedList<DuitangInfo> getmInfos() {	            return mInfos;	        }	        private LinkedList<DuitangInfo> mInfos;	        private XListView mListView;			private AsyncImageLoader mAsyncImageLoader;	        public StaggeredAdapter(Context context, XListView xListView) {	            mContext = context;	            mInfos = new LinkedList<DuitangInfo>();	            mListView = xListView;	            mAsyncImageLoader = AsyncImageLoader.getInstance();	        }	        @Override	        public View getView(int position, View convertView, ViewGroup parent) {	            ViewHolder holder;	            DuitangInfo duitangInfo = mInfos.get(position);	            if (convertView == null) {	                LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());	                convertView = layoutInflator.inflate(R.layout.infos_list, null);	                holder = new ViewHolder();	                holder.imageView = (ScaleImageView) convertView.findViewById(R.id.news_pic);	                holder.contentView = (TextView) convertView.findViewById(R.id.news_title);	                	                convertView.setTag(holder);	            }	            holder = (ViewHolder) convertView.getTag();	            holder.imageView.setImageWidth(duitangInfo.getWidth());	            holder.imageView.setImageHeight(duitangInfo.getHeight());	            holder.contentView.setText(duitangInfo.getMsg());//	            Bitmap bitmap = mAsyncImageLoader.decodeSampledBitmapFromResource(//	                    duitangInfo.getIsrc(), duitangInfo.getWidth());//	            mAsyncImageLoader.addBitmapToMemoryCache(duitangInfo.getIsrc(), bitmap);//	            holder.imageView.setImageBitmap(bitmap);	            mImageFetcher.loadImage(duitangInfo.getIsrc(), holder.imageView);	            return convertView;	        }	        class ViewHolder {	            ScaleImageView imageView;	            TextView contentView;	            TextView timeView,news_time;	        }	        @Override	        public int getCount() {	            return mInfos.size();	        }	        @Override	        public Object getItem(int arg0) {	            return mInfos.get(arg0);	        }	        @Override	        public long getItemId(int arg0) {	            return 0;	        }	        public void addItemLast(List<DuitangInfo> datas) {	            mInfos.addAll(datas);	        }	        public void addItemTop(List<DuitangInfo> datas) {	            for (DuitangInfo info : datas) {	                mInfos.addFirst(info);	            }	        }	    }    private void initChildViews() {        // TODO Auto-generated method stub    }    @Override    protected void onResume() {        super.onResume();        mImageFetcher.setExitTasksEarly(false);        mAdapterView.setAdapter(mAdapter);        AddItemToContainer(currentPage, 2,albumId);    }    @Override    protected void onDestroy() {        super.onDestroy();    }    @Override    public void onRefresh() {        AddItemToContainer(++currentPage, 1,albumId);    }    @Override    public void onLoadMore() {        AddItemToContainer(++currentPage, 2,albumId);    }}