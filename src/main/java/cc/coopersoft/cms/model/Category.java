package cc.coopersoft.cms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@NamedEntityGraph(name = "category.breadcrumb", attributeNodes = {@NamedAttributeNode("parent")})
public class Category {

    public interface Title {}
    public interface Summary extends Title {}
    public interface Breadcrumb extends Title {}
    public interface Description extends Title{}

    @Id
    @JsonView(Title.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(max = 16)
    @NotBlank
    @JsonView(Title.class)
    private String name;

    @Size(max = 64)
    @JsonView(Description.class)
    private String description;

    @Column(name = "_order")
    @JsonIgnore
    private int order;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    @JsonView(Breadcrumb.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Category parent;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @OrderBy("order")
    @JsonView({Summary.class,Description.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Category> children = new ArrayList<>(0);
}
