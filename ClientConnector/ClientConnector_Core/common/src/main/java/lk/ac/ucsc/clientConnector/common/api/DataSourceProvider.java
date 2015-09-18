package lk.ac.ucsc.clientConnector.common.api;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class DataSourceProvider {
    private static DataSource dataSource;
    private static DataSource backOfficeDataSource;

    private DataSourceProvider() {
    }

    public static void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    public static DataSource getDataSource() throws TrsException {
        if (dataSource == null) {
            throw new TrsException("Data-source is not initialized");
        }
        return dataSource;
    }

    public static DataSource getTestDataSource() throws TrsException {
        if (dataSource == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext("/implGeneral/spring-common-testdb.xml");
            dataSource = context.getBean("testDataSource", BasicDataSource.class);
        }
        return dataSource;
    }

    public static DataSource getBackOfficeDataSource() throws TrsException {
        if (backOfficeDataSource == null) {
            throw new TrsException("Backoffice Data-Source is not initialized");
        }
        return backOfficeDataSource;
    }

    public static void setBackOfficeDataSource(DataSource backOfficeDataSource) {
        DataSourceProvider.backOfficeDataSource = backOfficeDataSource;
    }
}
