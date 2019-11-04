package com.mps.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mps.message.Message;
import com.mps.message.MessageDao;
import com.mps.message.Response;
import com.mps.util.MPSUtil;




public class SearchMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchMessageServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Search messaging started");
		response.setContentType("application/json");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if(bufferedReader != null){
			json = bufferedReader.readLine();
		}
		System.out.println("Find Mails request JSON : " + json);

		Message mail = MPSUtil.mapper.readValue(json, Message.class);

		Response changeMailResponse = new MessageDao().findMessages(mail);

		MPSUtil.mapper.writeValue(response.getOutputStream(), changeMailResponse);

		System.err.println(changeMailResponse);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}