import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class InvoiceDao {
    public List<Invoice> listAllUnpaid() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery query = cb.createQuery(Invoice.class);
            Root<Invoice> root = query.from(Invoice.class);

            query.select(root).where(
                    cb.equal(root.get("ifPaid"), false));
            return session.createQuery(query).getResultList();
        }
    }

    public void issueAnInvoice(Invoice invoice) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            invoice.setDateRelease(LocalDate.now());
            session.update(invoice);

            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public double getInvoicePriceById(Invoice invoice) {
        return invoice.getAmountOnBill();
    }

    public void listProductFromInvoice(Invoice invoice) {
        for (Product pr : invoice.getPruductList()) {
            System.out.println(pr);
        }
    }

    public void setInvoiceAsPaid(Invoice invoice) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            invoice.setIfPaid(true);
            invoice.setDateAndHourOfPayment(LocalDateTime.now());
            session.update(invoice);

            transaction.commit();

        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<Invoice> getAllFromLastWeek() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery query = cb.createQuery(Invoice.class);
            Root<Invoice> root = query.from(Invoice.class);

            query.select(root).where(
                    cb.between(root.get("dateOfCreation"),
                            LocalDateTime.now().minusDays(7),
                            LocalDateTime.now())
            );
            return session.createQuery(query).getResultList();
        }
    }

    public List<Invoice> getSumFromTodaysInvoices() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery query = cb.createQuery(Invoice.class);
            Root<Invoice> root = query.from(Invoice.class);
            Join<Invoice, Product> join = root.join("product", JoinType.LEFT);

            query.select(cb.sum(cb.prod(join.get("price"),
                    join.get("stock")))).where(cb.between(
                    root.get("dateOfCreation"),
                    LocalDate.now().atStartOfDay(),
                    LocalDateTime.now()));

            return session.createQuery(query).getResultList();
        }
    }
}
