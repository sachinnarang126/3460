package com.tech.quiz.databasemanager;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Sachin Narang
 */

public class DatabaseEngine extends OrmLiteConfigUtil {

    /**
     * Database helper class used to manage the creation and upgrading of your
     * database. This class also usually provides the DAOs used by the other
     * classes.
     */

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile("ormlite_config.txt");
    }

}
