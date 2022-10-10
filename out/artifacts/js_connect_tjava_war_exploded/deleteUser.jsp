<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/8/22
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath(); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>删除用户</title>
</head>
<body>
    <button type="button" onclick="certain()" >确 定</button>
    <button type="button" onclick="cancel1()" >取 消</button>
</body>
</html>

<script>
    function certain() {
        window.location.href = "<%=path%>/_userservlet?type=3";

    }

    function cancel1() {
        window.location.href = "<%=path%>/_userservlet?type=0";

    }
</script>