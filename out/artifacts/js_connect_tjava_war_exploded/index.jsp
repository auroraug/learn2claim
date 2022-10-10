<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/8/11
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath(); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>$Title$</title>
</head>
<body>
<fmt:requestEncoding value="utf-8"></fmt:requestEncoding>
<c:set var="addr" scope="session" value="${addr}"></c:set>  <!--取出登录功能页面输入的信息-->
<div class="title">
<div id="Accframe" class="Cn" style="position: absolute;top: 5px;right: 5px ; border:2px solid #6dc3bb; border-radius: 20px;width: 230px;
        height: 40px; padding: 0 10px; display: flex; flex-direction: row; justify-content: space-between;line-height: 40px;">

    <div id="Accountsvalue" style="font-size: 13px;"><c:out value="${Accountsvalue}"></c:out></div>
    <button id="Accountsbutton" onclick="connect()" style="margin-top: 3px; height: 34px; width: 80px; border-radius: 10px; font-size: 14px;
    background: linear-gradient(to right,#ead6ee,#a0f1ea);border: 0px; color: rgb(0, 0, 0)"><c:out value="${Accountsbutton}"></c:out></button>
    <input hidden <c:out value="${addr}"></c:out>/>
</div>
<div id="network" class="net" style="position: absolute;top: 10px;right: 280px ;">${network}</div>
<a style="color: #DCDCDC" href="<%=path%>/_logoutservlet">退出系统</a><br><br>

<c:forEach items="${menus}" var="m">
  <c:if test="${m.checkId != null}">
    <a href="<%=path%>${m.menuPath}">${m.menuName}</a> &nbsp;
  </c:if>
</c:forEach>
</div>
</body>
</html>

