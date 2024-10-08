package et.by.dao.pull;

import et.by.dao.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
	private String url;
	private String user;
	private String user_password;
	private int maxSize;
	private int checkConnectionTimeout;

	private BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();
	private Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();

	private ConnectionPool(){}
	public ConnectionPool(String driverClass, String url, String user, String password, int startSize, int maxSize, int checkConnectionTimeout) throws DaoException {
		try {
			destroy();
			Class.forName(driverClass);
			this.url = url;
			this.user = user;
			this.user_password = password;
			this.maxSize = maxSize;
			this.checkConnectionTimeout = checkConnectionTimeout;
			for(int counter = 0; counter < startSize; counter++) {
				freeConnections.put(createConnection());
			}
		} catch(ClassNotFoundException | SQLException | InterruptedException e) {
			throw new DaoException(e);
		}
	}

	public synchronized Connection getConnection() throws DaoException {
		PooledConnection connection = null;
		while(connection == null) {
			try {
				if(!freeConnections.isEmpty()) {
					connection = freeConnections.take();
					if(!connection.isValid(checkConnectionTimeout)) {
						try {
							connection.getConnection().close();
						} catch(SQLException e) {}
						connection = null;
					}
				} else if(usedConnections.size() < maxSize) {
					connection = createConnection();
				} else {
					throw new DaoException();
				}
			} catch(InterruptedException | SQLException e) {
				throw new DaoException(e);
			}
		}
		usedConnections.add(connection);
		return connection;
	}

	synchronized void freeConnection(PooledConnection connection) {
		try {
			if(connection.isValid(checkConnectionTimeout)) {
				connection.clearWarnings();
				connection.setAutoCommit(true);
				usedConnections.remove(connection);
				freeConnections.put(connection);
			}
		} catch(SQLException | InterruptedException e1) {
			try {
				connection.getConnection().close();
			} catch(SQLException e2) {}
		}
	}

	public synchronized void init(String driverClass, String url, String user, String password, int startSize, int maxSize, int checkConnectionTimeout) throws DaoException {
		try {
			destroy();
			Class.forName(driverClass);
			this.url = url;
			this.user = user;
			this.user_password = password;
			this.maxSize = maxSize;
			this.checkConnectionTimeout = checkConnectionTimeout;
			for(int counter = 0; counter < startSize; counter++) {
				freeConnections.put(createConnection());
			}
		} catch(ClassNotFoundException | SQLException | InterruptedException e) {
			throw new DaoException(e);
		}
	}

	private static ConnectionPool instance = new ConnectionPool();

	public static ConnectionPool getInstance() {
		return instance;
	}

	private PooledConnection createConnection() throws SQLException {
		return new PooledConnection(DriverManager.getConnection(url, user, user_password));
	}
	public synchronized void destroy() {
		usedConnections.addAll(freeConnections);
		freeConnections.clear();
		for(PooledConnection connection : usedConnections) {
			try {
				connection.getConnection().close();
			} catch(SQLException e) {}
		}
		usedConnections.clear();
	}
}