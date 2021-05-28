package multicapas.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import multicapas.entities.Cliente;

public class ClienteDao {

    private static ClienteDao instance;

    protected EntityManager entityManager;

    public static ClienteDao getInstance() {
        if (instance == null) {
            instance = new ClienteDao();
        }
        return instance;
    }

    private ClienteDao() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory
                = Persistence.createEntityManagerFactory("Multicapas");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public List<Cliente> findAll() {
        return entityManager.createQuery("FROM " + Cliente.class.getName())
                .getResultList();
    }

    public Cliente findById(int id) {
        return entityManager.find(Cliente.class, id);
    }

    public void create(Cliente cliente) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            JOptionPane.showMessageDialog(null,
                    "Erro ao incluir um cliente na base de dados. "
                    + "Contate o Desenvolvedor.");
        }
    }

    public void remove(int id) {
        try {
            entityManager.getTransaction().begin();
            Cliente cliente = entityManager.find(Cliente.class, id);
            entityManager.remove(cliente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            JOptionPane.showMessageDialog(null,
                    "Erro ao remover cliente na base de dados. "
                    + "Contate o Desenvolvedor.");
        }
    }

    public void merge(Cliente cliente) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            JOptionPane.showMessageDialog(null,
                    "Erro ao editar cliente no banco de dados. "
                    + "Contate o Desenvolvedor.");
        }
    }
}
