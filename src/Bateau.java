//********************************************************************************************************************
// Auteur: Mickaêl D. Pernet                                                                                         *
// SeaWar: Class Bateau pour la Bataille navale pour le cour de programmation orienté objet IED Paris 8              *
// Date: MARS 2021                                                                                                   *
//********************************************************************************************************************

public class Bateau {
	private String name;
	private int size;
	private int life;
	private boolean coule;
	private int[][] cases;
	
	//Builder:
	public Bateau(String name, int size) {
		this.name = name;
		this.size = size;
		this.life = size;
		this.coule = false;
		this.cases = new int[size][2];
	}

	//Getter:
	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}
	
	public int getLife() {
		return life;
	}
	
	public boolean isCoule() {
		return coule;
	}
	
	public int[][] getCases() {
		return cases;
	}
	
	public int getCases(int i, int j) {
		return cases[i][j];
	}

	
	//Setter:
	public void setName(String name) {
		this.name = name;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void setCoule(boolean coule) {
		this.coule = coule;
	}
	
	public void setCases(int[][] cases) {
		this.cases = cases;
	}

	public void setCases(int[] coord, int i) {
		this.cases[i] = coord;
	}	
}