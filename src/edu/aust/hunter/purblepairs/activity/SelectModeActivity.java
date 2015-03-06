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
 *ѡ����Ϸ��ģʽ����սģʽ����ͨģʽ������ģʽ
 *��սģʽ��ÿһ�������Ƶ�����
 *��ͨģʽ��ÿһ������ʱ��ļ�ʱģʽ
 *����ģʽ��ֻ��һ�أ������ʱ��̡�ÿ��������ָ���������ƣ�����ָ�������Ƶ�˳��
 */
//������Ϸ ģʽѡ�� 1 ���� ��ͨģʽ
//1 ��ͨģʽ�ؿ����� ʱ��ݼ�
//2������ģʽ��ֻ�йؼ�¼������ɵĺ�ʱ
//0������
//���������ģʽ����һ��Activity
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
//	��ÿؼ�Id
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
//	����button������
	class BtnChallegeListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			Intent intent = new Intent(SelectModeActivity.this,ChallengeGameActivity1.class);
			SelectModeActivity.this.startActivity(intent);
		}
		
	}
	class BtnGeneralListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			Intent intent = new Intent(SelectModeActivity.this,GeneralGameActivity1.class);
			intent.putExtra("switchMode", 1);
			SelectModeActivity.this.startActivity(intent);
		}
		
	}
	
	class BtnCompetionListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			Intent intent = new Intent(SelectModeActivity.this,GeneralGameActivity1.class);
			intent.putExtra("switchMode", 2);
			SelectModeActivity.this.startActivity(intent);
		}
		
	}
}
