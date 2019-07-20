package model.othello.dto;

import model.othello.player.Player;

public final class Stone implements Cloneable {
  private Player player;

  public Stone(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return this.player;
  }

  public void turnOver() {
    player = Player.changer(player);
  }

  @Override
  protected Stone clone() {
    return new Stone(this.player);
  }
}
