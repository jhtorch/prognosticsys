package com.nian.firstproject.server;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class Upload extends HttpServlet {

	// init the blog store service
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doPost(HttpServletRequest request, HttpServletResponse res)
			throws ServletException, IOException {

		// this has to be used in an rpc call to get the url to be used with
		// this request
		// String url = blobstoreService.createUploadUrl("/upload");

		int len = request.getContentLength();
		int mb = (1024 * 1024) * 10;
		if (len > mb) {
			throw new RuntimeException(
					"Sorry that file is too large. Try <10M file");
		}

		/*
		 * Enumeration paramNames = request.getParameterNames();
		 * while(paramNames.hasMoreElements()) { String paramName =
		 * (String)paramNames.nextElement(); String[] paramValues =
		 * request.getParameterValues(paramName); System.out.println(paramName +
		 * "=" + paramValues.toString() + "\n"); }
		 */

		@SuppressWarnings("deprecation")
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
		BlobKey blobKey = blobs.get("myFile");

		if (blobKey == null) {
			res.sendRedirect("/");
		} else {
			res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
		}

	}

}