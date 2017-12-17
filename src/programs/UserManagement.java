package programs;

import java.sql.Connection;

import mypackage.ConnectorToDatabase;
import mypackage.User;

public class UserManagement {
	public static void manageUsers() {
		Communication.Greetings();
		Connection conn = ConnectorToDatabase.getConnection();
		boolean flag = true;
		while (flag) {

			printAllUsers(conn);

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

	private static void printAllUsers(Connection conn) {
		User[] users = User.loadAll(conn);
		for (User user : users) {
			System.out.println(user.toString());
		}
	}

	private static void addOption(Connection conn) {
		String username;
		String email;
		String password;
		int groupId;
		System.out.println("Podaj username nowego użytkownika:");
		username = Communication.getAnswer();
		System.out.println("Podaj email nowego użytkownika:");
		email = Communication.getAnswer();
		System.out.println("Podaj hasło nowego użytkownika:");
		password = Communication.getAnswer();
		System.out.println("Podaj grupę nowego użytkownika:");
		groupId = tryToParse();
		User tmpUser = new User(username, email, password, groupId);
		tmpUser.saveToDB(conn);

	}

	private static void editOption(Connection conn) {
		String username;
		String email;
		String password;
		int groupId;
		System.out.println("Podaj id użytkownika do edycji:");
		int userId = tryToParse();
		System.out.println("Podaj nowy username:");
		username = Communication.getAnswer();
		System.out.println("Podaj nowy email:");
		email = Communication.getAnswer();
		System.out.println("Podaj nowe hasło:");
		password = Communication.getAnswer();
		System.out.println("Podaj nową grupę:");
		groupId = tryToParse();
		User tmpUser = User.loadById(conn, userId);
		tmpUser.setUsername(username).setEmail(email).setPassword(password).setGroupId(groupId);
		tmpUser.saveToDB(conn);
	}

	private static void deleteOption(Connection conn) {
		System.out.println("Podaj id użytkownika do usunięcia:");
		int userId = tryToParse();

		User tmpUser = User.loadById(conn, userId);
		tmpUser.delete(conn);

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
				System.out.println("Nieudało się rozpoznać id. Podaj jeszcze raz.");
			}
		}
		return result;
	}

}
