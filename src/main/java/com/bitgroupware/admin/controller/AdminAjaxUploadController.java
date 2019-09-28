package com.bitgroupware.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bitgroupware.community.service.DocCenterService;
import com.bitgroupware.community.service.NoticeService;
import com.bitgroupware.community.utils.TemporaryFileUrl;
import com.bitgroupware.community.utils.UploadFileUtils;

@Controller
@RequestMapping("/admin")
public class AdminAjaxUploadController {

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private DocCenterService docCenterService;

	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> uploadAjax(MultipartFile file, HttpServletRequest request) throws Exception {
		String uploadPath = request.getSession().getServletContext().getRealPath("/");
		String fileUrl = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		TemporaryFileUrl.fileUrl.put(fileUrl, fileUrl);
		return new ResponseEntity<String>(fileUrl, HttpStatus.OK);
	}

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

	@RequestMapping(value = "/deleteNoticeFileAjax", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> deleteNoticeFileAjax(String fileUrl, HttpServletRequest request) {
		String uploadPath = request.getSession().getServletContext().getRealPath("/");
		String temporaryFileUrl = fileUrl.substring(1);
		new File(uploadPath + temporaryFileUrl).delete();
		TemporaryFileUrl.fileUrl.remove(fileUrl);
		noticeService.deleteNoticeFile(fileUrl);
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteDocCenterFileAjax", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> deleteDocCenterFileAjax(String fileUrl, HttpServletRequest request) {
		String uploadPath = request.getSession().getServletContext().getRealPath("/");
		String temporaryFileUrl = fileUrl.substring(1);
		new File(uploadPath + temporaryFileUrl).delete();
		TemporaryFileUrl.fileUrl.remove(fileUrl);
		docCenterService.deleteDocCenterFile(fileUrl);
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
}
