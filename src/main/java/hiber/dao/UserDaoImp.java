package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @Transactional
   public User getUserByCarModelAndSeries(String model, int series) {
//        String query = "from User where car.model=: modelParam and car.series=: seriesParam";
      String query = "from User";

      List<User> users = sessionFactory.getCurrentSession().createQuery(query).getResultList();
      for (User user : users) {
         if (user.getCar().getModel().equals(model) && user.getCar().getSeries() == series) {
            return user;
         }
      }
      return null;
//            return sessionFactory.getCurrentSession().createQuery(query, User.class).setParameter("modelParam", model).setParameter("seriesParam", series).getSingleResult();
   }
}
