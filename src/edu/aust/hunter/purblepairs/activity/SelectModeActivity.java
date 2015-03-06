package edu.aust.hunter.purblepairs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.purblepairs.activity.R;

/**
 * @author hutao
 *选择游戏的模式：挑战模式，普通模式，竞技模式
 *挑战模式：每一关增加牌的数量
 *普通模式：每一关缩短时间的计时模式
 *竞技模式：只有一关，计算耗时最短。每连续翻错指定数量的牌，调换指定数量牌的顺序
 */
//翻牌游戏 模式选择 1 代表 普通模式
//1 普通模式关卡递增 时间递减
//2代表竞技模式，只有关记录翻牌完成的耗时
//0监测错误
//这里的两个模式共用一个Activity
public class SelectModeActivity extends Activity {

	private Button btnChallege;
	private Button btnGeneral;
	private Button btnCompetion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_select_mode);
		this.findId();
		this.setListener();
	}
//	获得控件Id
	private void findId()
	{
		this.btnChallege=(Button)findViewById(R.id.selbutton1);
		this.btnGeneral=(Button)findViewById(R.id.selbutton2);
		this.btnCompetion=(Button)findViewById(R.id.selbutton3);
	}
	private void setListener()
	{
		this.btnChallege.setOnClickListener(new BtnChallegeListener());
		this.btnGeneral.setOnClickListener(new BtnGeneralListener());
		this.btnCompetion.setOnClickListener(new BtnCompetionListener());
	}
//	设置button监听器
	class BtnChallegeListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent intent = new Intent(SelectModeActivity.this,ChallengeGameActivity1.class);
			SelectModeActivity.this.startActivity(intent);
		}
		
	}
	class BtnGeneralListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent intent = new Intent(SelectModeActivity.this,GeneralGameActivity1.class);
			intent.putExtra("switchMode", 1);
			SelectModeActivity.this.startActivity(intent);
		}
		
	}
	
	class BtnCompetionListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent intent = new Intent(SelectModeActivity.this,GeneralGameActivity1.class);
			intent.putExtra("switchMode", 2);
			SelectModeActivity.this.startActivity(intent);
		}
		
	}
}
