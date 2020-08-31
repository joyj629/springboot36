package ksmart36.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ksmart36.mybatis.domain.Member;

@Mapper
public interface MemberMapper {
	//회원로그인이력 조회
	public List<Map<String, Object>> getLoginHistory(Map<String, Object>parameterMap);
	
	//회원목록 조건 검색
	public List<Member> getSearchMemberList(String sk, String sv);
	
	//1.로그인 테이블(login_id) 삭제
	public int removeLogin(String memberId);
	//2-1. select 상품테이블(g_seller_id) 통해 g_code조회 후
	public List<Map<String, Object>> getGoodsCodeById(String memberId);
	//2-2. 주문 테이블 삭제
	public int removeOrder(List<Map<String, Object>> goodsCodeList);				
	//3. 상품테이블(g_seller_id)삭제
	public int removeGoods(String memberId);
	//4. 멤버테이블삭제	
	public int removeMember(String memberId);
	
	//회원정보수정
	public int modifyMember(Member member);
	
	//id로 회원정보 조회
	public Member getMemberById(String memberId);
	
	//회원 목록 조회
	public List<Member> getMemberList();
	
	//회원 정보 등록
	public int addMember(Member member);

	public int getLoginCount();

	
	
}
