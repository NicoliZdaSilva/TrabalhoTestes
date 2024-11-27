package dao;

import jakarta.persistence.*;
import model.Especialidade;
import model.Veterinario;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VeterinarioDAO {

private final EntityManagerFactory emf;

    public VeterinarioDAO() {
        emf = Persistence.createEntityManagerFactory("testes");
        EntityManager em = emf.createEntityManager();

    }

    public void save(Veterinario veterinario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();

            Set<Especialidade> especialidadesGerenciadas = veterinario.getEspecialidades()
                    .stream()
                    .map(especialidadeDAO::save)
                    .collect(Collectors.toSet());

            veterinario.setEspecialidades(especialidadesGerenciadas, especialidadeDAO);
            em.merge(veterinario);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar o veterinário: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }


    public Veterinario remove(String cpf) {
        EntityManager em = emf.createEntityManager();
        Veterinario vet = null;

        try {
            vet = em.createQuery("SELECT v FROM Veterinario v WHERE v.cpf = :cpf", Veterinario.class)
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
            return em.createQuery("SELECT v FROM Veterinario v WHERE v.cpf = :cpf", Veterinario.class)
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
            return em.createQuery("SELECT v FROM Veterinario v", Veterinario.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public void saveEspecialidade(Especialidade especialidade) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            TypedQuery<Especialidade> query = em.createQuery(
                    "SELECT e FROM Especialidade e WHERE e.nome = :nome", Especialidade.class
            );
            query.setParameter("nome", especialidade.getNome());
            List<Especialidade> result = query.getResultList();

            if (result.isEmpty()) {
                em.persist(especialidade);
            } else {
                especialidade.setId(result.get(0).getId());
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar especialidade: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void closeFactory() {
        emf.close();
    }

}