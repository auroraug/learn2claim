<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/9/26
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Examination Management</title>
    <link href="../css/ManagementMainStyle.css" type="text/css" rel="stylesheet">
    <link href="../css/TableStyle.css" type="text/css" rel="stylesheet">
    <jsp:include page="../index.jsp"/>
</head>
<body>
<div class="Body" >
    <div class="leftBox">
        <ul>
            <li class="negative"><a href="../verifierPage/verifierHome.jsp">Home</a></li>
            <li class="negative"><a href="<%=path%>/_userservlet?type=15">Scholar Management</a></li>
            <li><a style="background-color: #c8c8dc" href="<%=path%>/_examservlet?type=4">Exam Management</a></li>
            <li class="negative"><a href="../verifierPage/gradeManagement.jsp">Grade Management</a></li>
        </ul>
    </div>
    <div class="main">
        <div style="margin-top: 10px;text-align: center">
            <button type="button" onclick="addExam()">AddExam</button>
            <form style="display: inline" method="post" id="queryForm" action="<%=path%>/_examservlet">
                <input type="text" name="ExamId"  placeholder="examination id"/>
                <input type="text" name="description"  placeholder="description"/>
                <%--若想在查询框中留下输入记录 加 value="${user.userId}" 以及 value = "${user.userName}"--%>

                <input type="hidden" name="type" value="4"/>
                <input type="hidden" name="pageNum" value="${pageNum}"/>
                <input type="hidden" name="changeNum" id="changeNum" />
                <button onclick="changePage(0)">查 询</button>
            </form>
        </div>


        <div style="overflow: auto;margin-top: 10px">
            <%--加入边框--%>
            <table style="margin: 0 auto">
                <tr>
                    <td width="70">序号</td>
                    <td width="70">Exam Id</td>
                    <td>description</td>
                    <td width="70">time</td>
                    <td>操作</td>
                </tr>
                <%--    items用于接收servlet的值 --%>
                <c:forEach items="${exam}" var="l" varStatus="vs">
                    <tr>
                        <input type="hidden" name="syncNum" value="${pageNum}"/>
                        <td>${vs.index + 1}</td>
                        <td>${l.examId}</td>
                        <td>${l.description}</td>
                        <td>${l.time}</td>
                        <td>
                            <button type="button" onclick="editExam('${l.examId}')" >edit</button>
                            <button type="button" onclick="deleteExam('${l.examId}')" >delete</button>
                        </td>
                    </tr>

                </c:forEach>
            </table>
        </div>
        <div style="margin-top: 25px;text-align: center" >
            <button type="button" onclick="changePage(-1);">上一页</button>
            <button type="button" onclick="changePage(1);">下一页</button>
            <button type="button" onclick="changePage(-100)">首页</button>
            <button type="button" onclick="changePage(100)">尾页</button><br>
            当前第 ${pageNum}页，共 ${totalPage}页 ，共 ${totalNum}条记录
        </div>
        <div style="height: 480px;">
            <table style="text-align: center;margin: 0 auto;">
                <tr><th style="width: 300px;text-align: center;">请下载试题模板，根据模板的格式添加试题</th><td width="120px"><input type="button" onclick="downloadQuestionsTemplate();" value="下载模板"></td></tr>
                <tr><td colspan="2"><p style="text-align: left;padding-left: 20px;">模板说明：question是问题的描述，options是选项（用@分隔开）<br>,correct_answer是正确答案，min_score是占的分值，type是题<br>目的类型（只能是judgement或choice）</p></td></tr>
            </table>

            <form action="<%=path%>/_questionsupload" method="post" onsubmit="return checkForm();" enctype="multipart/form-data">
                <table style="text-align: center;margin: 15px auto;">
                    <tr><th style="width: 120px;text-align: center;">文件</th><th style="width: 120px;text-align: center;">examId</th><th style="width: 120px;text-align: center;">description</th><th style="width: 120px;text-align: center;">time(minute)</th></th><th style="width: 120px;text-align: center;">管理</th></tr>
                    <tr><td><input id="file" type="file" accept=".xlsx,.xls" name="excelFile" style="width: 190px;"></td><td><input id="exam_description_id" type="text" name="exam_description_id" style="width: 120px;"> </td>
                        <td><input id="exam_description" type="text" name="exam_description" style="width: 120px;"> </td><td><input id="exam_time" type="text" name="exam_time" style="width: 120px;"> </td><td><input type="submit" value="发布试题" name="release"></td></tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>
<script src="https://cdn.ethers.io/lib/ethers-5.2.umd.min.js" type="application/javascript"></script>
<script>

    function changePage(num) {
        document.getElementById("changeNum").value = num ;
        document.getElementById("queryForm").submit();
    }

    function addExam() {
        window.location.href = "<%=path%>/addExam.jsp"
    }

    function editExam(examid) {
        window.location.href = "<%=path%>/_examservlet?type=2&examid="+examid;
    }

    function checkForm() {
        var file = document.getElementById('file');
        if (!file.value) {
            alert('请选择信息文件！');
            return false;
        }
        return true;
    }

    function downloadQuestionsTemplate() {
        window.location.href = '/excelfile/question_template.xlsx';
    }

    function deleteExam(examid) {
        if(window.confirm("Are you sure delete the scholar？")){
            window.location.href = "<%=path%>/_examservlet?type=3&dexamid="+examid;

        }
    }

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