package com.mps.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mps.message.Account;
import com.mps.message.AccountDao;
import com.mps.message.Response;
import com.mps.util.MPSUtil;

@WebServlet(value = "/getAllUsers")
public class GetUserIds extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetUserIds() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("Retriving all Account ids got called");
		response.setContentType("application/json");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (bufferedReader != null) {
			json = bufferedReader.readLine();
		}
		System.out.println("Retriving all Account ids request JSON : " + json);

		Account account = MPSUtil.mapper.readValue(json, Account.class);
		Response allAccountsResponse = new AccountDao().findAllUsers(account);
		MPSUtil.mapper.writeValue(response.getOutputStream(), allAccountsResponse);

		System.err.println(allAccountsResponse);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
