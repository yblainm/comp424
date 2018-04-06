package student_player;

import tablut.TablutBoardState;
import tablut.TablutMove;

class Minimax{
	enum PlayerType { MAX, MIN }
	static private final int MAX_DEPTH = 4;
	static private final Node DEFAULT_ALPHA = new Node(Float.NEGATIVE_INFINITY);
	static private final Node DEFAULT_BETA = new Node(Float.POSITIVE_INFINITY);
	
	/**
	 * @pre pRootState != null
	 * @param pRootState Board state from which we're searching
	 * @param pType Type of player, Min or Max.
	 * @return Optimal move according to evaluation function
	 */
	static TablutMove run(TablutBoardState pRootState, PlayerType pType){
		Node result;
		if(pType == PlayerType.MAX){
			result = alphaBetaMax(new Node(pRootState), DEFAULT_ALPHA, DEFAULT_BETA);
		}
		else {
			result = alphaBetaMin(new Node(pRootState), DEFAULT_ALPHA, DEFAULT_BETA);
		}
		do{
			result = result.getParent();
		} while (result.getDepth() != 1);
		
		return result.getPreviousMove();
	}
	
	static private Node alphaBetaMax(Node state, Node alpha, Node beta){
		if(state.getDepth()==MAX_DEPTH){
			state.evaluate();
			return state;
		}
		
		Node maxAlpha = alpha;
		
		for(TablutMove move : state.getBoardState().getAllLegalMoves()){
			Node minNode = alphaBetaMin(new Node(move, state), maxAlpha, beta);
			if(minNode.getScore() > maxAlpha.getScore()){ maxAlpha = minNode; }
			if(maxAlpha.getScore() >= beta.getScore()){ return beta; }
		}
		return maxAlpha;
	}
	
	static private Node alphaBetaMin(Node state, Node alpha, Node beta){
		if(state.getDepth()==MAX_DEPTH){
			state.evaluate();
			return state;
		}
		
		Node minBeta = beta;
		
		for(TablutMove move : state.getBoardState().getAllLegalMoves()){
			Node maxNode = alphaBetaMax(new Node(move, state), alpha, minBeta);
			if(maxNode.getScore() < minBeta.getScore()){ minBeta = maxNode; }
			if(alpha.getScore() >= minBeta.getScore()){ return alpha; }
		}
		
		return minBeta;
	}
	
	static private class Node{
		private int depth;
		private TablutBoardState boardState;
		private TablutMove previousMove;
		private Node parent;
		private Float score;
		
		TablutBoardState getBoardState(){ return (TablutBoardState) boardState.clone(); }
		Node getParent(){ return parent; }
		Float getScore(){ return score; }
		int getDepth(){ return depth; }
		TablutMove getPreviousMove(){ return previousMove; }
		
		//Why have one constructor when you can have THREEEEE?! I swear I took COMP 303 and it was useful.
		private Node(Float pScore){
			score = pScore;
		}
		/**
		 * Constructor for initial board state for minimax
		 * @param pBoardState Initial board state
		 */
		Node(TablutBoardState pBoardState){
			depth = 0;
			boardState = pBoardState;
		}
		Node(TablutMove pMove, Node pParent){
			previousMove = pMove;
			parent = pParent;
			depth = parent.getDepth() + 1;
			boardState = parent.getBoardState();
			boardState.processMove(previousMove);
			score = null;
		}
		
		void evaluate(){
			score = MyTools.evaluate(getBoardState());
		}
	}
}