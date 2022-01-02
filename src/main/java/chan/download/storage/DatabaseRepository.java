package chan.download.storage;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import chan.download.util.URLUtil;

public class DatabaseRepository implements Repository {

	private static final String SCHEMA = "4cdl";
	private static final String TABLE = "Images";
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
		return "CREATE DATABASE IF NOT EXISTS " + SCHEMA;
	}
	
	private void createTable() throws SQLException {
		Statement stmnt = conn.createStatement();
		stmnt.executeUpdate(makeImagesTableDDL());
		stmnt.close();
	}
	
	private String makeImagesTableDDL() {
		return "CREATE TABLE IF NOT EXISTS " + TABLE + "("
				+ "sha1 CHAR(40),"
				+ "name VARCHAR(32)," 
				+ "data LONGBLOB,"
				+ "PRIMARY KEY(sha1)"
				+ ")";
	}
	
	private void setupPreparedStatement() throws SQLException {
		this.prepStmnt = this.conn.prepareStatement("INSERT INTO " + TABLE + " VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE sha1=sha1");
	}
	
	public void save(String url) throws SaveException {
		try {
			String fileName = URLUtil.getFileNameFromURL(url);
			byte[] image = new URL(url).openStream().readAllBytes();
			String sha1 = makeSHA1(image);
			prepStmnt.setString(1, sha1);
			prepStmnt.setString(2, fileName);
			prepStmnt.setBytes(3, image);
			prepStmnt.executeUpdate();
		} catch (Exception e) {
			throw new SaveException(e.getMessage());
		}
	}
	
	private String makeSHA1(byte[] image) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(image);
		byte[] digest = md.digest();
		
		StringBuffer hexBuffer = new StringBuffer();
		for(int i = 0; i < digest.length; i++) {
			hexBuffer.append(Integer.toHexString(0xFF & digest[i]));
		}
		
		return hexBuffer.toString();
	}

}
