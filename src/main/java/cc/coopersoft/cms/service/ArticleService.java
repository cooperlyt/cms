package cc.coopersoft.cms.service;

import cc.coopersoft.cms.model.Article;
import cc.coopersoft.cms.model.Category;
import cc.coopersoft.cms.model.Document;
import cc.coopersoft.cms.repository.ArticleRepository;
import cc.coopersoft.cms.repository.CategoryRepository;
import cc.coopersoft.cms.repository.DocumentRepository;
import cc.coopersoft.common.cloud.schemas.WeedFsResult;
import com.github.wujun234.uid.UidGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class ArticleService {

    @Resource
    private UidGenerator defaultUidGenerator;

    private final ArticleRepository articleRepository;

    private final DocumentRepository documentRepository;

    private final CategoryRepository categoryRepository;

    public ArticleService( ArticleRepository articleRepository,
                           DocumentRepository documentRepository,
                           CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.documentRepository = documentRepository;
        this.categoryRepository = categoryRepository;
    }

    private String articleSummary(String context){


            String contextText = context.replaceAll("</?[^>]+>", "")
                    .replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "")
                    .replaceAll("&\\w+;","");
            if (contextText.length() > 100){
                contextText = contextText.substring(0,100) + "...";
            }
            return contextText;
    }

    private String docSummary(List<Document> documents){
        //todo doc summary
        return null;
    }


    private Article saveArticle(Article article){

        article.setSummary(articleSummary(article.getContent()));
        article.setDocSummary(docSummary(article.getDocuments()));
        article.setTime(new Date());
        return articleRepository.save(article);
    }

    @Transactional
    public Article addArticle(long category, @NotNull Article article){
        article.setCategory(categoryRepository.findById(category).orElseThrow());
        article.setTime(new Date());
        article.setId(defaultUidGenerator.getUID());
        if (StringUtils.isEmpty(article.getMimeType())){
            article.setMimeType(MediaType.TEXT_HTML_VALUE);
        }
        return saveArticle(article);
    }

    @Transactional
    public void delArticle(long id){
        articleRepository.deleteById(id);
    }

    @Transactional
    public Article editArticle(long id, @NotNull Article article){
        Article result = articleRepository.findById(id).orElseThrow();
        result.setThumbnail(article.getThumbnail());
        result.setContent(article.getContent());
        result.setTitle(article.getTitle());
        result.setSubTitle(article.getSubTitle());
        result.setMimeType(article.getMimeType());
        result.setTags(article.getTags());
        return saveArticle(result);
    }

    @Transactional
    public Article changeArticleCategory(long id , long toCategory){
        Article article = articleRepository.findById(id).orElseThrow();
        article.setCategory(categoryRepository.findById(toCategory).orElseThrow());
        return articleRepository.save(article);
    }

    @Transactional
    public void deleteDocument(long id){
        documentRepository.deleteById(id);
    }

    @Transactional
    public Document addDocument(long articleId, Document doc){
        Article article = articleRepository.findById(articleId).orElseThrow();
        doc.setId(defaultUidGenerator.getUID());
        doc.setArticle(article);
        return documentRepository.save(doc);
    }


    public Article article(long id){
        return articleRepository.findById(id).orElseThrow();
    }

    private void allCategoryIds(List<Long> ids, Category category){
        ids.add(category.getId());
        for (Category children: category.getChildren()){
            allCategoryIds(ids,children);
        }
    }


    public Page<Article> articles( int page, int size, Long category, String key){

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC, "time");
        if (category != null){
            List<Long> ids = new ArrayList<>();
            allCategoryIds(ids,categoryRepository.findById(category).orElseThrow());
            return articleRepository.findByCategoryIdIn(ids, pageable);
        }else{
            return articleRepository.findByCategoryIsNull(pageable);
        }

        //TODO  https://zhuanlan.zhihu.com/p/133225635 ElasticSearch全文索引

    }

    public Page<Article> documentsArticle(int page,int size){
        return articleRepository.findByDocSummaryIsNotNull(PageRequest.of(page,size, Sort.Direction.DESC, "time"));
    }

    public List<Article> articles(int top, Long category) {

        if (category != null){
            List<Long> ids = new ArrayList<>();
            allCategoryIds(ids,categoryRepository.findById(category).orElseThrow());
            return articleRepository.queryByCategoryIdIn(ids,ArticleRepository.topArticle(top));
        }else{
            return articleRepository.findBy(ArticleRepository.topArticle(top));
        }

    }

    public List<Document> documents(int top){
        return documentRepository.findBy(DocumentRepository.topDocument(top));
    }
}
