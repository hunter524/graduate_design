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

import com.example.purblepairs.activity.R;

/**
 * @author hutao
 *存储游戏模式和分数
 *1普通模式 2 竞技模式 3 挑战模式
 *根据不同模式设置显示不同的成功图片
 */
public class SuccessActivity extends Activity {

	private int gameMode;
	private int score;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_success);
		Intent intent = getIntent();
		this.gameMode=intent.getIntExtra("switchMode", 0);
		this.score= intent.getIntExtra("socer", 0);
		Button suButton1 = (Button)findViewById(R.id.successbutton1);
		Button suButton2 = (Button)findViewById(R.id.successbutton2);
		Button suButton3 = (Button)findViewById(R.id.successbutton3);
		if (gameMode==3) {
			suButton1.setText(""+RunningActivity.challengeGameActivity1.getFinalScore());
		}
		if (gameMode==1||gameMode==2) {
			suButton1.setText(""+RunningActivity.GeneralAndCompetitionGameActivity.getFinalScore());
		}
		
		if (this.gameMode==2) {
			suButton2.setText("再来一局");	
		}
		
		suButton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
			}
		});
		suButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (gameMode==1) {
					RunningActivity.GeneralAndCompetitionGameActivity.setNextLevel();
					RunningActivity.GeneralAndCompetitionGameActivity.showAllImgView();
					RunningActivity.GeneralAndCompetitionGameActivity.setPause=false;
					SuccessActivity.this.finish();
					SuccessActivity.this.onDestroy();
				}
				if (gameMode==2) {
					RunningActivity.GeneralAndCompetitionGameActivity.setNextLevel();
					RunningActivity.GeneralAndCompetitionGameActivity.showAllImgView();
					RunningActivity.GeneralAndCompetitionGameActivity.setPause=false;
					SuccessActivity.this.finish();
					SuccessActivity.this.onDestroy();
				}
				if(gameMode==3)
				{
					if(RunningActivity.challengeGameActivity1.Level<3)
					{
						RunningActivity.challengeGameActivity1.Level++;
					}
					RunningActivity.challengeGameActivity1.setPause=false;
					SuccessActivity.this.finish();
					SuccessActivity.this.onDestroy();
				}
			}
		});
		suButton3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (gameMode==1||gameMode==2) {
					RunningActivity.GeneralAndCompetitionGameActivity.exitBoolean=true;
					SuccessActivity.this.finish();
					SuccessActivity.this.onDestroy();
				}
				if (gameMode==3) {
					RunningActivity.challengeGameActivity1.exitBoolean=true;
					SuccessActivity.this.finish();
					SuccessActivity.this.onDestroy();
				}

			}
		});
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	// TODO Auto-generated method stub
	return false;
	}
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
