package com.yao.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.ArticleNumber;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.warehouse.domain.Record;

/**
* @author Lenovo
* @description 针对表【article_number】的数据库操作Service
* @createDate 2022-06-08 17:10:06
*/
public interface ArticleNumberService extends IService<ArticleNumber> {

	R<Page<ArticleNumber>> getArticlePage(int page, int pageSize, String productname, String productid);
}
