package by.resliv.bot.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {
    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);
    private static final String CITY_XML = "city.hbm.xml";

    public Session createHibernateSession() {
        Session session = null;
        SessionFactory sessionFactory = null;
        ServiceRegistry serviceRegistry = null;
        try {
            try {
                Configuration cfg = new Configuration().
                        addResource(CITY_XML).configure();
                serviceRegistry = new StandardServiceRegistryBuilder().
                        applySettings(cfg.getProperties()).build();
                sessionFactory = cfg.buildSessionFactory(serviceRegistry);
            } catch (Throwable e) {
                logger.error(e);
                throw new ExceptionInInitializerError(e);
            }
            session = sessionFactory.openSession();
        } catch (Exception e) {
            logger.error(e);
        }
        return session;
    }
}
