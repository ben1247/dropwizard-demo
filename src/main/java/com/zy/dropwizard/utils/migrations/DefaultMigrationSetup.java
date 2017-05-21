package com.zy.dropwizard.utils.migrations;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.migration.DataSourceConnectionProvider;
import org.apache.ibatis.migration.MigrationException;
import org.apache.ibatis.migration.operations.PendingOperation;
import org.apache.ibatis.migration.operations.UpOperation;
import org.apache.ibatis.migration.options.DatabaseOperationOption;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

/**
 * Component:
 * Description:
 * Date: 17/5/16
 *
 * @author yue.zhang
 */
@Slf4j
public class DefaultMigrationSetup implements MigrationSetup{

    @Override
    public void execute(DataSource dataSource, String profile) throws Exception {
        String migrationPath = "META-INF/migrations/" + profile + "/scripts/";
        // 有多个文件
        Enumeration<URL> paths =  this.getClass().getClassLoader().getResources(migrationPath);

        ComplexMigrationLoader migrationLoader = new ComplexMigrationLoader(migrationPath,paths);
        migrationLoader.init();

        DataSourceConnectionProvider connectionProvider = new DataSourceConnectionProvider(dataSource);

        // changelog table is Exists ?
        if(!checkChangelogExists(connectionProvider)){
            createChangelogTable(connectionProvider);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        log.info("Migrating **Pending** SQL scripts");

        DatabaseOperationOption option = new DatabaseOperationOption();
        option.setSendFullScript(true);
        option.setAutoCommit(true);

        new PendingOperation().operate(connectionProvider,migrationLoader,option,printStream);
        new UpOperation().operate(connectionProvider,migrationLoader,option,printStream);

        log.info(new String(byteArrayOutputStream.toByteArray()));
        printStream.close();
    }

    private boolean checkChangelogExists(DataSourceConnectionProvider dataSourceConnectionProvider) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSourceConnectionProvider.getConnection();
            statement = connection.createStatement();
            statement.executeQuery("SELECT COUNT(1) FROM changelog");
            return true;
        }catch (SQLException e) {
            log.error("changelog table check fail : {}",e.getMessage(),e);
            return false;
        }finally {
            close(connection);
            close(statement);
        }
    }

    private void createChangelogTable(DataSourceConnectionProvider dataSourceConnectionProvider) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSourceConnectionProvider.getConnection();
            statement = connection.createStatement();

            statement.execute("CREATE TABLE changelog ( " +
                    "ID NUMERIC(20,0) NOT NULL, " +
                    "APPLIED_AT VARCHAR(25) NOT NULL, " +
                    "DESCRIPTION VARCHAR(255) NOT NULL, " +
                    "PRIMARY KEY(ID) )");
        }catch (SQLException e){
            throw new MigrationException("create changelog table fail: " , e);
        }finally {
            close(statement);
            close(connection);
        }
    }

    private void close(Connection connection){
        try {
            if (null != connection){
                connection.close();
            }
        }catch (Exception e){
            log.error("close sql connection error : ", e);
        }
    }

    private void close(Statement stmt){
        try {
            if(null != stmt){
                stmt.close();
            }
        }catch (Exception e){
            log.error("close sql statement error : ", e);
        }
    }


}
