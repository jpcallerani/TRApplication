package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import application.entity.Erro;

@Repository
public interface ErroRepository extends JpaRepository<Erro, String>, JpaSpecificationExecutor<Erro> {

}
/* List<Erro> findByErroLike(String erro); */
