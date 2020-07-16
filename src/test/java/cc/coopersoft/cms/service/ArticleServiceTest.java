package cc.coopersoft.cms.service;

import cc.coopersoft.cms.Application;
import cc.coopersoft.cms.model.Article;
import cc.coopersoft.cms.model.Document;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeType;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
//@ContextConfiguration
@Slf4j
@FixMethodOrder(MethodSorters.JVM)
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;;

    @Test
    void articleCurd() {
        Article article = new Article();
        article.setTitle("test");
        article.setSubTitle("sub title test");
//        article.setMimeType();
        article.setContent("test content");
        article.setMimeType(MediaType.TEXT_HTML_VALUE);

        Article result =  articleService.addArticle(1,article);
        assertEquals(result.getCategory().getId(), Long.valueOf(1l));

        article.setTitle("modify");
        result = articleService.editArticle(result.getId(),article);
        assertEquals(articleService.article(result.getId()).getTitle(), "modify");

        assertEquals(articleService.articles(1, Long.valueOf(1)).get(0).getTitle(),"modify");

        assertEquals(articleService.articles(0,10, Long.valueOf(1), null).getTotalElements(),5);

        articleService.delArticle(result.getId());
        //assertNull(articleService.article(result.getId()));
        assertEquals(articleService.articles(0,10, Long.valueOf(1), null).getTotalElements(),4);

        result =  articleService.addArticle(1,article);

        result = articleService.changeArticleCategory(result.getId(),2);
        assertEquals(result.getCategory().getId(), Long.valueOf(2l));


        Document document = new Document();
        document.setName("text");
        document.setContent("test11");

        document = articleService.addDocument(result.getId(), document);

// TODO article doc summary
//        assertEquals(articleService.documentsArticle(1,10).getTotalElements(), 1);


        assertEquals(articleService.documents(1).get(0).getName(), "text");

        articleService.deleteDocument(document.getId());


        assertEquals(articleService.documents(1).size(), 0);

    }



}