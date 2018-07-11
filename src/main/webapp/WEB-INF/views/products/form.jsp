<%--
  Created by IntelliJ IDEA.
  User: felipe
  Date: 10/07/18
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cadastro de Livros</title>
</head>
<body>
    <c:url value="/products" var="url" />
    <form action="${url}" method="post">
        <div>
            <label for="title">Título</label>
            <input type="text" name="title" id="title"/>
        </div>
        <div>
            <label for="description">Descrição</label>
            <textarea rows="10" cols="20" name="description" id="description"></textarea>
        </div>
        <div>
            <label for="numberOfPages">Número de Páginas</label>
            <input type="text" name="numberOfPages" id="numberOfPages"/>
        </div>
        <c:forEach items="${types}" var="bookType" varStatus="status">
            <div>
                <label for="price_${bookType}">${bookType}</label>
                <input type="text" name="prices[${status.index}].value" id="price_${bookType}"/>
                <input type="hidden" name="prices[${status.index}].bookType" value="${bookType}"/>
            </div>
        </c:forEach>
        <div>
            <button type="submit">Enviar</button>
        </div>
    </form>
</body>
</html>
