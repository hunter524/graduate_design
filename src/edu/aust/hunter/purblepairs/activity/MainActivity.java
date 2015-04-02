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
 *����������������棬�ṩ����ѡ�����
 *���Խ������á����ڡ�ģʽѡ�����
 *Ҳ����ֱ�ӽ��������Լ��𶯵Ŀ���
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
//		������Ϣ��չʾ
		mainImg=(ImageView)findViewById(R.id.mainImageView1);
		mainImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(MainActivity.this,AboutActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});


	}
//	��ò��ֿؼ�Id
	private void findId() {
		this.btnMode=(Button) findViewById(R.id.mabutton1);
		this.btnSetting=(Button) findViewById(R.id.mabutton2);
		this.btnAbout=(Button) findViewById(R.id.mabutton3);
	}
//	���ü�����
	private void setListener()
	{
		this.btnAbout.setOnClickListener(new BtnAboutListenner());
		this.btnMode.setOnClickListener(new BtnModeListenner());
		this.btnSetting.setOnClickListener(new BtnSettingListenner());
	}
	
//	����Button��������
	class BtnModeListenner implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			Intent intent = new Intent(MainActivity.this,SelectModeActivity.class);
			MainActivity.this.startActivity(intent);
		}
		
	}
	class BtnSettingListenner implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			Intent intent = new Intent(MainActivity.this,SettingActivity.class);
			MainActivity.this.startActivity(intent);
		}
		
	}
	
	class BtnAboutListenner implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			Intent intent = new Intent(MainActivity.this,HelpActivity.class);
			MainActivity.this.startActivity(intent);
		}
		
	}
	
}
