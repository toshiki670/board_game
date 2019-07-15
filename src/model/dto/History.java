package model.dto;

import model.player.Player;

public final class History implements Cloneable {

  private Player player;
  private Board board;

  public History(Player player, Board board) {
    this.player = player;
    this.board = board.clone();
  }

  public Player getPlayer() {
    return player;
  }

  public Board getBoard() {
    return board.clone();
  }

  @Override
  protected History clone() throws CloneNotSupportedException {
    return new History(player, board.clone());
  }
}
