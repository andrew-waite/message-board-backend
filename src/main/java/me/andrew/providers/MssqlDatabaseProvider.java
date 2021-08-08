package me.andrew.providers;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.proxy.HibernateProxy;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MssqlDatabaseProvider implements DatabaseProvider {
    private SessionFactory sessionFactory = null;

    public MssqlDatabaseProvider() {
        this.sessionFactory = createHibernateSessionFactory();
    }

    private SessionFactory createHibernateSessionFactory() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load hibernate properties file", e);
        }

        var configuration = new Configuration().setProperties(properties);
        loadHibernateMappings(configuration);

        var serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    private void loadHibernateMappings(Configuration configuration) {
        var hibernateMappingDirectory = new File(this.getClass().getClassLoader().getResource("hibernate-mappings").getPath());

        for (var file : hibernateMappingDirectory.listFiles()) {
            configuration.addFile(file);
        }
    }

    @Override
    public Object save(Object object) {
        var session = sessionFactory.openSession();

        try {
            var transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return object;
    }

    @Override
    public Object getById(Class<?> clazz, int id) {
        var session = sessionFactory.openSession();
        Object result = null;

        try {
            var transaction = session.beginTransaction();
            result = session.get(clazz, id);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }
}
