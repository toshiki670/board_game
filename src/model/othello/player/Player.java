package model.othello.player;

public interface Player {

  public String getUserName();

  public void setUserName(String userName);

  public static Player changer(Player player) {
    if (player == DarkPlayer.getInstance()) {
      return WhitePlayer.getInstance();
    }
    if (player == WhitePlayer.getInstance()) {
      return DarkPlayer.getInstance();
    }
    return player;
  }

}
