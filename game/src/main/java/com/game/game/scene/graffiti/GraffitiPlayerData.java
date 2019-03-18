package com.game.game.scene.graffiti;

import java.util.ArrayList;

import com.game.framework.util.Clear;
import com.game.message.proto.ProtoContext_Common.PlayerCardData;

/**
 * 
 * @author suchen
 * @date 2018年12月3日下午2:17:46
 */
public class GraffitiPlayerData implements Clear {
	
	private PlayerCardData playerCardData = null;
	//	是否使用过一次更新词表的机会
	private boolean isUpdatedWord = false;
	//	该玩家会在list中选择一个词
	private ArrayList<Integer> wordList = new ArrayList<>(4);
	//
	private boolean guessRight = false;
	//	总得分
	private int totalScore = 0;
	//	所画的总点赞数
	private int totalGood = 0;
	//	所画的总差评数
	private int totalBad = 0;
	//	当前轮次得了多少分
	private int roundScore = 0;
	
	private int quitTotalScore = 0;
	
	private int quitTotalGood = 0;
	/** 本回合是否猜词	*/
	private boolean roundGuess = false;
	
	
	public void clearData() {
		quitTotalScore = totalScore;
		quitTotalGood = totalGood;
		
		playerCardData = null;
		isUpdatedWord = false;
		wordList.clear();
		guessRight = false;
		roundGuess = false;
		totalScore = 0;
		totalGood = 0;
		totalBad = 0;
		roundScore = 0;
	}
	
	public GraffitiPlayerData() {
		
	}
	
	public boolean isRoundGuess() {
		return roundGuess;
	}

	public void setRoundGuess(boolean roundGuess) {
		this.roundGuess = roundGuess;
	}
	
	public boolean isGuessRight() {
		return guessRight;
	}
	
	public void incrTotalGood() {
		totalGood++;
	}

	public void incrTotalBad() {
		totalBad++;
	}

	public void setGuessRight(boolean guessRight) {
		this.guessRight = guessRight;
	}

	public ArrayList<Integer> getWordList() {
		return wordList;
	}

	public void setWordList(ArrayList<Integer> wordList) {
		this.wordList = wordList;
	}

	public boolean isUpdatedWord() {
		return isUpdatedWord;
	}

	public void setUpdatedWord(boolean isUpdatedWord) {
		this.isUpdatedWord = isUpdatedWord;
	}

	public PlayerCardData getPlayerCardData() {
		return playerCardData;
	}

	public void setPlayerCardData(PlayerCardData playerCardData) {
		this.playerCardData = playerCardData;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getTotalGood() {
		return totalGood;
	}

	public void setTotalGood(int totalGood) {
		this.totalGood = totalGood;
	}

	public int getTotalBad() {
		return totalBad;
	}

	public void setTotalBad(int totalBad) {
		this.totalBad = totalBad;
	}

	public int getRoundScore() {
		return roundScore;
	}

	public void setRoundScore(int roundScore) {
		this.roundScore = roundScore;
	}

	
	public int getQuitTotalScore() {
		return quitTotalScore;
	}

	public void setQuitTotalScore(int quitTotalScore) {
		this.quitTotalScore = quitTotalScore;
	}

	public int getQuitTotalGood() {
		return quitTotalGood;
	}

	public void setQuitTotalGood(int quitTotalGood) {
		this.quitTotalGood = quitTotalGood;
	}

	@Override
	public String toString() {
		return "GraffitiPlayerData [playerCardData=" + playerCardData + ", isUpdatedWord=" + isUpdatedWord
				+ ", wordList=" + wordList + ", guessRight=" + guessRight + ", totalScore=" + totalScore
				+ ", totalGood=" + totalGood + ", totalBad=" + totalBad + ", roundScore=" + roundScore
				+ ", quitTotalScore=" + quitTotalScore + ", quitTotalGood=" + quitTotalGood + "]";
	}
	
}
