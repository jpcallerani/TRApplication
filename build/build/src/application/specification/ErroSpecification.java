package application.specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import application.entity.Erro;

public class ErroSpecification {

	/**
	 * 
	 * @param erro
	 * @param Date
	 * @return
	 */
	public static Specification<Erro> findByFilter(String erro, String Date, String fileName, String username) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (erro.length() > 0) {
				predicates.add(cb.and(cb.like(cb.upper(root.get("erro")), "%" + erro + "%".toUpperCase())));
			}
			if (fileName.length() > 0) {
				predicates.add(cb.and(cb.like(cb.upper(root.get("objeto")), "%" + fileName + "%".toUpperCase())));
			}
			if (Date.length() > 0) {
				try {
					predicates.add(cb.and(
							cb.greaterThanOrEqualTo(root.get("data"), new SimpleDateFormat("dd/MM/yyyy").parse(Date))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (username.length() > 0) {
				predicates.add(cb.and(cb.like(cb.upper(root.get("usuario")), "%" + username + "%".toUpperCase())));
			}
			query.orderBy(cb.asc(root.get("data")));
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}
}
