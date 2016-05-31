package nl.tue.database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by OttkO on 5/17/2016.
 */
public class MySqlConnectorTest {
    @Test
    public void getPathQueryThreeCount() throws Exception {
        assertEquals(MySqlConnector.GetPathQueryThreeCount(0,4,3),85);

    }
    @Test
    public void getPathQueryTwoCount() throws Exception {
        assertEquals(MySqlConnector.GetPathQueryTwoCount(0,4),293);

    }
    @Test
    public void getPathQueryFourCount() throws Exception {
        assertEquals(MySqlConnector.GetPathQueryFourCount(0,4,1,2),121);

    }
    @Test
    public void getPathQueryFiveCount() throws Exception {
        assertEquals(MySqlConnector.GetPathQueryFiveCount(0,4,1,2,3),0);

    }
    @Test
    public void getAll() throws Exception {
        assertEquals(MySqlConnector.GetAll().size(),2998);

    }

}
