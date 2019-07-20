/**
 * 
 */
package model.othello.dto;

import model.base.dto.History;
import model.base.dto.State;

/**
 * @author toshiki
 *
 */
public final class OthelloHistory extends History<Stone> {
  private State currentState;

  /**
   * @param board
   */
  public OthelloHistory(OthelloBoard board, State currentState) {
    super(board);
    this.currentState = currentState;
  }


  @Override
  public OthelloBoard getBoard() {
    return (OthelloBoard) super.getBoard();
  }
  

  /**
   * @return the currentState
   */
  public State getCurrentState() {
    return currentState;
  }
}
