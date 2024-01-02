//********************************************************************************************************************
//Auteur: Mickaêl D. Pernet                                                                                         *
//SeaWar: Class Game pour la Bataille navale pour le cour de programmation orienté objet IED Paris 8              *
//Date: MARS 2021                                                                                                   *
//********************************************************************************************************************


public class Game {
	private int current_player;
	private int other_player;
	private boolean anty_spy;
	String[][] spy_table;
	
	//Builder
	public Game () {
		this.current_player = 0;
		this.other_player = 2;
		this.spy_table = new String[][] { {"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
                                          {"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
                                          {"   ", " S ", " E ", " C ", " U ", " R ", " I ", " T ", " Y ", "   "},
                                          {"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
                                          {"   ", "   ", "   ", "   ", " I ", " S ", "   ", "   ", "   ", "   "},
                                          {"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
                                          {"   ", " E ", " N ", " A ", " B ", " L ", " E ", " D ", " ! ", "   "},
                                          {"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
                                          {"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
                                          {"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "} };
	}
	
	
	
	//getter
	public int current_player() {
		return this.current_player;
	}
	
	public int getOther_player() {
		return other_player;
	}
	
	public String[][] getSpy_table() {
		return spy_table;
	}
	
	
	
	//setter
	public void setCurrent_player(int current_player) {
		this.current_player = current_player;
	}

	public void nextplayer(int player) {
		this.other_player= this.current_player();
		this.current_player=player;
	}

	public void setOther_player(int other_player) {
		this.other_player = other_player;
	}

	public void setSpy_table(String[][] spy_table) {
		this.spy_table = spy_table;
	}	
}
