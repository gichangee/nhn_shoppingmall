<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.nhnacademy.shoppingmall.product.domain.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style="margin: auto; width: 400px;">
  <div class="p-2">
    <form method="post" action="/productinsertAction.do" enctype="multipart/form-data">

      <h1 class="h3 mb-3 fw-normal">상품 등록 페이지</h1>

      <div class="form-floating">
        <input type="text" name="ProductID" class="form-control" id="ProductID" placeholder="상품 아이디" required>
        <label for="ProductID">상품아이디</label>
      </div>

      <c:set var="list" value="${sessionScope.list}" />

      <div class="form-floating">
        <select name="CategoryID" class="form-control" id="CategoryID" required>
          <c:forEach var="i" items="${list}">
            <option value="${i}">${i}</option>
          </c:forEach>
        </select>
        <label for="CategoryID">카테고리아이디</label>
      </div>
      <div class="form-floating">
        <input type="text" name="ModelNumber" class="form-control" id="ModelNumber" placeholder="모델번호" required>
        <label for="ModelNumber">모델번호</label>
      </div>

      <div class="form-floating">
        <input type="text" name="ModelName" class="form-control" id="ModelName" placeholder="모델이름" required>
        <label for="ModelName">모델이름</label>
      </div>


      <div class="form-floating">
        <input type="text" name="UnitCost" class="form-control" id="UnitCost" placeholder="상품가격" required>
        <label for="UnitCost">상품가격</label>
      </div>

      <div class="form-floating">
        <input type="file" name="ProductImage" class="form-control" id="ProductImage" placeholder="상품이미지" required>
        <label for="ProductImage">상품이미지</label>
      </div>


      <div class="form-floating">
        <input type="text" name="Description" class="form-control" id="Description" placeholder="상품설명" required>
        <label for="Description">상품설명</label>
      </div>


      <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">상품 등록하기</button>

      <p class="mt-5 mb-3 text-muted">© 2022-2024</p>

    </form>
  </div>
</div>