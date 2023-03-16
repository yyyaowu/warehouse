package com.yao.warehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.ArticleNumber;
import com.yao.warehouse.domain.Record;
import com.yao.warehouse.service.ArticleNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.controller
 * @className: ArticleNumberController
 * @author: yao
 * @description: TODO
 * @date: 2022/6/13 9:37
 * @version: 1.0
 */

@RestController
@RequestMapping("/product")
public class ArticleNumberController {
	@Autowired
	private ArticleNumberService articleNumberService;


	@GetMapping("/page")
	public R<Page<ArticleNumber>> getArticlePage(int page, int pageSize, String productname, String productid){
		return articleNumberService.getArticlePage(page,pageSize,productname,productid);
	}

	@PostMapping("/addProduct")
	public R<String> addArticle(@RequestBody ArticleNumber articleNumber){
		articleNumberService.save(articleNumber);
		return R.success("成功");
	}

	@PutMapping("/update")
	public R<String> updateArticle(@RequestBody ArticleNumber articleNumber){
		articleNumberService.updateById(articleNumber);
		return R.success("成功");
	}

	@DeleteMapping("/delete")
	public R<String> deleteArticle(Long id){
		articleNumberService.removeById(id);

		return R.success("成功");
	}

}
