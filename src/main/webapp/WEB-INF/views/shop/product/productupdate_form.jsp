<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.nhnacademy.shoppingmall.product.domain.Product" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <form method="post" action="/productupdateAction.do">

            <h1 class="h3 mb-3 fw-normal">상품 조회 페이지</h1>

            <%
                Product product = (Product) request.getAttribute("product");
                String defaultImagePath = "/resources/images/no-image.png";
                String imageFolder = "/Users/park/Desktop/shop/java-servlet-jsp-shoppingmall/src/main/webapp";
                String productImagePath = product.getProductimage();
                String imagePath = (Files.exists(Paths.get(imageFolder + productImagePath)))
                        ? productImagePath
                        : defaultImagePath;
            %>
            <input type="hidden" name="productimage" value="<%=product.getProductimage()%>">

            <div class="form-floating">
                <input type="text" name="productId" class="form-control" id="productId"
                       placeholder="<%=product.getProductID()%>" value="<%=product.getProductID()%>" readonly>
                <label for="productId">상품아이디</label>
            </div>


            <div class="form-floating">
                <input type="text" name="categoryId" class="form-control" id="categoryId"
                       placeholder="<%=product.getCategoryID()%>" value="<%=product.getCategoryID()%>" required>
                <label for="categoryId">카테고리아이디</label>
            </div>


            <div class="form-floating">
                <input type="text" name="ModelNumber" class="form-control" id="ModelNumber"
                       placeholder="<%=product.getModelNumber()%>" value="<%=product.getModelNumber()%>" required>
                <label for="ModelNumber">모델번호</label>
            </div>

            <div class="form-floating">
                <input type="text" name="ModelName" class="form-control" id="ModelName"
                       placeholder="<%=product.getModelName()%>" value="<%=product.getModelName()%>" required>
                <label for="ModelName">모델이름</label>
            </div>


            <img class="bd-placeholder-img card-img-top" src="<%= imagePath %>">

            <div class="form-floating">
                <input type="text" name="UnitCost" class="form-control" id="UnitCost"
                       placeholder="<%=product.getUnitCost()%>" value="<%=product.getUnitCost()%>" required>
                <label for="UnitCost">상품가격</label>
            </div>


            <div class="form-floating">
                <input type="text" name="Description" class="form-control" id="Description"
                       placeholder="<%=product.getDescription()%>" value="<%=product.getDescription()%>" required>
                <label for="Description">상품설명</label>
            </div>


            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">상품 수정</button>

        </form>

        <form action="/productdeleteAction.do" method="post">
            <input type="hidden" name="productid" value="<%=product.getProductID()%>">
            <input class="w-100 btn btn-lg btn-primary mt-3" type="submit" value="상품 삭제">
        </form>
    </div>
</div>