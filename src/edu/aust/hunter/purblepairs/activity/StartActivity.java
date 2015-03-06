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
 * StartActivity��ʾһ����������ͼƬ
 * ��ʾ���֮����ת��MainActivity
 *
 */
public class StartActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 2000; // �����ӳ�ʱ��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_start);
//		���������ӳ�����������
//		����  SPLASH_DISPLAY_LENGHT  �����ӳ�ʱ��
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
