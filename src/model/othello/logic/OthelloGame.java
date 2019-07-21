/**
 * (C) 2020 Toshiki.
 */
package model.othello.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import model.base.dto.Board;
import model.base.dto.Coord;
import model.base.dto.State;
import model.othello.dto.DarkState;
import model.othello.dto.OthelloBoard;
import model.othello.dto.OthelloHistory;
import model.othello.dto.Stone;
import model.othello.dto.WhiteState;

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

  private LinkedList<OthelloHistory> histories;
  private OthelloBoard board;
  private Boolean isStateOfPutPlace;
  private State currentPlayerState;


  private OthelloGame() {
    histories = new LinkedList<OthelloHistory>();
    board = new OthelloBoard() {
      {
        putStoneTo(new Coord(4, 4), new Stone(WhiteState.getInstance()));
        putStoneTo(new Coord(5, 5), new Stone(WhiteState.getInstance()));
        putStoneTo(new Coord(4, 5), new Stone(DarkState.getInstance()));
        putStoneTo(new Coord(5, 4), new Stone(DarkState.getInstance()));
      }
    };
    histories.add(new OthelloHistory(board, currentPlayerState));
    isStateOfPutPlace = false;
    currentPlayerState = DarkState.getInstance();
  }

  public static OthelloGame getInstance() {
    if (instance == null) {
      instance = new OthelloGame();
    }
    return instance;
  }

  public void resetInstance() {
    instance = new OthelloGame();
  }

  public State getState() {
    return currentPlayerState;
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
   * Boardを複製して取得
   * 
   * @return {@code Board<Stone>}
   */
  public Board<Stone> getBoard() {
    return board.clone();
  }

  public Boolean isEnd() {
    Boolean isFullOfTheBoard = true;

    exitLoop: for (var vertical : board.getField()) {
      for (var stone : vertical) {
        if (stone == null) {
          isFullOfTheBoard = false;
          break exitLoop;
        }
      }
    }
    return isFullOfTheBoard;
  }

  /**
   * プレーヤーが石を置いた後、結果を反映.
   * <p>
   * 石を置いていない場合、Falseを返す.
   * 
   * @return 結果の反映結果
   */
  public Boolean commit() {
    if (!isStateOfPutPlace) {
      return false;
    }
    changeState();
    histories.add(new OthelloHistory(board, currentPlayerState));
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
    OthelloHistory h = histories.getLast();
    board = h.getBoard();
    currentPlayerState = h.getCurrentState();
    isStateOfPutPlace = false;
    return true;
  }

  /**
   * オセロの石を置く.
   * <p>
   * オセロの石を置き、"{@code turnOverOpponentsStone()}"を呼び出して相手の石をひっくり返す。
   * 相手の石をひっくり返せた場合、インスタンスメソッドの{@code isPutStone}をtrueに設定し、 Commitを可能とする。
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

      if (current != null && current.getState() != currentPlayerState
          && turnOverOpponentsStone(point, direction)) {
        this.isStateOfPutPlace = true;
      }
    });

    if (this.isStateOfPutPlace) {
      board.putStoneTo(target, new Stone(currentPlayerState));
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
    if (board.getStoneOf(point).getState() == currentPlayerState) {
      return true;
    }
    if (turnOverOpponentsStone(point.moveTo(next), next)) {
      board.getStoneOf(point).callMethod();
      return true;
    } else {
      return false;
    }
  }

  /**
   * 現在のプレイヤーを切り替える.
   */
  private void changeState() {
    if (currentPlayerState == DarkState.getInstance()) {
      currentPlayerState = WhiteState.getInstance();
    }
    if (currentPlayerState == WhiteState.getInstance()) {
      currentPlayerState = DarkState.getInstance();
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
        add(c -> c.addV(-1));
        // 下
        add(c -> c.addV(+1));
        // 左
        add(c -> c.addH(-1));
        // 右
        add(c -> c.addH(+1));
        // 左上
        add(c -> {
          c.addH(-1);
          c.addV(-1);
        });
        // 右上
        add(c -> {
          c.addH(+1);
          c.addV(-1);
        });
        // 左下
        add(c -> {
          c.addH(-1);
          c.addV(+1);
        });
        // 右下
        add(c -> {
          c.addH(+1);
          c.addV(+1);
        });
      }
    });

    return result;
  }
}
