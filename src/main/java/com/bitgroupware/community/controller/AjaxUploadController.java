package com.bitgroupware.community.controller;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class AjaxUploadController {

	@RequestMapping("/displayFile")
	@ResponseBody
	public ResponseEntity<byte[]> displayFile(String fileUrl, HttpServletRequest request) throws Exception {
		String uploadPath = request.getSession().getServletContext().getRealPath("/");
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(uploadPath + fileUrl);
			fileUrl = fileUrl.substring(fileUrl.indexOf("_") + 1);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.add("Content-Disposition",
					"attachment; filename=\"" + new String(fileUrl.getBytes("utf-8"), "iso-8859-1") + "\"");
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			if (in != null)
				in.close();
		}
		return entity;
	}
}
