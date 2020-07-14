package com.example.demo.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import com.example.demo.httpserver.HttpRequest;
import com.example.demo.httpserver.HttpResponse;
import com.example.demo.httpserver.ServletMapping;
import com.example.demo.httpserver.type.HttpMethodType;

public class HelloServlet implements SimpleServlet {

	@Override
	public void service(HttpRequest req, HttpResponse res) throws IOException {

		String body = "";
		if (req.getRequestType().equals(HttpMethodType.GET.name())) {
			ServletMapping servlet = req.getServletMap().get(req.getFullPath());

			body = new StringBuilder("<HTML>\r\n").append("<HEAD><TITLE>Hello, World</TITLE>\r\n").append("</HEAD>\r\n")
					.append("<BODY>").append("<H1>Hello, World</H1>\r\n")
					.append("<p>Request URI: " + req.getFullPath() + "</p>")
					.append("<p>Request Protocol: " + req.getRequestProtocol() + "</p>")
					.append("<p>Servlet Class: " + servlet.getServletClass() + "</p>")
					.append("<p>Servlet Name: " + servlet.getServletName() + "</p>").append("</BODY></HTML>\r\n")
					.toString();

		} else {
			body = new StringBuilder("<HTML>\r\n").append("<HEAD><TITLE>Not Implemented</TITLE>\r\n")
					.append("</HEAD>\r\n").append("<BODY>").append("<H1>HTTP Error 501: Not Implemented</H1>\r\n")
					.append("</BODY></HTML>\r\n").toString();
		}

		if (req.getRequestProtocol().startsWith("HTTP/")) {
			sendHeader(res.getWriter(), "HTTP/1.0", "text/html; charset=utf-8", body.length());
		}

		res.writeLine(body);
		res.getWriter().close();
	}

	private void sendHeader(DataOutputStream out, String responseCode, String contentType, int length)
			throws IOException {
		out.writeBytes(responseCode + "\r\n");
		Date now = new Date();
		out.writeBytes("Date: " + now + "\r\n");
		out.writeBytes("Server: JHTTP 2.0\r\n");
		out.writeBytes("Content-length: " + length + "\r\n");
		out.writeBytes("Content-type: " + contentType + "\r\n\r\n");
		out.flush();
	}
}
