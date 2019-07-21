/**
 * (C) 2020 Toshiki.
 */
package model.base.dto;

/**
 * Boardで扱えるようにするためのインターフェース
 * 
 * @author toshiki
 * @since 1.0
 */
public interface Cell extends Cloneable {

  /**
   * Cellの状態を取得.
   * 
   * @return
   */
  public State getState();

  /**
   * Cellに対して処理を実行.
   */
  public void callMethod();
}
