<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/9/27
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Grade Management</title>
    <link href="../css/ManagementMainStyle.css" type="text/css" rel="stylesheet">
    <link href="../css/TableStyle.css" type="text/css" rel="stylesheet">
    <jsp:include page="../index.jsp"/>
</head>
<script type="text/javascript">
    function onLoad() {
        //为奇数、偶数的表格行添加className
        var tables = document.getElementsByClassName('table')
        for (var i = 0; i < tables.length; i++) {
            var rows = tables[i].getElementsByTagName("tr");
            for (var j = 0; j < rows.length; j++) {
                if (j % 2 == 0) {
                    rows[j].className = "evenrow";
                } else {
                    rows[j].className = "oddrow";
                }
            }
        }
    }
</script>
<body onload="onLoad();">
<sql:setDataSource user="root" password="123456" url="jdbc:mysql://os01:3306/userdb?characterEncoding=utf-8" driver="com.mysql.jdbc.Driver" var="userdb"/>
<div class="Body" >
    <div class="leftBox">
        <ul>
            <li class="negative"><a href="../verifierPage/verifierHome.jsp">Home</a></li>
            <li class="negative"><a href="<%=path%>/_userservlet?type=15">Scholar Management</a></li>
            <li class="negative"><a href="<%=path%>/_examservlet?type=4">Exam Management</a></li>
            <li><a style="background-color: #c8c8dc" href="../verifierPage/gradeManagement.jsp">Grade Management</a></li>
        </ul>
    </div>
    <div class="main">
        <sql:query var="exams_description1" dataSource="${userdb}" scope="page">
            select * from exam_description order by id;
        </sql:query>
        <h4 style="text-align: center;">已发布的试题&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总数：<c:out value="${exams_description1.rowCount}"></c:out>个</h4>
        <div style="overflow: auto;height: 220px;">
            <table class="table" style="margin: 0 auto;text-align: center;">
                <tr><th style="width: 142px;text-align: center;">索引（唯一自增）</th><th style="width: 142px;text-align: center;">试题描述</th><th style="width: 142px;text-align: center;">考试时间(分钟)</th><th style="width: 60px;text-align: center;">管理</th></tr>
                <c:forEach var="row" items="${exams_description1.rows}">
                    <tr>
                        <td><c:out value="${row.id}"></c:out></td>
                        <td><c:out value="${row.exam_description}"></c:out></td>
                        <td><c:out value="${row.exam_time}"></c:out></td>
                        <td>
                            <form action="../verifierPage/gradeManagement.jsp" method="get">
                                <input type="hidden" value="<c:out value="${row.id}"></c:out>" name="exam_description_id">
                                <input type="hidden" value="<c:out value="${row.exam_description}"></c:out>" name="exam_description">
                                <input type="submit" value="查询">
                            </form>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <h4 style="text-align: center;">查询结果</h4>
        <!--这里是处理根据试题号索引唯一索引ID查询数据库的统计数据做查询结果显示的-->
        <c:if test="${not empty param.exam_description_id}">
            <!--查询已完成考试的成绩统计-->
            <sql:query var="statistic" scope="page" dataSource="${userdb}">
                SELECT examrecord.exam_description_id,exam_description,total_score,MAX(obtain_score) maximum,
                MIN(obtain_score) minimum,AVG(obtain_score) average FROM examrecord,exam_description where
                examrecord.exam_description_id=exam_description.id group by exam_description_id,
                exam_description,total_score having exam_description_id=?;
                <sql:param value="${param.exam_description_id}"></sql:param>
            </sql:query>
            <!--查询已完成考试的考生得分-->
            <sql:query var="exam_finished_info" scope="page" dataSource="${userdb}">
                SELECT scholarInfo.address,exam_description,total_score,obtain_score
                FROM scholarInfo, exam_description,examrecord where scholarInfo.address=examrecord.address
                and exam_description.id=examrecord.exam_description_id and examrecord.exam_description_id=?
                order by scholarInfo.address;
                <sql:param value="${param.exam_description_id}"></sql:param>
            </sql:query>
        </c:if>
        <div style="height: 520px;overflow: auto;">
            <table class="table" style="margin: 0 auto;text-align: center;">
                <tr><th style="width: 120px;text-align: center;">索引（唯一自增）</th><th style="width: 120px;text-align: center;">试题描述</th><th style="width: 60px;text-align: center;">总分</th><th style="width: 60px;text-align: center;">最高分</th><th style="width: 60px;text-align: center;">最低分</th><th style="width: 60px;text-align: center;">平均分</th></tr>
                <c:forEach var="row" items="${statistic.rows}">
                    <tr><td><c:out value="${row.exam_description_id}"></c:out></td><td><c:out value="${row.exam_description}"></c:out></td><td><c:out value="${row.total_score}"></c:out></td><td><c:out value="${row.maximum}"></c:out></td><td><c:out value="${row.minimum}"></c:out></td><td><c:out value="${row.average}"></c:out></td></tr>
                </c:forEach>
            </table>
            <table class="table" style="margin: 20px auto;text-align: center;">
                <tr><th style="width: 120px;text-align: center;">addresss</th><th style="width: 120px;text-align: center;">description</th><th style="width: 60px;text-align: center;">TotalPoints</th><th style="width: 60px;text-align: center;">Points</th></tr>
                <c:forEach var="row" items="${exam_finished_info.rows}">
                    <tr><td><c:out value="${row.address}"></c:out></td><td><c:out value="${row.exam_description}"></c:out></td><td><c:out value="${row.total_score}"></c:out></td><td><c:out value="${row.obtain_score}"></c:out></td></tr>
                </c:forEach>
            </table>
        </div>
        </div>
    </div>
</div>
</body>
</html>
<script src="https://cdn.ethers.io/lib/ethers-5.2.umd.min.js" type="application/javascript"></script>
<script>

    async function connect() {
        const provider = new ethers.providers.Web3Provider(window.ethereum);
        provider.provider.selectedAddress

        const accounts = await provider.send("eth_requestAccounts",[]);
        const provider1 = new ethers.providers.Web3Provider(window["ethereum"]);
        const signer = await provider1.getSigner();
        console.log("signer", signer);
        if(accounts[0]){
            let acc = accounts[0];
            let network = await provider.getNetwork();
            let networkName = ""
            if (network.chainId == 80001){
                networkName = "Polygon Mumbai"
            }else networkName = "Wrong Network,please change to Polygon Mumbai"
            const accfront = acc.slice(0,6)
            const accbehind = acc.slice(-4)
            document.getElementById("Accountsvalue").innerHTML = accfront+"..."+accbehind
            document.getElementById("Accountsbutton").innerHTML = "已连接"
            document.getElementById("network").innerHTML = networkName
            // document.getElementById("address").value = acc
        }
    }
    window.onunload = connect();

    ethereum.on('accountsChanged',(accounts) => {
        window.location.href = "<%=path%>/_logoutservlet"
    })
    ethereum.on('networkChanged',(network) => {
        connect();
    })
</script>