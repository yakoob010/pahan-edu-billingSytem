<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/layout/base.jsp">
    <jsp:param name="pageTitle" value="Create Invoice" />
    <jsp:param name="activePage" value="order" />
    <jsp:param name="content" value="/WEB-INF/views/order/order-content.jsp" />
</jsp:include>
