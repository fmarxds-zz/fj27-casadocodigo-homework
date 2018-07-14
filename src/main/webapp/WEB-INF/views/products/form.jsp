<%--
  Created by IntelliJ IDEA.
  User: felipe
  Date: 10/07/18
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Cadastro de Livros</title>
</head>
<body>
    <c:url value="/products" var="url" />
    <form:form action="${spring:mvcUrl('PC#save').build()}" method="post" commandName="product">
        <div>
            <label for="title">Título</label>
            <form:input path="title" id="title"/>
            <form:errors path="title"/>
        </div>
        <div>
            <label for="description">Descrição</label>
            <form:textarea rows="10" cols="20" path="description" id="description"/>
            <form:errors path="description"/>
        </div>
        <div>
            <label for="numberOfPages">Número de Páginas</label>
            <form:input path="numberOfPages" id="numberOfPages"/>
            <form:errors path="numberOfPages"/>
        </div>
        <div>
            <label for="releaseDate">Data de Lançamento</label>
            <form:input path="releaseDate" type="date" id="releaseDate"/>
            <form:errors path="releaseDate"/>
        </div>
        <c:forEach items="${types}" var="bookType" varStatus="status">
            <div>
                <label for="price_${bookType}">${bookType}</label>
                <form:input type="text" path="prices[${status.index}].value" id="price_${bookType}"/>
                <form:errors path="prices[${status.index}].value"/>
                <input type="hidden" name="prices[${status.index}].bookType" value="${bookType}"/>
            </div>
        </c:forEach>
        <div>
            <button type="submit">Enviar</button>
        </div>
    </form:form>
</body>
</html>
