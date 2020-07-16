package cc.coopersoft.cms.repository;

import cc.coopersoft.cms.model.Document;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends CrudRepository<Document,Long> {

    static Pageable topDocument(int limit){

        return PageRequest.of(0,limit, Sort.Direction.DESC, "id");
    }

    List<Document> findBy(Pageable limit);
}
