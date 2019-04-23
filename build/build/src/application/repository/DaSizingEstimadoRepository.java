package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import application.entity.DaSizingEstimado;

@Repository
public interface DaSizingEstimadoRepository
		extends JpaRepository<DaSizingEstimado, String>, JpaSpecificationExecutor<DaSizingEstimado> {
	
}