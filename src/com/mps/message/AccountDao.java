package com.mps.message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mps.util.DBCUtil;

public class AccountDao {

	public Response accountRegistration(Account account) {
		Response response = new Response();
		String query = "insert into user values ('" + account.getEmail() + "','" + account.getfName() + "','"
				+ account.getlName() + "','" + account.getAddress() + "','" + account.getMobile() + "','"
				+ account.getPassword() + "')";
		int saveDBResponse = DBCUtil.insert(query);
		if (saveDBResponse == 0) {
			response.setResponseStatus("Fail");
			response.setResponseMessage("Please verify details");
			response.setResponseBody(account);
		} else if (saveDBResponse == -1) {
			response.setResponseStatus("Error");
			response.setResponseMessage("Internal server error");
			response.setResponseBody(account);
		} else {
			response.setResponseStatus("Success");
			response.setResponseMessage("Registration success");
			response.setResponseBody(account);
		}
		System.out.println(" ******************* Registration Response : " + response + " ******************* ");
		return response;
	}

	public Response findUserByEmailIdAndPassword(Account account) {
		Response response = new Response();
		String query = "select * from user where email ='" + account.getEmail() + "' and password = '"
				+ account.getPassword() + "'";
		account = parseUser(DBCUtil.getData(query));
		if (null == account) {
			response.setResponseStatus("Fail");
			response.setResponseMessage("Please verify credentials");
			response.setResponseBody(account);
		} else {
			response.setResponseStatus("Success");
			response.setResponseMessage("Login success");
			response.setResponseBody(account);
		}
		System.out.println(" ******************* Login Response : " + response + " ******************* ");
		return response;
	}

	private List<String> parseUserIds(ResultSet rs) {
		List<String> users = new ArrayList<String>();
		try {
			while (null != rs && rs.next()) {
				users.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	private Account parseUser(ResultSet rs) {
		Account account = null;
		try {
			if (null != rs && rs.next()) {
				account = new Account();
				account.setEmail(rs.getString(1));
				account.setfName(rs.getString(2));
				account.setlName(rs.getString(3));
				account.setAddress(rs.getString(4));
				account.setMobile(rs.getString(5));
				account.setPassword(rs.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	public Response findAllUsers(Account user) {
		Response response = new Response();
		List<String> users = null;
		String query = "select email from user where  email !='" + user.getEmail() + "'";
		users = parseUserIds(DBCUtil.getData(query));
		if (users.size() == 0) {
			response.setResponseStatus("Fail");
			response.setResponseMessage("findAllUsers fail");
			response.setResponseBody(users);
		} else {
			response.setResponseStatus("Success");
			response.setResponseMessage("findAllUsers success");
		}
		response.setResponseBody(users);
		System.out.println(" ******************* findAllUsers Response : " + response + " ******************* ");
		return response;
	}
}
