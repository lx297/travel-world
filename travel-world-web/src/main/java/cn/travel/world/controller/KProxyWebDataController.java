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

import cn.travel.world.request.QueryDetailsReq;
import cn.travel.world.request.QueryListReq;
import cn.travel.world.service.WebDataService;

@Controller
@RequestMapping(value = "kproxy", method = RequestMethod.POST)
@ResponseBody
public class KProxyWebDataController {
	Logger logger=LoggerFactory.getLogger(getClass());
	@Autowired
	private WebDataService webDataService;
	
	@RequestMapping(value = "artList")
	public Object artList(@RequestBody QueryListReq req) {
		logger.info("artList requesst:{}",JSONObject.toJSONString(req) );
		List<Object> dataList = webDataService.getDataList(req);
		return dataList;
	}
	@RequestMapping(value = "getDataDetail")
	public Object getDataDetail(@RequestBody QueryDetailsReq req) {
		logger.info("artList requesst:{}",JSONObject.toJSONString(req) );
		return  webDataService.getDataDetail(req);
	}

}
