package com.market.dao;

import com.market.vo.MemberVo;

public class MemberDao extends DBConn {
	/*
	 * 고객정보 확인
	 */
	public MemberVo select(String mid) {
		MemberVo member = new MemberVo();
		StringBuffer sb = new StringBuffer(100);
		sb.append(" SELECT mid, pass, name, addr, phone, mdate FROM BOOKMARKET_MEMBER ");
		sb.append(" WHERE MID = ? ");

		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid.toUpperCase());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member.setMid(rs.getString(1));
				member.setPass(rs.getString(2));
				member.setName(rs.getString(3));
				member.setAddr(rs.getString(4));
				member.setPhone(rs.getString(5));
				member.setMdate(rs.getString(6));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return member;
	}

	/*
	 * 로그인 체크
	 */
	public boolean select(String mid, String pass) {
		boolean result = false;
		StringBuffer sb = new StringBuffer(100);
		sb.append(" SELECT COUNT(*) FROM BOOKMARKET_MEMBER ");
		sb.append(" WHERE MID=? AND PASS=?");

		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid.toUpperCase());
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();

			int val = 0;
			while (rs.next()) {
				val = rs.getInt(1);
			}
			if (val == 1)
				result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
