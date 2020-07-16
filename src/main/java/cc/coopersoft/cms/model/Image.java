package cc.coopersoft.cms.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Image {

    @Id
    private Long id;

    private String content;



}
