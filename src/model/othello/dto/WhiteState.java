/**
 * (C) 2020 Toshiki.
 */
package model.othello.dto;

import model.base.dto.State;

/**
 * Othelloの白を表すシングルトンクラス
 * <p>
 * このクラスのインスタンスは常に一つである.<br>
 * 故にボード上のWhiteStateのインスタンスは一意に定まる.
 * 
 * @author toshiki
 * @since 1.0
 */
public final class WhiteState implements State {
  private static WhiteState instance;

  private WhiteState() {
  }

  public static State getInstance() {
    if (instance == null) {
      instance = new WhiteState();
    }
    return instance;
  }

}
