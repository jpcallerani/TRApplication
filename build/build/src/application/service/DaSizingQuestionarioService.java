package application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import application.entity.DaSizingQuestionario;
import application.repository.DaSizingQuestionarioRepository;

@Service
public class DaSizingQuestionarioService {

	@Autowired
	private DaSizingQuestionarioRepository repo;

	/**
	 * 
	 * @param questionario
	 */
	@Transactional
	public void deleteDaSizingQuestionario(DaSizingQuestionario questionario) {
		repo.delete(questionario);
	}

	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<DaSizingQuestionario> findAll() {
		return repo.findAll();
	}

	/**
	 * 
	 * @param awnsers
	 * @return
	 */
	@Transactional
	public String saveAll(List<DaSizingQuestionario> awnsers) {
		try {
			repo.saveAll(awnsers);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 
	 */
	public void sizingCalculate() {
		this.repo.sizingCalculate();
	}
}
