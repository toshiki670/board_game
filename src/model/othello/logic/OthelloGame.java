/**
 * (C) 2020 Toshiki.
 */
package model.othello.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import model.othello.dto.Board;
import model.othello.dto.Coord;
import model.othello.dto.History;
import model.othello.dto.Stone;
import model.othello.player.DarkPlayer;
import model.othello.player.Player;
import model.othello.player.WhitePlayer;

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
  private Boolean isStateOfPutPlace;

  private OthelloGame() {
    histories = new LinkedList<>();
    board = new Board();
    board.putStoneTo(new Coord(4, 4), WhitePlayer.getInstance());
    board.putStoneTo(new Coord(4, 5), DarkPlayer.getInstance());
    board.putStoneTo(new Coord(5, 4), DarkPlayer.getInstance());
    board.putStoneTo(new Coord(5, 5), WhitePlayer.getInstance());
    player = DarkPlayer.getInstance();
    histories.add(new History(player, board));
    isStateOfPutPlace = false;
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
   * プレイヤーがコマの設置状況を返す.
   * <p>
   * コマを置いていた場合、Trueを返す.
   * 
   * @return
   */
  public Boolean isStateOfPutPlace() {
    return this.isStateOfPutPlace;
  }

  /**
   * プレーヤーが移動した後、結果を反映.
   * 
   * 石を置いていない場合、Falseを返す。
   * <ul>
   * <li>プレイヤーが移動していないなど.</li>
   * </ul>
   * 
   * @return 結果の反映結果
   */
  public Boolean commit() {
    if (!isStateOfPutPlace) {
      return false;
    }
    player = Player.changer(player);
    histories.add(new History(player, board));
    isStateOfPutPlace = false;
    return true;
  }

  /**
   * プレーヤーが移動した後、結果を取り消す.
   * 
   * 石を置いていない場合、Falseを返す。
   * <ul>
   * <li>プレイヤーが移動していないなど.</li>
   * </ul>
   * 
   * @return 結果の取り消し結果
   */
  public Boolean fallback() {
    if (!isStateOfPutPlace) {
      return false;
    }
    History h = histories.getLast();
    player = h.getPlayer();
    board = h.getBoard();
    isStateOfPutPlace = false;
    return true;
  }

  /**
   * オセロの石を置く.
   * <p>
   * オセロの石を置き、"turnOverOpponentsStone()"を呼び出して相手の石をひっくり返す。
   * 相手の石をひっくり返せた場合、インスタンスメソッドのisPutStoneをtrueに設定し、 Commitを可能とする。
   * 
   * @param target 石を置く場所
   */
  public void putStone(Coord target) {
    
    // 石を置いたことがある場合、且つ石が置けない場合は何もしない
    if (this.isStateOfPutPlace || !board.isPutableStone(target)) {
      return;
    }
    
    OthelloGame.getEightDirections().forEach(direction -> {
      Coord point = target.clone().moveTo(direction);
      Stone current = board.getStoneOf(point);

      if (current != null && current.getPlayer() != player
          && turnOverOpponentsStone(point, direction)) {
        this.isStateOfPutPlace = true;
      }
    });

    if (this.isStateOfPutPlace) {
      board.putStoneTo(target, player);
    }
  }

  /**
   * nextの方向にpointを移動し、自分のコマだった場合は相手の駒をひっくり返す.
   * <p>
   * ひっくり返せた場合、Trueを返す.
   * 
   * @param point
   * @param next 
   * @return 相手のコマをひっくり返した場合、trueを返す
   */
  private Boolean turnOverOpponentsStone(Coord point, Consumer<Coord> next) {
    if (board.getStoneOf(point) == null) {
      return false;
    }
    if (board.getStoneOf(point).getPlayer() == player) {
      return true;
    }
    if (turnOverOpponentsStone(point.moveTo(next), next)) {
      board.getStoneOf(point).turnOver();
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
  private static List<Consumer<Coord>> getEightDirections() {
    List<Consumer<Coord>> result = Collections.unmodifiableList(new ArrayList<Consumer<Coord>>() {
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

    return result;
  }
}
