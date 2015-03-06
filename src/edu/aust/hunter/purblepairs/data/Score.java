package edu.aust.hunter.purblepairs.data;


public class Score {
	private int score;
	
	public Score( ) {
		super();
		this.score = 0;
	}
	public void add()
	{
		this.score=this.score+20;
	}
	public void reduce()
	{
		this.score=this.score-2;
	}
	public int getScore()
	{
		return score;
	}
	

}
