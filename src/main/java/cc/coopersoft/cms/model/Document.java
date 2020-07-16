package cc.coopersoft.cms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class Document {

    public interface Summary {}

    @Id
    @JsonIgnore
    private Long id;

    @Size(max = 32)
    private String md5;

    @Size(max = 512)
    @JsonView(Summary.class)
    private String mimeType;

    @JsonView(Summary.class)
    private int size;

    @Size(max = 16)
    @JsonView(Summary.class)
    private String ext;

    @Size(max = 32)
    @JsonView(Summary.class)
    private String content;

    @Size(max = 32)
    @NotBlank
    @JsonView(Summary.class)
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Article article;
}
