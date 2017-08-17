package com.alican.shuffle_deck.model;
/**
 * Created by Alican on 8/15/17.
 */
public class Card {
	public enum Suit { spade, club, heart, diamond, };
	public String Rank;
	
	public String getRank() {
		return Rank;
	}
	public void setRank(String rank) {
		Rank = rank;
	}
	
}
