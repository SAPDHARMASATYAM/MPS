package com.mps.message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mps.util.DBCUtil;
import com.mps.util.MPSUtil;

public class MessageDao {

	public Response updateMessage(Message message) {
		Response response = new Response();
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			String query = "update message set  content = ?, date = ?,subject = ?  where id = ?";
			connection = DBCUtil.getconnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, message.getBody());
			pstmt.setString(2, MPSUtil.getSqlDateTime());
			pstmt.setString(3, message.getSubject());
			pstmt.setInt(4, message.getId());

			System.err
					.println(" ******************* Prepared Statement for update message after bind variables set:\n\t"
							+ pstmt.toString() + " ******************* ");
			int updateResponse = pstmt.executeUpdate();
			if (updateResponse != 0) {
				response.setResponseStatus("Success");
				response.setResponseMessage("message Update Successfull");
			} else {
				response.setResponseStatus("Fail");
				response.setResponseMessage("message Update Fail");
			}
		} catch (SQLException e) {
			response.setResponseStatus("Exception");
			response.setResponseMessage("message Update Fail");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
		}
		response.setResponseBody(message);
		System.err.println(" ******************* update message response : " + response + " ******************* ");
		return response;
	}

	public Response getAllMessagesByType(Message message) {
		Response response = new Response();
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet resultSet = null;
		List<Message> messages = null;
		try {
			String query = "select * from message  where sender = ?";
			connection = DBCUtil.getconnection();
			if (message.isType()) {// public
				query += " union select * from message  where sender != ? and type =?";
				query += " union select * from message where sender in (select m_id from ms where u_id = ?)";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(2, message.getFrom());
				pstmt.setBoolean(3, message.isType());
				pstmt.setString(4, message.getFrom());
			} else {
				pstmt = connection.prepareStatement(query);
			}
			pstmt.setString(1, message.getFrom());
			System.err.println(" ******************* Prepared Statement for read messages after bind variables set:\n\t"
					+ pstmt.toString() + " ******************* ");
			resultSet = pstmt.executeQuery();
			if (resultSet != null) {
				messages = new ArrayList<Message>();
				while (resultSet.next()) {
					Message messageMsg = new Message();
					messageMsg.setId(resultSet.getInt(1));
					messageMsg.setBody(resultSet.getString(2));
					messageMsg.setDate(resultSet.getString(3));
					messageMsg.setSubject(resultSet.getString(4));
					messageMsg.setFrom(resultSet.getString(5));
					messageMsg.setType(resultSet.getBoolean(6));
					messages.add(messageMsg);
				}
				response.setResponseStatus("Success");
				response.setResponseMessage("messages Reading Successfull");
			} else {
				response.setResponseStatus("Fail");
				response.setResponseMessage("messages Reading Fail");
			}
		} catch (SQLException e) {
			response.setResponseStatus("Exception");
			response.setResponseMessage("messages Reading Fail");
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {

			}
		}
		response.setResponseBody(messages);
		System.err.println(" ******************* read messages response : " + response + " ******************* ");
		return response;
	}

	public Response findMessages(Message message) {
		Response response = new Response();
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet resultSet = null;
		List<Message> messages = null;
		try {
			String query = "select * from message  where sender = ?";
			connection = DBCUtil.getconnection();
			if (message.getSubject().trim().length() > 0 && message.getBody().trim().length() > 0) {
				query += " and subject like ? and content like ?";
				query += " union select * from message  where sender != ? and type =? and subject like ? and content like ?";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, message.getFrom());
				pstmt.setString(2, "%" + message.getSubject() + "%");
				pstmt.setString(3, "%" + message.getBody() + "%");
				pstmt.setString(4, message.getFrom());
				pstmt.setBoolean(5, true);
				pstmt.setString(6, "%" + message.getSubject() + "%");
				pstmt.setString(7, "%" + message.getBody() + "%");
			} else if (message.getSubject().trim().length() > 0 && message.getBody().trim().length() == 0) {
				query += " and messageSubject like ? ";
				query += " union select * from message  where sender != ? and type =? and subject like ? ";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, message.getFrom());
				pstmt.setString(2, "%" + message.getSubject() + "%");
				pstmt.setString(3, message.getFrom());
				pstmt.setBoolean(4, true);
				pstmt.setString(5, "%" + message.getSubject() + "%");
			} else if (message.getSubject().trim().length() == 0 && message.getBody().trim().length() > 0) {
				query += " and  messageContent like ?";
				query += " union select * from message  where sender != ? and type =? and  content like ?";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, message.getFrom());
				pstmt.setString(2, "%" + message.getBody() + "%");
				pstmt.setString(3, message.getFrom());
				pstmt.setBoolean(4, true);
				pstmt.setString(5, "%" + message.getBody() + "%");
			} else {
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, "");
			}

			System.err.println(" ******************* Prepared Statement for find messages after bind variables set:\n\t"
					+ pstmt.toString() + " ******************* ");
			resultSet = pstmt.executeQuery();
			if (resultSet != null) {
				messages = new ArrayList<Message>();
				while (resultSet.next()) {
					Message messageMsg = new Message();
					messageMsg.setId(resultSet.getInt(1));
					messageMsg.setBody(resultSet.getString(2));
					messageMsg.setDate(resultSet.getString(3));
					messageMsg.setSubject(resultSet.getString(4));
					messageMsg.setFrom(resultSet.getString(5));
					messageMsg.setType(resultSet.getBoolean(6));
					messages.add(messageMsg);
				}
				response.setResponseStatus("Success");
				response.setResponseMessage("Find messages Successfull");
			} else {
				response.setResponseStatus("Fail");
				response.setResponseMessage("Find messages Fail");
			}
		} catch (SQLException e) {
			response.setResponseStatus("Exception");
			response.setResponseMessage("Find messages Fail");
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {

			}
		}
		response.setResponseBody(messages);
		System.err.println(" ******************* Find messages response : " + response + " ******************* ");
		return response;
	}

	public Response newMessage(Message message) {
		Response response = new Response();
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			String query = "insert into message (sender,content,date,subject,type) values (?,?,?,?,?)";
			connection = DBCUtil.getconnection();
			pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, message.getFrom());
			pstmt.setString(2, message.getBody());
			pstmt.setString(3, MPSUtil.getSqlDateTime());
			pstmt.setString(4, message.getSubject());
			pstmt.setBoolean(5, message.isType());
			System.err.println(" ******************* Prepared Statement for save message after bind variables set:\n\t"
					+ pstmt.toString() + " ******************* ");
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				int messageId = rs.getInt(1);
				System.out.println("Generated message Id: " + messageId);
				message.setId(messageId);
				MSDao dao = new MSDao();
				if (message.getTo() != null && message.getTo().length > 0) {
					dao.shareMessage(messageId, message.getTo());
				}
				response.setResponseStatus("Success");
				response.setResponseMessage("Message Post Successfull");
			} else {
				message.setId(0);
				response.setResponseStatus("Fail");
				response.setResponseMessage("Message Post Fail");
			}
		} catch (SQLException e) {
			message.setId(0);
			response.setResponseStatus("Exception");
			response.setResponseMessage("Message Post Fail");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				// if(connection != null) connection.close();
			} catch (Exception ex) {
			}
		}
		response.setResponseBody(message);
		System.err.println(" ******************* save message response : " + response + " ******************* ");
		return response;
	}

	public Response removeMessage(Message message) {
		Response response = new Response();
		PreparedStatement pstmt = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			String query = "delete from message  where id = ?";
			connection = DBCUtil.getconnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, message.getId());
			System.err
					.println(" ******************* Prepared Statement for delete message after bind variables set:\n\t"
							+ pstmt.toString() + " ******************* ");
			int updateResponse = pstmt.executeUpdate();
			if (updateResponse != 0) {
				MSDao dao = new MSDao();
				dao.deleteSharingMessages(message.getId());
				response.setResponseStatus("Success");
				response.setResponseMessage("message Delete Successfull");
			} else {
				response.setResponseStatus("Fail");
				response.setResponseMessage("message Delete Fail");
			}
		} catch (SQLException e) {
			response.setResponseStatus("Exception");
			response.setResponseMessage("message Delete Fail");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
		}
		response.setResponseBody(message);
		System.err.println(" ******************* delete message response : " + response + " ******************* ");
		return response;
	}

}
