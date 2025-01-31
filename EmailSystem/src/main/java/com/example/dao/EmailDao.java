package com.example.dao;

import com.example.model.Email;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class EmailDao {
    private EntityManagerFactory emf;

    public EmailDao() {
        try {
            emf = Persistence.createEntityManagerFactory("tushar");
        } catch (Exception e) {
            System.err.println("❌ Error initializing EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Add an email
    public void addEmail(Email email) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(email);
            em.getTransaction().commit();
            System.out.println("✅ Email saved successfully!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("❌ Error saving email: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Search emails
    public List<Email> searchEmails(String query) {
        EntityManager em = emf.createEntityManager();
        List<Email> results = null;
        try {
            Query q = em.createQuery("SELECT e FROM Email e WHERE e.subject LIKE :query OR e.body LIKE :query");
            q.setParameter("query", "%" + query + "%");
            results = q.getResultList();
        } catch (Exception e) {
            System.err.println("❌ Error fetching emails: " + e.getMessage());
        } finally {
            em.close();
        }
        return results;
    }

    // Delete an email by ID
    public void deleteEmail(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Email email = em.find(Email.class, id);
            if (email != null) {
                em.remove(email);
                em.getTransaction().commit();
                System.out.println("✅ Email deleted successfully!");
            } else {
                System.out.println("⚠️ Email with ID " + id + " not found!");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("❌ Error deleting email: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
