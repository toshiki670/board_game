package model.dto;

import model.player.DarkPlayer;
import model.player.Player;
import model.player.WhitePlayer;

public final class Stone implements Cloneable {
  private Player player;

  public Stone(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return this.player;
  }

  public void turnOver() {
    if (player == DarkPlayer.getInstance()) {
      player = WhitePlayer.getInstance();
    }
    if (player == WhitePlayer.getInstance()) {
      player = DarkPlayer.getInstance();
    }
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return new Stone(this.player);
  }


}
