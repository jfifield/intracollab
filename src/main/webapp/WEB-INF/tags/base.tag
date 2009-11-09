<%
	String scheme = request.getScheme();
	String host = request.getServerName();
	int port = request.getServerPort();
	String context = request.getContextPath();
	String href = scheme + "://" + host;
	boolean defaultPort = (scheme.equals("http") && port == 80) || (scheme.equals("https") && port == 443);
	if (!defaultPort) {
		href += ":" + port;
	}
	href += context;
	out.print("<base href=\"" + href + "/\"/>");
%>