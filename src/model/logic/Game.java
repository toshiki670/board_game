
package model.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import model.dto.Board;
import model.dto.History;
import model.player.DarkPlayer;
import model.player.Player;
import model.player.WhitePlayer;

public final class Game {
  private static Game instance;
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

  private LinkedList<History> histories;
  private Board board;
  private Player player;
  private Boolean isPutStone;


  private Game() {
    histories = new LinkedList<>();
    board = new Board();
    board.putStoneTo(3, 3, WhitePlayer.getInstance());
    board.putStoneTo(3, 4, DarkPlayer.getInstance());
    board.putStoneTo(4, 3, DarkPlayer.getInstance());
    board.putStoneTo(4, 4, WhitePlayer.getInstance());
    player = DarkPlayer.getInstance();
    histories.add(new History(player, board));
    isPutStone = false;
  }

  public static Game getInstance() {
    if (instance == null) {
      instance = new Game();
    }
    return instance;
  }

  public Player getPlayer() {
    return player;
  }

  public Boolean commit() {
    if (!isPutStone) {
      return false;
    }
    player = Player.changer(player);
    histories.add(new History(player, board));
    isPutStone = false;
    return true;
  }

  public Boolean fallback() {
    if (!isPutStone) {
      return false;
    }
    History h = histories.getLast();
    player = h.getPlayer();
    board = h.getBoard();
    isPutStone = false;
    return true;
  }

  public Boolean putStoneIfTurnedOver(int x, int y) {
    if (isPutStone || !board.isPutableStone(x, y)) {
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
        if (RecursivelyTurnOver(addedX, addedY, addX, addY)) {
          result = true;
        }
      }
    }

    if (result) {
      board.putStoneTo(x, y, player);
      isPutStone = true;
    }

    return result;
  }

  private Boolean RecursivelyTurnOver(int x, int y, int addX, int addY) {
    if (board.getPlayerOf(x, y) == null) {
      return false;
    }
    if (board.getPlayerOf(x, y) == player) {
      return true;
    }
    if (RecursivelyTurnOver(x + addX, y + addY, addX, addY)) {
      board.turnOver(x, y);
      return true;
    } else {
      return false;
    }
  }
}
