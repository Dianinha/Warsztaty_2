package mypackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Group {
	private int id;
	private String name;

	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Group(String name) {
		super();
		this.name = name;
		this.id=0;
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}

	public static Group[] loadAll(Connection conn) {
		List<Group> groups = new ArrayList<>();
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM user_group");
			while (rs.next()) {
				Group tmpgroup = new Group();
				tmpgroup.setName(rs.getString("name"));
				tmpgroup.setId(rs.getInt("id"));
				groups.add(tmpgroup);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Group[] groupsArr = new Group[groups.size()];
		groups.toArray(groupsArr);

		return groupsArr;
	}

	public Group saveToDB(Connection conn) {
		if (this.getId() == 0) {
			String query = "INSERT INTO user_group VALUES (null, ?)";
			String[] generatedColumns = { "id" };
			try {
				PreparedStatement pst = conn.prepareStatement(query, generatedColumns);
				pst.setString(1, getName());
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
				PreparedStatement pst = conn.prepareStatement("UPDATE user_group SET name=? WHERE id=?");
				pst.setString(1, getName());
				pst.setInt(2, this.getId());

				pst.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this;

	}
	
	public static Group loadById(Connection conn, int id){
		String query= "SELECT name FROM user_group WHERE id=?";
		Group tmpGroup;
		String name ="";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				name=rs.getString("name");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tmpGroup = new Group(name);
		tmpGroup.setId(id);
		
		return tmpGroup;
		
	}
	
	public void delete(Connection conn){
		String query = "DELETE FROM user_group WHERE id=?";
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
