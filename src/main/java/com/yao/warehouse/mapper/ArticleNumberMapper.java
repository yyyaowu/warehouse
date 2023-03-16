package com.yao.warehouse.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.yao.warehouse.domain.ArticleNumber;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Lenovo
* @description 针对表【article_number】的数据库操作Mapper
* @createDate 2022-06-08 17:10:06
* @Entity com.yao.warehouse.domain.ArticleNumber
*/
@Mapper
public interface ArticleNumberMapper extends BaseMapper<ArticleNumber> {

	List<ArticleNumber> selectArticleNoAndNameByColourAndSize(@Param("colour") String colour, @Param("size") int size);

}




