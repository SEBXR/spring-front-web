package springapp.dba;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBAConnection {

    public static Connection connect() throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://kashin.db.elephantsql.com/qbnpxwvl", "qbnpxwvl",
                    "8qSUVo8xWesrWApezFU4_1jquZz_Ee23");
        } catch (Exception e) {
            throw e;
        }
        return connection;
    }
}
