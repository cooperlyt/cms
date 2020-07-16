package cc.coopersoft.cms.controller;

import cc.coopersoft.cms.model.Article;
import cc.coopersoft.cms.model.Category;
import cc.coopersoft.cms.model.Document;
import cc.coopersoft.cms.service.ArticleService;
import cc.coopersoft.cms.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value="mgr")
public class ManagerController {

    private final CategoryService categoryService;

    private final ArticleService articleService;

    public ManagerController(CategoryService categoryService, ArticleService articleService) {
        this.categoryService = categoryService;
        this.articleService = articleService;
    }

    @RequestMapping(value = "/category/add", method = RequestMethod.POST)
    public Mono<Long> addCategory(
            @RequestParam(value = "parent", required = false) Long parent,
            @RequestBody @Valid @NotNull Category category){
        return Mono.just(categoryService.addCategory(parent,category).getId());
    }

    @RequestMapping(value = "/category/{id}/del", method = RequestMethod.DELETE)
    public Mono<Void> deleteCategory(@PathVariable("id") long id){
        categoryService.delCategory(id);
        return Mono.empty().then();
    }

    @RequestMapping(value = "/category/{id}/edit", method = RequestMethod.PATCH)
    public  Mono<Long> editCategory(@PathVariable("id") long id ,
                                    @RequestBody @Valid @NotNull Category category){
        return Mono.just(categoryService.editCategory(id, category).getId());
    }

    @RequestMapping(value = "/category/{category}/article/add" , method = RequestMethod.POST)
    public Mono<Long> addArticle(@PathVariable("category") long category,
                                 @RequestBody @Valid @NotNull Article article){
        return Mono.just(articleService.addArticle(category,article).getId());
    }

    @RequestMapping(value = "/article/{id}/del", method = RequestMethod.DELETE)
    public Mono<Void> deleteArticle(@PathVariable("id") long id){
        articleService.delArticle(id);
        return Mono.empty().then();
    }

    @RequestMapping(value = "/article/{id}/edit", method = RequestMethod.PATCH)
    public Mono<Long> editArticle(@PathVariable("id") long id  ,
                                  @RequestBody @Valid Article article){
        return Mono.just(articleService.editArticle(id,article).getId());
    }

    @RequestMapping(value = "/article/{id}/move", method = RequestMethod.PATCH)
    public Mono<Long> changeArticleCategory(@PathVariable("id") long id,
                                          @RequestBody @NotNull @Valid long toCategory){
        return Mono.just(articleService.changeArticleCategory(id,toCategory).getId());
    }

    @RequestMapping(value = "/article/{id}/doc/add", method = RequestMethod.POST)
    public Mono<Document> addDocument(@PathVariable("id") long id,
                                      @RequestBody @NotNull @Valid  Document doc){
        return Mono.just(articleService.addDocument(id,doc));
    }

    @RequestMapping(value = "/document/{id}/del", method = RequestMethod.POST)
    public Mono<Void> delDocument(@PathVariable("id") long id){
        articleService.deleteDocument(id);
        return Mono.empty().then();
    }

}
