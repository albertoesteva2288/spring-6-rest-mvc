package guru.springframework.spring6restmvc.repository.specification;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class BeerSpecification {
    public static Specification<Beer> hasBeerNameLike(String beerName) {
        return (Root<Beer> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (beerName == null || beerName.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("beerName")), "%" + beerName.toLowerCase() + "%");
        };
    }

    public static Specification<Beer> hasBeerStyle(BeerStyle beerStyle) {
        return (Root<Beer> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (beerStyle == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("beerStyle"), beerStyle);
        };
    }

}
