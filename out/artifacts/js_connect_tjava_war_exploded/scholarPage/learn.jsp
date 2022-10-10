<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/9/30
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<html>
<head>
    <title>learning...</title>
    <link href="../css/ManagementMainStyle.css" type="text/css" rel="stylesheet">
    <link href="../css/TableStyle.css" type="text/css" rel="stylesheet">
    <jsp:include page="../index.jsp"/>
</head>
<script type="text/javascript">
    function onLoad() {
        //为奇数、偶数的表格行添加className
        var tables = document.getElementsByClassName('table')
        for(var i=0;i<tables.length;i++){
            var rows = tables[i].getElementsByTagName("tr");
            for (var j = 0; j< rows.length; j++) {
                if (j % 2 == 0) {
                    rows[j].className = "evenrow";
                } else {
                    rows[j].className = "oddrow";
                }
            }
        }
        var message = '<%=request.getParameter("message")%>';
        if (message=="success"){
            alert("交卷成功！");
            window.location.href='myExam.jsp';
        }
        else if (message=="fail"){
            alert("交卷失败，请联系管理员");
            window.location.href='myExam.jsp';
        }
    }
</script>

<body onload="onLoad();">
<sql:setDataSource user="root" password="123456" url="jdbc:mysql://os01:3306/userdb?characterEncoding=utf-8" driver="com.mysql.jdbc.Driver" var="userdb"/>
<!--查询数据库中的数据，做信息显示-->
<!--行数代表全部考试的数目（包括已考和未考）-->
<sql:query var="total" scope="page" dataSource="${userdb}">
    SELECT * from readInfo;
</sql:query>
<!--行数代表未考试的数目-->
<sql:query var="unfinished" scope="page" dataSource="${userdb}">
    SELECT * from readInfo where id NOT in (SELECT id FROM readrecord WHERE address=?)
    <sql:param value="${addr}"></sql:param>
</sql:query>
<!--行数代表完成的数目-->
<sql:query var="finishedRecord" scope="page" dataSource="${userdb}">
    select * from readrecord WHERE address=?;
    <sql:param value="${addr}"></sql:param>
</sql:query>


<div class="Body" >
    <div class="leftBox">
        <ul>
            <li class="negative"><a href="../scholarPage/scholarHome.jsp">Home</a></li>
            <li><a style="background-color: #c8c8dc" href="../scholarPage/learn.jsp">Learn</a></li>
            <li class="negative"><a href="../scholarPage/myExam.jsp">Examination</a></li>
            <li class="negative"><a href="../scholarPage/claim.jsp">Claim</a></li>
        </ul>
    </div>
    <div class="main">
        <div style="text-align: center;">
            <h4 style="text-align: center;">您有<c:out value="${total.rowCount}"></c:out>篇需要阅读，其中未读<c:out value="${unfinished.rowCount}"></c:out>个 ，已读<c:out value="${finishedRecord.rowCount}"></c:out> </h4>
            <div id="unfinished" style="height: 363px;overflow: auto;">

                <table class="table" style="margin: 0 auto;">
                    <tr><th width="100px">id</th><th width="200px">description</th><th width="100px" style="text-align: center;">learn</th></tr>
                    <c:if test="${not empty unfinished}">
                        <c:forEach var="row" items="${unfinished.rows}" varStatus="status">
                            <tr>
                                <td><c:out value="${status.count}"></c:out></td>
                                <td><c:out value="${row.description}"></c:out></td>
                                <td style="text-align: center;"><form action="<%=path%>/_readservlet" method="get"> <input type="hidden" name="exam_description_id" value="<c:out value="${row.id}"></c:out>">  <input type="hidden" name="exam_description" value="<c:out value="${row.description}"></c:out>">   <input type="submit" value="learn"></form></td>
                            </tr>
                        </c:forEach>
                    </c:if>
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
