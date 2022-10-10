<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/9/27
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Exam</title>
</head>
<body>
<form id="form" method="post" action="<%=path%>/_examservlet">
    <input type="hidden" value="${flag}" id="flag" name="flag">
    <input type="hidden" value="1" name="type" />
    <%--为了避免功能以及数据串用（即需区分查询和添加或编辑）add表单的name与查询表单的name不能相同 add表单的name前面加下划线例如"_userId"--%>
    examId：<input type="text" id="examId" name="_examid" value="${examId}"/><br>
    description：<input type="text" id="description" name="_description" value="${description}"/><br>
    time: <input type="text" id="time" name="_time" value="${time}"/><br>
    <%--登录密码：<input type="password" id="userPassword" name="_userPassword" value="${user.userPassword}"/><br>
        用户姓名：<input type="text" id="userName" name="_userName" value="${user.userName}"/><br>
        角色：<select id="roleId" name="_roleId">
        <option value="">请选择...</option>
        <c:forEach items="${roleList}" var="r" >
            <option value="${r.roleId}"<c:if test="${user.roleId == r.roleId}">selected</c:if>>${r.roleName}</option>
        </c:forEach>
    </select><br>--%>
    <button type="button" onclick="add()" >Save</button>
    <button type="button" onclick="cancel()" >Cancel</button>
</form>
</body>
</html>
<script>
    function add() {
        var flag = document.getElementById("flag").value;
        var examId = document.getElementById("examId").value;
        var description = document.getElementById("description").value;
        var time = document.getElementById("time").value;
        if(examId == null || examId == ''){
            alert("请输入编号！");
            return;
        }
        if(description == null || description == ''){
            alert("请输入描述！");
            return;
        }
        if(time == null || time == ''){
            alert("请选择时间！");
            return;
        }
        var ret ;
        if(flag != "1"){
            //判断用户名是否重复，避免页面多次跳转繁杂这里用 ajax
            //ajax
            //步骤是先判断用户id是否存在，若不存在就submit，若存在则return，有一个等待过程因此需要使用同步而不是异步
            var url = '<%=path%>/_examservlet?type=5',params='examId='+examId;
            ret = getDataByAjax(url,params);
        }else {
            ret = "1";
        }

        //submit
        if(ret == "1"){
            document.getElementById("form").submit();
        }else {
            alert("The examId already exists！");
            return;
        }
        //return
    }



    function cancel() {
        window.location.href = "<%=path%>/_examservlet?type=4";
    }


    function getDataByAjax(url,params) {
        var ajaxObj = null;
        //判断用户使用的浏览器是低版本还是高版本
        //低版本
        if(window.ActiveXObject) {
            ajaxObj = new ActiveXObject("Microsoft.XMLHTTP");
        }
        //高版本
        else {
            ajaxObj = new XMLHttpRequest();
        }
        ajaxObj.open('POST',url,false); //false同步 true异步
        ajaxObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");//post
        ajaxObj.send(params);
        return ajaxObj.responseText;
    }
</script>