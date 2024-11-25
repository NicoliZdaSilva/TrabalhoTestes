package dao;

import jakarta.persistence.*;
import model.Especialidade;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EspecialidadeDAO {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public EspecialidadeDAO(EntityManager em) {
        this.em = em;
        emf = Persistence.createEntityManagerFactory("testes");
    }

    public Especialidade save(Especialidade especialidade) {
        TypedQuery<Especialidade> query = em.createQuery(
                "SELECT e FROM Especialidade e WHERE e.nome = :nome", Especialidade.class);
        query.setParameter("nome", especialidade.getNome());
        List<Especialidade> result = query.getResultList();

        if (result.isEmpty()) {
            em.persist(especialidade);
            return especialidade;
        } else {
            return em.find(Especialidade.class, result.get(0).getId());
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

    public Especialidade findOrCreate(String nome) {
        Especialidade especialidade = findByName(nome);
        if (especialidade == null) {
            especialidade = new Especialidade(nome);
            em.persist(especialidade);
        }
        return especialidade;
    }

    public Especialidade findByName(String nome) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Especialidade> query = em.createQuery(
                    "SELECT e FROM Especialidade e WHERE e.nome = :nome", Especialidade.class
            );
            query.setParameter("nome", nome);
            List<Especialidade> resultado = query.getResultList();
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
            em.close();
        }
    }

    public Set<String> findAllNames() {
        TypedQuery<String> query = em.createQuery("SELECT e.nome FROM Especialidade e", String.class);
        return new HashSet<>(query.getResultList());
    }



    public void closeFactory() {
        emf.close();
    }
}
