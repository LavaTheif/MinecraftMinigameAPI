package com.weebly.kyslol.MinigameAPI.teams;

@Deprecated
public class TeamMiniGame {
	TeamMain main;
	
	public TeamMain getMain() {
		return main;
	}

	public void setMain(TeamMain main) {
		this.main = main;
	}

	public TeamMiniGame(TeamMain tm){
		main = tm;
	}
	
}
