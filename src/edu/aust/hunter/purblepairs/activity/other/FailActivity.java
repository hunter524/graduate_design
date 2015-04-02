package edu.aust.hunter.purblepairs.activity.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.purblepairs.activity.R;

public class FailActivity extends Activity {

	private int gameMode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_fail);
		Intent intent = getIntent();
		this.gameMode=intent.getIntExtra("switchMode", 0);

		
		
		
		TextView faiTextView1=(TextView)findViewById(R.id.failtextView1);
		Button failButton1 = (Button)findViewById(R.id.failbutton1);
		Button failButton2 = (Button)findViewById(R.id.failbutton2);
		Button failButton3 = (Button)findViewById(R.id.failbutton3);
		failButton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
			}
		});
		failButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (gameMode==1||gameMode==2) {
					RunningActivity.GeneralAndCompetitionGameActivity.setPause=false;
					FailActivity.this.finish();
				}
				if (gameMode==3) {
					RunningActivity.challengeGameActivity1.setPause=false;
					FailActivity.this.finish();
				}

			}
		});
		failButton3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (gameMode==1||gameMode==2) {
					RunningActivity.GeneralAndCompetitionGameActivity.exitBoolean=true;
					FailActivity.this.finish();
				}
				if (gameMode==3) {
					RunningActivity.challengeGameActivity1.exitBoolean=true;
					FailActivity.this.finish();
				}

			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
	// TODO Auto-generated method stub
	return false;
	}
//	屏蔽返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自动生成的方法存根
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
