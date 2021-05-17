package cn.zsw.campus.forum.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * @author zsw
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "article",shards = 6,replicas = 3)
public class Article implements Serializable {
    /**
     *
     */
    @Id
    private Integer id;

    /**
     *
     */
    @Field(type=FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    /**
     *
     */
    @Field(type=FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    /**
     *
     */
    @Field(type = FieldType.Integer)
    private Integer userId;

    /**
     *
     */
    @Field(type = FieldType.Date)
    private Date createTime;

    /**
     *
     */
    @Field(type = FieldType.Integer)
    private Integer categoryId;

    /**
     *
     */
    @Field(type = FieldType.Boolean)
    private Boolean top;

    /**
     *
     */
    @Field(type = FieldType.Integer)
    private Integer commentCount;

    @Field(type = FieldType.Integer)
    private Integer score;

    private static final long serialVersionUID = 1L;
}