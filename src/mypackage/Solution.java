package mypackage;

import java.sql.Timestamp;

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

	private void setId(int id) {
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
	
//	public static Excercise[] loadAll(Connection conn) {
//		List<Excercise> excercises = new ArrayList<>();
//		Statement st;
//		try {
//			st = conn.createStatement();
//			ResultSet rs = st.executeQuery("SELECT * FROM excercise");
//			while (rs.next()) {
//				Excercise tmpExcercise = new Excercise();
//				tmpExcercise.setTitle(rs.getString("title"));
//				tmpExcercise.setDescription(rs.getString("description"));
//				tmpExcercise.setId(rs.getInt("id"));
//				excercises.add(tmpExcercise);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		Excercise[] excercisesArr = new Excercise[excercises.size()];
//		excercises.toArray(excercisesArr);
//
//		return excercisesArr;
//	}
//
//	public Excercise saveToDB(Connection conn) {
//		if (this.getId() == 0) {
//			String query = "INSERT INTO excercise VALUES (null, ?, ?)";
//			String[] generatedColumns = { "id" };
//			try {
//				PreparedStatement pst = conn.prepareStatement(query, generatedColumns);
//				pst.setString(1, getTitle());
//				pst.setString(1, getDescription());
//				pst.executeUpdate();
//				ResultSet rs = pst.getGeneratedKeys();
//				if (rs.next()) {
//					this.setId(rs.getInt(1));
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//		} else {
//			try {
//				PreparedStatement pst = conn.prepareStatement("UPDATE excercise SET title=?, description=? WHERE id=?");
//				pst.setString(1, getTitle());
//				pst.setString(2, getDescription());
//				pst.setInt(3, this.getId());
//
//				pst.executeUpdate();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return this;
//
//	}
//	
//	public Excercise loadById(Connection conn, int id){
//		String query= "SELECT title, description FROM excercise WHERE id=?";
//		Excercise tmpEx;
//		String title ="";
//		String desc ="";
//		
//		try {
//			PreparedStatement ps = conn.prepareStatement(query);
//			ps.setInt(1, id);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				title=rs.getString("title");
//				desc=rs.getString("description");
//			}
//			
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		tmpEx = new Excercise(title, desc);
//		tmpEx.setId(id);
//		
//		return tmpEx;
//		
//	}
//	
//	public void delete(Connection conn){
//		String query = "DELETE FROM excercise WHERE id=?";
//		PreparedStatement ps;
//		try {
//			ps = conn.prepareStatement(query);
//			ps.setInt(1, this.getId());
//			ps.executeUpdate();
//			System.out.println("Usunięto");
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//	}

}
