<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/9/26
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link href="../css/ManagementMainStyle.css" type="text/css" rel="stylesheet">
    <link href="../css/TableStyle.css" type="text/css" rel="stylesheet">
    <jsp:include page="../index.jsp"/>
</head>
<body>
<div class="Body" >
    <div class="leftBox">
        <ul>
            <li><a style="background-color: #c8c8dc" href="../scholarPage/scholarHome.jsp">Home</a></li>
            <li class="negative"><a href="../scholarPage/learn.jsp">Learn</a></li>
            <li class="negative"><a href="../scholarPage/myExam.jsp">Examination</a></li>
            <li class="negative"><a href="../scholarPage/claim.jsp">Claim</a></li>
        </ul>
    </div>
    <div class="main">
        <div style="text-align: center;">
            <h1>Welcome to examination system</h1>
            <h1>Learn to claim your SoulBoundToken</h1>
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