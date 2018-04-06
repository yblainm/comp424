package student_player;

import coordinates.Coord;
import coordinates.Coordinates;
import tablut.TablutBoardState;

public class MyTools {
	
    public static Float evaluate(TablutBoardState pBoardState) {
    	if(pBoardState.gameOver()) {
    		if(pBoardState.getWinner()==TablutBoardState.SWEDE) {
    			return 0f;
    		} else { return 10000f; }
    	}
        return (float) (1f * Math.pow(kingCornerDistance(pBoardState), 2));
    }
    
    private static int kingCornerDistance(TablutBoardState pBoardState) {
    	return Coordinates.distanceToClosestCorner(pBoardState.getKingPosition());
    }
    
    private static Float kingSafety() {
    	return 1f;
    }
}
