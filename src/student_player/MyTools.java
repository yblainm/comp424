package student_player;

import java.util.Arrays;
import java.util.List;

import coordinates.Coord;
import coordinates.Coordinates;
import student_player.Minimax.Node;
import tablut.TablutBoardState;
import tablut.TablutBoardState.Piece;

public class MyTools {
	
	private static final float SCORE_WIN_MAX = 100000f;
	private static final float SCORE_WIN_MIN = -5f; //Offset by depth, pick closest move first (assuming depth at most 5)
	private static final int DEFAULT_PIECES_SWEDE = 9;
	private static final int DEFAULT_PIECES_MUSCOVITE = 16;
	private static final int PIECES_CONSTANT = DEFAULT_PIECES_SWEDE*DEFAULT_PIECES_MUSCOVITE;
	private static final List<int[]> DIAGONAL_NEIGHBORS = Arrays.asList(new int[]{0, 0}, new int[]{0, 2}, new int[]{2, 0}, new int[] {2, 2});
	private enum PiecesWithWall{ WHITE, BLACK, EMPTY, KING, WALL } 
	
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
        return 1f * (kingCornerDistance(boardState) * (pieceAdvantage(boardState) + kingSafety(boardState)));
    }
    
    private static int kingCornerDistance(TablutBoardState pBoardState) {
    	return Coordinates.distanceToClosestCorner(pBoardState.getKingPosition());
    }
    
    private static int pieceAdvantage(TablutBoardState pBoardState) {
    	return DEFAULT_PIECES_MUSCOVITE * (DEFAULT_PIECES_SWEDE - pBoardState.getNumberPlayerPieces(TablutBoardState.SWEDE)) +
    			DEFAULT_PIECES_SWEDE * pBoardState.getNumberPlayerPieces(TablutBoardState.MUSCOVITE); /*PIECES_CONSTANT - DEFAULT_PIECES_MUSCOVITE*pBoardState.getNumberPlayerPieces(TablutBoardState.SWEDE)
    			+ DEFAULT_PIECES_SWEDE * pBoardState.getNumberPlayerPieces(TablutBoardState.MUSCOVITE)*/
    }
    
    private static int kingSafety(TablutBoardState pBoardState) {
    	PiecesWithWall[][] kingNeighbors = getKingNeighborPieces(pBoardState);
    	
    	return 100 + scoreEnemyHalfSandwich(kingNeighbors) + scoreOneCorner(kingNeighbors) + scoreTwoCorners(kingNeighbors) + scoreThreeCorners(kingNeighbors) 
    		+ scoreFourCorners(kingNeighbors);
    }
    
    private static int scoreFourCorners(PiecesWithWall[][] kingNeighbors) {
		// TODO Auto-generated method stub
		return getCornerWhiteCount(kingNeighbors) == 4 ? -10 : 0;
	}

	private static int scoreThreeCorners(PiecesWithWall[][] kingNeighbors) {
		// TODO Auto-generated method stub
		return getCornerWhiteCount(kingNeighbors) == 3 ? -30 : 0;
	}

	private static int scoreTwoCorners(PiecesWithWall[][] kingNeighbors) {
		if(kingNeighbors[0][0] == PiecesWithWall.WHITE && kingNeighbors[2][2] == PiecesWithWall.WHITE 
				&& !(kingNeighbors[0][2] == PiecesWithWall.WHITE) && !(kingNeighbors[2][0] == PiecesWithWall.WHITE) 
				|| !(kingNeighbors[0][0] == PiecesWithWall.WHITE) && !(kingNeighbors[2][2] == PiecesWithWall.WHITE) 
				&& kingNeighbors[0][2] == PiecesWithWall.WHITE && kingNeighbors[2][0] == PiecesWithWall.WHITE) {
			return -50;
		}
		return 0;
	}

	private static int scoreOneCorner(PiecesWithWall[][] kingNeighbors) {
		return getCornerWhiteCount(kingNeighbors) == 1 ? -20 : 0;
	}

	private static int scoreEnemyHalfSandwich(PiecesWithWall[][] kingNeighbors) {
		int result = 0;
		
		if(kingNeighbors[0][1] == PiecesWithWall.BLACK && kingNeighbors[2][1] == PiecesWithWall.EMPTY || 
				kingNeighbors[0][1] == PiecesWithWall.EMPTY && kingNeighbors[2][1] == PiecesWithWall.BLACK) {
			result += 50;
		}
		
		if(kingNeighbors[1][0] == PiecesWithWall.BLACK && kingNeighbors[1][2] == PiecesWithWall.EMPTY || 
				kingNeighbors[1][0] == PiecesWithWall.EMPTY && kingNeighbors[1][2] == PiecesWithWall.BLACK) {
			result += 50;
		}
		
		return result;
	}
	
	private static int getCornerWhiteCount(PiecesWithWall[][] kingNeighbors) {
		int count = 0;
		for(int[] n : DIAGONAL_NEIGHBORS) {
				if(kingNeighbors[n[0]][n[1]] == PiecesWithWall.WHITE) {
					count++;
				}
		}
		return count;
	}

	public static PiecesWithWall[][] getKingNeighborPieces(TablutBoardState pBoardState){
    	Coord kingCoord = pBoardState.getKingPosition();
    	PiecesWithWall[][] neighbors = new PiecesWithWall[3][3];
    	
    	for(int i = -1; i < 2; i++) {
    		for(int j = -1; j < 2; j++) {
    			try {
    				neighbors[i+1][j+1] = PiecesWithWall.valueOf(pBoardState.getPieceAt(kingCoord.x + i, kingCoord.y + j).name());
    			} catch (IndexOutOfBoundsException e) {
    				neighbors[i+1][j+1] = PiecesWithWall.WALL;
//    				System.out.println("Turn "+pBoardState.getTurnNumber());
//    				System.out.println((kingCoord.x + i) +", " + (kingCoord.y + j));
//    				System.out.println(neighbors[i+1][j+1]==null);
    			}
    		}
    	}
    	
		return neighbors;
    }
}
