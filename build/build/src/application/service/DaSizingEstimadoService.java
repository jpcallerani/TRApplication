package application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import application.entity.DaSizingEstimado;
import application.repository.DaSizingEstimadoRepository;

@Service
public class DaSizingEstimadoService {

	@Autowired
	private DaSizingEstimadoRepository repo;

	/**
	 * 
	 * @param questionario
	 */
	@Transactional
	public void deleteDaSizingEstimado(DaSizingEstimado estimado) {
		repo.delete(estimado);
	}

	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<DaSizingEstimado> findAll() {
		return repo.findAll();
	}

	/**
	 * 
	 * @param awnsers
	 * @return
	 */
	@Transactional
	public String saveAll(List<DaSizingEstimado> awnsers) {
		try {
			repo.saveAll(awnsers);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
