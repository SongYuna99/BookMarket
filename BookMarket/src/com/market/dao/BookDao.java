package com.market.dao;

import java.util.ArrayList;
import com.market.vo.BookVo;

public class BookDao extends DBConn {
	/*
	 * 도서 등록
	 */
	public int insert(BookVo book) {
		int val = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append("INSERT INTO BOOKMARKET_BOOK");
		sb.append("(ISBN, TITLE, PRICE, AUTHOR, INTRO, PART, PDATE, IMG, BDATE) ");
		sb.append("VALUES ('ISBN_'||LTRIM(TO_CHAR(SEQU_BOOKMARKET_BOOK_ISBN.NEXTVAL, '0000')), ");
		sb.append("?, ?, ?, ?, ?, ?, NULL, SYSDATE) ");
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, book.getTitle());
			pstmt.setInt(2, book.getPrice());
			pstmt.setString(3, book.getAuthor());
			pstmt.setString(4, book.getIntro());
			pstmt.setString(5, book.getPart());
			pstmt.setString(6, book.getPdate());

			val = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return val;
	}

	/* 도서 전체 리스트 조회 */
	public ArrayList<BookVo> select() {
		ArrayList<BookVo> list = new ArrayList<BookVo>();
		StringBuffer sb = new StringBuffer(100);
		sb.append("SELECT * FROM BOOKMARKET_BOOK ORDER BY ISBN ASC");

		try {
			getPreparedStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BookVo book = new BookVo();
				book.setIsbn(rs.getString(1));
				book.setTitle(rs.getString(2));
				book.setPrice(rs.getInt(3));
				book.setAuthor(rs.getString(4));
				book.setIntro(rs.getString(5));
				book.setPart(rs.getString(6));
				book.setPdate(rs.getString(7));
				book.setImg(rs.getString(8));
				book.setBdate(rs.getString(9));
				list.add(book);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
