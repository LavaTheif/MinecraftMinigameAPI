package com.weebly.kyslol.MinigameAPI.Team;

public class TeamMiniGame {
	private GameData data;
	public TeamMiniGame(String gameName, String mapName){
		data = new GameData(mapName, gameName);
	}
	public GameData getData() {
		return data;
	}
	public void setData(GameData data) {
		this.data = data;
	}
}
