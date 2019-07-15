
package model.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import model.dto.Board;
import model.player.Player;

public final class Game {
  private static final Map<Integer, Integer> DIRECTION =
      Collections.unmodifiableMap(new HashMap<Integer, Integer>() {
        private static final long serialVersionUID = 6067989766198502055L;
        {
          put(-1, -1);
          put(-1, 0);
          put(-1, 1);
          put(0, -1);
          put(0, 1);
          put(1, -1);
          put(1, 0);
          put(1, -1);
        }
      });

  public static Boolean putStoneIfTurnedOver(int x, int y, Player player, Board board) {
    if (!board.isPutableStone(x, y)) {
      return false;
    }
    Boolean result = false;


    for (Map.Entry<Integer, Integer> e : DIRECTION.entrySet()) {
      int addX = e.getKey();
      int addY = e.getValue();
      int addedX = x + addX;
      int addedY = y + addY;
      Player current = board.getPlayerOf(addedX, addedY);

      if (current != null && current != player) {
        if (RecursivelyTurnOver(addedX, addedY, addX, addY, player, board)) {
          result = true;
        }
      }
    }

    if (result) {
      board.putStoneTo(x, y, player);
    }

    return result;
  }

  private static Boolean RecursivelyTurnOver(int x, int y, int addX, int addY, Player player,
      Board board) {
    if (board.getPlayerOf(x, y) == null) {
      return false;
    }
    if (board.getPlayerOf(x, y) == player) {
      return true;
    }
    if (RecursivelyTurnOver(x + addX, y + addY, addX, addY, player, board)) {
      board.turnOver(x, y);
      return true;
    } else {
      return false;
    }
  }

}
