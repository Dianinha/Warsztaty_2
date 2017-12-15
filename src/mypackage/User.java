package mypackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class User {
	private int id = 0;
	private String username;
	private String email;
	private String password;
	private int groupId;

	public User() {
		super();
	}

	public User(String username, String email, String password) {
		super();
		this.setUsername(username).setEmail(email).setPassword(password);

	}

	public int getId() {
		return id;
	}

	private User setId(int id) {
		this.id = id;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
		return this;
	}

	public static User[] loadAll(Connection conn) {
		List<User> users = new ArrayList<>();

		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM users");
			while (rs.next()) {
				User tmpUser = new User();
				tmpUser.setEmail(rs.getString("email")).setUsername(rs.getString("username"));
				tmpUser.password = rs.getString("password");
				tmpUser.setId(rs.getInt("id"));
				users.add(tmpUser);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		User[] usersArr = new User[users.size()];
		users.toArray(usersArr);

		return usersArr;
	}

	public User saveToDB(Connection conn) {
		if (this.getId() == 0) {
			String query = "INSERT INTO users VALUES (null, ?, ?, ?)";
			String[] generatedColumns = { "id" };
			try {
				PreparedStatement pst = conn.prepareStatement(query, generatedColumns);
				pst.setString(1, getUsername());
				pst.setString(2, getEmail());
				pst.setString(3, getPassword());

				pst.executeUpdate();
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					this.setId(rs.getInt(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			try {
				PreparedStatement pst = conn.prepareStatement("UPDATE users SET username=?, email=?, password=? WHERE id=?");
				pst.setString(1, getUsername());
				pst.setString(2, getEmail());
				pst.setString(3, getPassword());
				pst.setInt(4, this.getId());

				pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this;

	}
	
	public User loadById(Connection conn, int id){
		String query = "SELECT username, email, password FROM users WHERE id=?";
		User tmpUser;
		String username ="";
		String password="";
		String email="";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				username=rs.getString("username");
				password = rs.getString("password");
				email=rs.getString("email");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tmpUser = new User(username, email, password);
		tmpUser.setId(id);
		
		return tmpUser;
		
	}
	
	public void delete(Connection conn){
		String query = "DELETE FROM users WHERE id=?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, this.getId());
			ps.executeUpdate();
			System.out.println("Usunięto");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + "]";
	}

}
