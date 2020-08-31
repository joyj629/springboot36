package ksmart36.mybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksmart36.mybatis.domain.Goods;
import ksmart36.mybatis.domain.Member;
import ksmart36.mybatis.mapper.GoodsMapper;
import ksmart36.mybatis.mapper.MemberMapper;

@Service
public class MemberService {
	
	
	
	@Autowired 
	private MemberMapper memberMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	
	//회원로그인이력조회
	public Map<String, Object> getLoginHistory(int currentPage){
		//보여줄 행의 갯수
		final int ROW_PER_PAGE =10;
		
		//보여줄 행의 시작점 초기화
		int starRow = 0;
		
		// 페이징 알고리즘
		starRow = (currentPage - 1) * ROW_PER_PAGE;
		Map<String ,Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("startRow", starRow);
		parameterMap.put("rowPerPage", ROW_PER_PAGE);
		
		//시작페이지번호,끝페이지번호
		int startPageNum = 1;
	    int lastPageNum = ROW_PER_PAGE;
	    
	    //6번째 가운데 위치
	    if(currentPage > (ROW_PER_PAGE/2)) {
            startPageNum = currentPage - (lastPageNum/2);
            lastPageNum += (startPageNum-1);
	    }
	    
	    
		double totalRowCount = memberMapper.getLoginCount();
		
		int lastPage = (int) Math.ceil(totalRowCount / ROW_PER_PAGE);
		
		List<Map<String, Object>> loginHistory = memberMapper.getLoginHistory(parameterMap);
		
		if(currentPage <= (lastPageNum-4)){
			lastPageNum = lastPage;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("loginHistory", loginHistory);
        resultMap.put("lastPage", lastPage);
        resultMap.put("startPageNum", startPageNum);
        resultMap.put("lastPageNum", lastPageNum);
		return resultMap;
	}
	
	//상품삭제처리
	public int removeGoods(String goodsCode) {
		int result = 0;
		result = goodsMapper.deleteOrder(goodsCode);
		result = goodsMapper.deleteGoods(goodsCode);
		System.out.println(goodsCode);
		
		//delete 성공 결과
		return result;
	}
	
	//상품정보수정
	public int modifyGoods(Goods goods) {
		return goodsMapper.modifyGoods(goods);
	}
	//상품추가
	public int addgoods(Goods goods) {
		int result = goodsMapper.addGoods(goods);
		System.out.println(result+"<-result값");
		return result;
	} 
	//상품리스트
	public List<Goods> getGoodsList(){
		List<Goods> goodsList = goodsMapper.getGoodsList();
		
		return goodsList;
	}
	
	//회원목록조건검색
	public List<Member> getSearchMemberList(String sk, String sv){
		// 회원들의 정보가 담긴 list객체
		List<Member> memberList = memberMapper.getSearchMemberList(sk, sv);
		
		//list객체가 null이 아닌 경우 = 조회 성공
		if(memberList != null) {			
			for(int i=0; i<memberList.size(); i++) {	
				//변수(회원레벨) 초기화
				int memberLevel = 0;
				memberLevel = memberList.get(i).getMemberLevel();
				//회원레벨이 담겨져 있는 경우 체크
				if(memberLevel > 0) {					
					if(memberLevel == 1) {
						memberList.get(i).setMemberLevelName("관리자");
					}else if(memberLevel == 2){
						memberList.get(i).setMemberLevelName("판매자");
					}else if(memberLevel == 3){
						memberList.get(i).setMemberLevelName("구매자");
					}else {
						memberList.get(i).setMemberLevelName("일반회원");
					}
				}
			}
		}
		return memberList;
	}
	
	
	//회원정보삭제
	public int removeMember(String memberId,String memberPw) {
		int result = 0;
		
		Member member = memberMapper.getMemberById(memberId);
		if(member != null && memberPw != null && !"".equals(memberPw)) {
			if(memberPw.equals(member.getMemberPw())) {
				//1.로그인 테이블(login_id) 삭제
				result += memberMapper.removeLogin(memberId);
				//2-1. select 상품테이블(g_seller_id) 통해 g_code조회 후
				List<Map<String, Object>> goodsCodeList = memberMapper.getGoodsCodeById(memberId);
				//2-2. 주문 테이블 삭제
				result += memberMapper.removeOrder(goodsCodeList);				
				//3. 상품테이블(g_seller_id)삭제
				result += memberMapper.removeGoods(memberId);
				//4. 멤버테이블삭제
				result += memberMapper.removeMember(memberId);
			}
		}
		
		//delete 성공 결과
		return result;
	}
	
	//회원정보수정
	public int modifyMember(Member member) {
		return memberMapper.modifyMember(member);
	}
	
	//id로 회원정보 조회
	public Member getMemberById(String memberId) {
		Member member = memberMapper.getMemberById(memberId);
		
		//레벨에 따라 memberLevelName 값을 setter
		if(member != null) {
			int memberLevel = 0;
			memberLevel = member.getMemberLevel();
			if(memberLevel > 0) {					
				if(memberLevel == 1) {
					member.setMemberLevelName("관리자");
				}else if(memberLevel == 2){
					member.setMemberLevelName("판매자");
				}else if(memberLevel == 3){
					member.setMemberLevelName("구매자");
				}else {
					member.setMemberLevelName("일반회원");
				}
			}
		}
		return member;
		
	}
	public int addMember(Member member) {
		int result = memberMapper.addMember(member);
		
		return result;
	}
	
	public List<Member> getMemberList(){
		//회원드르이 정보가 담긴 list객체
		List<Member> memberList = memberMapper.getMemberList();
		
		//list객체가 null이 아닌 경우 = 조회 성공
		if(memberList != null) {			
			for(int i=0; i<memberList.size(); i++) {	
				//변수(회원레벨) 초기화
				int memberLevel = 0;
				memberLevel = memberList.get(i).getMemberLevel();
				//회원레벨이 담겨져 있는 경우 체크
				if(memberLevel > 0) {					
					if(memberLevel == 1) {
						memberList.get(i).setMemberLevelName("관리자");
					}else if(memberLevel == 2){
						memberList.get(i).setMemberLevelName("판매자");
					}else if(memberLevel == 3){
						memberList.get(i).setMemberLevelName("구매자");
					}else {
						memberList.get(i).setMemberLevelName("일반회원");
					}
				}
			}
		}
		return memberList;
	}

	public List<Goods> getSearchGoodsList(String gk, String gv) {
		List<Goods> goodsList = goodsMapper.getSearchGoodsList(gk, gv);
		
		return goodsList;
		
	}

}
