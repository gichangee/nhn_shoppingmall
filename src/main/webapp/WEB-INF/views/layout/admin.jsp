<%@ page import="com.nhnacademy.shoppingmall.product.domain.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style>
        table {
            width: 800px;
            border-collapse: collapse;
            border: 1px #ccc solid;
        }

        table tr > td, th {
            padding: 5px;
            border: 1px #ccc solid;
        }
    </style>
</head>
<body>

<form method="get" action="/productinsert.do">
    <button type="submit">상품등록</button>
</form>

<%
    List<Product> productList = (List<Product>) session.getAttribute("productlist");
    int itemsPerPage = 3;
    int currentPage = 1;
    String requestedPage = request.getParameter("page");
    if (requestedPage != null && requestedPage.matches("\\d+")) {
        currentPage = Integer.parseInt(requestedPage);
    }

    int startIndex = (currentPage - 1) * itemsPerPage;
    int endIndex = Math.min(startIndex + itemsPerPage, productList.size());
%>


<table>
    <thead>
    <tr>
        <th>상품아이디</th>
        <th>상품카테고리아이디</th>
        <th>상품모델번호</th>
        <th>상품모델이름</th>
        <th>상품가격</th>
        <th>상품설명</th>
        <th>상품조회</th>
    </tr>
    </thead>
    <tbody>

    <% for (int i = startIndex; i < endIndex; i++) {
        Product product = productList.get(i);
    %>
    <tr>
    <td><%=product.getProductID()%></td>
    <td><%=product.getCategoryID()%></td>
    <td><%=product.getModelNumber()%></td>
    <td><%=product.getModelName()%></td>
    <td><%=product.getUnitCost()%></td>
    <td><%=product.getDescription()%></td>
        <td>
            <c:url var="view_link" value="/productview.do" scope="request">
                <c:param name="id" value="<%= String.valueOf(product.getProductID()) %>" />
            </c:url>
            <a href="${view_link}" >조회</a>
        </td>
    </tr>


    <%}%>



    </tbody>
</table>
<div class="d-flex justify-content-center">
    <nav aria-label="Page navigation">
        <ul class="pagination">
            <% for (int i = 1; i <= Math.ceil((double) productList.size() / itemsPerPage); i++) { %>
            <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                <a class="page-link" href="?page=<%= i %>"><%= i %></a>
            </li>
            <% } %>
        </ul>
    </nav>
</div>

</body>
</html>
