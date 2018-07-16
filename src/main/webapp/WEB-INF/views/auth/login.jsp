<%--
  Created by IntelliJ IDEA.
  User: felipe
  Date: 15/07/18
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Casa do Código</title>
</head>
<body>
    <h3>Efetue login</h3>
    <form:form servletRelativeAction="/login">
        <table>
            <tr>
                <td>Login:</td>
                <td><input type="text" name="username" value=""/></td>
            </tr>
            <tr>
                <td>Senha:</td>
                <td><input type="password" name="password" value=""/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input name="submit" type="submit" value="Logar"/>
                </td>
            </tr>
        </table>
    </form:form>
</body>
</html>
