package com.ljppff.duitang;import android.app.Activity;import android.os.Bundle;import android.view.View;import android.view.Window;import android.widget.ImageView;public class PhotoActiivity extends Activity{	private ImageView mIvdele11;	@Override	protected void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		requestWindowFeature(Window.FEATURE_NO_TITLE);		setContentView(R.layout.photo);		mIvdele11 =(ImageView)this.findViewById(R.id.mIvdele11);		mIvdele11.setOnClickListener(new View.OnClickListener() {						@Override			public void onClick(View v) {				finish();			}		});									}}