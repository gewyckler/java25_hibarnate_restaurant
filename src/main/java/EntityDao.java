import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityDao {
    public <T extends IBaseEntity> void saveOrUpdate(T entity) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        } catch (HibernateException he) {
            he.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public <T extends IBaseEntity> Optional<T> getById(Class<T> classT, Long id) {
        List<T> list = new ArrayList<>();
        SessionFactory factory = HibernateUtil.getSessionFactory();

        try (Session session = factory.openSession()) {
            T entity = session.get(classT, id);

            return Optional.ofNullable(entity);
        }
    }

    public <T extends IBaseEntity> List<T> getAll(Class<T> classT) {
        List<T> list = new ArrayList<>();
        SessionFactory factory = HibernateUtil.getSessionFactory();

        try (Session session = factory.openSession()) {

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(classT);
            Root<T> rootTable = criteriaQuery.from(classT);
            criteriaQuery.select(rootTable);
            list.addAll(session.createQuery(criteriaQuery).list());

        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }
}
