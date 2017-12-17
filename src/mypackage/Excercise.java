package mypackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Excercise {
	
	private int id;
	private String title;
	private String description;
	
	public Excercise() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Excercise(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Exercise [id=" + id + ", title=" + title + ", description=" + description + "]";
	}

	public static Excercise[] loadAll(Connection conn) {
		List<Excercise> excercises = new ArrayList<>();
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM excercise");
			while (rs.next()) {
				Excercise tmpExcercise = new Excercise();
				tmpExcercise.setTitle(rs.getString("title"));
				tmpExcercise.setDescription(rs.getString("description"));
				tmpExcercise.setId(rs.getInt("id"));
				excercises.add(tmpExcercise);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Excercise[] excercisesArr = new Excercise[excercises.size()];
		excercises.toArray(excercisesArr);

		return excercisesArr;
	}

	public Excercise saveToDB(Connection conn) {
		if (this.getId() == 0) {
			String query = "INSERT INTO excercise VALUES (null, ?, ?)";
			String[] generatedColumns = { "id" };
			try {
				PreparedStatement pst = conn.prepareStatement(query, generatedColumns);
				pst.setString(1, getTitle());
				pst.setString(1, getDescription());
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
				PreparedStatement pst = conn.prepareStatement("UPDATE excercise SET title=?, description=? WHERE id=?");
				pst.setString(1, getTitle());
				pst.setString(2, getDescription());
				pst.setInt(3, this.getId());

				pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this;

	}
	
	public Excercise loadById(Connection conn, int id){
		String query= "SELECT title, description FROM excercise WHERE id=?";
		Excercise tmpEx;
		String title ="";
		String desc ="";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				title=rs.getString("title");
				desc=rs.getString("description");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tmpEx = new Excercise(title, desc);
		tmpEx.setId(id);
		
		return tmpEx;
		
	}
	
	public static Solution[] loadAllByUserId(Connection conn, int userId) {
		List<Solution> usersSolutions = new ArrayList<>();
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT s.* FROM excercise e JOIN solution s ON e.id=s.excercise_id JOIN users u ON s.users_id = u.id WHERE u.id=" + userId);
			while (rs.next()) {
				Solution tmpSolution = new Solution();
				tmpSolution.setCreated(rs.getTimestamp("created"));
				tmpSolution.setUpdated(rs.getTimestamp("updated"));
				tmpSolution.setDescription(rs.getString("description"));
				tmpSolution.setId(rs.getInt("id"));
				tmpSolution.setExcercise_id(rs.getInt("excercise_id"));
				tmpSolution.setUsers_id(rs.getInt("users_id"));
				usersSolutions.add(tmpSolution);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Solution[] solutionsArr = new Solution[usersSolutions.size()];
		usersSolutions.toArray(solutionsArr);

		return solutionsArr;
	}
	
	public void delete(Connection conn){
		String query = "DELETE FROM excercise WHERE id=?";
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

}
