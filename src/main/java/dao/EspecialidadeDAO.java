package dao;

import jakarta.persistence.*;
import model.Especialidade;

import java.util.List;

public class EspecialidadeDAO {

    private final EntityManagerFactory emf;

    public EspecialidadeDAO() {
        emf = Persistence.createEntityManagerFactory("testes");
    }

    public void save(Especialidade especialidade) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(especialidade);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Especialidade remove(Long id) {
        EntityManager em = emf.createEntityManager();
        Especialidade especialidade = null;

        try {
            especialidade = em.find(Especialidade.class, id);
            if (especialidade != null) {
                em.getTransaction().begin();
                em.remove(especialidade);
                em.getTransaction().commit();
            } else {
                System.out.println("Especialidade não encontrada com ID: " + id);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return especialidade;
    }

    public void update(Especialidade especialidade) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(especialidade);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Especialidade findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Especialidade especialidade = null;

        try {
            especialidade = em.find(Especialidade.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return especialidade;
    }

    public List<Especialidade> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Especialidade> especialidades = null;

        try {
            especialidades = em.createQuery("SELECT e FROM Especialidade e", Especialidade.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return especialidades;
    }

    public void closeFactory() {
        emf.close();
    }
}
