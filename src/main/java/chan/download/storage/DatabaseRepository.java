package chan.download.storage;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseRepository implements Repository {

	private static final String SCHEMA = "4cdl";
	private Connection conn;
	private PreparedStatement prepStmnt;

	public DatabaseRepository(String host) {
		try {
			setupConnection(host);
			setupDatabase();
			setupPreparedStatement();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	private void setupConnection(String host) throws SQLException {
		String jdbcUrl = makeJdbcURL(host);
		this.conn = DriverManager.getConnection(jdbcUrl);
	}

	private String makeJdbcURL(String host) {
		String user = host.split(":")[0];
		String pw = host.split(":")[1].split("@")[0];
		String addr = host.split("@")[1];
		String url = "jdbc:mariadb://" + addr + "/?user=" + user + "&password=" + pw;
		
		return url;
	}
	
	private void setupDatabase() throws SQLException {
		createSchema();
		createTable();
		conn.commit();
	}

	private void createSchema() throws SQLException {
		Statement stmnt = conn.createStatement();
		stmnt.executeUpdate(makeSchemaDDL());
		stmnt.close();
		conn.setCatalog(SCHEMA);
	}
	
	private String makeSchemaDDL() {
		return "CREATE OR REPLACE DATABASE " + SCHEMA;
	}
	
	private void createTable() throws SQLException {
		Statement stmnt = conn.createStatement();
		stmnt.executeUpdate(makeImagesTableDDL());
		stmnt.executeUpdate(makeDeleteImagesSQL());
		stmnt.close();
	}
	
	private String makeImagesTableDDL() {
		return "CREATE OR REPLACE TABLE Images("
				+ "id INT AUTO_INCREMENT,"
				+ "img LONGBLOB,"
				+ "PRIMARY KEY(id)"
				+ ")";
	}
	
	private String makeDeleteImagesSQL() {
		return "DELETE FROM Images";
	}
	
	private void setupPreparedStatement() throws SQLException {
		this.prepStmnt = this.conn.prepareStatement("INSERT INTO Images VALUES (default, ?)");
	}
	
	public void save(String url) throws SaveException {
		try {
			InputStream image = new URL(url).openStream();
			prepStmnt.setBinaryStream(1, image);
			prepStmnt.executeUpdate();
		} catch (Exception e) {
			throw new SaveException(e.getMessage());
		}
		
	}

}
