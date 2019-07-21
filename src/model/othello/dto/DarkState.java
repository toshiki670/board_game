/**
 * (C) 2020 Toshiki.
 */
package model.othello.dto;

import model.base.dto.State;

/**
 * Othelloの黒を表すシングルトンクラス
 * <p>
 * このクラスのインスタンスは常に一つである.<br>
 * 故にボード上のDarkStateのインスタンスは一意に定まる.
 * 
 * @author toshiki
 * @since 1.0
 */
public final class DarkState implements State {
  private static DarkState instance;

  private DarkState() {
  }

  public static State getInstance() {
    if (instance == null) {
      instance = new DarkState();
    }
    return instance;
  }
  
  /**
   * インスタンスを取り除く
   * 
   * @return インスタンスを取り除いた自身
   */
  public static State removeInstance() {
    instance = null;
    return instance;
  }
}
