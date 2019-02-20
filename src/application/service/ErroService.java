package application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import application.entity.Erro;
import application.repository.ErroRepository;
import application.specification.ErroSpecification;

@Service
public class ErroService {

	@Autowired
	private ErroRepository repo;

	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Erro> findAll() {
		return repo.findAll();
	}

	/**
	 * 
	 * @param erro
	 * @param Date
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Erro> findByFilter(String erro, String Date, String fileName, String username) {
		return repo.findAll(ErroSpecification.findByFilter(erro, Date, fileName, username));
	}
}
