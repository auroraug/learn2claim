<%--
  Created by IntelliJ IDEA.
  User: LEGION
  Date: 2022/8/11
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Button</title>
    <script>
        function fn() {
            var xmlHttp = new XMLHttpRequest();

            xmlHttp.onreadystatechange = function() {

                if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {


                    var data = xmlHttp.responseText;


                    document.getElementById("span1").innerHTML=data;

                }

            }

            /*//3.绑定服务器地址

            //第一个参数：请求方式GET/POST

            //第二个参数：后台服务器地址

            //第三个参数：是否是异步 true--异步   false--同步*/

            xmlHttp.open("GET", "${pageContext.request.contextPath}/ajaxServlet?username=zhangsan", true);

            xmlHttp.send();

        }

    </script>
</head>
<body>
<input type="button" value="异步请求服务器" onclick="fn()"><span id = <span1> </span><p></p><p></p><p>
</body>
</html>
