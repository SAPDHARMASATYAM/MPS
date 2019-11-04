package com.mps.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MPSUtil {
	public static ObjectMapper mapper = new ObjectMapper();

	public static String getSqlDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
}
