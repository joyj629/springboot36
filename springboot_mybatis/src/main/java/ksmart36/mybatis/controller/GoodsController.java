package ksmart36.mybatis.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ksmart36.mybatis.domain.Goods;
import ksmart36.mybatis.service.MemberService;

@Controller
public class GoodsController {
	
	@Autowired
	private MemberService goodsService;
	
	//검색
	@PostMapping("/goodsList")
	public String getMemberList(Model model
								,@RequestParam(value="gk", required = false) String gk
								,@RequestParam(value="gv", required = false) String gv) {
		
		List<Goods> goodsList = goodsService.getSearchGoodsList(gk, gv);
		System.out.println("goodsList->" + goodsList);
		
		model.addAttribute("goodsList", goodsList);
		model.addAttribute("title", "상품검색화면");
		
		return "goods/goodsList";
	}
	
	//상품삭제
	@RequestMapping(value="/removeGoods", method = RequestMethod.GET)
	public String deleteGoods(Goods goods
								,HttpSession session) throws IOException {
		String memberId = (String) session.getAttribute("SID");
		int memberLevel = (int) session.getAttribute("SLEVEL");
		System.out.println(memberId);
		System.out.println(memberLevel);
		System.out.println(goods.getGoodsSellerId());
		if(memberId.equals(goods.getGoodsSellerId()) || memberLevel == 1) {
			goodsService.removeGoods(goods.getGoodsCode());
			return "goods/removeGoods";
		}else {
			return "redirect:/goodsList";
		}
	}
	@PostMapping("/removeGoods")
	public String deleteGoods(Goods goods) {
	
		return "redirect:/goodsList";		
	}
	
	
	//상품수정
	@RequestMapping(value="/modifyGoods", method = RequestMethod.GET)
	public String modifyGoodsList(Goods goods
									,HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
		int memberLevel = (int) session.getAttribute("SLEVEL");
		if(memberId.equals(goods.getGoodsSellerId()) || memberLevel == 1) {
			return "goods/modifyGoods";
		}else {
			return "redirect:/goodsList";
		}
	}
	
	@PostMapping("/modifyGoods")
	public String modifyGoodsList(Goods goods) {
		goodsService.modifyGoods(goods);
	
		return "redirect:/goodsList";		
	}
	

	@GetMapping("/goodsList")
	public String goodsList(Model model) {
		List<Goods> getGoodsList = goodsService.getGoodsList();
		model.addAttribute("goodsList", getGoodsList);
		model.addAttribute("title", "상품리스트");
		System.out.println(getGoodsList);
		return "goods/goodsList";
	}
	@RequestMapping(value="/addgoods", method = RequestMethod.GET)
	public String addMemberList(Model model) {
		return "goods/addgoods";
	}
	@PostMapping("/addgoods")
	public String addgoods(Goods goods
						  ,@RequestParam(value="goodsName", required = false) String goodsName
						  ,@RequestParam(value="goodsPrice", required = false) String goodsPrice
						  ,HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
		goods.setGoodsSellerId(memberId);
		int addgoods = goodsService.addgoods(goods);
		System.out.println("goods =>"+addgoods);
		System.out.println(memberId);
		System.out.println(goodsName);
		System.out.println(goodsPrice);
		return "redirect:/goodsList";
	}
}
