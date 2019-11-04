package com.mps.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mps.message.Account;
import com.mps.message.AccountDao;
import com.mps.message.Response;
import com.mps.util.MPSUtil;

public class GetUserIds extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetUserIds() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("Retriving all users service got called");
		response.setContentType("application/json");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (bufferedReader != null) {
			json = bufferedReader.readLine();
		}
		System.out.println("Authorization request JSON : " + json);

		Account account = MPSUtil.mapper.readValue(json, Account.class);
		Response loginResponse = new AccountDao().findAllUsers(account);
		MPSUtil.mapper.writeValue(response.getOutputStream(), loginResponse);

		System.err.println(loginResponse);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
