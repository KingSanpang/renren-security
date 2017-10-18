package io.renren.modules.sys.controller;

import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.service.SysMenuService;
import io.renren.modules.sys.util.MenuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 系统页面视图
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月24日 下午11:05:27
 */
@Controller
public class SysPageController extends AbstractController{
	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping("modules/{module}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url){
		return "modules/" + module + "/" + url + ".html";
	}

	@RequestMapping("{url}.html")
	public String url(@PathVariable("url") String url){
		return url + ".html";
	}

	@RequestMapping("/")
	public String index(){
		return "index.html";
	}

	@RequestMapping("/gotoIndex")
	public ModelAndView gotoIndex(){
		ModelAndView mav = new ModelAndView();
		Long userId = getUserId();
		List<SysMenuEntity> menuList = MenuUtils.getMenuListInRedis(userId);
		SysMenuEntity first = menuList.get(0);
		String url = first.getUrl();
		logger.debug("gotoIndex redirect = " + "redirect:" + url);
//		mav.setViewName("redirect:" + url);
		mav.setViewName("forward:" + url);
		return mav;
	}
}
