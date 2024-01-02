import java.util.*;

//********************************************************************************************************************
//Auteur: Mickaêl D. Pernet                                                                                         *
//SeaWar: Class Player pour la Bataille navale pour le cour de programmation orienté objet IED Paris 8              *
//Date: MARS 2021                                                                                                   *
//********************************************************************************************************************

public class Player {
	private String type;
	private boolean deployement;
	private int navires;
	private HashMap<Integer, Bateau> flotte;
	private Bateau porte_avion;
	private Bateau croiseur;
	private Bateau contre_torpilleur;
	private Bateau sous_marin;
	private Bateau torpilleur;
	private String[][] deployementTable;
	private String[][] attackTable;
	private String lastPlay;
	//attribut spécifique à l'automatisation
	private int[] firstHit;
	private int[] lastHit;
	int lostSignal; 
	int nbtouche;
	Stack<int[]> firstHitMemory;	
	private HashMap<Integer, Boolean> aliveTarget;
	int target;


	//constructeur
	public Player(String type) {
		this.type = type;
		this.deployement = false;
		this.navires = 5;
        this.flotte = new HashMap<Integer, Bateau>();
        flotte.put(1, new Bateau("porte_avion", 5));
        flotte.put(2, new Bateau("croiseur", 4));
        flotte.put(3, new Bateau("contre_torpilleur", 3));
        flotte.put(4, new Bateau("sous_marin", 3));
        flotte.put(5, new Bateau("torpilleur", 2));    
		this.deployementTable = new String[][] { {"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
												 {"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
												 {"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
												 {"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
												 {"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
												 {"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
												 {"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
												 {"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
												 {"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
												 {"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"} };                                        
		this.attackTable = new String[][] { {"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
			 								{"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
			 								{"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
			 								{"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
			 								{"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
			 								{"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
			 								{"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
			 								{"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
			 								{"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"},
			 								{"≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈", "≈≈≈"} }; 
		this.lastPlay = " ";
		this.firstHit = new int[] {-1, -1};
		this.lastHit = new int[] {-1, -1};
		this.lostSignal = 0; 
		this.nbtouche = 0;
		this.firstHitMemory = new Stack<int[]>();
		this.target = 5;
		this.aliveTarget =new HashMap<Integer, Boolean>();
		aliveTarget.put(1, true);
		aliveTarget.put(2, true);
		aliveTarget.put(3, true);
		aliveTarget.put(4, true);
		aliveTarget.put(5, true);
	}
	
	//getter
	public String getType() {
		return type;
	}

	public boolean isDeployement() {
		return deployement;
	}
	
	public int getNavires() {
		return navires;
	}
	
	public HashMap<Integer, Bateau> getFlotte() {
		return flotte;
	}
	
	public String getFlotte(int i) {
		Bateau current = flotte.get(i);
		return current.getName();
	}
	
	public Bateau getFlotteO(int i) {
		return flotte.get(i);
	}
	
	public String[][] getDeployementTable() {
		return deployementTable;
	}
	
	public String getDeployementTable(int x, int y) {
		return deployementTable[x][y];
	}

	
	public String[][] getAttackTable() {
		return attackTable;
	}
	
	public String getAttackTable(int x, int y) {
		return attackTable[x][y];
	}
	
	public String getLastPlay() {
		return lastPlay;
	}
	
	//getter specifique pour l'automatisation
	public int[] getFirstHit() {
		return firstHit;
	}
	
	public int[] getLastHit() {
		return lastHit;
	}
	
	public int getLostSignal() {
		return lostSignal;
	}
	
	public Stack<int[]> getFirstHitMemory() {
		return firstHitMemory;
	}
	
	public int getTarget() {
		return target;
	}
	
	public boolean getAliveTarget(int x) {
		return aliveTarget.get(x);
	}
	
	public int getNbtouche() {
		return nbtouche;
	}
	
	public HashMap<Integer, Boolean> getAliveTarget() {
		return aliveTarget;
	}
	
	//setter
	public void setType(String type) {
		this.type = type;
	}

	public void setDeployement(boolean deployement) {
		this.deployement = deployement;
	}

	public void setNavires(int navires) {
		this.navires = navires;
	}


	public void setFlotte(HashMap<Integer, Bateau> flotte) {
		this.flotte = flotte;
	}

	public void setDeployementTable(String[][] deployementTable) {
		this.deployementTable = deployementTable;
	}
	
	public void setDeployementTable(int x, int y, String content) {
		this.deployementTable[x][y] = content;
	}

	public void setAttackTable(String[][] attackTable) {
		this.attackTable = attackTable;
	}
	
	public void setAttackTable(int x, int y, String content) {
		this.attackTable[x][y] = content;
	}

	public void setLastPlay(String lastPlay) {
		this.lastPlay = lastPlay;
	}

	//setter specifique pour l'automatisation
	public void setFirstHit(int[] firstHit) {
		this.firstHit = firstHit;
	}

	public void setFirstHit(int x, int y) {
		this.firstHit[0] = x;
		this.firstHit[1] = y;	
	}

	public void pushFirstHitMemory(int[] firstHitMemory) {
		this.firstHitMemory.push(firstHitMemory);
	}
	
	public void setLastHit(int[] lastHit) {
		this.lastHit = lastHit;
	}
	
	public void setLastHit(int x, int y) {
		this.lastHit[0] = x;
		this.lastHit[1] = y;	
	}
	
	public void setLostSignal(int lostSignal) {
		this.lostSignal = lostSignal;
	}
	
	public void setTarget(int target) {
		this.target = target;
	}

	public void replaceAliveTarget(int x, boolean bool) {
		this.aliveTarget.replace(x, bool);
	}

	public void setNbtouche(int nbtouche) {
		this.nbtouche = nbtouche;
	}

	public void setAliveTarget(HashMap<Integer, Boolean> aliveTarget) {
		this.aliveTarget = aliveTarget;
	}
}
