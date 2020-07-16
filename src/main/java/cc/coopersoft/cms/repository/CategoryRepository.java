package cc.coopersoft.cms.repository;

import cc.coopersoft.cms.model.Category;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long> {

    static Pageable topCategory(int limit){
        return PageRequest.of(0,limit, Sort.Direction.ASC, "order");
    }

    Optional<Category> findFirstByParentIdOrderByOrderDesc(long parent);

    Optional<Category> findFirstByParentIsNullOrderByOrderDesc();


    List<Category> findByParentIdOrderByOrder(long parent);

    List<Category> findByParentIsNullOrderByOrder();

    List<Category> findByParentId(long parent, Pageable limit);

    List<Category> findByParentIsNull(Pageable limit);

}
