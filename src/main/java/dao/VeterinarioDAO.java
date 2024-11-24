package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import model.Veterinario;

import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO {

private final EntityManagerFactory emf;

    public VeterinarioDAO() {
        emf = Persistence.createEntityManagerFactory("testes");
    }

    public void save(Veterinario vet) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (em.contains(vet) || readByCPF(vet.getCpf()) != null) {
                throw new IllegalArgumentException("Veterinário com este CPF já existe.");
            }
            em.persist(vet);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Veterinario remove(String cpf) {
        EntityManager em = emf.createEntityManager();
        Veterinario vet = null;

        try {
            vet = em.createQuery("SELECT v FROM veterinarios v WHERE v.cpf = :cpf", Veterinario.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();

            if (vet != null) {
                em.getTransaction().begin();
                em.remove(em.contains(vet) ? vet : em.merge(vet));
                em.getTransaction().commit();
            }
        } catch (NoResultException e) {
            System.out.println("Veterinário não encontrado com CPF: " + cpf);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return vet;
    }

    public void update(Veterinario vet) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Veterinario existingVet = readByCPF(vet.getCpf());
            if (existingVet == null) {
                throw new IllegalArgumentException("Veterinário não encontrado para atualização.");
            }
            em.merge(vet);
            em.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Veterinario readByCPF(String cpf) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT v FROM veterinarios v WHERE v.cpf = :cpf", Veterinario.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Veterinário não encontrado com CPF: " + cpf);
            return null;
        } finally {
            em.close();
        }
    }

    public List<Veterinario> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT v FROM veterinarios v", Veterinario.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public void closeFactory() {
        emf.close();
    }

}