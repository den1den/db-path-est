package nl.tue.database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by OttkO on 5/17/2016.
 */
public class MySqlConnectorTest {
    @Test
    public void getPathQueryLikeCount() throws Exception {
        assertEquals(MySqlConnector.GetPathQueryLikeCount(),85);

    }

    @Test
    public void getAll() throws Exception {
        assertEquals(MySqlConnector.GetAll().size(),1499);

    }

}