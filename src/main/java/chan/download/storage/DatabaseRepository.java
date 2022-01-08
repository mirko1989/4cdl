package chan.download.storage;

import java.io.IOException;
import java.net.MalformedURLException;
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
	private String host;
	private Connection conn;
	private PreparedStatement prepStmnt;
	private boolean isDatabaseUp;

	public DatabaseRepository(String host) {
		this.host = host;
		this.isDatabaseUp = false;
	}

	private void connect(String host) throws SQLException {
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
		if(!isDatabaseUp) {
			setupConnection();
		}
		
		try {
			String fileName = URLUtil.getFileNameFromURL(url);
			byte[] image = new URL(url).openStream().readAllBytes();
			String sha1 = makeSHA1(image);
			prepStmnt.setString(1, sha1);
			prepStmnt.setString(2, fileName);
			prepStmnt.setBytes(3, image);
			prepStmnt.executeUpdate();
		} catch (MalformedURLException e) {
			throw new SaveException("Invalid URL, skipping.");
		} catch (IOException e) {
			throw new SaveException("General I/O Error, skipping.");
		} catch (NoSuchAlgorithmException e) {
			throw new SaveException("Invalid hash algorithm specified, skipping.");
		} catch (SQLException e) {
			throw new SaveException("General SQL Error, skipping.");
		} catch (Exception e) {
			System.out.println("Unknown error, skipping. Details see stacktrace below.\n\n");
			e.printStackTrace();
		}
	}
	
	private void setupConnection() {
		try {
			connect(host);
			setupDatabase();
			setupPreparedStatement();
			isDatabaseUp = true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
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
