package com.market.dao;

import java.util.ArrayList;

import com.market.vo.CartVo;

public class CartDao extends DBConn {
	/*
	 * DELETE QTY
	 */
	public int delete(String isbn, String mid) {
		int result = 0;

		try {

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * REMOVE
	 */
	public int delete(String cid, int qty) {
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("UPDATE BOOKMARKET_CART SET QTY = ? WHERE CID = ?");
		try {
			getPreparedStatement(sb.toString());
			pstmt.setInt(1, qty);
			pstmt.setString(2, cid.toUpperCase());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int deleteMid(String mid) {
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("DELETE FROM BOOKMARKET_CART WHERE MID = ?");
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid.toUpperCase());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int delete(String cid) {
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("DELETE FROM BOOKMARKET_CART WHERE CID = ?");
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, cid.toUpperCase());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/*
	 * SELECT
	 */
	public ArrayList<CartVo> select(String mid) {
		ArrayList<CartVo> list = new ArrayList<CartVo>();
		StringBuffer sb = new StringBuffer(100);
		sb.append("SELECT ROWNUM, ISBN, TITLE, PRICE, TO_CHAR(PRICE, 'L999,999') SPRICE, ");
		sb.append("QTY, QTY*PRICE TOTAL_PRICE, TO_CHAR(QTY*PRICE, 'L999,999') STOTAL_PRICE , CID ");
		sb.append("FROM (SELECT B.ISBN, B.TITLE, B.PRICE, C.QTY, C.MID , C.CID ");
		sb.append("FROM BOOKMARKET_CART C, BOOKMARKET_BOOK B, BOOKMARKET_MEMBER M ");
		sb.append("WHERE C.MID = M.MID AND B.ISBN = C.ISBN) ");
		sb.append("WHERE MID = ?");
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid.toUpperCase());
			rs = pstmt.executeQuery();

			// rno, isbn, title, price, sprice, qty, total_price, stotal_price
			while (rs.next()) {
				CartVo cart = new CartVo();
				cart.setRno(rs.getInt(1));
				cart.setIsbn(rs.getString(2));
				cart.setTitle(rs.getString(3));
				cart.setPrice(rs.getInt(4));
				cart.setSprice(rs.getString(5));
				cart.setQty(rs.getInt(6));
				cart.setTotal_price(rs.getInt(7));
				cart.setStotal_price(rs.getString(8));
				cart.setCid(rs.getString(9));

				list.add(cart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/*
	 * 장바구니 수량 체크
	 */
	public int getSize(String mid) {
		int size = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("SELECT COUNT(*) FROM BOOKMARKET_CART WHERE MID = ?");
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid.toUpperCase());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return size;
	}

	/*
	 * 장바구니 수량 늘리기
	 */
	public int updateQty(CartVo cartVo) {
		int val = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("UPDATE BOOKMARKET_CART SET QTY = QTY + 1 WHERE MID = ? AND ISBN = ?");
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, cartVo.getMid().trim().toUpperCase());
			pstmt.setString(2, cartVo.getIsbn().trim().toUpperCase());

			val = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return val;
	}

	/*
	 * 장바구니 중복 검사
	 */
	public int insertCheck(CartVo cartVo) {
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("SELECT COUNT(*) FROM BOOKMARKET_CART WHERE MID = ? AND ISBN = ?");
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, cartVo.getMid().toUpperCase());
			pstmt.setString(2, cartVo.getIsbn().toUpperCase());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/*
	 * 장바구니 추가
	 */
	public int insert(CartVo cartVo) {
//System.out.println("mid--->>" + cartVo.getMid());		
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append(" insert into bookmarket_cart ");
		sb.append(" values('C_'||LTRIM(TO_CHAR(SEQU_BOOKMARKET_CART_CID.NEXTVAL,'0000')),");
		sb.append(" sysdate, 1, ?,? )");

		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, cartVo.getIsbn().toUpperCase());
			pstmt.setString(2, cartVo.getMid().toUpperCase());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
