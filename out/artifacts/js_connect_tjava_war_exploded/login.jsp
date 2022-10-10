<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/8/11
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath(); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>系统登录</title>
</head>
<link rel="stylesheet" href="css/login.css" type="text/css">

<body>


<div id="Accframe" class="Cn" style="border:2px solid #6dc3bb; border-radius: 20px;width: 230px;
        height: 40px; padding: 0 10px; display: flex; flex-direction: row; justify-content: space-between;line-height: 40px;">
    <div id="Accountsvalue" style="font-size: 13px;">钱包未连接，请先登录</div>
    <button id="Accountsbutton" onclick="connect()" style="margin-top: 3px; height: 34px; width: 80px; border-radius: 10px; font-size: 14px;
    background: linear-gradient(to right,#ead6ee,#a0f1ea);border: 0px; color: rgb(0, 0, 0)">Connnect Wallet</button>

</div>
<div id="network" hidden class="net">网络</div>
<%--<div class="main">
    <form method="post" id="form" action="<%=path%>/loginServlet">
        <tr><td><div class="info"><strong>Login</strong></div></td></tr><br>
        <tr>
            <td height="20"><img src="image/UserNameIcon.png" width="25px" height="25px" ></td>
            <td><input type = "text" id = "username" name="username" value="${username}" placeholder="username"></td>
        </tr>
        <br><br>
        <tr>
            <td height="20"><img src="image/UserPasswordIcon.png" width="25px" height="25px" ></td>
            <td><input type="password" id="password" name="password" placeholder="password" /></td>
        </tr>
        <br><br>
        <span>&nbsp&nbsp&nbsp&nbsp&nbsp</span><input class="button" type="button"  name="loginButton" onclick="loginVerify()" value="登 录">
        <span>&nbsp&nbsp&nbsp&nbsp&nbsp</span><input class="button" type="reset" name="reset" value="重 置">

</form>
</div>--%>
<div class="login_box">
<form style="display: inline"  method="post" id="form1" action="<%=path%>/_adminservlet">
    <input type="hidden" id="address" name="address" value="">
    <input type="hidden" id="networkname" name="networkname" value="">
    <input type="button" class="btn" id="gly" onclick="admin()" value="管理员登录前请先连接钱包">
    <br>${error}
</form>


    <form style="display: inline"  method="post"  id="form2"  action="<%=path%>/_verifierservlet">
        <input type="hidden" id="address1" name="address1" value="">
        <input type="hidden" id="networkname1" name="networkname1" value="">
        <input type="button" class="btn" id="yzz" onclick="verifier()" value="验证者登录前请先连接钱包">
        <br>${error1}
    </form>

    <form style="display: inline"  method="post"  id="form3"  action="<%=path%>/_scholarservlet">
        <input type="hidden" id="address2" name="address2" value="${acc}">
        <input type="hidden" id="networkname2" name="networkname2" value="">
        <input type="button" class="btn" id="xz" onclick="scholar()" value="学者登录前请先连接钱包">
        <br>${error2}
    </form>
</div>
<%--<div>
    <video  autoplay="autoplay" loop class="fillWidth" muted='muted' >
        <source src="video/hacker.mp4" type="video/mp4"/>
    </video>
</div>--%>



</body>
</html>
<script src="https://cdn.ethers.io/lib/ethers-5.2.umd.min.js" type="application/javascript"></script>


<script>
    function loginVerify(){
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;
        if(username == ""){
            alert('用户名不能为空!')
            return;
        }
        if(password == ""){
            alert('密码不能为空!')
            return;
        }
        //调用后端servlet ，并将数据进行传递
        document.getElementById("form").submit();
    }

    function admin(){
        var _address = document.getElementById("address").value;
        console.log(_address)
        if(_address == ""){
            alert("钱包未连接")
            return;
        }
        document.getElementById("form1").submit();
    }

    function verifier(){
        var _address = document.getElementById("address").value;
        if(_address == ""){
            alert("钱包未连接")
            return;
        }
        document.getElementById("form2").submit();

    }

    function scholar(){
        var _address = document.getElementById("address").value;
        if(_address == ""){
            alert("钱包未连接")
            return;
        }
        document.getElementById("form3").submit();

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
            const accfront = acc.slice(0,4)
            const accbehind = acc.slice(-4)
            document.getElementById("Accountsvalue").innerHTML = accfront+"..."+accbehind
            document.getElementById("Accframe").style.width = "180px"
            document.getElementById("Accountsbutton").innerHTML = "已连接"
            document.getElementById("network").innerHTML = networkName
            document.getElementById("address").value = acc
            document.getElementById("networkname").value = networkName
            document.getElementById("gly").value = "admin登录"
            document.getElementById("yzz").value = "verifier登录"
            document.getElementById("xz").value = "scholar登录"
            document.getElementById("address1").value = acc
            document.getElementById("address2").value = acc
            document.getElementById("networkname1").value = networkName
            document.getElementById("networkname2").value = networkName
        }
    }
        /*window.onbeforeunload = function(){
            console.log('页面刷新之前触发');
        }

        window.onunload = function(){
            console.log('页面刷新完成触发');}*/


    ethereum.on('accountsChanged',(accounts) => {
        connect();
    })
    ethereum.on('networkChanged',(network) => {
        connect();
    })

</script>
