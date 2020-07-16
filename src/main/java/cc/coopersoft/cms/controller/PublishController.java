package cc.coopersoft.cms.controller;

import cc.coopersoft.cms.model.Article;
import cc.coopersoft.cms.model.Category;
import cc.coopersoft.cms.model.Document;
import cc.coopersoft.cms.service.ArticleService;
import cc.coopersoft.cms.service.CategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@RestController
public class PublishController {

    private final CategoryService categoryService;
    private final ArticleService articleService;

    public PublishController(CategoryService categoryService, ArticleService articleService) {
        this.categoryService = categoryService;
        this.articleService = articleService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String serverExceptionHandler(Exception ex) {
        //LOGGER.error(ex.getMessage(),ex);
        return ex.getMessage();
    }

    @RequestMapping(value = "/category/tree", method = RequestMethod.GET)
    @JsonView(Category.Summary.class)
    public Flux<Category> categoryTree(
            @RequestParam(value = "parent", required = false) Long parent){
        return Flux.fromIterable(categoryService.categories(parent)) ;
    }


    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @JsonView(Category.Description.class)
    public Flux<Category> categories(
            @RequestParam(value = "parent", required = false) Long parent,
            @RequestParam(value = "top", required = false) Integer top){
        return Flux.fromIterable(categoryService.categories(parent,top));
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    @JsonView(Category.Breadcrumb.class)
    public Mono<Category> category(@PathVariable("id") long id){
        return Mono.just(categoryService.category(id));
    }

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    @JsonView(Article.Summary.class)
    public Mono<Page<Article>> articles(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "category", required = false) Long category,
            @RequestParam(value = "key", required = false) String key){
        return Mono.just(articleService.articles(page, size, category, key));
    }

    @RequestMapping(value = "/document/articles", method = RequestMethod.GET)
    @JsonView(Article.DocumentSummary.class)
    public Mono<Page<Article>> documentsArticle(
            @RequestParam("page") int page,
            @RequestParam("size") int size){
        return Mono.just(articleService.documentsArticle(page, size));
    }

    @RequestMapping(value = "/articles/top/{top}", method = RequestMethod.GET)
    @JsonView(Article.Title.class)
    public Flux<Article> articles(
            @PathVariable("top") int top,
            @RequestParam(value = "category", required = false) Long category){
        return Flux.fromIterable(articleService.articles(top,category));
    }

    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
    @JsonView(Article.Details.class)
    public Mono<Article> article(@PathVariable long id){
        return Mono.just(articleService.article(id));
    }

    @RequestMapping(value = "/documents/top/{top}", method = RequestMethod.GET)
    @JsonView(Document.Summary.class)
    public Flux<Document> documents(@PathVariable("top") int top){
        return Flux.fromIterable(articleService.documents(top));
    }

}
