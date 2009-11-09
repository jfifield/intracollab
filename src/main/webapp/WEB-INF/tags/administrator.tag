<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope['org.programmerplanet.intracollab.web.UserSession'].user.administrator}">
<jsp:doBody/>
</c:if>