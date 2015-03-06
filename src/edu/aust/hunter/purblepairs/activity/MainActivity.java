package edu.aust.hunter.purblepairs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.purblepairs.activity.R;

/**
 * @author hutao
 *除启动界面的主界面，提供功能选择界面
 *可以进入设置、关于、模式选择界面
 *也可以直接进行声音以及震动的开关
 */
public class MainActivity extends Activity {

	private Button btnMode=null;
	private  Button btnSetting=null;
	private  Button btnAbout=null;
	private ImageView mainImg=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		findId();
		setListener();
//		关于信息的展示
		mainImg=(ImageView)findViewById(R.id.mainImageView1);
		mainImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(MainActivity.this,AboutActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});


	}
//	获得布局控件Id
	private void findId() {
		this.btnMode=(Button) findViewById(R.id.mabutton1);
		this.btnSetting=(Button) findViewById(R.id.mabutton2);
		this.btnAbout=(Button) findViewById(R.id.mabutton3);
	}
//	设置监听器
	private void setListener()
	{
		this.btnAbout.setOnClickListener(new BtnAboutListenner());
		this.btnMode.setOnClickListener(new BtnModeListenner());
		this.btnSetting.setOnClickListener(new BtnSettingListenner());
	}
	
//	设置Button监听器类
	class BtnModeListenner implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent intent = new Intent(MainActivity.this,SelectModeActivity.class);
			MainActivity.this.startActivity(intent);
		}
		
	}
	class BtnSettingListenner implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent intent = new Intent(MainActivity.this,SettingActivity.class);
			MainActivity.this.startActivity(intent);
		}
		
	}
	
	class BtnAboutListenner implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent intent = new Intent(MainActivity.this,HelpActivity.class);
			MainActivity.this.startActivity(intent);
		}
		
	}
	
}
