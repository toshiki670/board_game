/**
 * (C) 2020 Toshiki.
 */
package model.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;
import model.dto.Board;
import model.dto.Coord;
import model.dto.History;
import model.dto.Stone;
import model.player.DarkPlayer;
import model.player.Player;
import model.player.WhitePlayer;

/**
 * 
 * 
 * @author toshiki
 * @since 1.0
 */
public final class OthelloGame {
  private static OthelloGame instance;

  private LinkedList<History> histories;
  private Board board;
  private Player player;
  private Boolean isPutStone;

  private OthelloGame() {
    histories = new LinkedList<>();
    board = new Board();
    board.putStoneTo(new Coord(4, 4), WhitePlayer.getInstance());
    board.putStoneTo(new Coord(4, 5), DarkPlayer.getInstance());
    board.putStoneTo(new Coord(5, 4), DarkPlayer.getInstance());
    board.putStoneTo(new Coord(5, 5), WhitePlayer.getInstance());
    player = DarkPlayer.getInstance();
    histories.add(new History(player, board));
    isPutStone = false;
  }

  public static OthelloGame getInstance() {
    if (instance == null) {
      instance = new OthelloGame();
    }
    return instance;
  }

  public static OthelloGame resetInstance() {
    instance = new OthelloGame();
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

  public Boolean putStoneIfTurnedOver(Coord c) {
    if (isPutStone || !board.isPutableStone(c)) {
      return false;
    }
    Boolean result = false;
    List<UnaryOperator<Coord>> direction = Coord.getAround();

    for (UnaryOperator<Coord> d : direction) {
      Coord checker = c.clone().moveTo(d);
      Stone current = board.getStoneOf(checker);

      if (current != null && current.getPlayer() != player && RecursivelyTurnOver(checker, d)) {
        result = true;
      }
    }

    if (result) {
      board.putStoneTo(c, player);
      isPutStone = true;
    }

    return result;
  }

  private Boolean RecursivelyTurnOver(Coord c, UnaryOperator<Coord> next) {
    if (board.getStoneOf(c) == null) {
      return false;
    }
    if (board.getStoneOf(c).getPlayer() == player) {
      return true;
    }
    if (RecursivelyTurnOver(c.moveTo(next), next)) {
      board.getStoneOf(c).turnOver();
      return true;
    } else {
      return false;
    }
  }
}
