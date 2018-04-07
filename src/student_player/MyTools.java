package student_player;

import coordinates.Coord;
import coordinates.Coordinates;
import student_player.Minimax.Node;
import tablut.TablutBoardState;

public class MyTools {
	
	private static final int DEFAULT_PIECES_SWEDE = 9;
	private static final int DEFAULT_PIECES_MUSCOVITE = 16;
	private static final int PIECES_CONSTANT = DEFAULT_PIECES_SWEDE*DEFAULT_PIECES_MUSCOVITE;
	
    public static Float evaluate(Node state) {
    	TablutBoardState boardState = state.getBoardState();
    	
    	if(boardState.gameOver()) {
    		int winner = boardState.getWinner();
    		if(winner==TablutBoardState.SWEDE) {
    			return -5f + state.getDepth();
    		} else if(winner==TablutBoardState.MUSCOVITE){
    			return 100000f;
    		}
    	}
        return 1f * (kingCornerDistance(boardState) * pieceAdvantage(boardState));
    }
    
    private static int kingCornerDistance(TablutBoardState pBoardState) {
    	return Coordinates.distanceToClosestCorner(pBoardState.getKingPosition());
    }
    
    private static int pieceAdvantage(TablutBoardState pBoardState) {
    	return DEFAULT_PIECES_MUSCOVITE * (DEFAULT_PIECES_SWEDE - pBoardState.getNumberPlayerPieces(TablutBoardState.SWEDE)) + DEFAULT_PIECES_SWEDE * pBoardState.getNumberPlayerPieces(TablutBoardState.MUSCOVITE); /*PIECES_CONSTANT - DEFAULT_PIECES_MUSCOVITE*pBoardState.getNumberPlayerPieces(TablutBoardState.SWEDE)
    			+ DEFAULT_PIECES_SWEDE * pBoardState.getNumberPlayerPieces(TablutBoardState.MUSCOVITE)*/
    }
    
    private static Float kingSafety() {
    	return 1f;
    }
}
