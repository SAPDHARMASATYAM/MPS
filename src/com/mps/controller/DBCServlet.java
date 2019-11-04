package com.mps.controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mps.util.DBCUtil;

public class DBCServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		String dbURL = config.getServletContext().getInitParameter("DB_URL");
		String driverName = config.getServletContext().getInitParameter("DB_DRIVER");
		String userName = config.getServletContext().getInitParameter("DB_USER");
		String password = config.getServletContext().getInitParameter("DB_PASSWORD");
		DBCUtil dbcUtil = new DBCUtil(dbURL, driverName, userName, password);
		dbcUtil.ConfigureDBC();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
