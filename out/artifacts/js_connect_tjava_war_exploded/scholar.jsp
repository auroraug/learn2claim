<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/9/7
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Mint Page</title>
    <link href="css/ManagementMainStyle.css" type="text/css" rel="stylesheet">
    <link href="css/TableStyle.css" type="text/css" rel="stylesheet">
    <jsp:include page="/index.jsp"/>
</head>
<body>

<div class="Body">
    <div class="leftBox">
        <ul>
            <li class="negative"><a href="scholarPage/scholarHome.jsp">Home</a></li>
            <li><a style="background-color: #c8c8dc" href="<%=path%>/_userservlet?type=25">Scholar Management</a></li>
        </ul>
    </div>
    <div class="main">
        <div style="margin-top: 10px;text-align: center">
            <form method="post" id="queryForm" action="<%=path%>/_userservlet?type=32">
                <input type="hidden" id="address" name="address2" value="${address}"/>${error}
                <input type="hidden" id="mintNum" name="_mintNum" value="${mintnum}"/>
            </form>
        </div>
        <div style="overflow: auto;margin-top: 10px">
            <%--加入边框--%>
            <table border="2">
                <tr>
                    <td>SBT</td>
<%--                    <td>mintStatus</td>--%>
<%--                    <td>mintNum</td>--%>
<%--                    <td>操作</td>--%>
                </tr>
                <%--    items用于接收servlet的值 --%>
                <c:forEach items="${personal}" var="l" varStatus="vs">
                    <tr>
                        <input type="hidden" name="syncNum" value="${pageNum}"/>
                        <td><img title="头像" style="padding: 1px;" src="<c:out value="https://ipfs.filebase.io/ipfs/QmVcvHkQHkaP736SMWuaKgkeVpHYio59oeE8TAAxbk7Q4z?filename=rookie.png"></c:out>" width="300px" height="280px"></td>
                        <td hidden>${l.address}</td>
                        <td hidden>${l.status == 1 ? 'true':'false'}</td>
                        <td hidden>${l.mintnum}</td>

                    </tr>

                </c:forEach>
            </table>
                <button type="button" onclick="mintnft('${l.address}',${l.mintnum},${l.status})" >mint</button>
        </div>
<%--<button type="button" onclick="changePage(-1);">上一页</button>
<button type="button" onclick="changePage(1);">下一页</button>
<button type="button" onclick="changePage(-100)">首页</button>
<button type="button" onclick="changePage(100)">尾页</button><br>
当前第 ${pageNum}页，共 ${totalPage}页 ，共 ${totalNum}条记录--%>

</body>


</html>
<script src="https://cdn.ethers.io/lib/ethers-5.2.umd.min.js" type="application/javascript"></script>
<script>
    const sbtAbi = [{"inputs":[],"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"owner","type":"address"},{"indexed":true,"internalType":"address","name":"approved","type":"address"},{"indexed":true,"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"Approval","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"owner","type":"address"},{"indexed":true,"internalType":"address","name":"operator","type":"address"},{"indexed":false,"internalType":"bool","name":"approved","type":"bool"}],"name":"ApprovalForAll","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"previousOwner","type":"address"},{"indexed":true,"internalType":"address","name":"newOwner","type":"address"}],"name":"OwnershipTransferred","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"from","type":"address"},{"indexed":true,"internalType":"address","name":"to","type":"address"},{"indexed":true,"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"Transfer","type":"event"},{"inputs":[{"internalType":"address","name":"to","type":"address"},{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"approve","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"owner","type":"address"}],"name":"balanceOf","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"_to","type":"address"},{"internalType":"bool","name":"_mintStatus","type":"bool"}],"name":"changeMintStatus","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"string","name":"_uri","type":"string"}],"name":"changeuriIpfs","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"getApproved","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"owner","type":"address"},{"internalType":"address","name":"operator","type":"address"}],"name":"isApprovedForAll","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"max_supply","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"name","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"owner","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"ownerOf","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"renounceOwnership","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"safeMint","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"from","type":"address"},{"internalType":"address","name":"to","type":"address"},{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"safeTransferFrom","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"from","type":"address"},{"internalType":"address","name":"to","type":"address"},{"internalType":"uint256","name":"tokenId","type":"uint256"},{"internalType":"bytes","name":"data","type":"bytes"}],"name":"safeTransferFrom","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"operator","type":"address"},{"internalType":"bool","name":"approved","type":"bool"}],"name":"setApprovalForAll","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"_verifier","type":"address"},{"internalType":"bool","name":"_permission","type":"bool"}],"name":"setVerifier","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"bytes4","name":"interfaceId","type":"bytes4"}],"name":"supportsInterface","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"symbol","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"tokenURI","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"from","type":"address"},{"internalType":"address","name":"to","type":"address"},{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"transferFrom","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"newOwner","type":"address"}],"name":"transferOwnership","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"uriIpfs","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"}]
    const provider = new ethers.providers.Web3Provider(window.ethereum);
    const sbt_addr="0xf86307998ABA1829530230a0852dE2C418C72D2e";


    function changePage(num) {
        document.getElementById("changeNum").value = num ;
        document.getElementById("queryForm").submit();
    }

    async function mintnft(address,mintNum,status) {
        if(status == 1 && mintNum < 1){

            const signer = provider.getSigner();
            const sbt = new ethers.Contract(sbt_addr, sbtAbi, signer);
            //与合约交互；
            await  sbt.safeMint();

            window.location.href = "<%=path%>/_userservlet?type=32&address="+address+"&mintnum=1";
            alert("Congratulations!")

        }
        else alert("You are not in whitelist or not verification!")
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
            document.getElementById("address").value = acc
        }


    }
    window.onunload = connect();

    ethereum.on('accountsChanged',(accounts) => {
        window.location.href = "<%=path%>/_logoutservlet"
        console.log("wallet have been changed!")
    })
    ethereum.on('networkChanged',(network) => {
        connect();
    })
</script>