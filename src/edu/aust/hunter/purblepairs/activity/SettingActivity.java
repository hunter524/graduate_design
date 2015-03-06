package edu.aust.hunter.purblepairs.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.purblepairs.activity.R;

import edu.aust.hunter.purblepairs.resource.MediaPlay;

/**
 * @author hutao
 * SettingActivity游戏设置界面
 * 设置震动，声音.............
 *
 */
public class SettingActivity extends Activity {

	private ImageView stimg1;
	private ImageView stimg2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_setting);
		 stimg1 = (ImageView)findViewById(R.id.stimgView);
		 if (MediaPlay.soundOnOff1==true) {
			 SettingActivity.this.stimg1.setBackgroundResource(R.drawable.chice);
		}
		 if (MediaPlay.soundOnOff1==false) {
			 SettingActivity.this.stimg1.setBackgroundResource(R.drawable.no_chice);
		}
		stimg1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (MediaPlay.soundOnOff1==true) {
					SettingActivity.this.stimg1.setBackgroundResource(R.drawable.no_chice);
					MediaPlay.soundOnOff1=false;
				}
				else if (MediaPlay.soundOnOff1==false){
					SettingActivity.this.stimg1.setBackgroundResource(R.drawable.chice);
					MediaPlay.soundOnOff1=true;
				}
			}
		});
		 stimg2 = (ImageView)findViewById(R.id.stimgView2);
		 if (MediaPlay.soundOnOff2==true) {
			 SettingActivity.this.stimg2.setBackgroundResource(R.drawable.chice);
		}
		 if (MediaPlay.soundOnOff2==false) {
			 SettingActivity.this.stimg2.setBackgroundResource(R.drawable.no_chice);
		}
		stimg2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (MediaPlay.soundOnOff2==true) {
					SettingActivity.this.stimg2.setBackgroundResource(R.drawable.no_chice);
					MediaPlay.soundOnOff2=false;
				}
				else if (MediaPlay.soundOnOff2==false){
					SettingActivity.this.stimg2.setBackgroundResource(R.drawable.chice);
					MediaPlay.soundOnOff2=true;
				}
			}
		});
	}
}
