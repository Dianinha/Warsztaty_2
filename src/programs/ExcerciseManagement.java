package programs;

import java.sql.Connection;

import mypackage.ConnectorToDatabase;
import mypackage.Excercise;

public class ExcerciseManagement {
	
	public static void manageExcercises() {
		Communication.Greetings();
		Connection conn = ConnectorToDatabase.getConnection();
		boolean flag = true;
		while (flag) {

			printAllEx(conn);

			Communication.Options(true, false);
			String answer = Communication.getAnswer();
			while (!(answer.equals("add") || answer.equals("edit") || answer.equals("delete")
					|| answer.equals("quit"))) {
				System.out.println("Nie rozpoznano komendy: " + answer);
				answer = Communication.getAnswer();
			}

			switch (answer) {

			case "add":
				addOption(conn);
				break;
			case "edit":
				editOption(conn);
				break;
			case "delete":
				deleteOption(conn);
				break;
			case "quit":
				Communication.closeScanner();
				Communication.goodbye();
				flag = false;
				System.exit(0);
				break;
			default:
				break;

			}
		}
	}

	private static void printAllEx(Connection conn) {
		Excercise[] exs = Excercise.loadAll(conn);
		for (Excercise excercise : exs) {
			System.out.println(excercise.toString());
		}
	}

	private static void addOption(Connection conn) {
		String title;
		String desc;
		System.out.println("Podaj tytuł zadania:");
		title = Communication.getAnswer();
		System.out.println("Podaj treść zadania:");
		desc = Communication.getAnswer();
		Excercise tmpExcercise = new Excercise(title, desc);
		tmpExcercise.saveToDB(conn);

	}

	private static void editOption(Connection conn) {
		String title;
		String desc;
		int exId;
		System.out.println("Podaj id zadania do edycji:");
		exId = tryToParse();
		System.out.println("Podaj nowy tytuł zadania:");
		title = Communication.getAnswer();
		System.out.println("Podaj nową treść zadania:");
		desc = Communication.getAnswer();
		System.out.println("Podaj nowe hasło:");
		Excercise tmpExcercise = Excercise.loadById(conn, exId);
		tmpExcercise.setTitle(title);
		tmpExcercise.setDescription(desc);
		tmpExcercise.saveToDB(conn);
	}

	private static void deleteOption(Connection conn) {
		System.out.println("Podaj id zadania do usunięcia:");
		int exId = tryToParse();

		Excercise tmpExcercise = Excercise.loadById(conn, exId);
		tmpExcercise.delete(conn);

	}

	private static int tryToParse() {
		int result = 0;
		String stringToParse;
		boolean isParseOk = true;
		while (isParseOk) {
			stringToParse = Communication.getAnswer();
			try {
				result = Integer.parseInt(stringToParse);
				isParseOk = false;
			} catch (Exception e) {
				System.out.println("Nieudało się rozpoznać liczby. Podaj jeszcze raz.");
			}
		}
		return result;
	}
}
