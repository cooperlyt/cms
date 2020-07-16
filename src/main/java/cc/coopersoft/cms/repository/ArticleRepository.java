package cc.coopersoft.cms.repository;

import cc.coopersoft.cms.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long>, CrudRepository<Article,Long> {

    static Pageable topArticle(int limit){
        return PageRequest.of(0,limit, Sort.Direction.DESC, "time");
    }

    Page<Article> findByCategoryIdIn(Iterable<Long> categories, Pageable pageable);

    Page<Article> findByCategoryIsNull(Pageable pageable);

    Page<Article> findByDocSummaryIsNotNull(Pageable pageable);

    List<Article> queryByCategoryIdIn(Iterable<Long>  categories, Pageable pageable);

    List<Article> findBy(Pageable pageable);
}
