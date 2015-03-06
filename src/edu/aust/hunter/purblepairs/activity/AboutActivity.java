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
 *用于显示开发者的相关信息，以及游戏的相关信息
 */
public class AboutActivity extends Activity {


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		WindowManager.LayoutParams lp=getWindow().getAttributes();
		lp.alpha=0.6f;
		getWindow().setAttributes(lp);
		setContentView(R.layout.activity_about);
	}

}
