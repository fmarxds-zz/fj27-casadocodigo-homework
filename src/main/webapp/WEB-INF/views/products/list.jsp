<%--
  Created by IntelliJ IDEA.
  User: felipe
  Date: 11/07/18
  Time: 00:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Livros Cadastrados</title>
</head>
<body>
    <h2>Listagem de Livros</h2>
    <p>${livro_cadastrado_sucesso}</p>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>TÍTULO</th>
                <th>DESCRIÇÃO</th>
                <th>PÁGINAS</th>
                <th>VALORES</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${products}" var="livro">
                <tr>
                    <td>${livro.id}</td>
                    <td>${livro.title}</td>
                    <td>${livro.description}</td>
                    <td>${livro.numberOfPages}</td>
                    <td>
                        <c:forEach items="${livro.prices}" var="preco">
                            <br>${preco.bookType}: ${preco.value}
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>