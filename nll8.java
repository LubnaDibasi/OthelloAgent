package edu.ksu.csc.ai.othello;
import java.util.*;
import edu.ksu.csc.ai.othello.GameState.GameStatus;


//Authors:
//Lubna Aldibasi
//Nada Aldalbahi
//Lina Alsaeed

public class nll8 extends OthelloPlayer {
public nll8(String name) {
		super(name);
	}
	
	
public Square getMove(GameState currentState, Date Deadline) {
	
	Square validMoves[] = currentState.getValidMoves().toArray(new Square[0]); //gets valid moves
	int bestmove = 0;
	int bestScore = Integer.MIN_VALUE;
	int score =0;
	
	int corner = corners((GameState)currentState.clone()); //sends a clone to corner method
	if (corner != -1) // if it has a valid index
		return validMoves[corner]; //a corner square is helpful whenever it's available 
	
	//otherwise consider minmax
	for (int i=0; i< validMoves.length; i++) {
		GameState CloneSTATE = (GameState)currentState.clone(); //making a copy of the current state
		score = minmax(CloneSTATE.applyMove(validMoves[i]), 6, true); //calling minmax 
		if (score > bestScore) //if the new result is better
		{ bestmove = i;	//save index
		bestScore = score;  //update the best score so far
		} 
	} // end for
	this.registerCurrentBestMove((Square) currentState.getValidMoves().toArray()[bestmove]); //helpful in dealing with deadline 
	return (Square) currentState.getValidMoves().toArray()[bestmove]; // the move to be taken
}// end getMove



public int minmax(GameState current, int depth, boolean turn) {
	 
	 int score =0 , local =0;
	
	 if (depth == 0 ) { //base case
	   if (turn)  
		   return current.getScore(current.getCurrentPlayer()); //return the agent's estimated score
	   return current.getScore(current.getOpponent(current.getCurrentPlayer())); //return the opponent's estimated score
	}
	
	Square []validMoves = current.getValidMoves().toArray(new Square[0]); //array contains all valid moves
	
	if (turn) {
		 score = Integer.MIN_VALUE;  //assume -infinity
		
		for (int i=0; i< validMoves.length; i++) {
			GameState apply = current.applyMove(validMoves[i]);     //making index i move of the valid moves
			
			local = Math.max(score, minmax(apply, depth -1, false));//largest value between the assumption 
			                                                       //and the returned score from minmax
			if (local > score ) //if a larger value found
				score = local; //update the largest score so far
			
		}// end loop
	    return score; //maximum score
	    }// end if
	
	else {
		 score = Integer.MAX_VALUE; // assume infinity
		for (int i=0; i< validMoves.length; i++) { 
			GameState apply = current.applyMove(validMoves[i]); //making index i move of the valid moves
			
			local = Math.min(score, minmax(apply, depth -1, true)); //smallest value between the assumption 
			                                                       //and the returned score from minmax
			if (local < score )    //if a smaller value found    
				score = local;    //update the smallest score so far
		} // end loop
		  return score; //minimum score
	}// end else
	
}// end minmax

public int corners(GameState current) { //evaluation function
	Square []validMoves = current.getValidMoves().toArray(new Square[0]); //array contains all valid moves
	
	for (int i=0; i< validMoves.length; i++) { //searches for corners by [row, col]
		if(current.getSquare(validMoves[i]) == current.getSquare(0, 0) || current.getSquare(validMoves[i]) == current.getSquare(0, 7) || current.getSquare(validMoves[i]) == current.getSquare(7, 0) || current.getSquare(validMoves[i]) == current.getSquare(7, 7))
			return i;	//the corners index as a move in the valid moves array
	}
	return -1; //no corners found
	
}
	  }