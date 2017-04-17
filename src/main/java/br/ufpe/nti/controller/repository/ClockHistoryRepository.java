package br.ufpe.nti.controller.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufpe.nti.model.Clock;

@Repository
@Transactional//(readOnly = true)
public class ClockHistoryRepository {

	@PersistenceContext
	private EntityManager em;	
	EntityManagerFactory factory;
	
	public void openConection(){
		factory = Persistence.createEntityManagerFactory("smartclock_h2");		
		em = factory.createEntityManager();
		em.getTransaction().begin();
	}
	
	public void closeConection(){
		em.close();
		factory.close();
	}

	public List<Clock> listAll() {
		this.openConection();
		List<Clock> lista = em.createQuery("SELECT c FROM Clock c", Clock.class).getResultList();
		this.closeConection();
		System.out.println("Retorno: "+lista.size()+" linhas");
		return lista;
	}
	
	public Clock findByID(String id){
		this.openConection();
		Clock c = em.createQuery("SELECT c FROM Clock c WHERE id = "+id, Clock.class).getSingleResult();
		this.closeConection();
		System.out.println("Clock Encontrado: "+c.getId());//+", "+c.getCreatedAt()+", "+c.getTime());
		return c;
	}

	@Transactional
	public Clock save(Clock c){
		this.openConection();
		em.persist(c);
		em.getTransaction().commit();
		return c;
	}

}