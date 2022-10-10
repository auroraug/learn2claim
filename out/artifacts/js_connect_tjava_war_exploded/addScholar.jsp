<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/9/6
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Scholar</title>
</head>
<body>
<form id="form" method="post" action="<%=path%>/_userservlet">
    <input type="hidden" value="${flag}" id="flag" name="flag">
    <input type="hidden" value="21" name="type" />
    <%--为了避免功能以及数据串用（即需区分查询和添加或编辑）add表单的name与查询表单的name不能相同 add表单的name前面加下划线例如"_userId"--%>
    address：<input type="text" id="address" name="_address" value="${address}"/><br>
    status：<input type="radio" id="status1" name="_status1" value="1"<c:if test="${scholar.status == 1}">checked</c:if>/> true
    <input type="radio" id="status2" name="_status2" value="0"<c:if test="${scholar.status == 0}">checked</c:if>/> false <br>
    mintNum：<input type="text" id="mintnum" name="_mintnum" value="${mintnum}"/><br>
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

<script src="https://cdn.ethers.io/lib/ethers-5.2.umd.min.js" type="application/javascript"></script>

<script>
    const sbtAbi = [{"inputs":[],"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"owner","type":"address"},{"indexed":true,"internalType":"address","name":"approved","type":"address"},{"indexed":true,"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"Approval","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"owner","type":"address"},{"indexed":true,"internalType":"address","name":"operator","type":"address"},{"indexed":false,"internalType":"bool","name":"approved","type":"bool"}],"name":"ApprovalForAll","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"previousOwner","type":"address"},{"indexed":true,"internalType":"address","name":"newOwner","type":"address"}],"name":"OwnershipTransferred","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"internalType":"address","name":"from","type":"address"},{"indexed":true,"internalType":"address","name":"to","type":"address"},{"indexed":true,"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"Transfer","type":"event"},{"inputs":[{"internalType":"address","name":"to","type":"address"},{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"approve","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"owner","type":"address"}],"name":"balanceOf","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"_to","type":"address"},{"internalType":"bool","name":"_mintStatus","type":"bool"}],"name":"changeMintStatus","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"string","name":"_uri","type":"string"}],"name":"changeuriIpfs","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"getApproved","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"owner","type":"address"},{"internalType":"address","name":"operator","type":"address"}],"name":"isApprovedForAll","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"max_supply","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"name","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"owner","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"ownerOf","outputs":[{"internalType":"address","name":"","type":"address"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"renounceOwnership","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"safeMint","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"from","type":"address"},{"internalType":"address","name":"to","type":"address"},{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"safeTransferFrom","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"from","type":"address"},{"internalType":"address","name":"to","type":"address"},{"internalType":"uint256","name":"tokenId","type":"uint256"},{"internalType":"bytes","name":"data","type":"bytes"}],"name":"safeTransferFrom","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"operator","type":"address"},{"internalType":"bool","name":"approved","type":"bool"}],"name":"setApprovalForAll","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"_verifier","type":"address"},{"internalType":"bool","name":"_permission","type":"bool"}],"name":"setVerifier","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"bytes4","name":"interfaceId","type":"bytes4"}],"name":"supportsInterface","outputs":[{"internalType":"bool","name":"","type":"bool"}],"stateMutability":"view","type":"function"},{"inputs":[],"name":"symbol","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"tokenURI","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"},{"inputs":[{"internalType":"address","name":"from","type":"address"},{"internalType":"address","name":"to","type":"address"},{"internalType":"uint256","name":"tokenId","type":"uint256"}],"name":"transferFrom","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[{"internalType":"address","name":"newOwner","type":"address"}],"name":"transferOwnership","outputs":[],"stateMutability":"nonpayable","type":"function"},{"inputs":[],"name":"uriIpfs","outputs":[{"internalType":"string","name":"","type":"string"}],"stateMutability":"view","type":"function"}]
    const provider = new ethers.providers.Web3Provider(window.ethereum);
    const sbt_addr="0xf86307998ABA1829530230a0852dE2C418C72D2e";

    async function add() {
        var flag = document.getElementById("flag").value;
        var address = document.getElementById("address").value;
        let str = ""

        if(document.getElementById("status1").value != null && document.getElementById("status1").value != ""){
            str = "true";
        }
        if (document.getElementById("status2").value != null && document.getElementById("status2").value != ""){
            str = "false";
        }

        // var roleId = document.getElementById("roleId").value;
        if(address == null || address == ''){
            alert("请输入钱包地址！");
            return;
        }
        /*        if(roleId == null || roleId == ''){
                    alert("请选择角色！");
                    return;
                }*/
        var ret ;
        if(flag != "1"){
            //判断用户名是否重复，避免页面多次跳转繁杂这里用 ajax
            //ajax
            //步骤是先判断用户id是否存在，若不存在就submit，若存在则return，有一个等待过程因此需要使用同步而不是异步
            var url = '<%=path%>/_userservlet?type=24',params='address='+address;
            ret = getDataByAjax(url,params);
        }else {
            ret = "1";
        }

        //submit
        if(ret == "1"){
            const signer = provider.getSigner();
            const sbt = new ethers.Contract(sbt_addr, sbtAbi, signer);
            //与合约交互；
            await  sbt.changeMintStatus(address,str);

            document.getElementById("form").submit();
            alert("succeed!");
        }

        else {
            alert("The address already exists！");
            return;
        }
        //return
    }

    function cancel() {
        window.location.href = "<%=path%>/_userservlet?type=15";
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