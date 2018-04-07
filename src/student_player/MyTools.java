package student_player;

import coordinates.Coord;
import coordinates.Coordinates;
import student_player.Minimax.Node;
import tablut.TablutBoardState;

public class MyTools {
	
	private static final float SCORE_WIN_MAX = 100000f;
	private static final float SCORE_WIN_MIN = -5f; //Offset by depth, pick closest move first (assuming depth at most 5)
	private static final int DEFAULT_PIECES_SWEDE = 9;
	private static final int DEFAULT_PIECES_MUSCOVITE = 16;
	private static final int PIECES_CONSTANT = DEFAULT_PIECES_SWEDE*DEFAULT_PIECES_MUSCOVITE;
	
    public static Float evaluate(Node state) {
    	TablutBoardState boardState = state.getBoardState();
    	
    	if(boardState.gameOver()) {
    		int winner = boardState.getWinner();
    		if(winner==TablutBoardState.SWEDE) {
    			return SCORE_WIN_MIN + state.getDepth();
    		} else if(winner==TablutBoardState.MUSCOVITE){
    			return SCORE_WIN_MAX;
    		}
    	}
        return 1f * (kingCornerDistance(boardState) * pieceAdvantage(boardState));
    }
    
    private static int kingCornerDistance(TablutBoardState pBoardState) {
    	return Coordinates.distanceToClosestCorner(pBoardState.getKingPosition());
    }
    
    private static int pieceAdvantage(TablutBoardState pBoardState) {
    	return DEFAULT_PIECES_MUSCOVITE * (DEFAULT_PIECES_SWEDE - pBoardState.getNumberPlayerPieces(TablutBoardState.SWEDE)) +
    			DEFAULT_PIECES_SWEDE * pBoardState.getNumberPlayerPieces(TablutBoardState.MUSCOVITE); /*PIECES_CONSTANT - DEFAULT_PIECES_MUSCOVITE*pBoardState.getNumberPlayerPieces(TablutBoardState.SWEDE)
    			+ DEFAULT_PIECES_SWEDE * pBoardState.getNumberPlayerPieces(TablutBoardState.MUSCOVITE)*/
    }
    
    private static Float kingSafety() {
    	return 1f;
    }
}
