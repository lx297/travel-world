package cn.travel.world.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.travel.world.request.ArticleDescReq;
import cn.travel.world.response.ArticleDescResp;
import cn.travel.world.service.WebDataService;

@Controller
@RequestMapping(value = "web", method = RequestMethod.POST)
@ResponseBody
public class WebController {
	Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private WebDataService webDataService;
	
	@RequestMapping(value = "artList")
	public String artList(@RequestBody String json) {
		logger.info("artList requesst:{}",json );
		JSONObject jsonReq= JSONObject.parseObject(json);
		List<ArticleDescResp> dataList = webDataService.getDataList(JSONObject.toJavaObject(jsonReq, ArticleDescReq.class));
		return JSONObject.toJSONString(dataList);
	}

}
