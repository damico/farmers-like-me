package com.traitify;



import java.util.List;

import com.traitify.models.Assessment;
import com.traitify.models.Deck;

public class TestMain {

	public static void main(String[] args) {
		Traitify.apiKey = "1qpki7qm7a55ule51amsjcm6cm";
        List<Deck> decks = Deck.list();
        Deck deck = null;
        for(Deck _deck:decks){
        	System.out.println(">> "+_deck.getName());
            if(_deck.getName().equals("Careers")){
                deck = _deck;
            }
        }
        
        Assessment assessment = Assessment.create(deck.getId());
        System.out.println(assessment.getId());
	}

}
