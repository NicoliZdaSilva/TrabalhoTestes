package dao;

import jakarta.persistence.*;
import model.Especialidade;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EspecialidadeDAO {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public EspecialidadeDAO() {
        emf = Persistence.createEntityManagerFactory("testes");
        this.em = emf.createEntityManager();
    }


    public Especialidade save(Especialidade especialidade) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Especialidade> query = em.createQuery(
                    "SELECT e FROM Especialidade e WHERE e.nome = :nome", Especialidade.class
            );
            query.setParameter("nome", especialidade.getNome());
            List<Especialidade> result = query.getResultList();

            if (!result.isEmpty()) {
                return em.merge(result.get(0));
            }

            em.getTransaction().begin();
            em.persist(especialidade);
            em.getTransaction().commit();

            return especialidade;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar especialidade: " + e.getMessage(), e);
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
