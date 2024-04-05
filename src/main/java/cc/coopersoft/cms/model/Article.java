package cc.coopersoft.cms.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor

@NamedEntityGraphs(
        {
                @NamedEntityGraph(
                    name = "article.details",
                    attributeNodes = {
                            @NamedAttributeNode("content"),
                            @NamedAttributeNode(value = "category", subgraph = "category.breadcrumb")
                    },
                    subgraphs = {
                            @NamedSubgraph(name = "category.breadcrumb", attributeNodes = @NamedAttributeNode("parent"))
                    }
                ),
                @NamedEntityGraph(
                        name = "article.document",
                        attributeNodes = {
                                @NamedAttributeNode("docSummary")
                        }
                )
        }

)


public class Article {


    public interface Title extends  Category.Breadcrumb  {}
    public interface Summary extends Title {}
    public interface Details extends Summary, Document.Summary {}
    public interface DocumentSummary extends Summary {}

    @Id
    @JsonView(Title.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(max = 32)
    @JsonView(Title.class)
    private String thumbnail;

    @Basic(fetch = FetchType.LAZY)
    @JsonView(Details.class)
    private String content;

    @Size(max = 1024)
    @JsonView(Summary.class)
    private String summary;

    @Size(max = 256)
    @NotBlank
    @JsonView(Title.class)
    private String title;

    @Size(max = 512)
    @JsonView(Summary.class)
    private String subTitle;

    @Size(max = 512)
    @JsonView(Summary.class)
    private String tags;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Title.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date time;

    @JsonView(Title.class)
    private String mimeType;

    @Basic(fetch = FetchType.LAZY)
    @JsonRawValue
    @JsonView(DocumentSummary.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String docSummary;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonView(Title.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Category category;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonView(Details.class)
    @OrderBy("id")
    private List<Document> documents = new ArrayList<>(0);
}
