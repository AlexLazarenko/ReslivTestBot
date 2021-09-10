package by.resliv.bot.repository.impl;

import by.resliv.bot.pojo.City;
import by.resliv.bot.repository.CityRepoFind;
import by.resliv.bot.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CityRepoFindImpl implements CityRepoFind {
    @Autowired
    HibernateUtil hibernateUtil;

    @Override
    public List<City> findByCityName(String cityName) {
        Session session = hibernateUtil.createHibernateSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<City> cr = cb.createQuery(City.class);
        Root<City> root = cr.from(City.class);
        cr.select(root).where(root.get("name").in(cityName));
        Query<City> query = session.createQuery(cr);
        List<City> cities = query.getResultList();
        return cities;
    }
}
