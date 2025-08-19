<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/layout/base.jsp">
    <jsp:param name="pageTitle" value="Item Management" />
    <jsp:param name="activePage" value="item" />
    <jsp:param name="content" value="/WEB-INF/views/item/item-content.jsp" />
</jsp:include>
