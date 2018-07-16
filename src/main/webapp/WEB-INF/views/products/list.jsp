<%--
  Created by IntelliJ IDEA.
  User: felipe
  Date: 11/07/18
  Time: 00:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="cdc" tagdir="/WEB-INF/tags" %>
<cdc:page title="Casa do Código">
    <h2>Listagem de Livros</h2>
    <p>${livro_cadastrado_sucesso}</p>
    <h4>Items no carrinho: ${shoppingCart.quantity}</h4>
    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal" var="user"/>
        <div>
            <p>Olá ${user.name}</p>
        </div>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <c:url value="/products/form" var="formLink"/>
        <p><a href="${formLink}">Cadastrar novo produto</a></p>
    </sec:authorize>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>TÍTULO</th>
                <th>DESCRIÇÃO</th>
                <th>PÁGINAS</th>
                <th>LANÇAMENTO</th>
                <th>SUMÁRIO</th>
                <th>VALORES</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${products}" var="livro">
                <tr>
                    <td>${livro.id}</td>
                    <td>
                        <c:url value="/products/${livro.id}" var="linkDetalhar"/>
                        <a href="${linkDetalhar}">${livro.title}</a>

                    </td>
                    <td>${livro.description}</td>
                    <td>${livro.numberOfPages}</td>
                    <td><fmt:formatDate value="${livro.releaseDate.time}" pattern="dd/MM/yyyy"/></td>
                    <td><a href="${livro.summaryPath}"/>Acessar</td>
                    <td>
                        <c:forEach items="${livro.prices}" var="preco">
                            <br>${preco.bookType}: ${preco.value}
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</cdc:page>