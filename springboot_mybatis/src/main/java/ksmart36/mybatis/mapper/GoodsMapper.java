package ksmart36.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ksmart36.mybatis.domain.Goods;

@Mapper
public interface GoodsMapper {
	
	public List<Goods> getGoodsList();

	public int addGoods(Goods goods);

	public int modifyGoods(Goods goods);

	public int deleteGoods(String goodsCode);
	
	public int deleteOrder(String goodsCode);

	public List<Goods> getSearchGoodsList(String gk, String gv);

}
