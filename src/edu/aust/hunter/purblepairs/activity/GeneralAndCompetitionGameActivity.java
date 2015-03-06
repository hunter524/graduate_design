package edu.aust.hunter.purblepairs.activity;

import java.util.ArrayList;
import java.util.Random;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.purblepairs.activity.R;
import edu.aust.hunter.purblepairs.data.GeneralLevel;
import edu.aust.hunter.purblepairs.data.JudgePairs;
import edu.aust.hunter.purblepairs.data.Score;
import edu.aust.hunter.purblepairs.resource.MediaPlay;
import edu.aust.hunter.purblepairs.resource.PictureSetting;

/**
 * @author hutao
 * 游戏界面
 * 游戏的主体所有游戏在这个界面显示
 * 提供给玩家进行翻牌交互
 *
 */


public class GeneralAndCompetitionGameActivity extends Activity {
//	翻牌游戏 模式选择 1 代表 普通模式
//	1 普通模式关卡递增 时间递减
//	2代表竞技模式，只有关记录翻牌完成的耗时
//	0监测错误
	public MediaPlay mediaPlay ;
	private Boolean pauseButtonBoolean= false;
	private ImageView ggImg;
	private int switchmode=0;
//	存储图片和设置图片大小private
	private PictureSetting pSetting;
//	存储ImgView控件，通过下标获取控件
	private  ArrayList<ImageView> imgList;
//	通过imgview 下标获取随机分配的图片Id
	private ArrayList<Integer> buttonIdToPicId ;
//	存储每一张牌是否被翻拍了
//	true表示可以翻，false表示已经被翻了，不可以再翻
	private ArrayList<Boolean> imgBooleans ;
	private JudgePairs judgePairs;
//	记录关卡数
	static int Level=0;
//	记录关卡的时间数
	private int levelTime=0;
	private TextView ggtextView2;
	private TextView ggTextView1;
	private TextView ggTextView3;
//	记录已经翻牌的次数
	private int countOver=0;
//	记录已经消掉的牌的对数
	private int countRemove=0;
//	计时进程 
//	区分普通模式 和竞技模式的计时方式
//	普通模式使用handler1时间递减 倒数计时
//	竞技模式使用handler2时间递增计算完成时的用时
	public Handler handler1=new Handler();
	public Handler handler2 = new Handler();	
	private UpdateRunnable1 updateRunnable1=new UpdateRunnable1();
	private UpdateRunnable2 updateRunnable2=new UpdateRunnable2();
	public  Boolean setPause = false;
//当子窗口退出时标记这个值如果为true则该父窗口也消失
//	如果为false则不消失
	public Boolean exitBoolean=false;
//	计分对象
	public Score score;
	
	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		mediaPlay.backMusicReset();
		this.handler1.removeCallbacks(updateRunnable1);
		this.handler2.removeCallbacks(updateRunnable2);
		this.setPause=true;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_game_general);

		mediaPlay=new MediaPlay(GeneralAndCompetitionGameActivity.this);
		RunningActivity.generalGameActivity=GeneralAndCompetitionGameActivity.this;
		Intent recIntent =getIntent();
		switchmode=recIntent.getIntExtra("switchMode", 0);
		GeneralAndCompetitionGameActivity.Level=1;

	}
//	覆写onStart方法
	

//	覆写onResume方法，再来一局和进入下一关的时候调用
	
/**
 * 	随机分配牌并记录每个imageView对应的牌
 * 同关imgview的编号可以获取按钮所显示的图片的编号
 * buttonIdToPicId是一个List
 * 其下标表示button的id
 *
 */
	private void radomSignPic()
	{
		ArrayList< Integer> arrayList1=new ArrayList<Integer>();
		ArrayList< Integer> arrayList2=new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			this.buttonIdToPicId.add(255);
			arrayList1.add(i);
		}
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			int randtemp = random.nextInt(20-i);
			arrayList2.add(arrayList1.get(randtemp));
			arrayList1.remove(randtemp);
		}
//		buttonIdToPicId初始化存储每个ImgView所对应的的图片
		for (int i = 0; i < arrayList2.size(); i++) {
			buttonIdToPicId.set(arrayList2.get(i), i%10);
			imgList.get(arrayList2.get(i)).setImageBitmap(pSetting.bitmapList.get(i%10));
		}
		
	}
	
	
@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		mediaPlay.backMusicStart();
		this.score=new Score();
		this.countRemove=0;
//		当子窗口点击退出时，父窗口也随之消失并返回
		if (GeneralAndCompetitionGameActivity.this.exitBoolean)
		{
			handler1.removeCallbacks(updateRunnable1);
			handler2.removeCallbacks(updateRunnable2);
			GeneralAndCompetitionGameActivity.this.finish();
			GeneralAndCompetitionGameActivity.this.onDestroy();
			return;
		}
		if (switchmode==1) {
			this.setFuction1();
			this.showAllImgView();
		}
		if (switchmode==2) {
			this.setFuction2();
			this.showAllImgView();
		}
		if (switchmode==0) {
			
		}
		
		
	}


@Override
	protected void onRestart() {
		// TODO 自动生成的方法存根
		super.onRestart();
		System.out.println("调用此方法");
	}


@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
//		初始化相关变量
		
//		GeneralGameActivity.Level=1;
//		this.setFuction1();
	}



/**
 * 获得图片资源
 * 存储进入pSetting的bitmapList中
 *
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
		pSetting.backBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.back);
		
	}
	/**
	 * 获得控件的id
	 * 将二十个ImageView的Id传入imgList中方便调用
	 * 其余控件一一存放
	 */
	private void getWidetId()
	{
		this.ggImg=(ImageView)findViewById(R.id.ggtimageView1);
		this.ggTextView1= (TextView)findViewById(R.id.ggtextView1);
		this.ggtextView2= (TextView)findViewById(R.id.ggtextView2);
		this.ggTextView3= (TextView)findViewById(R.id.ggtextView3);
		ImageView imageView;
		imageView=(ImageView)findViewById(R.id.ggimageView1);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView2);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView3);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView4);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView5);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView6);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView7);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView8);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView9);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView10);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView11);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView12);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView13);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView14);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView15);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView16);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView17);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView18);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView19);
		this.imgList.add(imageView);
		imageView=(ImageView)findViewById(R.id.ggimageView20);
		this.imgList.add(imageView);
	}
	
	/**
	 * 	将屏幕的参数传给PictureSetting类
	 */
	private void getSetWindPra ()
	{
		 DisplayMetrics dm = new DisplayMetrics();  
	     this.getWindowManager().getDefaultDisplay().getMetrics(dm);  
	     pSetting.setDisplayPra(dm.widthPixels, dm.heightPixels, dm.density);
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
			if (switchmode==1) {
				imgClick1(imgId);
			}
			if (switchmode==2) {
				imgClick2(imgId);
			}
			
		}
	
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
	 * 设置img为显示背景，同时初始化imgBooleans使其为可点击状态
	 */
	private void setImgViewBack () {

		for (int i = 0; i < imgList.size(); i++) {
			imgList.get(i).setImageBitmap(pSetting.backBitmap);
			imgBooleans.add(true);
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
		   Animation animation = AnimationUtils.loadAnimation(GeneralAndCompetitionGameActivity.this, R.anim.back_scale);
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
					imgList.get(imgId).startAnimation(AnimationUtils.loadAnimation(GeneralAndCompetitionGameActivity.this, R.anim.front_scale));
				}
			});
    	   imgList.get(imgId).startAnimation(animation);
	}
	/**
	 * 普通模式计时模块
	 *
	 */
	class UpdateRunnable1 implements Runnable{
		@Override
		public void run() {
			if (levelTime!=0) {
				levelTime--;
				ggtextView2.setText(""+levelTime);
			}
			else if (levelTime==0) {
				mediaPlay.failPlay();
				//将线程从Handler对象中移除
				handler1.removeCallbacks(updateRunnable1);
				//关闭游戏，回到首页
				GeneralAndCompetitionGameActivity.this.onPause();
				Intent startIntent = new Intent(GeneralAndCompetitionGameActivity.this,FailActivity.class);
				GeneralAndCompetitionGameActivity.this.startActivity(startIntent);
				return;
			}
			else {

			}
			//调用线程
			handler1.postDelayed(updateRunnable1,1000);

		}
	}
	/**
	 * 比赛竞技模式
	 * 计时模块
	 *
	 */
	class UpdateRunnable2 implements Runnable{
		@Override
		public void run() {
				levelTime++;
				ggtextView2.setText(""+levelTime);
			//调用线程
			handler2.postDelayed(updateRunnable2,1000);
		}
	}
	/**
	 * 普通模式功能设置
	 *
	 */
	private void setFuction1()
	{
		if (setPause==false) {
			this.levelTime=GeneralLevel.getlevelTime(GeneralAndCompetitionGameActivity.Level);
		}	
		this.imgBooleans=new ArrayList<Boolean>();
		this.buttonIdToPicId=new ArrayList<Integer>();
		this.imgList=new ArrayList<ImageView>();
		pSetting=new PictureSetting();
		judgePairs = new JudgePairs();
//		调用相关功能函数
		this.getWidetId();
		this.ggTextView1.setText(""+0);
		this.ggtextView2.setText(""+this.levelTime);
		this.ggTextView3.setText(""+Level);
		this.getSetWindPra();
		this.getResToList();
		pSetting.scalePicture();
		this.radomSignPic();
		this.setImgViewBack();
		this.setImgListener();
		this.setPauseButton1();
		this.handler1.postDelayed(updateRunnable1, 1000);
	}
	/**
	 * 比赛竞技模式
	 * 功能设置
	 *
	 */
	private void setFuction2()
	{
		this.imgBooleans=new ArrayList<Boolean>();
		this.buttonIdToPicId=new ArrayList<Integer>();
		this.imgList=new ArrayList<ImageView>();
		pSetting=new PictureSetting();
		judgePairs = new JudgePairs();
//		调用相关功能函数

		this.getWidetId();
		GeneralAndCompetitionGameActivity.Level=0;
		if(this.setPause==false)
		{
			this.levelTime = GeneralLevel.getlevelTime(Level);
		}
		
		this.ggTextView1.setText("0");
		this.ggtextView2.setText(""+this.levelTime);
		this.ggTextView3.setText(""+(GeneralAndCompetitionGameActivity.Level+1));
		this.getSetWindPra();
		this.getResToList();
		pSetting.scalePicture();
		this.radomSignPic();
		this.setImgViewBack();
		this.setImgListener();
		this.setPauseButton2();
		this.handler2.postDelayed(updateRunnable2, 1000);
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
			countOver++;
			mediaPlay.backToFrontPlay();
			imgBooleans.set(imgId, false);
		}

		if (temp==-1) {


		}
		if (temp==1) {
			this.score.add();
			this.ggTextView1.setText(""+this.score.getScore());
			imgList.get(judgePairs.getImg1()).setVisibility(View.INVISIBLE);
			imgList.get(judgePairs.getImg2()).setVisibility(View.INVISIBLE);
			this.countRemove++;
//			全部消除完跳出，成功窗口
//			注意消除是设为不可见！！！！
			mediaPlay.samePlay();
			System.out.println(this.countRemove);
		
			if (this.countRemove==10) {
				handler1.removeCallbacks(updateRunnable1);
				GeneralAndCompetitionGameActivity.this.onPause();
				Intent startIntent = new Intent(GeneralAndCompetitionGameActivity.this,SuccessActivity.class);
				startIntent.putExtra("switchMode", switchmode);
				GeneralAndCompetitionGameActivity.this.startActivity(startIntent);
				mediaPlay.successPlay();
			}
			judgePairs.set255();
		}
		if (temp==0) {
			mediaPlay.differentPlay();
			score.reduce();
			this.ggTextView1.setText(""+score.getScore());
			imgBooleans.set(judgePairs.getImg1(), true);
			imgBooleans.set(judgePairs.getImg2(), true);
			startAnimation(judgePairs.getImg1());
			startAnimation(judgePairs.getImg2());
			judgePairs.set255();

		}
		
	}
	/**
	 * 竞技比赛模式
	 * imgView点击事件
	 *
	 */
	private void imgClick2(int imgId)
	{
		int temp = -9999;
		if (imgBooleans.get(imgId)==true) {
			temp = judgePairs.add(buttonIdToPicId.get(imgId),imgId);
			startAnimation(imgId);
			countOver++;
			mediaPlay.backToFrontPlay();
			imgBooleans.set(imgId, false);
		}

		if (temp==-1) {


		}
		if (temp==1) {
			mediaPlay.samePlay();
			this.score.add();
			this.ggTextView1.setText(""+this.score.getScore());
			imgList.get(judgePairs.getImg1()).setVisibility(View.INVISIBLE);
			imgList.get(judgePairs.getImg2()).setVisibility(View.INVISIBLE);
			countRemove++;
//			全部消除完跳出，成功窗口
//			注意消除是设为不可见！！！！
			if (countRemove==10) {
				mediaPlay.successPlay();
				handler2.removeCallbacks(updateRunnable2);
				GeneralAndCompetitionGameActivity.this.onPause();
				Intent startIntent = new Intent(GeneralAndCompetitionGameActivity.this,SuccessActivity.class);
				startIntent.putExtra("switchMode", 2);
				GeneralAndCompetitionGameActivity.this.startActivity(startIntent);
			}
			judgePairs.set255();
		}
		if (temp==0) {
			mediaPlay.differentPlay();
			this.score.reduce();
			this.ggTextView1.setText(""+this.score.getScore());
			imgBooleans.set(judgePairs.getImg1(), true);
			imgBooleans.set(judgePairs.getImg2(), true);
			startAnimation(judgePairs.getImg1());
			startAnimation(judgePairs.getImg2());
			judgePairs.set255();

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
	public int getFinalScore()
	{
		return( Integer.valueOf(this.ggTextView1.getText().toString())+
				Integer.valueOf(this.ggtextView2.getText().toString())*10);
	}
	public void  setNextLevel()
	{
		GeneralAndCompetitionGameActivity.Level++;
		GeneralAndCompetitionGameActivity.this.countRemove=0;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自动生成的方法存根
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
		{
			handler1.removeCallbacks(updateRunnable1);
			handler2.removeCallbacks(updateRunnable2);
			GeneralAndCompetitionGameActivity.this.finish();
			GeneralAndCompetitionGameActivity.this.onDestroy();
		}
			return true;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	public void setPauseButton1()
	{
		this.ggImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (GeneralAndCompetitionGameActivity.this.pauseButtonBoolean==false) {
					GeneralAndCompetitionGameActivity.this.pauseButtonBoolean=true;
					GeneralAndCompetitionGameActivity.this.ggImg.setImageResource(R.drawable.restart1);
					GeneralAndCompetitionGameActivity.this.handler1.removeCallbacks(updateRunnable1);
					for (int i = 0; i < imgList.size(); i++) {
						imgList.get(i).setClickable(false);
					}
				}
				else if (GeneralAndCompetitionGameActivity.this.pauseButtonBoolean==true) {
					GeneralAndCompetitionGameActivity.this.pauseButtonBoolean=false;
					GeneralAndCompetitionGameActivity.this.ggImg.setImageResource(R.drawable.pause1);
					for (int i = 0; i < imgList.size(); i++) {
						imgList.get(i).setClickable(true);
					}
					GeneralAndCompetitionGameActivity.this.handler1.postDelayed(updateRunnable1, 1000);
				}
			}
		});


	}
	public void  setPauseButton2() {

		this.ggImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (GeneralAndCompetitionGameActivity.this.pauseButtonBoolean==false) {
					GeneralAndCompetitionGameActivity.this.pauseButtonBoolean=true;
					GeneralAndCompetitionGameActivity.this.ggImg.setImageResource(R.drawable.restart1);
					GeneralAndCompetitionGameActivity.this.handler2.removeCallbacks(updateRunnable2);
					for (int i = 0; i < imgList.size(); i++) {
						imgList.get(i).setClickable(false);
					}
				}
				else if (GeneralAndCompetitionGameActivity.this.pauseButtonBoolean==true) {
					GeneralAndCompetitionGameActivity.this.pauseButtonBoolean=false;
					GeneralAndCompetitionGameActivity.this.ggImg.setImageResource(R.drawable.pause1);
					for (int i = 0; i < imgList.size(); i++) {
						imgList.get(i).setClickable(true);
					}
					GeneralAndCompetitionGameActivity.this.handler2.postDelayed(updateRunnable2, 1000);
				}
			}
		});


	
		
	}

	
}

