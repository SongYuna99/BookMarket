package com.market.dao;

import com.market.vo.OrderMemberVo;

public class OrderMemberDao extends DBConn {
	/*
	 * 데이터 추가 - PreparedStatement
	 */
//	public int insertPrepared(OrderMemberVo orderMember) {
//		int result = 0;
//		StringBuffer sb = new StringBuffer(100);
//		sb.append("INSERT INTO BOOKMARKET_ORDER VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
//		try {
//			getPreparedStatement(sb.toString());
//			for (int i = 0; i < orderMember.getQtyList().length; i++) {
//				pstmt.setString(1, orderMember.getOid());
//				pstmt.setString(2, orderMember.getOdate());
//				pstmt.setInt(3, orderMember.getQtyList());
//				pstmt.setString(4, orderMember.getIsbnList());
//				pstmt.setString(5, orderMember.getMid());
//				pstmt.setString(6, orderMember.getName());
//				pstmt.setString(7, orderMember.getPhone());
//				pstmt.setString(8, orderMember.getAddr());
//
//				pstmt.addBatch();
//				pstmt.clearParameters();
//			}
//			result = pstmt.executeBatch().length;
//			pstmt.clearBatch();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}

	/*
	 * SELECT
	 */
	public OrderMemberVo select(String mid) {
		OrderMemberVo orderMember = new OrderMemberVo();
		StringBuffer sb = new StringBuffer(100);
		sb.append("SELECT ROWNUM, MID, NAME, ADDR, PHONE, TO_CHAR(CDATE, 'YYYY-MM-DD') CDATE ");
		sb.append("FROM (SELECT M.MID, M.NAME, M.ADDR, M.PHONE, C.CDATE ");
		sb.append("FROM BOOKMARKET_CART C, BOOKMARKET_MEMBER M ");
		sb.append("WHERE C.MID = M.MID) ");
		sb.append("WHERE MID = ? AND ROWNUM = 1 ");
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid.toUpperCase());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderMember.setMid(rs.getString(2));
				orderMember.setName(rs.getString(3));
				orderMember.setAddr(rs.getString(4));
				orderMember.setPhone(rs.getString(5));
				orderMember.setCdate(rs.getString(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return orderMember;
	}
}
