package edu.aust.hunter.purblepairs.resource;

import java.util.ArrayList;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextThemeWrapper;

import com.example.purblepairs.activity.R;

/**
 * @author hutao
 *此类写图片大小缩放，以及图片显示效果控制的函数
 *图片的屏幕的适配函数在这个类完成
 */
public class PictureSetting {
	public ArrayList<Bitmap> bitmapList=new ArrayList<Bitmap>();
	public Bitmap backBitmap;
	private int displayWidth;
	private int displayHeigth;
	private float displayDensity;
	private int scaleWidth;
	private int scaleHeight;
	public double scale=0.8;
	
//	设置屏幕相关参数屏幕宽 高 像素  以及像素密度参数
	public void setDisplayPra(int displayWidth,int displayHeigth,float displayDensity) {
		this.displayWidth=displayWidth;
		this.displayDensity=displayDensity;
		this.displayHeigth=displayHeigth;
		this.scaleHeight=(int) (displayHeigth*0.8/5*this.scale);
		this.scaleWidth=(int) (((int)(displayWidth/100))*100/4*this.scale);
	}
	public int getScaleHeigh() {
		return this.scaleHeight;
	}
	public int getScalWidth() {
		return this.scaleWidth;
	}
	

	
//	对传入的图片，缩放到指定尺寸
	public void scalePicture()
	{
//		 Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.front); 
	     
	     for (int i = 0; i < this.bitmapList.size(); i++) {
	    	 Bitmap newBitmap = Bitmap.createScaledBitmap(this.bitmapList.get(i)
	    			 , this.scaleWidth, this.scaleHeight, true);
	    	 this.bitmapList.set(i,newBitmap);
		}
	     Bitmap  newBitmap = Bitmap.createScaledBitmap(backBitmap, this.scaleWidth, this.scaleHeight, true);
	     backBitmap=newBitmap;
	}
//	dp转换成px
	public float dipTopx(float dip)
	{
		float px=0;
		px= dip *this.displayDensity/ 160;
		return px;	
	}
//	px转换成dp
	public float pxTodip(float px)
	{
		float dip=0;
		dip=  px/(this.displayDensity/ 160);
		return dip;	
	}

}
