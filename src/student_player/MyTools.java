package student_player;

import coordinates.Coord;
import coordinates.Coordinates;
import tablut.TablutBoardState;

public class MyTools {
	
	private static final int DEFAULT_PIECES_SWEDE = 9;
	private static final int DEFAULT_PIECES_MUSCOVITE = 16;
	private static final int PIECES_CONSTANT = DEFAULT_PIECES_SWEDE*DEFAULT_PIECES_MUSCOVITE;
	
    public static Float evaluate(TablutBoardState pBoardState) {
    	if(pBoardState.gameOver()) {
    		if(pBoardState.getWinner()==TablutBoardState.SWEDE) {
    			return 0f;
    		} else { return 100000f; }
    	}
        return 1f * (kingCornerDistance(pBoardState) * pieceAdvantage(pBoardState));
    }
    
    private static int kingCornerDistance(TablutBoardState pBoardState) {
    	return Coordinates.distanceToClosestCorner(pBoardState.getKingPosition());
    }
    
    private static int pieceAdvantage(TablutBoardState pBoardState) {
    	return PIECES_CONSTANT - DEFAULT_PIECES_MUSCOVITE*pBoardState.getNumberPlayerPieces(TablutBoardState.SWEDE)
    			+ DEFAULT_PIECES_SWEDE * pBoardState.getNumberPlayerPieces(TablutBoardState.MUSCOVITE);
    }
    
    private static Float kingSafety() {
    	return 1f;
    }
}
