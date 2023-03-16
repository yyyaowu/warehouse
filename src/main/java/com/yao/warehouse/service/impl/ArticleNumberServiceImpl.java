package com.yao.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.ArticleNumber;
import com.yao.warehouse.domain.Record;
import com.yao.warehouse.service.ArticleNumberService;
import com.yao.warehouse.mapper.ArticleNumberMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;

/**
* @author Lenovo
* @description 针对表【article_number】的数据库操作Service实现
* @createDate 2022-06-08 17:10:06
*/
@Service
public class ArticleNumberServiceImpl extends ServiceImpl<ArticleNumberMapper, ArticleNumber>
    implements ArticleNumberService{

	@Override
	public R<Page<ArticleNumber>> getArticlePage(int page, int pageSize, String productname, String productid) {
		Page<ArticleNumber> pageinfo = new Page<>(page,pageSize);
		LambdaQueryWrapper<ArticleNumber> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(StringUtils.isNotBlank(productid),ArticleNumber::getArticleNo,productid)
			.like(StringUtils.isNotBlank(productname),ArticleNumber::getName,productname);
		this.page(pageinfo,lambdaQueryWrapper);

		return R.success(pageinfo);
	}
}




