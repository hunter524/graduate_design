package edu.aust.hunter.purblepairs.data;


public class JudgePairs {

	int img1,img2;
	int picId1,picId2;

	public JudgePairs() {
		super();
		this.img1 = 255;
		this.img2 = 255;
		this.picId1=255;
		this.picId2=255;
	}
	public int add(int picId,int img) {
		
		if (picId1==255) {
			picId1=picId;
			img1=img;
		}
		else if (picId1!=255&&picId2==255) {
			picId2=picId;
			img2=img;
		}
		else {
			
		}
//		不同
		if (picId1!=picId2&&picId1!=255&&picId2!=255) {
			return 0;
		}
//		相同
		if (picId1==picId2&&picId1!=255&&picId2!=255) {
			return 1;
		}
		return -1;
	}
	public int getImg1() {
		return img1;
	}
	public int getImg2() {
		return img2;
	}
	public void set255() {
		img1=255;
		img2=255;
		picId1=255;
		picId2=255;
		
	}
	
}
