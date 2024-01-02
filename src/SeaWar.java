import java.util.*;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

//********************************************************************************************************************
// Auteur: Mickaêl D. Pernet                                                                                         *
// SeaWar: Bataille navale pour le cour de programmation orienté objet IED Paris 8                                   *
// Date: MARS 2021                                                                                                   *
// Dépendance: fichier de class Bateau.java, Player.java, Game.java.                                                 *
//********************************************************************************************************************
public class SeaWar {
	
	//****************************************************************************************************************
	// Constante de couleur pour une bataille navale style programme de calcultrice scientifique sur terminal        *
	//        ANSI Escape in Console plugin: http://mihai-nita.net/2013/06/03/eclipse-plugin-ansi-in-console/        *
	//        Pour gerer les couleurs éclipses Help -> Install New software -> Add http://www.mihai-nita.net/eclipse *
	//****************************************************************************************************************

	// reset
	public static final String R = "\u001B[0m";
	
	// water
	public static final String WS = "\u001B[37m";
	public static final String WBG= "\u001B[44m";
	public static final String W  = WS+WBG;

	// cadre
	public static final String CS = "\u001B[91m";
	public static final String CBG= "\u001B[40m";
	public static final String C  = CS+CBG;

	// Standard
	public static final String SS = "\u001B[30m";
	public static final String SBG= "\u001B[100m";
	public static final String S  = SS+SBG;

	// desactive
	public static final String DD = SS+CBG;
	
	// Standard chiffre
	public static final String S2 = CS+SBG;
	
	// Radar
	public static final String RS = "\u001B[32m";
	public static final String Ra  = RS+CBG;
	
	// Waiting
	public static final String Wa = "\u001B[5m";
	
	// lisibilitié
	public static final String Li = "\u001B[37m";
	
	
	//****************************************************************************************************************
	// Comme on va effectuer des affiche textuel pour simuler des graphismes il y aura plusieurs fonctions dédiés à  *
	// l'apparence.                                                                                                  *
	// Fonctions d'apparence textuelles:                                                                             *
	//****************************************************************************************************************
	
	/*
	 *  spaceCounter:
	 *  Objectif: remplit un nombre fixe de caractère en fonction d'une variable de taille non fixe
	 *  Module & fonctions auxiliaires: N/A
	 *  Exception: N/A
	 *  Params:	- un int représentant le nombre de caractère totaux désirés 
	 *  		- Une variable contenant une String
	 *  retour: un string contenant le nombre d'espaces désirés
	 */			
	public static String spaceCounter(int size, String var) {
		String resultat = "";
		for(int start = 0; start < (size - var.length()); start++) {
			resultat = resultat + " ";
		}
		return resultat;
	}
	
	/*
	 *  blockTxt:
	 *  Objectif: Génere un block de texte de remplissage textuel de 100 colonnes par le nombre de lignes désirées
	 *  Module & fonctions auxiliaires: N/A
	 *  Exception: N/A
	 *  Params:	- un int représentant le nombre de ligne à remplire
	 *  retour: rien, mais imprime des lignes
	 */		
	public static void blockTxt(int lines) {
		String line = S+"";
		for(int x =0; x<100; x++) {
			line = line + " ";
		}
		for(int y = 0; y<lines; y++) {
			System.out.print(line);
		}
	}
	
	/*
	 *  blank:
	 *  Objectif: genere une page à la taille definit du terminal uniforme
	 *  Module & fonctions auxiliaires: blockTxt()
	 *  Exception: N/A
	 *  Params:	- N/A
	 *  retour: N/A
	 */		
	public static void blank() {
		blockTxt(41);
	}
	
	/*
	 *  tableLoader: 
	 *  Objectif: Charger d'afficher les lignes des tables de déploiements et d'attaques dans les représentations graphiques
	 *  Module & fonctions auxiliaires: N/A
	 *  Exception: N/A
	 *  Params:	- une matrice contenant des String
	 *  		- un int numéro de ligne
	 *  retour: un string formaté avec le contenu de la ligne de la matrice
	 */		
	public static String tableLoader(String[][] table, int x) {
		String resultat = "";
		for(int y = 0; y < 9; y++) {
			resultat = resultat + table[x][y] +"|";
		}
		resultat = resultat + table[x][9];
		return resultat;
	}
	
	/*
	 *  colorPanel: 
	 *  Objectif: Changer la couleur d'élément textuel en fonction de l'attribut booléen isCoule() d'un objet Bateau précis d'un joueur précis
	 *  Module & fonctions auxiliaires: Class Bateau, Class Player
	 *  Exception: N/A
	 *  Params:	- Un objet Player
	 *  		- un int permettant de trouver un objet Bateau grace à l'attribut getFlootteO() de la class Player
	 *  retour: un string formatée pour colorer un texte
	 */	
	public static String colorPanel(Player player, int i) {
		if(player.getFlotteO(i).getLife() > 0) {
			return Ra+"";
		}
		else {
			return C+"";
		}
			
	}
	
	/*
	 *  attackpanel: 
	 *  Objectif: Remplie le champs tectuel du "Radar" pour indiquer la dernière case attaqué par l'adversaire
	 *  Module & fonctions auxiliaires: Class Player
	 *  Exception: N/A
	 *  Params:	- Un objet Player pour trouver l'attribut lastPlay
	 *  retour: un string formatée pour colorer un texte
	 */	
	public static String attackpanel(Player player) {
		if(player.getLastPlay().equals(" ")) {
			return "Analyse des attaques";
		}
		else {
			String attaque = "   Attaque en " + player.getLastPlay() + spaceCounter(6, player.getLastPlay() );
			return attaque;
		}
	}
	
	
	/*
	 *  waitPrint: 
	 *  Objectif: permet de temporiser la poursuite du programme, utilisé pour gerer les temps d'affichage
	 *  Module & fonctions auxiliaires: java.util.concurrent.TimeUnit;
	 *  Exception: InterruptedException -> echec du processus
	 *  Params:	- Un int représentant le temps de la mpause en millisecondes
	 *  retour: rien
	 */	
	public static void waitPrint(int time) {
		try {
			TimeUnit.MILLISECONDS.sleep(time);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
	/*
	 *  result: 
	 *  Objectif: Fonction driver en charge de la mise en place des evenements résultat entre deux tours de jeux!
	 *  Module & fonctions auxiliaires:waitPrint(), black(), touchePrint(), coulePrint(), victoirePrint(), ratePrint()
	 *  Exception: N/A
	 *  Params:	- Une String correspondant à un évenement
	 *  retour: rien
	 */	
	public static void result(String resultat) {
		blank();
		waitPrint(250);
		if(resultat.equals("touche")) {
			touchePrint();
		}
		else if(resultat.equals("coule")) {
			coulePrint();
		}
		else if(resultat.equals("victoire")) {
			victoirePrint();
		}
		else {
			ratePrint();
		}
		waitPrint(700);
		blank();
		waitPrint(250);
	}
	
	/*
	 *  playPrint: 
	 *  Objectif: Affiche une interface "textuel" mise à jour et permet l'entrée d'instruction d'"attaque" (coordonée de tableau sous forme xy)
	 *  Module & fonctions auxiliaires: colorPanel(), tableLoader(), play(), Class Bateau, Class Player, Class Game
	 *  Exception: N/A
	 *  Params:	- Un objet Player qui est le joueur actuel
	 *  		- Un objet Player qui est l'adversaire
	 *  		- Un objet Game qui est la partie actuelle
	 *  		- Un string contenant une éventuelle erreur précédente 
	 *  		- un string contenant le type de l'adversaire afin d'adapter l'affichage pour eviter la triche si il est humain.
	 *  retour: un string contenant l'erreur d'entrée.
	 */	
	public static String playPrint(Player player1, Player player2, Game partie, String errorIn, String type) {
		String error = errorIn;
		String choix;
		String[][] Dt = new String[10][10];
		if(type.equals("human")) {
			Dt = partie.getSpy_table().clone();
		}
		else {
			Dt = player1.getDeployementTable().clone();
		}
		String[][] At = player1.getAttackTable().clone();
		Scanner enemy = new Scanner(System.in);
		System.out.println(S+"");
		System.out.println(S+ "                _______. _______      ___      ____    __    ____      ___      .______             ");
		System.out.println("               /       ||   ____|    /   \\     \\   \\  /  \\  /   /     /   \\     |   _  \\            ");
		System.out.println("              |   (----`|  |__      /  ^  \\     \\   \\/    \\/   /     /  ^  \\    |  |_)  |           ");
		System.out.println("               \\   \\    |   __|    /  /_\\  \\     \\            /     /  /_\\  \\   |      /            ");
		System.out.println("           .----)   |   |  |____  /  _____  \\     \\    /\\    /     /  _____  \\  |  |\\  \\----.       ");
		System.out.println("           |_______/    |_______|/__/     \\__\\     \\__/  \\__/     /__/     \\__\\ | _| `._____|       ");
		System.out.println(S2+"                                                                                                    ");
		System.out.println(S+"  Navire en jeu           vous     advesaire              "+C+"+---+---+---+---+---+---+---+---+---+---+"+S+" ");
		System.out.println(S+"                                                         "+C+"/ 1   2   3   4   5   6   7   8   9   10 /|"+S);
		System.out.println(S+"                                                         "+C+"+---+---+---+---+---+---+---+---+---+---+ |"+S);
		System.out.println(C+colorPanel(player1, 1)+"      ]-+-[             "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               A "+C+"|" +W+tableLoader(Dt, 0)+C+"| |"+S);
		System.out.println(C+colorPanel(player1, 1)+"_______| |_____\\/___\\/__"+S+"   "+C+colorPanel(player1, 1)+" X "+S+"       "+C+colorPanel(player2, 1)+" X "+S2+"               B "+C+"|" +W+tableLoader(Dt, 1)+C+"| |"+S);
		System.out.println(C+colorPanel(player1, 1)+"\\_1__(_2_ )_3__4___5___/"+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               C "+C+"|" +W+tableLoader(Dt, 2)+C+"| |"+S);
		System.out.println(S2+"                                                       D "+C+"|" +W+tableLoader(Dt, 3)+C+"| |"+S);
		System.out.println(C+colorPanel(player1, 2)+"   [====]        ___    "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               E "+C+"|" +W+tableLoader(Dt, 4)+C+"| |"+S);
		System.out.println(C+colorPanel(player1, 2)+"___|     \\______/  /    "+S+"   "+C+colorPanel(player1, 2)+" X "+S+"       "+C+colorPanel(player2, 2)+" X "+S2+"               F "+C+"|" +W+tableLoader(Dt, 5)+C+"| |"+S);
		System.out.println(C+colorPanel(player1, 2)+"|_1____2___3\\__4_ /     "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               G "+C+"|" +W+tableLoader(Dt, 6)+C+"| |"+S);
		System.out.println(S2+"                                                       H "+C+"|" +W+tableLoader(Dt, 7)+C+"| |"+S);
		System.out.println(C+colorPanel(player1, 3)+"    ____                "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               I "+C+"|" +W+tableLoader(Dt, 8)+C+"| |"+S);
		System.out.println(C+colorPanel(player1, 3)+"___/  \\ \\_____          "+S+"   "+C+colorPanel(player1, 3)+" X "+S+"       "+C+colorPanel(player2, 3)+" X "+S2+"               J "+C+"|" +W+tableLoader(Dt, 9)+C+"| |"+S);
		System.out.println(C+colorPanel(player1, 3)+"|_1_>_ 2> >3_/          "+S+"   "+C+"   "+S+"       "+C+"   "+S+"                 "+C+"+---+---+---+---+---+---+---+---+---+---+ |"+S);
		System.out.println(S2+"                                                        "+C+"+---+---+---+---+---+---+---+---+---+---+ |"+S+" ");
		System.out.println(C+colorPanel(player1, 4)+"     |__                "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               "+C+"+ 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10+ |/"+S+" ");
		System.out.println(C+colorPanel(player1, 4)+"  ___|//|____           "+S+"   "+C+colorPanel(player1, 4)+" X "+S+"       "+C+colorPanel(player2, 4)+" X "+S2+"            A "+C+"/" +W+tableLoader(At, 0)+C+"/  /"+S+"  ");
		System.out.println(C+colorPanel(player1, 4)+"X(1___ 2__>3>)          "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"           B "+C+"/" +W+tableLoader(At, 1)+C+"/  /"+S+"   ");
		System.out.println(S2+"                                                  C "+C+"/" +W+tableLoader(At, 2)+C+"/  /"+S+"    ");
		System.out.println(C+colorPanel(player1, 5)+"    _                   "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"         D "+C+"/" +W+tableLoader(At, 3)+C+"/  /"+S+"     ");
		System.out.println(C+colorPanel(player1, 5)+" __| \\___.7             "+S+"   "+C+colorPanel(player1, 5)+" X "+S+"       "+C+colorPanel(player2, 5)+" X "+S2+"        E "+C+"/" +W+tableLoader(At, 4)+C+"/  /"+S+"      ");
		System.out.println(C+colorPanel(player1, 5)+" |1__>_2>/              "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"       F "+C+"/" +W+tableLoader(At, 5)+C+"/  /"+S+"       ");
		System.out.println(S2+"                                              G "+C+"/" +W+tableLoader(At, 6)+C+"/  /"+S+"        ");
		System.out.println(Ra+" /   : \\    RADAR: _____________________  "+S2+"   H "+C+"/" +W+tableLoader(At, 7)+C+"/  /"+S+"         ");
		System.out.println(Ra+"( ('o ) )         (_"+Wa+attackpanel(player2)+R+Ra+"_)"+S2+"  I "+C+"/" +W+tableLoader(At, 8)+C+"/  /"+S+"          ");
		System.out.println(Ra+" \\ .   /                                  "+S2+" J "+C+"/" +W+tableLoader(At, 9)+C+"/  /"+S+"           ");
		System.out.println("                                            "+C+"+---+---+---+---+---+---+---+---+---+---+  /"+S+"            ");
		System.out.println("                                            "+C+"|                                       | /"+S+"             ");
		System.out.println("                                            "+C+"+---+---+---+---+---+---+---+---+---+---+/"+S+"              ");
		System.out.println(S+Li+"Tour du joueur "+partie.current_player()+":                                                                                   ");
		System.out.println("     choisissez une case à attaquer !                                                               ");
		System.out.println("                                                                                                    ");
		System.out.println("     " + error + spaceCounter(95, error));
		System.out.print(Wa+"choix:"+R+S+Li+" ");
		choix = enemy.nextLine();
		if((!Pattern.matches("[a-j,A-J]{1}[0-9]{1,2}",choix))
			|| ((choix.length() > 2) && (Pattern.matches("[0-9]{2}",choix.substring(1,3))) && (Integer.parseInt(choix.substring(1,3)) > 10))
			|| (Pattern.matches("[0]{1}",choix.substring(1,2))) ){
			playPrint(player1, player2, partie, "attention il faut faire une requete valide de type B10", type);
		}
		else {
			Play(player1, player2, partie, choix);
		}
		return error;
	}
	
	/*
	 *  touchePrint:
	 *  Objectif: genere une page à la taille definit du terminal pour indiquer une touche
	 *  Module & fonctions auxiliaires: blockTxt()
	 *  Exception: N/A
	 *  Params:	- N/A
	 *  retour: N/A
	 */	
	public static void touchePrint() {
		blockTxt(17);
		System.out.print(C+"                  .___________.  ______    __    __    ______  __    __   _______                   "+W);
		System.out.print(C+"                  |           | /  __  \\  |  |  |  |  /      ||  |  |  | |   ____|                 "+W);
		System.out.print(C+"                   `---|  |----`|  |  |  | |  |  |  | |  ,----'|  |__|  | |  |__                     "+W);
		System.out.print(C+"                      |  |     |  |  |  | |  |  |  | |  |     |   __   | |   __|                    "+W);
		System.out.print(C+"                      |  |     |  `--'  | |  `--'  | |  `----.|  |  |  | |  |____                   "+W);
		System.out.print(C+"                      |__|      \\______/   \\______/   \\______||__|  |__| |_______|                  "+W);
		blockTxt(18);
	}
	
	/*
	 *  coulePrint:
	 *  Objectif: genere une page à la taille definit du terminal pour indiquer un coule
	 *  Module & fonctions auxiliaires: blockTxt()
	 *  Exception: N/A
	 *  Params:	- N/A
	 *  retour: N/A
	 */	
	public static void coulePrint() {
		blockTxt(17);
		System.out.print(C+"                            ______   ______    __    __   __       _______                          "+W);
		System.out.print(C+"                           /      | /  __  \\  |  |  |  | |  |     |   ____|                        "+W);
		System.out.print(C+"                           |  ,----'|  |  |  | |  |  |  | |  |     |  |                             "+W);
		System.out.print(C+"                           |  |     |  |  |  | |  |  |  | |  |     |   __|                          "+W);
		System.out.print(C+"                           |  `----.|  `--'  | |  `--'  | |  `----.|  |____                         "+W);
		System.out.print(C+"                            \\______| \\______/   \\______/  |_______||_______|                         "+W);
		blockTxt(18);
	}
	
	
	/*
	 *  victoirePrint:
	 *  Objectif: genere une page à la taille definit du terminal pour indiquer une victoire
	 *  Module & fonctions auxiliaires: blockTxt()
	 *  Exception: N/A
	 *  Params:	- N/A
	 *  retour: N/A
	 */
	public static void victoirePrint() {
		blockTxt(17);
		System.out.print(C+"             ____    ____  __    ______ .___________.  ______    __  .______       _______          "+W);
		System.out.print(C+"             \\   \\  /   / |  |  /      ||           | /  __  \\  |  | |   _  \\     |   ____|         "+W);
		System.out.print(C+"              \\   \\/   /  |  | |  ,----'`---|  |----`|  |  |  | |  | |  |_)  |    |  |__            "+W);
		System.out.print(C+"               \\      /   |  | |  |         |  |     |  |  |  | |  | |      /     |   __|           "+W);
		System.out.print(C+"                \\    /    |  | |  `----.    |  |     |  `--'  | |  | |  |\\  \\----.|  |____        "+W);
		System.out.print(C+"                   \\__/     |__|  \\______|    |__|      \\______/  |__| | _| `._____||_______|         "+W);
		blockTxt(18);
	}
	
	/*
	 *  ratePrint:
	 *  Objectif: genere une page à la taille definit du terminal pour indiquer un rate
	 *  Module & fonctions auxiliaires: blockTxt()
	 *  Exception: N/A
	 *  Params:	- N/A
	 *  retour: N/A
	 */
	public static void ratePrint() {
		blockTxt(17);
		System.out.print(C+"                          .______           ___      .___________. _______                          "+W);
		System.out.print(C+"                          |   _  \\         /   \\     |           ||   ____|                         "+W);
		System.out.print(C+"                          |  |_)  |       /  ^  \\    `---|  |----`|  |__                            "+W);
		System.out.print(C+"                          |      /       /  /_\\  \\       |  |     |   __|                           "+W);
		System.out.print(C+"                          |  |\\  \\----. /  _____  \\      |  |     |  |____                          "+W);
		System.out.print(C+"                          | _| `._____|/__/     \\__\\     |__|     |_______|                         "+W);
		blockTxt(18);
	}
	
	/*
	 *  p2Print:
	 *  Objectif: genere une page à la taille definit du terminal pour indiquer le tour du joueur 2 lors d'une partie contre l'ordinateur
	 *  Module & fonctions auxiliaires: blockTxt()
	 *  Exception: N/A
	 *  Params:	- N/A
	 *  retour: N/A
	 */
	public static void p2Print() {
		blockTxt(17);
		System.out.print(C+"        .______    __           ___      ____    ____  _______ .______          ___                 "+W);
		System.out.print(C+"        |   _  \\  |  |         /   \\     \\   \\  /   / |   ____||   _  \\        |__ \\                "+W);
		System.out.print(C+"        |  |_)  | |  |        /  ^  \\     \\   \\/   /  |  |__   |  |_)  |          ) |               "+W);
		System.out.print(C+"        |   ___/  |  |       /  /_\\  \\     \\_    _/   |   __|  |      /          / /                "+W);
		System.out.print(C+"        |  |      |  `----. /  _____  \\      |  |     |  |____ |  |\\  \\----.    / /_                "+W);
		System.out.print(C+"        | _|      |_______|/__/     \\__\\     |__|     |_______|| _| `._____|   |____|               "+W);
		blockTxt(18);
	}
	
	//****************************************************************************************************************
	// Fonctions auxiliaires: aide les fonctions principales à atteindre les simplifiants pour atteindre leur but    *
	//****************************************************************************************************************
	
	/*
	 *  coord: 
	 *  Objectif: traduit les coordonnées en un couple de valeurs numériques
	 *  Module & fonctions auxiliaires: N/A
	 *  Exception: N/A
	 *  Params:	- Une String correspondant au pattern [a-jA-J]{1}[0-9]{1,2}
	 *  retour: un tableau d'une ligne et deux colonnes contenant deux Int (coordonnées)
	 */		 
	public static int[] coord(String loc){
		HashMap<String, Integer> coordLetter= new HashMap<String, Integer>();
        coordLetter.put("a", 0);
        coordLetter.put("b", 1);
        coordLetter.put("c", 2);
        coordLetter.put("d", 3);
        coordLetter.put("e", 4);
        coordLetter.put("f", 5);
        coordLetter.put("g", 6);
        coordLetter.put("h", 7);
        coordLetter.put("i", 8);
        coordLetter.put("j", 9);
		int[] resultat = {0,0};
		resultat[0] = coordLetter.get(loc.substring(0,1).toLowerCase());
		resultat[1] = Integer.parseInt(loc.substring(1,(loc.length()))) -1; 
		return resultat;
	}
	
	
	
	//****************************************************************************************************************
	// Fonctions de résolutions: toutes les fonctions permettant le coeur de la boucle du jeu:                       *
	//          Le déploiement, le tour de jeu, les conditions de victoires ...                                      *
	//****************************************************************************************************************
	
	/*
	 *  accueil: 
	 *  Objectif: Affiche une "interface" textuel et permet le choix du nombre de joueur ou quitter le jeu
	 *  Module & fonctions auxiliaires: N/A
	 *  Exception: InputMismatchException -> pas un int
	 *  Params:	N/A
	 *  retour: un Int gerer par l'appelant
	 */	
	public static int accueil() {
		int choix = 0;
		Scanner enemy = new Scanner(System.in);
		System.out.println(S+"");
		System.out.println(R+S+"                _______. _______      ___      ____    __    ____      ___      .______             ");
		System.out.println("               /       ||   ____|    /   \\     \\   \\  /  \\  /   /     /   \\     |   _  \\            ");
		System.out.println("              |   (----`|  |__      /  ^  \\     \\   \\/    \\/   /     /  ^  \\    |  |_)  |           ");
		System.out.println("               \\   \\    |   __|    /  /_\\  \\     \\            /     /  /_\\  \\   |      /            ");
		System.out.println("           .----)   |   |  |____  /  _____  \\     \\    /\\    /     /  _____  \\  |  |\\  \\----.       ");
		System.out.println("           |_______/    |_______|/__/     \\__\\     \\__/  \\__/     /__/     \\__\\ | _| `._____|       ");
		System.out.println(S2+"                                                                                                    ");
		System.out.println(S+"  Navire en jeu           vous     advesaire              "+C+"+---+---+---+---+---+---+---+---+---+---+"+S+" "+S);
		System.out.println("                                                         "+C+"/ 1   2   3   4   5   6   7   8   9   10 /|"+S);
		System.out.println(S+"                                                         "+R+C+"+---+---+---+---+---+---+---+---+---+---+ |"+S);
		System.out.println(C+"      ]-+-[             "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               A "+C+"|" +W+ "≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈"+C+"| |"+S);
		System.out.println(C+"_______| |_____\\/___\\/__"+S+"   "+C+" X "+S+"       "+C+" X "+S2+"               B "+C+"|" +W+ "≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈"+C+"| |"+S);
		System.out.println(C+"\\_1__(_2_ )_3__4___5___/"+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               C "+C+"|" +W+ "≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈"+C+"| |"+S);
		System.out.println(S2+"                                                       D "+C+"|" +W+ "≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈"+C+"| |"+S);
		System.out.println(C+"   [====]        ___    "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               E "+C+"|" +W+ "≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈"+C+"| |"+S);
		System.out.println(C+"___|     \\______/  /    "+S+"   "+C+" X "+S+"       "+C+" X "+S2+"               F "+C+"|" +W+ "≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈"+C+"| |"+S);
		System.out.println(C+"|_1____2___3\\__4_ /     "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               G "+C+"|" +W+ "≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈"+C+"| |"+S);
		System.out.println(S2+"                                                       H "+C+"|" +W+ "≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈"+C+"| |"+S);
		System.out.println(C+"    ____                "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               I "+C+"|" +W+ "≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈"+C+"| |"+S);
		System.out.println(C+"___/  \\ \\_____          "+S+"   "+C+" X "+S+"       "+C+" X "+S2+"               J "+C+"|" +W+ "≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈|≈≈≈"+C+"| |"+S);
		System.out.println(C+"|_1_>_ 2> >3_/          "+S+"   "+C+"   "+S+"       "+C+"   "+S+"                 "+C+"+---+---+---+---+---+---+---+---+---+---+ |"+S);
		System.out.println(S2+"                                                        "+C+"+---+---+---+---+---+---+---+---+---+---+ |"+S+" ");
		System.out.println(C+"     |__                "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               "+C+"+ 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10+ |/"+S+" ");
		System.out.println(C+"  ___|//|____           "+S+"   "+C+" X "+S+"       "+C+" X "+S2+"            A "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"  ");
		System.out.println(C+"X(1___ 2__>3>)          "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"           B "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"   ");
		System.out.println(S2+"                                                  C "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"    ");
		System.out.println(C+"    _                   "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"         D "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"     ");
		System.out.println(C+" __| \\___.7             "+S+"   "+C+" X "+S+"       "+C+" X "+S2+"        E "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"      ");
		System.out.println(C+" |1__>_2>/              "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"       F "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"       ");
		System.out.println(S2+"                                              G "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"        ");
		System.out.println(Ra+" /   : \\    RADAR: _____________________  "+S2+"   H "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"         ");
		System.out.println(Ra+"( ('o ) )         (_"+Wa+"Analyse des attaques"+R+Ra+"_)"+S2+"  I "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"          ");
		System.out.println(Ra+" \\ .   /                                  "+S2+" J "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"           ");
		System.out.println("                                            "+C+"+---+---+---+---+---+---+---+---+---+---+  /"+S+"            ");
		System.out.println("                                            "+C+"|                                       | /"+S+"             ");
		System.out.println("                                            "+C+"+---+---+---+---+---+---+---+---+---+---+/"+S+"              ");
		System.out.println(Li+"Bienvenue sur Seawar:                                                                               ");
		System.out.println("Selectionnez le nombre de Joueur: 1.  1 Joueur                                                      ");
		System.out.println("                                  2.  2 Joueurs                                                     ");
		System.out.println("                                  3.  quitter                                                       ");
		System.out.print(Wa+"choix:"+R+S+Li+" ");
		try {
			choix = enemy.nextInt();
		}
		catch(InputMismatchException e) {
			// si erreur de saisie: retour 0 pour relancer une nouvelle fresh loop
			return 0;
		}
		return choix;
	}	
	
	/*
	 *  place: 
	 *  Objectif: Affiche une "interface" textuel et permet  de selectionner des positions pour les navires
	 *  Module & fonctions auxiliaires: deploiement(), tableLoader(), class Player, class Bateau
	 *  Exception: vérification de pattern d'input utilisateur, boucle sans utilisation de try/catch
	 *  Params:	-un objet type Player qui est le joueur qui fait son déploiement
	 *  		-un int pour appeler un Objet Bateau particulier depuis un Objet Player
	 *  		-un int représentant le joueur actuelle
	 *  		-une String contenant une erreur de pattern renvoyé en cas d'erreur dans la boucle de test. 
	 *  retour: une String vide ("") ou non
	 */		
	public static String place(Player player, int i, int Current_player, String errorIn) {
		String error = errorIn;
		String choix;
		String Dt[][] = player.getDeployementTable().clone();
		Scanner enemy = new Scanner(System.in);
		System.out.println(S+"");
		System.out.println(R+S+ "                _______. _______      ___      ____    __    ____      ___      .______             ");
		System.out.println("               /       ||   ____|    /   \\     \\   \\  /  \\  /   /     /   \\     |   _  \\            ");
		System.out.println("              |   (----`|  |__      /  ^  \\     \\   \\/    \\/   /     /  ^  \\    |  |_)  |           ");
		System.out.println("               \\   \\    |   __|    /  /_\\  \\     \\            /     /  /_\\  \\   |      /            ");
		System.out.println("           .----)   |   |  |____  /  _____  \\     \\    /\\    /     /  _____  \\  |  |\\  \\----.       ");
		System.out.println("           |_______/    |_______|/__/     \\__\\     \\__/  \\__/     /__/     \\__\\ | _| `._____|       ");
		System.out.println(S2+"                                                                                                    ");
		System.out.println(S+"  Navire en jeu           vous     advesaire              "+C+"+---+---+---+---+---+---+---+---+---+---+"+S+" ");
		System.out.println("                                                         "+C+"/ 1   2   3   4   5   6   7   8   9   10 /|"+S);
		System.out.println(S+"                                                         "+C+"+---+---+---+---+---+---+---+---+---+---+ |"+S);
		System.out.println(C+"      ]-+-[             "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               A "+C+"|" +W+tableLoader(Dt, 0)+C+"| |"+S);
		System.out.println(C+"_______| |_____\\/___\\/__"+S+"   "+C+" X "+S+"       "+C+" X "+S2+"               B "+C+"|" +W+tableLoader(Dt, 1)+C+"| |"+S);
		System.out.println(C+"\\_1__(_2_ )_3__4___5___/"+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               C "+C+"|" +W+tableLoader(Dt, 2)+C+"| |"+S);
		System.out.println(S2+"                                                       D "+C+"|" +W+tableLoader(Dt, 3)+C+"| |"+S);
		System.out.println(C+"   [====]        ___    "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               E "+C+"|" +W+tableLoader(Dt, 4)+C+"| |"+S);
		System.out.println(C+"___|     \\______/  /    "+S+"   "+C+" X "+S+"       "+C+" X "+S2+"               F "+C+"|" +W+tableLoader(Dt, 5)+C+"| |"+S);
		System.out.println(C+"|_1____2___3\\__4_ /     "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               G "+C+"|" +W+tableLoader(Dt, 6)+C+"| |"+S);
		System.out.println(S2+"                                                       H "+C+"|" +W+tableLoader(Dt, 7)+C+"| |"+S);
		System.out.println(C+"    ____                "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               I "+C+"|" +W+tableLoader(Dt, 8)+C+"| |"+S);
		System.out.println(C+"___/  \\ \\_____          "+S+"   "+C+" X "+S+"       "+C+" X "+S2+"               J "+C+"|" +W+tableLoader(Dt, 9)+C+"| |"+S);
		System.out.println(C+"|_1_>_ 2> >3_/          "+S+"   "+C+"   "+S+"       "+C+"   "+S+"                 "+C+"+---+---+---+---+---+---+---+---+---+---+ |"+S);
		System.out.println(S2+"                                                        "+C+"+---+---+---+---+---+---+---+---+---+---+ |"+S+" ");
		System.out.println(C+"     |__                "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"               "+C+"+ 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10+ |/"+S+" ");
		System.out.println(C+"  ___|//|____           "+S+"   "+C+" X "+S+"       "+C+" X "+S2+"            A "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"  ");
		System.out.println(C+"X(1___ 2__>3>)          "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"           B "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"   ");
		System.out.println(S2+"                                                  C "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"    ");
		System.out.println(C+"    _                   "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"         D "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"     ");
		System.out.println(C+" __| \\___.7             "+S+"   "+C+" X "+S+"       "+C+" X "+S2+"        E "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"      ");
		System.out.println(C+" |1__>_2>/              "+S+"   "+C+"   "+S+"       "+C+"   "+S2+"       F "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"       ");
		System.out.println(S2+"                                              G "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"        ");
		System.out.println(Ra+" /   : \\    RADAR: _____________________  "+S2+"   H "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"         ");
		System.out.println(Ra+"( ('o ) )         (_"+Wa+"Analyse des attaques"+R+Ra+"_)"+S2+"  I "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"          ");
		System.out.println(Ra+" \\ .   /                                  "+S2+" J "+C+"/" +W+ "≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈/≈≈≈"+C+"/  /"+S+"           ");
		System.out.println("                                            "+C+"+---+---+---+---+---+---+---+---+---+---+  /"+S+"            ");
		System.out.println("                                            "+C+"|                                       | /"+S+"             ");
		System.out.println(S+"                                            "+R+C+"+---+---+---+---+---+---+---+---+---+---+/"+R+S+"              ");
		System.out.println(S+Li+"Deploiement du joueur "+Current_player+":                                                                            ");
		System.out.println("     choisissez l'emplacement pour votre "+ player.getFlotte(i)+":"+spaceCounter(58, player.getFlotte(i)));
		System.out.println("     Indiquez la case initiale de deploiement et le sens 1:haut 2:bas 3:gauche 4:droite (A1 4)      ");
		System.out.println("     " + error + spaceCounter(95, error));
		System.out.print(Wa+"choix:"+R+S+Li+" ");
		choix = enemy.nextLine();
		if((!Pattern.matches("[a-j,A-J]{1}[0-9]{1,2}[\\s]{1}[1-4]{1}",choix)) 
		  || (Pattern.matches("[0]{1}",choix.substring(1,2)))
		  || ((Pattern.matches("[0-9]{2}",choix.substring(1,3))) && (Integer.parseInt(choix.substring(1,3)) > 10))
		  || ((choix.length() > 6) &&(Pattern.matches("[0-9]{2}",choix.substring(5,7))) && (Integer.parseInt(choix.substring(5,7)) > 10)) ){
			error = deploiement("A0 0", player, player.getFlotteO(i));
		}
		else {
			error = deploiement(choix, player, player.getFlotteO(i));
		}
		return error;
	}
	
	/*
	 *  deploiement: 
	 *  Objectif:  verifie la validité et enregistre les marqueurs du bateau.
	 *  Module & fonctions auxiliaires: coord(), class Player, class Bateau
	 *  Exception: Test de validité & accumulateur erreur, retourne une string d'erreur si besoin (erreur > 0)
	 *  Params:	-une String contenant les instruction de pattern [a-jA-J]{1}[0-9]{1,2}[\\s]{1}[1-4]{1}
	 *  		-un objet Player pour le joueur concerné
	 *  		-un objet Bateau pour le bateau concerné
	 *  retour: une String vide ("") ou non
	 */
	public static String deploiement(String loc, Player player, Bateau type) {
		String errorMessage = "";
		int ind;
		int erreur;
		int[][] current = new int[6][2];
		if((loc.length()>3) && (loc.length()<6)){
			int[] coordonnees = coord(loc.substring(0,(loc.length()-2)));
			int sens =  Integer.parseInt(loc.substring(loc.length() - 1, loc.length()));
			switch(sens) {
				case 1:
					// haut
					erreur = 0;
					if((coordonnees[0] - type.getSize()) + 1 < 0) {
						erreur += 1;
					}
					else {
						for(int ord = coordonnees[0]; ord > (coordonnees[0] - type.getSize()); ord--){
							if(!"≈≈≈".equals(player.getDeployementTable(ord, coordonnees[1]))) {
								erreur = erreur +1; 
							}
						}
					}
					if(erreur > 0) {
						errorMessage = "Vers le haut: Le navire n'a pas pu être déployé: coordonnées invalides";
					}
					// pour chaque case 2: enregistrement du déploiement
					else {
						type.setCases(coordonnees, 0);
						player.setDeployementTable(coordonnees[0], coordonnees[1], SS+" O "+WS);
						ind = 1;
						for(int start = coordonnees[0]-1; start > coordonnees[0]+1-type.getSize(); start--) {
							current[ind][0] = start;
							current[ind][1] = coordonnees[1];
							type.setCases(current[ind], ind++);
						    player.setDeployementTable(start, coordonnees[1], SS+" X "+WS);
						}
						current[ind][0] = coordonnees[0]+1-type.getSize();
						current[ind][1] = coordonnees[1];
						type.setCases(current[ind], ind);
						player.setDeployementTable(coordonnees[0]+1-type.getSize(), coordonnees[1],  SS+" ^ "+WS);
					}
					break;
				case 2:
					// bas
					erreur = 0;
					if((coordonnees[0] + type.getSize()) -1 > 9) {
						erreur = erreur + 1;
					}
					else {
						for(int ord = coordonnees[0]; ord < (coordonnees[0] + type.getSize()); ord++){
							if(!"≈≈≈".equals(player.getDeployementTable(ord, coordonnees[1]))) {
								erreur = erreur +1; 
							}
						}
					}
					if(erreur > 0) {
						errorMessage = "Vers le bas: Le navire n'a pas pu être déployée: coordonnées invalides";
					}
					else {
						type.setCases(coordonnees, 0);
						player.setDeployementTable(coordonnees[0], coordonnees[1], SS+" O "+WS);
						ind = 1;
						for(int start = coordonnees[0]+1; start < coordonnees[0]-1+type.getSize(); start++) {
							 current[ind][0] = start;
							 current[ind][1] = coordonnees[1];
							 type.setCases(current[ind], ind++);
						     player.setDeployementTable(start, coordonnees[1], SS+" X "+WS);
						}
						current[ind][0] = coordonnees[0]-1+type.getSize();
						current[ind][1] = coordonnees[1];
						type.setCases(current[ind], ind);
						player.setDeployementTable(coordonnees[0]-1+type.getSize(), coordonnees[1],  SS+" v "+WS);
					}
					break;
				case 3:
					//gauche
					erreur = 0;
					if((coordonnees[1] - type.getSize()) +1 < 0) {
						erreur = erreur + 1;
					}
					else {
						for(int abs = coordonnees[1]; abs > (coordonnees[1] - type.getSize()); abs--){
							if(!"≈≈≈".equals(player.getDeployementTable(coordonnees[0], abs))) {
								erreur = erreur +1; 
							}
						}
					}
					if(erreur > 0) {
						errorMessage = "Vers la gauche: Le navire n'a pas pu être déployée: des coordonnées invalide";
					}
					else {
						type.setCases(coordonnees, 0);
						player.setDeployementTable(coordonnees[0], coordonnees[1], SS+"O  "+WS);
						ind = 1;
						for(int start = coordonnees[1]-1; start > coordonnees[1]+1-type.getSize(); start--) {
							 current[ind][0] = coordonnees[0];
							 current[ind][1] = start;
							 type.setCases(current[ind], ind++);
						     player.setDeployementTable(coordonnees[0], start, SS+" X "+WS);
						}
						current[ind][0] = coordonnees[0];
						current[ind][1] = coordonnees[1]+1-type.getSize();
						type.setCases(current[ind], ind);
						player.setDeployementTable(coordonnees[0], coordonnees[1]+1-type.getSize(),  SS+"  <"+WS);
					}
					break;
				case 4:
					//droite
					erreur = 0;
					if((coordonnees[1] + type.getSize())-1 > 9) {
						erreur = erreur + 1;
					}
					else {
						for(int abs = coordonnees[1]; abs < (coordonnees[1] + type.getSize()); abs++){
							if(!"≈≈≈".equals(player.getDeployementTable(coordonnees[0], abs))) {
								erreur = erreur +1; 
							}
						}
					}
					if(erreur > 0) {
						errorMessage = "Vers la droite: Le navire n'a pas pu être déployée dut à des coordonnées invalide";
					}
					else {
						type.setCases(coordonnees, 0);
						player.setDeployementTable(coordonnees[0], coordonnees[1], SS+"  O"+WS);
						ind = 1;
						for(int start = coordonnees[1]+1; start < coordonnees[1]-1+type.getSize(); start++) {
							 current[ind][0] = coordonnees[0];
							 current[ind][1] = start;
							 type.setCases(current[ind], ind++);
						     player.setDeployementTable(coordonnees[0], start, SS+" X "+WS);
						}
						current[ind][0] = coordonnees[0];
						current[ind][1] = coordonnees[1]-1+type.getSize();
						type.setCases(current[ind], ind);
						player.setDeployementTable(coordonnees[0], coordonnees[1]-1+type.getSize(),  SS+">  "+WS);
					}
					break;
				default:
					errorMessage = "Le navire n'a pas pu être déployée dut à des coordonnées invalide";
			}
			return errorMessage;
		}
		else {
			errorMessage = "Il faut nommer une case telle que A10 et une direction avec le chiffre approprié!";
			return errorMessage;
		}
	}
	
	/*
	 *  Play: 
	 *  Objectif:  fonction de jeu, verifie les effets des attaques et met à jour les variables impactées
	 *  Module & fonctions auxiliaires: coord(), result() ,class Player, class Game, class Bateau
	 *  Exception: N/A
	 *  Params:	- un objet Player pour le joueur actuel
	 *  		- un objet Player pour le joueur adverse
	 *  		- un objet Game pour la partie actuelle
	 *  		- une String contenant les coordonnées non traités de l'attaque (lettre + numero)
	 *  retour: N/A
	 */
	public static void Play(Player player1, Player player2, Game partie, String choix) {
		int[] coordonnees = coord(choix);		
		player1.setLastPlay(choix);
		if(!player2.getDeployementTable(coordonnees[0], coordonnees[1]).equals("≈≈≈") 
	       && !player2.getDeployementTable(coordonnees[0], coordonnees[1]).equals(DD+"≈≈≈"+W)){
			result("touche");
			player1.setAttackTable(coordonnees[0], coordonnees[1], C+" X "+W);
			player2.setDeployementTable(coordonnees[0], coordonnees[1], DD+"≈≈≈"+W);
			for(int i=1; i<6; i++){
				Bateau test = player2.getFlotteO(i);
				int[][] testCoord = test.getCases().clone();
				for(int j=0;j<test.getSize();j++) {
					if((testCoord[j][0] == coordonnees[0])
					   && (testCoord[j][1] == coordonnees[1])) {
						test.setLife(test.getLife() - 1);
						if(test.getLife() == 0){
							test.setCoule(true);
						}
						if(test.isCoule()) {
							player2.setNavires(player2.getNavires()-1);
							result("coule");
						}
						if(player2.getNavires() == 0) {
							result("victoire");
						}
					}
				}
			}
		}
		else {
			result("rate");
			if(!player1.getAttackTable(coordonnees[0], coordonnees[1]).equals(C+" X "+W)) {
				player1.setAttackTable(coordonnees[0], coordonnees[1], DD+"≈≈≈"+W);
				player2.setDeployementTable(coordonnees[0], coordonnees[1], DD+"≈≈≈"+W);
			}
		}
	}
	
	//****************************************************************************************************************
	// Fonctions de dédiées à l'automatisation du joueur 2                                                           *
	//****************************************************************************************************************
	
	/*
	 *  iaCoord: 
	 *  Objectif:  Traduit une abscisse choisit par l'ia en lettre pour simuler le jeu
	 *  Module & fonctions auxiliaires: N/A
	 *  Exception: N/A
	 *  Params:	- un int représentant un abscisse
	 *  retour: Une String contenant la lettre correspondante
	 */
	public static String iaCoord(int num) {
		HashMap<Integer, String> coordLetter= new HashMap<Integer, String>();
        coordLetter.put(0, "A");
        coordLetter.put(1, "B");
        coordLetter.put(2, "C");
        coordLetter.put(3, "D");
        coordLetter.put(4, "E");
        coordLetter.put(5, "F");
        coordLetter.put(6, "G");
        coordLetter.put(7, "H");
        coordLetter.put(8, "I");
        coordLetter.put(9, "J");
        return coordLetter.get(num);
	}
	
	/*
	 *  iaPlace: 
	 *  Objectif:  gènere une reponse pour le placement de l'ia
	 *  Module & fonctions auxiliaires: iaCoord(), Class Player, CLass Bateau
	 *  Exception: N/A
	 *  Params:	- un objet Player du joueur ia
	 *  		- un int pour trouver le bateau correspondant pour le placement
	 *  retour: Une String de commande de placement
	 */
	public static String iaPlace(Player player, int i) {
        Random dice = new Random();
        int sens = dice.nextInt(4)+1;
        String ord = iaCoord(dice.nextInt(10));
        int abs = dice.nextInt(10)+1;
		String choix = ord + abs + " " + sens;
		String error = deploiement(choix, player, player.getFlotteO(i));
		return error;
	}
	
	/*
	 *  minSizeShip: 
	 *  Objectif:  Définir la taille minimal du bateau encore enjeu
	 *  Module & fonctions auxiliaires: Class Player, CLass Bateau
	 *  Exception: N/A
	 *  Params:	- un objet Player du joueur ia
	 *  		- un int pour trouver le bateau correspondant pour le placement
	 *  retour: Une String de commande de placement
	 */
	public static int minSizeShip(Player player) {
		int max = 10;
		for(int i=1; i<6; i++){
			Bateau test = player.getFlotteO(i);
			if(!test.isCoule() && test.getSize() < max ) {
				max = test.getSize();
			}
		}
		return max;
	}
	
	
	/*
	 *  testRange: 
	 *  Objectif:  Vérifie si une proposition de l'ordinateur est correcte en vérifiant qu'un des bateaux recherches peut effectivement se trouver sous cette case
	 *  Module & fonctions auxiliaires: Class Player
	 *  Exception: N/A
	 *  Params:	- un int pour la modification de l'abscisse
	 *  		- un int pour l'abscisse initiale
	 *  		- un int pour la modification de l'ordonnée
	 *  		- un int pour l'ordonnée initiale
	 *  		- un int pour la taille minimale des bateaux recherchés
	 *  		- un objet Player pour la table d'attaque
	 *  retour: Un boolean
	 */
	public static boolean testRange(int modx, int x, int mody, int y, int size, Player player) {
		boolean result = false;
		int count = 0;
		for(int i = 1; i < size; i++) {
			if(x+i*modx>=0 && x+i*modx<10 && y+i*mody >= 0 && x+i*mody<10 && player.getAttackTable(x + i * modx, y + i * mody).equals("≈≈≈")) {
				count = count +1;
			}
		}
		if(count == size) {
			result = true;
		}
		return result;
	}
	
	/*
	 *  sizeCoule: 
	 *  Objectif:  Determine la taille du dernier bateau coule par l'ordinateur afin de pouvoir proceder à des vérifications
	 *  Module & fonctions auxiliaires: Class Player
	 *  Exception: N/A
	 *  Params:	- un objet Player pour le joueur 
	 *  		- un onjet Player pour l'adversaire
	 *  retour: Un int correspondant à la taille de l'objet bateau minimale pas encore coulé
	 */
    public static int sizeCoule(Player player1, Player player2){
    	int sc = 0;
    	for(int i = 1; i < 6; i++ ) {
    		System.out.println("target: " + player1.getAliveTarget(i) +" navire "+ player2.getFlotteO(i).isCoule());
    		if(player1.getAliveTarget(i) == player2.getFlotteO(i).isCoule()) {
    			player1.replaceAliveTarget(i, false);
    			sc = player2.getFlotteO(i).getSize();
    		}
    	}
    	return sc;
    }
    
	/*
	 *  iaPlay: 
	 *  Objectif:  Driver de decision pour le jeu de l'ordinateur:
	 *  	- En l'absence de touche positive tente des cases au hazard jusqu'à ce qu'une proposition valide des contraintes
	 *  	- Si la mémoire à une première touche unique cherche autour de cette case.
	 *  	- Si il y a au moins deux touches tente de continuer la ligne jusqu'a coule ou rate, si rate reprend dans l'autre sens, si encore rate passe en mode multitouche
	 *  	- Si coule mais que le nombre de touche superieur à la taille du bateau multitouche.
	 *  	- en multitouche il enregistre tous les potentiels touches initiales dans un tableau et les reprends une à une a partir de l'etape premiere touche.
	 *  Module & fonctions auxiliaires: Class Player, Class Game
	 *  Exception: N/A
	 *  Params:	- un objet Player pour le joueur 
	 *  		- un onjet Player pour l'adversaire
	 *  retour: Un int correspondant à la taille de l'objet bateau minimale pas encore coulé
	 */
	public static void iaPlay(Player player1, Player player2, Game partie) {
        Random dice = new Random();
        int ord;
        int abs;
		int offset;
        String ordS;
        String choix;
        int[] c;
		int[] hitpoint;
        // Si des mémoires sont disponible on les charges comme première touche
        if(!player1.getFirstHitMemory().isEmpty() && player1.getFirstHit()[0] == -1) {
        	c = player1.getFirstHitMemory().pop();
        	player1.setFirstHit(c);
        	player1.setNbtouche(1);
			iaPlay(player1, player2, partie);
        }
        // sinon en l'absence depremière touche on tente une case en random sous contrainte
        else if(player1.getFirstHit()[0] == -1) {
        	System.out.println("random");
        	do {
        		ord = dice.nextInt(10);
        		abs = dice.nextInt(10);
        	} while(!player1.getAttackTable(ord, abs).equals("≈≈≈")
        			&& 	(!testRange(-1, ord, 0, abs, minSizeShip(player2), player1)
        				||!testRange(1, ord, 0, abs, minSizeShip(player2), player1)
        				||!testRange(0, ord, -1, abs, minSizeShip(player2), player1)
        				||!testRange(0, ord, 1, abs, minSizeShip(player2), player1) ) );
        	ordS = iaCoord(ord);
        	choix = "" + ordS + (abs+1);
        	Play(player1, player2, partie, choix);
        	if(player1.getAttackTable(ord, abs).equals(C+" X "+W)) {
        		player1.setNbtouche(player1.getNbtouche()+1);
        		player1.setFirstHit(ord, abs);
        	}
        }
        // si l'on a une première touche on cherche l'orientation en tapant les cases disponibles adjacente
        else if(player1.getLastHit()[0] == -1) {
        	do {
        		int rand = dice.nextInt(4);
        		switch(rand) {
        			case 0:        	 
        				ord = player1.getFirstHit()[0] + 1;
        			    abs = player1.getFirstHit()[1];
        			    break;
        			case 1:
        				ord = player1.getFirstHit()[0];
        			    abs = player1.getFirstHit()[1] + 1;
        			    break;
        			case 2:
        				ord = player1.getFirstHit()[0] - 1;
        			    abs = player1.getFirstHit()[1];
        			    break;
        			case 3:
        				ord = player1.getFirstHit()[0];
        			    abs = player1.getFirstHit()[1] - 1;
        			    break;
        			default:
        				ord = player1.getFirstHit()[0];
        			    abs = player1.getFirstHit()[1];
        				break;
        		}
        	} while (ord<0 || abs<0 || ord>9 || abs>9 || !player1.getAttackTable(ord, abs).equals("≈≈≈"));
        	ordS = iaCoord(ord);
        	choix = "" + ordS + (abs+1);
        	Play(player1, player2, partie, choix);
        	if(player1.getAttackTable(ord, abs).equals(C+" X "+W)) {
        		player1.setNbtouche(player1.getNbtouche()+1);
        		player1.setLastHit(ord, abs);
        	}
        	// cas ou le bateau est coule a cette étape !
        	if(!(player1.getTarget() == player2.getNavires())) {
        		offset = sizeCoule(player1, player2);
        		player1.setNbtouche(0);
    			player1.setTarget(player1.getTarget() -1);
    			player1.setFirstHit(-1, -1);
    			player1.setLastHit(-1, -1);
    			player1.setLostSignal(0);
        	}
        }          
        // Avec deux point on peut tracer une ligne jusqu'à couler ou rater 2 fois
        else if(player1.getLostSignal() == 0 || player1.getLostSignal() == 1) {
        	if(player1.getLastHit()[0] > player1.getFirstHit()[0]) {
				ord = player1.getLastHit()[0]+1 ;
			    abs = player1.getLastHit()[1];
        	}
        	else if(player1.getLastHit()[0] < player1.getFirstHit()[0]) {
				ord = player1.getLastHit()[0]-1 ;
			    abs = player1.getLastHit()[1];
        	}
        	else if(player1.getLastHit()[1] > player1.getFirstHit()[1]) {
				ord = player1.getLastHit()[0];
			    abs = player1.getLastHit()[1]+1;
        	}
        	else {
				ord = player1.getLastHit()[0];
			    abs = player1.getLastHit()[1]-1;
        	}
        	// cas ou il faut chercher de l'autre côté de la ligne
        	if((ord<0 || abs<0 || ord>9 || abs>9) || !player1.getAttackTable(ord, abs).equals("≈≈≈")) {
        		player1.setLostSignal(player1.getLostSignal()+1);
        		if(player1.getLostSignal() == 1) {
        			c = new int[2];
        			c = player1.getFirstHit();
        			player1.setFirstHit(player1.getLastHit());
        			player1.setLastHit(c);
        			iaPlay(player1, player2, partie);
        		}
        	}
        	else if(player1.getAttackTable(ord, abs).equals("≈≈≈")) {
            	ordS = iaCoord(ord);
            	choix = "" + ordS + (abs+1);
            	Play(player1, player2, partie, choix);
        		if((player1.getAttackTable(ord, abs).equals(C+" X "+W)) && (player1.getTarget() == player2.getNavires())){
        			player1.setNbtouche(player1.getNbtouche()+1);
        			player1.setLastHit(ord, abs);
        		}
        		else if(player1.getTarget() == player2.getNavires()) {
            		player1.setLostSignal(player1.getLostSignal()+1);
            		if(player1.getLostSignal() == 1) {
            			c = new int[2];
            			c = player1.getFirstHit();
            			player1.setFirstHit(player1.getLastHit());
            			player1.setLastHit(c);
            		}
            		//cas de multitouche sans coule
            		else {
            			player1.setLostSignal(2);
            		}
        		}
        		else {
        			//verifier nombres de touches et taille du navire
        			offset = sizeCoule(player1, player2);
        			player1.setNbtouche(player1.getNbtouche()+1);
        			if(offset >= player1.getNbtouche()){
            			player1.setTarget(player1.getTarget() -1);
            			player1.setFirstHit(-1, -1);
            			player1.setLastHit(-1, -1);
            			player1.setLostSignal(0);
            			player1.setNbtouche(0);
        			}
        			// si on a touche un autre bateau
        			else {
        				player1.setNbtouche(player1.getNbtouche()-offset);
            			player1.setTarget(player1.getTarget() -1);;
            			player1.setLastHit(-1, -1);
            			player1.setLostSignal(1);
        			}
        		}
        	}
        	else {
        		player1.setLostSignal(player1.getLostSignal()+1);
        		if(player1.getLostSignal() == 1) {
        			c = new int[2];
        			c = player1.getFirstHit();
        			player1.setFirstHit(player1.getLastHit());
        			player1.setLastHit(c);
        			iaPlay(player1, player2, partie);
        		}
        	}
        }
        // multiple première touche
        else {
        	if(player1.getFirstHit()[0] > player1.getLastHit()[0]) {
        		for(int start = player1.getFirstHit()[0]; start >player1.getLastHit()[0]-1; start--) {
        			hitpoint = new int[] {start,  player1.getFirstHit()[1]};
        			player1.pushFirstHitMemory(hitpoint);
        		}
        	}
        	else if(player1.getFirstHit()[0] < player1.getLastHit()[0]) {
        		for(int start = player1.getFirstHit()[0]; start <player1.getLastHit()[0]+1; start++) {
        			hitpoint = new int[] {start,  player1.getFirstHit()[1]};
        			player1.pushFirstHitMemory(hitpoint);
        		}
        	}
        	else if(player1.getFirstHit()[1] > player1.getLastHit()[1]) {
        		for(int start = player1.getFirstHit()[1]; start >player1.getLastHit()[1]-1; start--) {
        			hitpoint = new int[] {player1.getFirstHit()[0], start};
        			player1.pushFirstHitMemory(hitpoint);
        		}
        	}
        	else if(player1.getFirstHit()[1] <  player1.getLastHit()[1]) {
        		for(int start = player1.getFirstHit()[1]; start < (player1.getLastHit()[1]+1); start++) {
        			hitpoint = new int[] {player1.getFirstHit()[0], start};
        			player1.pushFirstHitMemory(hitpoint);
        		}
        	}
			player1.setFirstHit(-1, -1);
			player1.setLastHit(-1, -1);
			player1.setLostSignal(0);
			player1.setNbtouche(0);
			iaPlay(player1, player2, partie);
        }
	}
	
	
	//****************************************************************************************************************
	//  Main driver de la fonction:                                                                                  *
	//****************************************************************************************************************
	public static void main(String[] args) {
		boolean run = true;
		boolean game = true;
		Game partie = new Game();
		HashMap<Integer, Player> players= new HashMap<Integer, Player>();
		int nb_joueur = 0;
		String erreur = "";

		//loop du programme                           
		while(run) {
			if(partie.current_player() == 0) {
				//reinitialisation/initialisation
				game = true;
				nb_joueur = 0;
				erreur = "";
		        players.put(1, new Player("human"));
		        players.put(2, new Player("human"));
				nb_joueur = accueil();
				if((nb_joueur > 0) && (nb_joueur < 3)) {
					if(nb_joueur == 1) {
						players.get(2).setType("ia");
					}
					partie.setCurrent_player(1);
				}
				else if(nb_joueur !=0) {
					System.out.print("Merci d'avoir joué à SeaWar\n");
					run = false;
				}
			}
			else if(nb_joueur != 0){
				//loop d'une partie
				while(game) {
					if(!players.get(partie.current_player()).isDeployement()){
						for(int i=1; i<7; i++){
							if(! erreur.equals("")) {
								i = i-1;
							}
							if (i < 6) {
								if(players.get(partie.current_player()). getType() == "human"){
									erreur = place(players.get(partie.current_player()), i, partie.current_player(), erreur);			
								}
								else {
									erreur = iaPlace(players.get(partie.current_player()), i);
								}
							}
						}
						players.get(partie.current_player()).setDeployement(true);
					}
					else {
						if(players.get(partie.current_player()). getType() == "human"){
							playPrint(players.get(partie.current_player()), players.get(partie.getOther_player()), partie, erreur, players.get(2).getType());
						}
						else {
							waitPrint(250);
							p2Print();
							waitPrint(750);
							iaPlay(players.get(partie.current_player()), players.get(partie.getOther_player()), partie);
						}
					}
					if(players.get(partie.getOther_player()).getNavires() == 0) {
						game = false;
						partie.nextplayer(0);
						partie.setOther_player(2);
					}
					else {	
						partie.nextplayer(partie.getOther_player());
					}
				}
			}
		}
	}
}
