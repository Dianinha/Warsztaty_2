package mypackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Solution {
	private int id;
	private Timestamp created;
	private Timestamp updated;
	private String description;
	private int excercise_id;
	private int users_id;

	public Solution() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Solution(Timestamp created, Timestamp updated, String description, int excercise_id, int users_id) {
		super();
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.excercise_id = excercise_id;
		this.users_id = users_id;
	}

	public int getId() {
		return id;
	}

	void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreated() {
		return created;
	}

	public Solution setCreated(Timestamp created) {
		this.created = created;
		return this;
	}

	public Timestamp getUpdated() {
		return updated;
	}

	public Solution setUpdated(Timestamp updated) {
		this.updated = updated;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Solution setDescription(String description) {
		this.description = description;
		return this;
	}

	public int getExcercise_id() {
		return excercise_id;
	}

	public Solution setExcercise_id(int excercise_id) {
		this.excercise_id = excercise_id;
		return this;
	}

	public int getUsers_id() {
		return users_id;
	}

	public Solution setUsers_id(int users_id) {
		this.users_id = users_id;
		return this;
	}

	@Override
	public String toString() {
		return "Solution [id=" + id + ", created=" + created + ", updated=" + updated + ", description=" + description
				+ ", excercise_id=" + excercise_id + ", users_id=" + users_id + "]";
	}

	public static Solution[] loadAll(Connection conn) {
		List<Solution> solutions = new ArrayList<>();
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM solution");
			while (rs.next()) {
				Solution tmpSolution = new Solution();
				tmpSolution.setCreated(rs.getTimestamp("created"));
				tmpSolution.setUpdated(rs.getTimestamp("updated"));
				tmpSolution.setDescription(rs.getString("description"));
				tmpSolution.setId(rs.getInt("id"));
				tmpSolution.setExcercise_id(rs.getInt("excercise_id"));
				tmpSolution.setUsers_id(rs.getInt("users_id"));
				solutions.add(tmpSolution);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Solution[] solutionsArr = new Solution[solutions.size()];
		solutions.toArray(solutionsArr);

		return solutionsArr;
	}
	
	public static Solution[] loadAllByExerciseId(Connection conn, int exId) {
		List<Solution> solutions = new ArrayList<>();
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM solution WHERE excercise_id=" + exId + " ORDER BY created DESC" );
			while (rs.next()) {
				Solution tmpSolution = new Solution();
				tmpSolution.setCreated(rs.getTimestamp("created"));
				tmpSolution.setUpdated(rs.getTimestamp("updated"));
				tmpSolution.setDescription(rs.getString("description"));
				tmpSolution.setId(rs.getInt("id"));
				tmpSolution.setExcercise_id(rs.getInt("excercise_id"));
				tmpSolution.setUsers_id(rs.getInt("users_id"));
				solutions.add(tmpSolution);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Solution[] solutionsArr = new Solution[solutions.size()];
		solutions.toArray(solutionsArr);

		return solutionsArr;
	}
	
	

	public Solution saveToDB(Connection conn) {
		if (this.getId() == 0) {
			String query = "INSERT INTO solution VALUES (null, ?, ?, ?, ?, ?)";
			String[] generatedColumns = { "id" };
			try {
				PreparedStatement pst = conn.prepareStatement(query, generatedColumns);
				pst.setTimestamp(1, getCreated());
				pst.setTimestamp(2, getUpdated());
				pst.setString(3, getDescription());
				pst.setInt(4, getExcercise_id());
				pst.setInt(5, getUsers_id());
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
				PreparedStatement pst = conn.prepareStatement(
						"UPDATE solution SET created=?, updated=?, description=?, excercise_id=?, users_id=? WHERE id=?");
				pst.setTimestamp(1, getCreated());
				pst.setTimestamp(2, getUpdated());
				pst.setString(3, getDescription());
				pst.setInt(4, getExcercise_id());
				pst.setInt(5, getUsers_id());
				pst.setInt(6, this.getId());

				pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this;

	}

	public Solution loadById(Connection conn, int id) {
		String query = "SELECT created, updated, description, excercise_id, users_id FROM solution WHERE id=?";
		Solution tmpSolution;
		Timestamp created = null;
		Timestamp updated = null;
		int excerciseId = 0;
		int usersId = 0;
		String desc = "";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				created = rs.getTimestamp("created");
				updated = rs.getTimestamp("updated");
				excerciseId= rs.getInt("excercise_id");
				usersId = rs.getInt("users_id");
				
				desc = rs.getString("description");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		tmpSolution = new Solution(created, updated, desc, excerciseId, usersId);
		tmpSolution.setId(id);

		return tmpSolution;

	}
	
	 public void delete(Connection conn){
	 String query = "DELETE FROM solution WHERE id=?";
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
