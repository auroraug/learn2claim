<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/8/14
  Time: 23:27
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户管理</title>
</head>

    <body>
    <jsp:include page="/index.jsp"/>
    <%--在userList.jsp页面中插入index.jsp页面--%>
    <button type="button" onclick="addUser()">添 加</button>
    <form method="post" id="queryForm" action="<%=path%>/_userservlet">
        <input type="text" name="userId"  placeholder="用户ID"/>
        <input type="text" name="userName"  placeholder="姓名"/>
        <%--若想在查询框中留下输入记录 加 value="${user.userId}" 以及 value = "${user.userName}"--%>
        <select name="gender">
            <option value="0">性别</option>
            <option value="1" >男</option>
            <%--若想留下性别输入记录 加 <c:if test="${user.gender == 1}">selected</c:if> --%>
            <option value="2" >女</option>
        </select>
        <input type="hidden" name="type" value="0"/>
        <input type="hidden" name="pageNum" value="${pageNum}"/>
        <input type="hidden" name="changeNum" id="changeNum" />
        <button onclick="changePage(0)">查 询</button>
    </form>
    <%--加入边框--%>
    <table border="2">
        <tr>
            <td>序号</td>
            <td>用户名ID</td>
            <td>姓名</td>
            <td>性别</td>
            <td>角色名称</td>
            <td>操作</td>
        </tr>
        <%--    items用于接收servlet的值 --%>
        <c:forEach items="${userList}" var="l" varStatus="vs">
            <tr>
                <input type="hidden" name="syncNum" value="${pageNum}"/>
                <td>${vs.index + 1}</td>
                <td>${l.userId}</td>
                <td>${l.userName}</td>
                <td>${l.gender == 1 ? '男':'女'}</td>
                <td>${l.roleName}</td>
                <td>
                    <button type="button" onclick="editUser('${l.userId}')" >编 辑</button>
                    <button type="button" onclick="deleteUser('${l.userId}')" >删 除</button>
                </td>
            </tr>

        </c:forEach>
    </table>
    <button type="button" onclick="changePage(-1);">上一页</button>
    <button type="button" onclick="changePage(1);">下一页</button>
    <button type="button" onclick="changePage(-100)">首页</button>
    <button type="button" onclick="changePage(100)">尾页</button><br>
    当前第 ${pageNum}页，共 ${totalPage}页 ，共 ${totalNum}条记录

    </body>


</html>

<script>
    function changePage(num) {
        document.getElementById("changeNum").value = num ;
        document.getElementById("queryForm").submit();
    }

    function addUser() {
        window.location.href = "<%=path%>/addUser.jsp"
    }

    function editUser(userId) {
        window.location.href = "<%=path%>/_userservlet?type=2&userId="+userId;
    }

    function deleteUser(userId) {
        if(window.confirm("您确定要删除此条记录吗？")){
            window.location.href = "<%=path%>/_userservlet?type=3&duserId="+userId;

        }
        <%--window.location.href = "<%=path%>/_userservlet?type=3",params = 'userId='+userId;--%>
    }

</script>