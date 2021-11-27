package me.andrew.providers;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component()
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class HsqlDatabaseProvider implements DatabaseProvider {
    @Override
    public Object save(Object object) {
        return null;
    }

    @Override
    public Object getById(Class<?> clazz, int id) {
        return 1;
    }
}
