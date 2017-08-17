package com.alican.shuffle_deck.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * Created by Alican on 8/15/17.
 */
public class Deck{
	
	private String[] deckArr;
	private int deckSize;
	private String deckName;
	private String  shuffleType = "";
	
	public Deck(){
	    // initialize deck array
		int suits = Card.Suit.values().length;
		String[] ranksArr = {"2", "3", "4", "5", "6", "7", "8", "9", "10","J", "Q", "K", "A"};
		int ranks = ranksArr.length;
		deckSize = Card.Suit.values().length * ranks;
		
	    deckArr = new String[deckSize];
	    for (int i = 0; i < ranks; i++) {
	        for (int j = 0; j < suits; j++) {
	        	deckArr[suits*i + j] = ranksArr[i] + "-" + Card.Suit.values()[j];
	        }
	    }
	}
	
	//simple shuffling algorithm that simply randomizes the deck in-place
	public void basicShuffle(){
	    for (int i = 0; i < deckSize; i++) {
	        int r = i + (int) (Math.random() * (deckSize-i));
	        String temp = deckArr[r];
	        deckArr[r] = deckArr[i];
	        deckArr[i] = temp;
	    }
	}

	public String getDeckName() {
		return deckName;
	}

	public void setDeckName(String deckName) {
		this.deckName = deckName;
	}
	
	public String[] getDeckArr() {
		return deckArr;
	}

	public void setDeckArr(String[] deckArr) {
		this.deckArr = deckArr;
	}
	
	/* More complex algorithm that simulates hand-shuffling, 
	** i.e. splitting the deck in half and interleaving the two halves, 
	   repeating the process multiple times.*/
	public void handShuffle() {
		
		//split deck from a random point
		Random generator = new Random();
        int splitPoint = generator.nextInt(deckSize);
		
		List<String> part1 = new ArrayList<String>();
		List<String> part2 = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		
		//create two parts
		for(int i=0;i<splitPoint;i++){
			part1.add(deckArr[i]);
		}
		
		for(int i=splitPoint; i < deckSize; i++){
			part2.add(deckArr[i]);
		}
		
		//if split from midpoint
	    if(part1.size() == part2.size()){
	      for(int j=0; j < part1.size(); j++){
	    	  result.add(part2.get(j));
	    	  result.add(part1.get(j));
	      }
	    }// having unequal parts
	    else{
	    	if(part1.size() > part2.size()){
	    		//interleave the small part
	    		for(int j=0; j < part2.size(); j++){
	  	    	  result.add(part2.get(j));
	  	    	  result.add(part1.get(j));
	  	      	}
	    		//add remaining part
	    		for(int j=part2.size(); j < part1.size(); j++){
		  	    	  result.add(part1.get(j));
		  	    }
	    	}else{
	    		//interleave the small part
	    		for(int j=0; j < part1.size(); j++){
		  	    	  result.add(part2.get(j));
		  	    	  result.add(part1.get(j));
		  	      	}
	    		//add remaining part
	    		for(int j=part1.size(); j < part2.size(); j++){
		  	    	  result.add(part2.get(j));
		  	    }
	    	}
	    }
	    String[] resultArr = new String[result.size()];
	    result.toArray(resultArr);
		deckArr = Arrays.copyOf(resultArr, resultArr.length);
	}

	public void shuffle(){
	    if("handShuffle".equals(shuffleType)){
	    	handShuffle();
	    }else{
	    	basicShuffle();
	    }
	  }

	public String getShuffleType() {
		return shuffleType;
	}

	public void setShuffleType(String shuffleType) {
		this.shuffleType = shuffleType;
	}
}
