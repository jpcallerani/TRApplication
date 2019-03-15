package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import application.entity.DaSizingQuestionario;

@Repository
public interface DaSizingQuestionarioRepository
		extends JpaRepository<DaSizingQuestionario, String>, JpaSpecificationExecutor<DaSizingQuestionario> {


    @Procedure(name = "sizingCalculate")
    void sizingCalculate();
	
}
/* List<Erro> findByErroLike(String erro); */
