package com.jaenyeong;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Application {

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:postgresql://localhost:5432/springdatajpa";
		String userName = "jaenyeong";
		String password = "1234";

		try (Connection connection = DriverManager.getConnection(url, userName, password)) {
			System.out.println("Connection created: " + connection);
			// 테이블 생성
//			String sql = "CREATE TABLE ACCOUNT (id int, username varchar(255), password varchar(255));";

			// 생성된 테이블에 데이터 삽입
			String sql = "INSERT INTO ACCOUNT VALUES(1, 'jaenyeong', '1234')";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.execute();
			}
		}
	}
}
