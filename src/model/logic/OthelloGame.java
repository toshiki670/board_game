/**
 * (C) 2020 Toshiki.
 */
package model.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import model.dto.Board;
import model.dto.Coord;
import model.dto.History;
import model.dto.Stone;
import model.player.DarkPlayer;
import model.player.Player;
import model.player.WhitePlayer;

/**
 * オセロを扱う基幹システム.
 * <p>
 * シングルトンクラス.<br>
 * ただし、インスタンスをリセットすることが出来る.<br>
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

  public static void resetInstance() {
    instance = new OthelloGame();
  }

  public Player getPlayer() {
    return player;
  }

  /**
   * プレーヤーが移動した後、結果を反映.
   * 
   * 反映に失敗した場合、Falseを返す。
   * <ul>
   * <li>プレイヤーが移動していないなど.</li>
   * </ul>
   * 
   * @return 結果の反映結果
   */
  public Boolean commit() {
    if (!isPutStone) {
      return false;
    }
    player = Player.changer(player);
    histories.add(new History(player, board));
    isPutStone = false;
    return true;
  }

  /**
   * プレーヤーが移動した後、結果を取り消す.
   * 
   * 取り消しに失敗した場合、Falseを返す。
   * <ul>
   * <li>プレイヤーが移動していないなど.</li>
   * </ul>
   * 
   * @return 結果の取り消し結果
   */
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

  /**
   * 
   * 
   * @param targeted
   * @return
   */
  public Boolean didItTurnOver(Coord targeted) {
    if (isPutStone || !board.isPutableStone(targeted)) {
      return false;
    }
    Boolean result = false;

    for (Consumer<Coord> d : OthelloGame.getAround()) {
      Coord spot = targeted.clone().moveTo(d);
      Stone current = board.getStoneOf(spot);

      if (current != null && current.getPlayer() != player && RecursivelyTurnOver(spot, d)) {
        result = true;
      }
    }

    if (result) {
      board.putStoneTo(targeted, player);
      isPutStone = true;
    }

    return result;
  }

  private Boolean RecursivelyTurnOver(Coord spot, Consumer<Coord> next) {
    if (board.getStoneOf(spot) == null) {
      return false;
    }
    if (board.getStoneOf(spot).getPlayer() == player) {
      return true;
    }
    if (RecursivelyTurnOver(spot.moveTo(next), next)) {
      board.getStoneOf(spot).turnOver();
      return true;
    } else {
      return false;
    }
  }

  /**
   * 鉢方向のリストを生成
   * 
   * @return ラムダ式のArrayList
   */
  @SuppressWarnings("serial")
  private static List<Consumer<Coord>> getAround() {
    List<Consumer<Coord>> around = Collections.unmodifiableList(new ArrayList<Consumer<Coord>>() {
      {
        // 上
        add(c -> c.addY(-1));
        // 下
        add(c -> c.addY(+1));
        // 左
        add(c -> c.addX(-1));
        // 右
        add(c -> c.addX(+1));
        // 左上
        add(c -> {
          c.addX(-1);
          c.addY(-1);
        });
        // 右上
        add(c -> {
          c.addX(+1);
          c.addY(-1);
        });
        // 左下
        add(c -> {
          c.addX(-1);
          c.addY(+1);
        });
        // 右下
        add(c -> {
          c.addX(+1);
          c.addY(+1);
        });
      }
    });

    return around;
  }
}
