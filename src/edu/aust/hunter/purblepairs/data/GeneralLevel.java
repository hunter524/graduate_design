package edu.aust.hunter.purblepairs.data;


public class GeneralLevel {
	static final int level0Time=0;
	static final int level1Time=160;
	static final int level2Time=140;
	static final int level3Time=120;
	static final int level4Time=100;
	static final int level5Time=90;
	static final int level6Time=80;
	static final int level7Time=70;
	static final int level8Time=60;
	static final int level9Time=50;
	static final int level10Time=40;
	static final int level11Time=30;
	static final int level12Time=20;
	static final int level13Time=10;
	static final int level14Time=5;

	
	static public int getlevelTime(int level)
	{
		if (level==1) {
			return level1Time;
		}
		if (level==2) {
			return level2Time;
		}
		if (level==3) {
			return level3Time;
		}
		if (level==4) {
			return level4Time;
		}
		if (level==5) {
			return level5Time;
		}
		if (level==6) {
			return level6Time;
		}
		if (level==7) {
			return level7Time;
		}
		if (level==8) {
			return level8Time;
		}
		if (level==9) {
			return level9Time;
		}
		if (level==10) {
			return level10Time;
		}
		if (level==11) {
			return level11Time;
		}
		if (level==12) {
			return level12Time;
		}
		if (level==13) {
			return level13Time;
		}
		if (level==14) {
			return level14Time;
		}
		if (level==0) {
			return level0Time;
		}
		else {
			return 2;
		}
		
	}


}
