package edu.aust.hunter.purblepairs.resource;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;

import com.example.purblepairs.activity.R;

import edu.aust.hunter.purblepairs.activity.ChallengeGameActivity1;
import edu.aust.hunter.purblepairs.activity.GeneralGameActivity1;

/**
 * @author hutao
 *�˴�д������Ч���Ƶĺ���
 *�����������Լ���Ϸ��Ч����
 *
 */
public class MediaPlay {
	public static Boolean soundOnOff1= true;
	public static Boolean soundOnOff2= true;
	private MediaPlayer backPlayer=null;
	private MediaPlayer backToFrontPlayer=null;
	private MediaPlayer differentPlayer=null;
	private MediaPlayer samePlayer=null;
	private MediaPlayer successPlayer=null;
	private MediaPlayer failPlayer=null;
	final public Context context;
	public MediaPlay(ChallengeGameActivity1 challengeGameActivity1)
	{
		context=challengeGameActivity1;
	}
	public MediaPlay(GeneralGameActivity1 generalGameActivity1)
	{
		context=generalGameActivity1;
	}
	
	public void backMusicStart()
	{
		if (soundOnOff1) {
			this.backPlayer=null;
			//MediaPlayer����Ϊ�գ���ʼ����
			if(this.backPlayer==null){
				//����MediaPlayer����
				this.backPlayer = MediaPlayer.create(this.context,R.drawable.back_music);
				//����ѭ������ΪTrue
				this.backPlayer.setLooping(true);
				//��ʼ����
				this.backPlayer.start();
			}
		}

	}
	public void backMusicPause()
	{
		if (soundOnOff1) {
			if (this.backPlayer.isPlaying()) {
				this.backPlayer.pause();
		}

		}
	}
	public void backMusicReset()
	{
		if (soundOnOff1) {
			if(this.backPlayer!=null){
				//ֹͣ����
				this.backPlayer.stop();
				//�ͷ���Դ
				this.backPlayer.release();
				//��mediaPlayer����Ϊ��
				this.backPlayer = null;
			}
		}

	}
	
	public void backToFrontPlay() {
		if (soundOnOff2) {
			MediaPlay.this.backToFrontPlayer=null;
			MediaPlay.this.backToFrontPlayer = MediaPlayer.create(MediaPlay.this.context, R.drawable.back_to_front_music);
			MediaPlay.this.backToFrontPlayer.start();
			MediaPlay.this.backToFrontPlayer.setOnCompletionListener(new myOnCompetionListenner(backToFrontPlayer));
		}
	
			
			}
	public void differentPlay() {
		if (soundOnOff2) {
			this.differentPlayer=null;
			this.differentPlayer = MediaPlayer.create(this.context, R.drawable.different_music);
			this.differentPlayer.start();
			this.differentPlayer.setOnCompletionListener(new myOnCompetionListenner(differentPlayer));
		}
	
		}
	public void samePlay() {
		if (soundOnOff2) {
			this.samePlayer=null;
			this.samePlayer = MediaPlayer.create(this.context, R.drawable.same_music);
			this.samePlayer.start();
			this.samePlayer.setOnCompletionListener(new myOnCompetionListenner(samePlayer));
		}
		
		}
	public void successPlay() {
		if (soundOnOff2) {
			this.successPlayer=null;
			this.successPlayer = MediaPlayer.create(this.context, R.drawable.success_music);
			this.successPlayer.start();
			this.successPlayer.setOnCompletionListener(new myOnCompetionListenner(successPlayer));
		}
	
		}
	public void failPlay() {
		if (soundOnOff2) {
			this.failPlayer=null;
			this.failPlayer = MediaPlayer.create(this.context, R.drawable.fail_music);
			this.failPlayer.start();
			this.failPlayer.setOnCompletionListener(new myOnCompetionListenner(failPlayer));
		}
		
		}
	
	
	
	private class myOnCompetionListenner implements OnCompletionListener
	{
		
		MediaPlayer thisPlayer;
		public myOnCompetionListenner(MediaPlayer thisPlayer) {
			super();
			this.thisPlayer = thisPlayer;
		}
		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO �Զ����ɵķ������
				//ֹͣ����
				thisPlayer.stop();
				//�ͷ���Դ
				thisPlayer.release();
				//��mediaPlayer����Ϊ��
				thisPlayer = null;
		}
	}

}
