package edu.aust.hunter.purblepairs.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.purblepairs.activity.R;

import edu.aust.hunter.purblepairs.activity.GeneralAndCompetitionGameActivity.imgListener;
import edu.aust.hunter.purblepairs.data.GeneralLevel;
import edu.aust.hunter.purblepairs.data.JudgePairs;
import edu.aust.hunter.purblepairs.data.Score;
import edu.aust.hunter.purblepairs.resource.MediaPlay;
import edu.aust.hunter.purblepairs.resource.PictureSetting;

public class ChallengeGameActivity extends Activity {
	
	public MediaPlay mediaPlay ;
	public  Boolean setPause = false;

	private Boolean pauseButtonBoolean = false;
	private ImageView cgtImg;
//	存储图片和设置图片大小private
	private PictureSetting pSetting=new PictureSetting();
//	存储ImgView控件，通过下标获取控件
	private  ArrayList<ImageView> imgList;
//	通过imgview 下标获取随机分配的图片Id
	private ArrayList<Integer> buttonIdToPicId ;
//	存储每一张牌是否被翻拍了
//	true表示可以翻，false表示已经被翻了，不可以再翻
	private ArrayList<Boolean> imgBooleans ;
	private JudgePairs judgePairs;
//	记录关卡数
	public  int Level=1;
//	记录关卡的时间数
	private int levelTime=0;
//	记录已经消掉的牌的对数
	private int countRemove=0;
//	计时进程 
//	区分普通模式 和竞技模式的计时方式
//	普通模式使用handler1时间递减 倒数计时
//	竞技模式使用handler2时间递增计算完成时的用时
	public Handler handler1=new Handler();
	private UpdateRunnable1 updateRunnable1=new UpdateRunnable1();
//当子窗口退出时标记这个值如果为true则该父窗口也消失
//	如果为false则不消失
	public Boolean exitBoolean=false;
//	计分对象
	public Score score;
//	标题部分的控件
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		RunningActivity.challengeGameActivity1=ChallengeGameActivity.this;
		mediaPlay=new MediaPlay(ChallengeGameActivity.this);
	}
	
	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		
		super.onResume();
		mediaPlay.backMusicStart();
		this.score=new Score();
		this.countRemove=0;
//		当子窗口点击退出时，父窗口也随之消失并返回
		if (ChallengeGameActivity.this.exitBoolean)
		{
			handler1.removeCallbacks(updateRunnable1);
			ChallengeGameActivity.this.finish();
			ChallengeGameActivity.this.onDestroy();
			return;
		}
		if (this.Level==1) {
			setContentView(R.layout.activity_game_challege1);
			setFuction1();
		}
		if (this.Level==2) {
			setContentView(R.layout.activity_game_challege2);
			setFuction2();
		}
		if (this.Level==3) {
			setContentView(R.layout.activity_game_challege3);
			setFuction3();
		}
	}
	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		mediaPlay.backMusicReset();
		this.handler1.removeCallbacks(updateRunnable1);
		this.setPause=true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自动生成的方法存根
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
		{
			handler1.removeCallbacks(updateRunnable1);
			ChallengeGameActivity.this.finish();
			ChallengeGameActivity.this.onDestroy();
		}
			return true;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 设置监听器
	 */
	private void setImgListener() {
		for (int i = 0; i < imgList.size(); i++) {
			imgList.get(i).setOnClickListener(new imgListener(i));
		}
	}
	/**
	 *  ImgView监听器类设置
	 *  根据所选择的模式不同设置不同的Click动作
	 */
	class imgListener implements OnClickListener
	{

		private Boolean bool;
		int imgId;
		int picId;
		public imgListener(int imgId) {
			super();
			this.imgId = imgId;
			picId=buttonIdToPicId.get(imgId);
			bool=false;
		}
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			imgClick1(imgId);
			
		}
	
	}	
	/**
	 * 普通模式imgView点击事件设置
	 *
	 */
	private void imgClick1(int imgId)
	{
		int temp = -9999;
		if (imgBooleans.get(imgId)==true) {
			temp = judgePairs.add(buttonIdToPicId.get(imgId),imgId);
			startAnimation(imgId);
//			countOver++;
			mediaPlay.backToFrontPlay();
			imgBooleans.set(imgId, false);
			mediaPlay.backToFrontPlay();
		}

		if (temp==-1) {


		}
		if (temp==1) {
			this.score.add();
			this.textView1.setText(""+this.score.getScore());
			imgList.get(judgePairs.getImg1()).setVisibility(View.INVISIBLE);
			imgList.get(judgePairs.getImg2()).setVisibility(View.INVISIBLE);
			this.countRemove++;
//			全部消除完跳出，成功窗口
//			注意消除是设为不可见！！！！
			mediaPlay.samePlay();
			System.out.println(this.countRemove);
		
			if (this.countRemove==(imgList.size()/2) ){
				handler1.removeCallbacks(updateRunnable1);
				ChallengeGameActivity.this.onPause();
				Intent startIntent = new Intent(ChallengeGameActivity.this,SuccessActivity.class);
				startIntent.putExtra("switchMode", 3);
				ChallengeGameActivity.this.startActivity(startIntent);
				mediaPlay.successPlay();
			}
			judgePairs.set255();
		}
		if (temp==0) {
			score.reduce();
			this.textView1.setText(""+score.getScore());
			imgBooleans.set(judgePairs.getImg1(), true);
			imgBooleans.set(judgePairs.getImg2(), true);
			startAnimation(judgePairs.getImg1());
			startAnimation(judgePairs.getImg2());
			judgePairs.set255();
			mediaPlay.differentPlay();

		}
		
	}
	/**
	 * @author hutao
	 *翻牌动画设置
	 *根据传入的imgView的Id值对
	 *控件执行该动画配置
	 */
	private void startAnimation(final int imgId)
	{
		   Animation animation = AnimationUtils.loadAnimation(ChallengeGameActivity.this, R.anim.back_scale);
    	   animation.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}
				@Override
				public void onAnimationRepeat(Animation animation) {
				}
				@Override
				public void onAnimationEnd(Animation animation) {
					if(imgBooleans.get(imgId)){
						imgList.get(imgId).setImageBitmap(pSetting.backBitmap);
					}else {
						imgList.get(imgId).setImageBitmap(pSetting.bitmapList.get(buttonIdToPicId.get(imgId)));

					}
	
					//通过AnimationUtils得到动画配置文件(/res/anim/front_scale.xml),然后在把动画交给ImageView
					imgList.get(imgId).startAnimation(AnimationUtils.loadAnimation(ChallengeGameActivity.this, R.anim.front_scale));
				}
			});
    	   imgList.get(imgId).startAnimation(animation);
	}
	private void setFuction3()
	{
		if (setPause==false) {
			this.levelTime=GeneralLevel.getlevelTime((ChallengeGameActivity.this.Level*3));
		}	
		this.imgBooleans=new ArrayList<Boolean>();
		this.buttonIdToPicId=new ArrayList<Integer>();
		this.imgList=new ArrayList<ImageView>();
		pSetting=new PictureSetting();
		judgePairs = new JudgePairs();
		score=new Score();
//		调用相关功能函数
		this.getWidetId3();
		this.textView1.setText(""+0);
		this.textView2.setText(""+this.levelTime);
		this.textView3.setText(""+Level);
		this.getSetWindPra();
		this.getResToList();
		pSetting.scale=0.3;
		pSetting.scalePicture();
		this.radomSignPic();
		this.setImgViewBack();
		this.setImgListener();
		this.setPauseButton();
		this.handler1.postDelayed(updateRunnable1, 1000);
	}
	private void setFuction2()
	{
		if (setPause==false) {
			this.levelTime=GeneralLevel.getlevelTime(ChallengeGameActivity.this.Level*3);
		}	
		this.imgBooleans=new ArrayList<Boolean>();
		this.buttonIdToPicId=new ArrayList<Integer>();
		this.imgList=new ArrayList<ImageView>();
		pSetting=new PictureSetting();
		judgePairs = new JudgePairs();
		score=new Score();
//		调用相关功能函数
		this.getWidetId2();
		this.textView1.setText(""+0);
		this.textView2.setText(""+this.levelTime);
		this.textView3.setText(""+Level);
		this.getSetWindPra();
		this.getResToList();
		pSetting.scale=0.3;
		pSetting.scalePicture();
		this.radomSignPic();
		this.setImgViewBack();
		this.setImgListener();
		this.setPauseButton();
		this.handler1.postDelayed(updateRunnable1, 1000);
	}
	private void setFuction1()
	{
		if (setPause==false) {
			this.levelTime=GeneralLevel.getlevelTime(ChallengeGameActivity.this.Level*3);
		}	
		this.imgBooleans=new ArrayList<Boolean>();
		this.buttonIdToPicId=new ArrayList<Integer>();
		this.imgList=new ArrayList<ImageView>();
		pSetting=new PictureSetting();
		judgePairs = new JudgePairs();
		score=new Score();
//		调用相关功能函数
		this.getWidetId1();
		this.textView1.setText(""+0);
		this.textView2.setText(""+this.levelTime);
		this.textView3.setText(""+Level);
		this.getSetWindPra();
		this.getResToList();
		pSetting.scale=0.3;
		pSetting.scalePicture();
		this.radomSignPic();
		this.setImgViewBack();
		this.setImgListener();
		this.setPauseButton();
		this.handler1.postDelayed(updateRunnable1, 1000);

	}
	/**
	 * 设置img为显示背景，同时初始化imgBooleans使其为可点击状态
	 */
	private void setImgViewBack () {

		for (int i = 0; i < imgList.size(); i++) {
			imgList.get(i).setImageBitmap(pSetting.backBitmap);
			imgBooleans.add(true);
		}
		
	}
	/**
	 * 使配对成功的imgView重现
	 * 配对成功即消除
	 * 即设置为不可见
	 * 进行下一关时使用此函数使其重新，同时设置imgBooleans
	 * 设置为true使其可见
	 */
		public  void showAllImgView()
		{
			for (int i = 0; i < imgList.size(); i++) {
				this.imgList.get(i).setVisibility(View.VISIBLE);
				this.imgBooleans.set(i, true);
			}
		}
	/**
	 * 	将屏幕的参数传给PictureSetting类
	 * 在调用psetting的方法之前需要调用这个方法
	 */
	private void getSetWindPra ()
	{
		 DisplayMetrics dm = new DisplayMetrics();  
	     this.getWindowManager().getDefaultDisplay().getMetrics(dm);  
	     pSetting.setDisplayPra(dm.widthPixels, dm.heightPixels, dm.density);
	}
	private void radomSignPic()
	{
		ArrayList< Integer> arrayList1=new ArrayList<Integer>();
		ArrayList< Integer> arrayList2=new ArrayList<Integer>();
		for (int i = 0; i < imgList.size(); i++) {
			this.buttonIdToPicId.add(255);
			arrayList1.add(i);
		}
		Random random = new Random();
		for (int i = 0; i < this.imgList.size(); i++) {
			int randtemp = random.nextInt(this.imgList.size()-i);
			arrayList2.add(arrayList1.get(randtemp));
			arrayList1.remove(randtemp);
		}
//		buttonIdToPicId初始化存储每个ImgView所对应的的图片
//		System.out.println(""+arrayList2.size());
//		System.out.println(""+imgList.size());
		for (int i = 0; i < arrayList2.size(); i++) {
			buttonIdToPicId.set(arrayList2.get(i), i%((int)imgList.size()/2));
//			System.out.println("1++"+i);
//			System.out.println("2++"+i%(imgList.size()/2));
			imgList.get(arrayList2.get(i)).setImageBitmap(pSetting.bitmapList.get(i%(imgList.size()/2)));
		}
		
	}
	/**
	 * 在调用此类前需要调用getSetWindPra（）类
	 */
	private void getResToList()
	{
		Bitmap rawBitmap;
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.layer01);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.layer02);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.layer03);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aobama01);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aobama02);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snoden01);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snoden02);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.putin01);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.putin02);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.putin03);
		pSetting.bitmapList.add(rawBitmap);	
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aobama03);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snoden03);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.layer04);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.putin04);
		pSetting.bitmapList.add(rawBitmap);	
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aobama04);
		pSetting.bitmapList.add(rawBitmap);
		rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snoden04);
		pSetting.backBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.back);
		
	}
	
	/**
	 * 用之前先删除所有元素
	 * imglist.clear();方法
	 */
	private void getWidetId1()
	{
		this.cgtImg=(ImageView)findViewById(R.id.cgimageView1);
		this.textView1=(TextView)findViewById(R.id.cgttextView11);
		this.textView2=(TextView)findViewById(R.id.cgttextView12);
		this.textView3=(TextView)findViewById(R.id.cgttextView13);
		this.imageView1=(ImageView)findViewById(R.id.cgtimageView11);
		this.imageView1=(ImageView)findViewById(R.id.cgtimageView12);
		this.imageView1=(ImageView)findViewById(R.id.cgtimageView13);
		ImageView imageView;
		imageView=(ImageView)findViewById(R.id.cgimageView11);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView12);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView13);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView14);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView15);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView16);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView17);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView18);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView19);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView110);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView111);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView112);
		this.imgList.add(imageView);

	}
	/**
	 * 用之前先删除所有元素
	 * imglist.clear();方法
	 */
	private void getWidetId2()
	{
		this.cgtImg=(ImageView)findViewById(R.id.cgimageView2);
		this.textView1=(TextView)findViewById(R.id.cgttextView21);
		this.textView2=(TextView)findViewById(R.id.cgttextView22);
		this.textView3=(TextView)findViewById(R.id.cgttextView23);
		this.imageView1=(ImageView)findViewById(R.id.cgtimageView21);
		this.imageView1=(ImageView)findViewById(R.id.cgtimageView22);
		this.imageView1=(ImageView)findViewById(R.id.cgtimageView23);
		ImageView imageView;
		imageView=(ImageView)findViewById(R.id.cgImageView21);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView22);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView23);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView24);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView25);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView26);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView27);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView28);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView29);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView210);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView211);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView212);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView213);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView214);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView215);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView216);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView217);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView218);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView219);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView220);
		this.imgList.add(imageView);
	}
	public int getFinalScore()
	{
		return( Integer.valueOf(this.textView1.getText().toString())+
				Integer.valueOf(this.textView2.getText().toString())*10);
	}
	/**
	 * 用之前先删除所有元素
	 * imglist.clear();方法
	 */
	private void getWidetId3()
	{
		this.cgtImg=(ImageView)findViewById(R.id.cgimageView3);
		this.textView1=(TextView)findViewById(R.id.cgttextView31);
		this.textView2=(TextView)findViewById(R.id.cgttextView32);
		this.textView3=(TextView)findViewById(R.id.cgttextView33);
		this.imageView1=(ImageView)findViewById(R.id.cgtimageView31);
		this.imageView1=(ImageView)findViewById(R.id.cgtimageView32);
		this.imageView1=(ImageView)findViewById(R.id.cgtimageView33);
		ImageView imageView;
		imageView=(ImageView)findViewById(R.id.cgImageView31);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView32);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView33);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView34);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView35);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView36);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView37);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView38);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView39);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView310);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView311);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView312);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView313);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView314);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView315);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView316);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView317);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView318);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView319);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgimageView320);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView321);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView322);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView323);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView324);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView325);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView326);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView327);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView328);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView329);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.cgImageView330);
		this.imgList.add(imageView);
	}
	public void setPauseButton()
	{
		this.cgtImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (ChallengeGameActivity.this.pauseButtonBoolean==false) {
					ChallengeGameActivity.this.pauseButtonBoolean=true;
					ChallengeGameActivity.this.cgtImg.setImageResource(R.drawable.restart1);
					ChallengeGameActivity.this.handler1.removeCallbacks(updateRunnable1);
					for (int i = 0; i < imgList.size(); i++) {
						imgList.get(i).setClickable(false);
					}
				}
				else if (ChallengeGameActivity.this.pauseButtonBoolean==true) {
					ChallengeGameActivity.this.pauseButtonBoolean=false;
					ChallengeGameActivity.this.cgtImg.setImageResource(R.drawable.pause1);
					for (int i = 0; i < imgList.size(); i++) {
						imgList.get(i).setClickable(true);
					}
					ChallengeGameActivity.this.handler1.postDelayed(updateRunnable1, 1000);
				}
			}
		});


	}

	/* * 普通模式计时模块
	 *
	 */
	class UpdateRunnable1 implements Runnable{
		@Override
		public void run() {
			if (levelTime!=0) {
				levelTime--;
				textView2.setTypeface(null, Typeface.BOLD);
				textView2.setText(""+levelTime);
			}
			else if (levelTime==0) {
				mediaPlay.failPlay();
				//将线程从Handler对象中移除
				handler1.removeCallbacks(updateRunnable1);
				//关闭游戏，回到首页
				ChallengeGameActivity.this.onPause();
				Intent startIntent = new Intent(ChallengeGameActivity.this,FailActivity.class);
				startIntent.putExtra("switchMode", 3);
				ChallengeGameActivity.this.startActivity(startIntent);

				return;
			}
			else {

			}
			//调用线程
			handler1.postDelayed(updateRunnable1,1000);

		}
	}
}

