package com.huewu.pla;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * �첽����ͼƬ
 * ע������ʹ��??SoftReference������ͼƬ������ GC����Ҫ��ʱ??���ԶԻ����е�ͼƬ��������??
 * @author zhanggq
 *
 */
public class AsyncImageLoader {

	private HashMap<String, SoftReference<Bitmap>> imageCache;
	/**
	 * ImageLoader��ʵ����
	 */
	private static AsyncImageLoader mAsyncImageLoader;
	
    public AsyncImageLoader() {
    	// ��ȡӦ�ó����������ڴ�
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
        imageCache = new HashMap<String, SoftReference<Bitmap>>();
    }
 
    public static AsyncImageLoader getInstance(){
    	if(mAsyncImageLoader == null){
    		mAsyncImageLoader = new AsyncImageLoader();
    	}
    	return mAsyncImageLoader;
    }
    
    public void addBitmapToMemoryCache(String imageUrl , Bitmap bitmap){
    	if (!imageCache.containsKey(imageUrl)){
    		imageCache.put(imageUrl, new SoftReference<Bitmap>(bitmap));
    	}
    }
    
    public Bitmap getBitmapFromMemoryCache(String imageUrl){
    	 SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
    	 if(softReference == null){
    		return null; 
    	 }
         return softReference.get();
    }
    public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth) {
		// ԴͼƬ�Ŀ��
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (width > reqWidth) {
			// �����ʵ�ʿ�Ⱥ�Ŀ���ȵı���
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = widthRatio;
		}
		return inSampleSize;
	}
    public static Bitmap decodeSampledBitmapFromResource(String pathName,
			int reqWidth) {
		// ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		// �������涨��ķ�������inSampleSizeֵ
		options.inSampleSize = calculateInSampleSize(options, reqWidth);
		// ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(pathName, options);
	}
}
