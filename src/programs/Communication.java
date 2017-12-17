package programs;

import java.util.Scanner;

public class Communication {
	public static Scanner sc = new Scanner(System.in);

	public static void Greetings(){
		System.out.println("*** Witaj w programie! ***");
		System.out.println();
		
	}
	
	public static void goodbye(){
		System.out.println("*** Do zobaczenia! ***");
		System.out.println();
		
	}
	
	public static void Options(boolean edit, boolean view ){
		System.out.println();
		System.out.println("Wybierz jedną z opcji:");
		System.out.println("add");
		if (edit) System.out.println("edit");
		if (view) System.out.println("view");
		System.out.println("delete");
		System.out.println("quit");
		System.out.println();
	}
	public static String getAnswer(){
		String userInput="";
		userInput = sc.nextLine();
		return userInput;
	}
	
	public static void closeScanner(){
		sc.close();
	}
}
