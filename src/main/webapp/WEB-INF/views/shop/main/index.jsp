<%@ page import="java.util.List" %>
<%@ page import="java.nio.file.*" %>
<%@ page import="com.nhnacademy.shoppingmall.product.domain.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    List<Product> productList = (List<Product>) session.getAttribute("productlist");
    String defaultImagePath = "/resources/images/no-image.png";
    String imageFolder = "/Users/park/Desktop/shop/java-servlet-jsp-shoppingmall/src/main/webapp";
    int itemsPerPage = 3;
    int currentPage = 1;

    String requestedPage = request.getParameter("page");
    if (requestedPage != null && requestedPage.matches("\\d+")) {
        currentPage = Integer.parseInt(requestedPage);
    }

    int startIndex = (currentPage - 1) * itemsPerPage;
    int endIndex = Math.min(startIndex + itemsPerPage, productList.size());
%>

<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
    <% for (int i = startIndex; i < endIndex; i++) {
        Product product = productList.get(i);
        String productImagePath = product.getProductimage();
        String imagePath = (Files.exists(Paths.get(imageFolder + productImagePath)))
                ? productImagePath
                : defaultImagePath;
    %>
    <div class="col">
        <div class="card shadow-sm">
            <img class="bd-placeholder-img card-img-top" src="<%= imagePath %>">
            <div class="card-body">
                <p class="card-text"><%= product.getDescription() %></p>
                <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                        <c:url var="view_link" value="/productview.do" scope="request">
                            <c:param name="id" value="<%= String.valueOf(product.getProductID()) %>" />
                        </c:url>
                        <a href="${view_link}" class="btn btn-sm btn-outline-secondary">View</a>
                        <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
                    </div>
                    <small class="text-muted"><%= product.getUnitCost() %></small>
                </div>
            </div>
        </div>
    </div>
    <% } %>
</div>

<%
    int totalPages = (int) Math.ceil((double) productList.size() / itemsPerPage);
%>

<div class="d-flex justify-content-center">
    <nav aria-label="Page navigation">
        <ul class="pagination">
            <% for (int i = 1; i <= totalPages; i++) { %>
            <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                <a class="page-link" href="?page=<%= i %>"><%= i %></a>
            </li>
            <% } %>
        </ul>
    </nav>
</div>