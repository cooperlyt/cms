package cc.coopersoft.cms.service;

import cc.coopersoft.cms.model.Category;
import cc.coopersoft.cms.repository.CategoryRepository;
import com.github.wujun234.uid.UidGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Resource
    private UidGenerator defaultUidGenerator;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category addCategory(Long parent, @NotNull Category category){

        Category parentCategory;
        int order;
        if (parent != null) {
            parentCategory = categoryRepository.findById(parent).orElseThrow();
            order =  categoryRepository.findFirstByParentIdOrderByOrderDesc(parent).map(Category::getOrder).orElse(-1);
        }else {
            parentCategory = null;
            order =  categoryRepository.findFirstByParentIsNullOrderByOrderDesc().map(Category::getOrder).orElse(-1);
        }
        category.setOrder(++order);
        category.setParent(parentCategory);
        category.setId(defaultUidGenerator.getUID());
        return categoryRepository.save(category);
    }

    @Transactional
    public void delCategory(long id){
        categoryRepository.deleteById(id);
    }

    @Transactional
    public Category editCategory(long id, Category source){
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(source.getName());
        category.setDescription(source.getDescription());
        return categoryRepository.save(category);
    }

    public List<Category> categories(Long parent){
        return Optional.ofNullable(parent)
                .map(categoryRepository::findByParentIdOrderByOrder)
                .orElseGet(categoryRepository::findByParentIsNullOrderByOrder);
    }

    public List<Category> categories(Long parent, Integer top){
        return Optional.ofNullable(top)
                .map(t -> Optional.ofNullable(parent)
                        .map(p -> categoryRepository.findByParentId(p,CategoryRepository.topCategory(top)))
                        .orElse(categoryRepository.findByParentIsNull(CategoryRepository.topCategory(top))))
                .orElse(this.categories(parent));
    }

    public Category category(long id){
        return categoryRepository.findById(id).orElseThrow();
    }
}
