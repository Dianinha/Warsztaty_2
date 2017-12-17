package programs;

import java.sql.Connection;

import mypackage.ConnectorToDatabase;
import mypackage.Group;

public class GroupManager {
	public static void manageGroups() {
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
		Group[] groups = Group.loadAll(conn);
		for (Group gr : groups) {
			System.out.println(gr.toString());
		}
	}

	private static void addOption(Connection conn) {
		String name;
		System.out.println("Podaj nazwę grupy:");
		name = Communication.getAnswer();
		Group tmpGroup = new Group(name);
		tmpGroup.saveToDB(conn);

	}

	private static void editOption(Connection conn) {
		String name;
		int grId;
		System.out.println("Podaj id grupy do edycji:");
		grId = tryToParse();
		System.out.println("Podaj nową nazwę grupy:");
		name = Communication.getAnswer();
		Group tmpGroup = Group.loadById(conn, grId);
		tmpGroup.setName(name);
		tmpGroup.saveToDB(conn);
	}

	private static void deleteOption(Connection conn) {
		System.out.println("Podaj id grupy do usunięcia:");
		int grId = tryToParse();

		Group tmpGroup = Group.loadById(conn, grId);
		tmpGroup.delete(conn);

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
