package edu.aust.hunter.purblepairs.activity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.example.purblepairs.activity.R;

/**
 * @author hutao
 * StartActivity显示一张启动界面图片
 * 显示完成之后跳转至MainActivity
 *
 */
public class StartActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 2000; // 设置延迟时间
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_start);
//		启动界面延迟启动主界面
//		设置  SPLASH_DISPLAY_LENGHT  设置延迟时间
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent startIntent = new Intent(StartActivity.this,MainActivity.class);
				StartActivity.this.startActivity(startIntent);
				StartActivity.this.finish();
			}
		},SPLASH_DISPLAY_LENGHT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_start, menu);
		return true;
	}

}
