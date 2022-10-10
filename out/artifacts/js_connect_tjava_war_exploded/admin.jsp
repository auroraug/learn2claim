<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/9/5
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administrator</title>
    <link href="css/ManagementMainStyle.css" type="text/css" rel="stylesheet">
    <link href="css/TableStyle.css" type="text/css" rel="stylesheet">
    <jsp:include page="/index.jsp"/>
</head>

<body>
<div class="Body" >
    <div class="leftBox">
        <ul>
            <li class="negative"><a href="adminPage/adminHome.jsp">Home</a></li>
            <li><a style="background-color: #c8c8dc" href="<%=path%>/_userservlet?type=5">Verifier Management</a></li>
        </ul>
    </div>
    <div class="main">
        <div style="margin-top: 10px;text-align: center">
            <button type="button" onclick="addVerifier()">Add</button>
            <form style="display: inline" method="post" id="queryForm" action="<%=path%>/_userservlet">
                <input type="text" name="Address"  placeholder="verifier address"/>
            <%--    <input type="text" name="userName"  placeholder="姓名"/>--%>
                <%--若想在查询框中留下输入记录 加 value="${user.userId}" 以及 value = "${user.userName}"--%>
                <%--<select name="gender">
                    <option value="0">性别</option>
                    <option value="1" >男</option>
                    &lt;%&ndash;若想留下性别输入记录 加 <c:if test="${user.gender == 1}">selected</c:if> &ndash;%&gt;
                    <option value="2" >女</option>
                </select>--%>
                <input type="hidden" name="type" value="5"/>
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
                <td width="380px">address</td>
                <td width="70">角色名称</td>
                <td >操作</td>
            </tr>
            <%--    items用于接收servlet的值 --%>
            <c:forEach items="${admin}" var="l" varStatus="vs">
                <tr>
                    <input type="hidden" name="syncNum" value="${pageNum}"/>
                    <td>${vs.index + 1}</td>
                    <td>${l.address}</td>
                    <td>${l.roleName}</td>
                    <td>
        <%--                <button type="button" onclick="editVerifier('${l.address}')" >edit</button>--%>
                        <button type="button" onclick="deleteVerifier('${l.address}')" >delete</button>
                    </td>
                </tr>

            </c:forEach>
        </table>
        </div>
        <div style="margin-top: 25px;text-align: center" >
        <button type="button" onclick="changePage(-1);">上一页</button>
        <button type="button" onclick="changePage(1);">下一页</button>
        <button type="button" onclick="changePage(-100)">首页</button>
        <button type="button" onclick="changePage(100)">尾页</button>&nbsp;&nbsp;
        当前第 ${pageNum}页，共 ${totalPage}页 ，共 ${totalNum}条记录
        </div>
    </div>
</div>
</body>


</html>
<script src="https://cdn.ethers.io/lib/ethers-5.2.umd.min.js" type="application/javascript"></script>
<script>
    const sbtAbi = [{"inputs":[],"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"owner","type":"address"},{"indexed":true,"internalType":"address","name":"approved","type":"address"},{"indexed":true,"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"Approval","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"owner","type":"address"},{"indexed":true,"internalType":"address","name":"operator","type":"address"},{"indexed":false,"internalType":"bool","name":"approved","type":"bool"}],"name":"ApprovalForAll","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"previousOwner","type":"address"},{"indexed":true,"internalType":"address","name":"newOwner","type":"address"}],"name":"OwnershipTransferred","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"from","type":"address"},{"indexed":true,"internalType":"address","name":"to","type":"address"},{"indexed":true,"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"Transfer","type":"event"},{"inputs":[{"internalType":"address","name":"to","type":"address"},{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"approve","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"owner","type":"address"}],"name":"balanceOf","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"_to","type":"address"},{"internalType":"bool","name":"_mintStatus","type":"bool"}],"name":"changeMintStatus","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"getApproved","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"owner","type":"address"},{"internalType":"address","name":"operator","type":"address"}],"name":"isApprovedForAll","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"max_supply","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"name","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"owner","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"ownerOf","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"renounceOwnership","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"safeMint","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"from","type":"address"},{"internalType":"address","name":"to","type":"address"},{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"safeTransferFrom","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"from","type":"address"},{"internalType":"address","name":"to","type":"address"},{"internalType":"uint256","name":"tokenId","type":"uint256"},{"internalType":"bytes","name":"data","type":"bytes"}],"name":"safeTransferFrom","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"operator","type":"address"},{"internalType":"bool","name":"approved","type":"bool"}],"name":"setApprovalForAll","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"_verifier","type":"address"},{"internalType":"bool","name":"_permission","type":"bool"}],"name":"setVerifier","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"bytes4","name":"interfaceId","type":"bytes4"}],"name":"supportsInterface","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"symbol","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"tokenURI","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"from","type":"address"},{"internalType":"address","name":"to","type":"address"},{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"transferFrom","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"newOwner","type":"address"}],"name":"transferOwnership","outputs":[],"stateMutability":"nonpayable","type":"function"}]
    const provider = new ethers.providers.Web3Provider(window.ethereum);
    const sbt_addr="0xf86307998ABA1829530230a0852dE2C418C72D2e";


    function changePage(num) {
        document.getElementById("changeNum").value = num ;
        document.getElementById("queryForm").submit();
    }

    function addVerifier() {
        window.location.href = "<%=path%>/addVerifier.jsp"
    }

    function editVerifier(address) {
        window.location.href = "<%=path%>/_userservlet?type=12&address="+address;
    }

    async function deleteVerifier(address) {
        if(window.confirm("Are you sure delete the verifier？")){
            const signer = provider.getSigner();
            const sbt = new ethers.Contract(sbt_addr, sbtAbi, signer);
            //与合约交互；
            await  sbt.setVerifier(address,false);
            window.location.href = "<%=path%>/_userservlet?type=13&daddress="+address;
        }
        <%--window.location.href = "<%=path%>/_userservlet?type=3",params = 'userId='+userId;--%>
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