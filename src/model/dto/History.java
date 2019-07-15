package model.dto;

import model.player.Player;

public final class History implements Comparable<History> {
	
	private Player player;
	
	private Board board;
	
	public History(Player player, Board board) {
		this.player = player;
		
	}
	

	@Override
	public int compareTo(History arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
