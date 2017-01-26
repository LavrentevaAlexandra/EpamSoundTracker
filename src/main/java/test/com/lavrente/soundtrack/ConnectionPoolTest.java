package test.com.lavrente.soundtrack;

import com.lavrente.soundtrack.pool.ConnectionPool;
import com.lavrente.soundtrack.pool.ProxyConnection;
import org.junit.*;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by 123 on 26.01.2017.
 */
public class ConnectionPoolTest {
    private static ConnectionPool pool;
    private static ArrayList<ProxyConnection> connections;

    @BeforeClass
    public static void initConnectionPool() {
        pool = ConnectionPool.getInstance();
    }

    @Before
    public void initConnections() {
        connections = new ArrayList<>();
    }

    @After
    public void destroyConnections() {
        connections.clear();
    }

    @AfterClass
    public static void closeConnectionPool() {
        pool.terminatePool();
    }

    @Test
    public void checkGetConnection() throws SQLException {
        int expected = 10;
        for (int i = 0; i < expected; i++) {
            connections.add(pool.getConnection());
        }
        int actual = connections.size();
        for (ProxyConnection connection : connections) {
            connection.close();
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkReturnConnection() throws SQLException {
        ProxyConnection connectionFirst = pool.getConnection();
        connectionFirst.close();
        ProxyConnection connectionSecond;
        for (int i = 0; i < 9; i++) {
            connectionSecond = pool.getConnection();
            connectionSecond.close();
        }
        connectionSecond = pool.getConnection();
        connectionSecond.close();
        Assert.assertEquals(connectionFirst, connectionSecond);
    }
}