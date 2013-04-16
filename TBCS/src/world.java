import java.util.Scanner;

public class world{
	
	public static int pX = 7;
	public static int pY = 7;
	public static int gameOn = 1;
	public static String cmdString = "";
	public static String[][] world = {
			{ "1", "1", "1", "1", "1", "0", "0", "0" },
			{ "1", "1", "1", "1", "1", "0", "0", "0" },
			{ "0", "0", "0", "0", "0", "0", "1", "0" },
			{ "0", "0", "0", "0", "0", "0", "1", "0" },
			{ "0", "0", "1", "1", "1", "0", "1", "0" },
			{ "0", "0", "0", "0", "1", "0", "1", "0" },
			{ "1", "0", "0", "0", "0", "b", "0", "0" },
			{ "1", "1", "1", "0", "1", "1", "1", "x" }
	};
	
	public static void main(String[] args){
		Scanner movement = new Scanner(System.in);		
		
		System.out.println("Welcome to the game!");
		System.out.println("For complete list of commands type HELP");
		
		do{
			cmdString = movement.nextLine();
			System.out.println(cmdString);
			if (cmdString.equals("N")){
				moveN();
			} else if (cmdString.equals("S")){
				moveS();
			} else if (cmdString.equals("W")){
				moveW();
			} else if (cmdString.equals("E")){
				moveE();
			} else if (cmdString.equals("LN")){
				lookN();
			} else if (cmdString.equals("LS")){
				lookS();
			} else if (cmdString.equals("LW")){
				lookW();
			} else if (cmdString.equals("LE")){
				lookE();
			} else if (cmdString.equals("KN")){
				knife(cmdString);
			} else if (cmdString.equals("KS")){
				knife(cmdString);
			} else if (cmdString.equals("KW")){
				knife(cmdString);
			} else if (cmdString.equals("KE")){
				knife(cmdString);
			} else if (cmdString.equals("Q")){
				quitGame();
			} else if (cmdString.equals("MAP")){
				printWorld();
			} else if (cmdString.equals("HELP")){
				showCMD();
			} else {
				System.out.println("Game is case sensitive!");
			}
		}
		while (gameOn == 1);
		
		movement.close();
		System.out.println("See you soon!");
		
	}
	
	static void moveN(){
		try{	
			if(pX < 8 && 0 < pX && world[pX - 1][pY] != "1"){
				world[pX][pY] = "0";
				world[pX - 1][pY] = "x";
				pX--;
				System.out.println("You move NORTH.");
			} else if (world[pX - 1][pY].equals("b")){
				System.out.println("An enemy is blocking your path!");
			} else {
				System.out.println("Can't move NORTH!");
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Can't move NORTH!");
		}
	}
	
	static  void moveS(){
		try{
			if(pX < 8 && 0 < pX && world[pX + 1][pY] != "1"){
				world[pX][pY] = "0";
				world[pX + 1][pY] = "x";
				pX++;
				System.out.println("You move SOUTH.");
			} else if (world[pX + 1][pY].equals("b")){
				System.out.println("An enemy is blocking your path!");
			} else {
				System.out.println("Can't move SOUTH!");
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Can't move SOUTH!");
		}
	}

	static  void moveW(){
		try{	
			if(pX < 8 && 0 < pX && world[pX][pY - 1] != "1" && world[pX][pY - 1] != "b"){
				world[pX][pY] = "0";
				world[pX][pY - 1] = "x";
				pY--;
				System.out.println("You move WEST.");
			} else if (world[pX][pY - 1].equals("b")){
				System.out.println("An enemy is blocking your path!");
			} else {
				System.out.println("Can't move WEST!");
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Can't move WEST!");
		}
	}

	static  void moveE(){
		try{	
			if(pX < 8 && 0 < pX && world[pX][pY + 1] != "1"){
				world[pX][pY] = "0";
				world[pX][pY + 1] = "x";
				pY++;
				System.out.println("You move EAST.");
			} else if (world[pX][pY + 1].equals("b")){
				System.out.println("An enemy is blocking your path!");
			} else {
				System.out.println("Can't move EAST!");
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Can't move EAST!");
		}
	}
	
	static void lookN(){
		try{	
			if (world[pX-1][pY].equals("b")){
				System.out.println("There is an enemy infront of you!");
			} else {
				System.out.println("You can't see anyone.");
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Can't look NORTH!");
		}
	}
	
	static void lookS(){
		try{	
			if (world[pX+1][pY].equals("b")){
				System.out.println("There is an enemy infront of you!");
			} else {
				System.out.println("You can't see anyone.");
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Can't look SOUTH!");
		}
	}
	
	static void lookW(){
		try{	
			if (world[pX][pY-1].equals("b")){
				System.out.println("There is an enemy infront of you!");
			} else {
				System.out.println("You can't see anyone.");
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Can't look WEST!");
		}
	}
	
	static void lookE(){
		try{	
			if (world[pX][pY+1].equals("b")){
				System.out.println("There is an enemy infront of you!");
			} else {
				System.out.println("You can't see anyone.");
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Can't look EAST!");
		}
	}
	
	static void printWorld(){
		for (int i = 0; i < world.length; i++){
			System.out.print(world[i][0] + " ");
			for (int j = 1; j < world[i].length; j++){
				System.out.print(world[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	static void quitGame(){
		gameOn = 0;
	}
	
	static void showCMD(){
		System.out.println(
				"N - go north\n" +
				"S - go south\n" +
				"W - go west\n" +
				"E - go east\n" +
				"LN - look north\n" +
				"LS - look south\n" +
				"LW - look west\n" +
				"LE - look east\n" +
				"KN - use knife in north direction\n" +
				"KS - use knife in south direction\n" +
				"KW - use knife in west direction\n" +
				"KE - use knife in east direction\n" +
				"QUIT - quit game\n" +
				"MAP - show map\n" +
				"HELP - show commands\n");
	}
	
	static void knife(String dir){
		try{
			if (dir.equals("KN")){
				try{
					if (world[pX - 1][pY].equals("b")){
						System.out.println("You stabbed a bot and killed it!");
						world[pX - 1][pY] = "0";
					} else {
						System.out.println("There is nobody to stab!");
					}
				} catch (ArrayIndexOutOfBoundsException e){
					System.out.println("You can't stab the MATRIX!");
				}
			} else if (dir.equals("KS")){
				try{
					if (world[pX + 1][pY].equals("b")){
						System.out.println("You stabbed a bot and killed it!");
						world[pX + 1][pY] = "0";
					} else {
						System.out.println("There is nobody to stab!");
					}
				} catch (ArrayIndexOutOfBoundsException e){
					System.out.println("You can't stab the MATRIX!");
				}
			} else if (dir.equals("KW")){
				try{
					if (world[pX][pY - 1].equals("b")){
						System.out.println("You stabbed a bot and killed it!");
						world[pX][pY - 1] = "0";
					} else {
						System.out.println("There is nobody to stab!");
					}
				} catch (ArrayIndexOutOfBoundsException e){
					System.out.println("You can't stab the MATRIX!");
				}
			} else if (dir.equals("KE")){
				try{
					if (world[pX][pY + 1].equals("b")){
						System.out.println("You stabbed a bot and killed it!");
						world[pX][pY + 1] = "0";
					} else {
						System.out.println("There is nobody to stab!");
					}
				} catch (ArrayIndexOutOfBoundsException e){
					System.out.println("You can't stab the MATRIX!");
				}
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("You can't stab the MATRIX!");
		}
	}
}