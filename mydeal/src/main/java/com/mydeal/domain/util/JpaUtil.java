package com.mydeal.domain.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.cfg.AvailableSettings;

import java.util.Map;

public class JpaUtil {
    private static final EntityManagerFactory emf;
    private static HikariDataSource ds;

    static {
        ds = createHikariCpDataSource();
        emf = Persistence.createEntityManagerFactory("mydeal", Map.of(AvailableSettings.DATASOURCE, ds));
    }

    private static HikariDataSource createHikariCpDataSource() {
        if (ds == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/mydeal");
            config.setUsername("root");
            config.setPassword("1234");
//            config.setPassword("Dola1234@");
            config.setMaximumPoolSize(30);
            config.addDataSourceProperty("cachePrepStmts", true);
            config.addDataSourceProperty("prepStmtCacheSize", 250);
            config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            ds = new HikariDataSource(config);
        }
        return ds;
    }

    public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        if (emf != null) {
            emf.close();
        }
        if (ds != null) {
            ds.close();
        }
    }
}