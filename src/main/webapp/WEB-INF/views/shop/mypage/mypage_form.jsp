<%@ page import="com.nhnacademy.shoppingmall.user.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>

<div style="margin: auto; width: 400px;">
    <div class="p-2">


        <form method="get" action = "/update.do">
            <%
                HttpSession session = request.getSession();
                User user = (User)session.getAttribute("user");
                String name = user.getUserName();
            %>
            <h1 class="h3 mb-3 fw-normal"><%=name %>님의 마이페이지 입니다.</h1>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">회원정보 수정하기</button>
        </form>
        <form method="post" action="/deleteAction.do">
            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">탈퇴하기</button>
            <p class="mt-5 mb-3 text-muted">© 2022-2024</p>
        </form>
    </div>
</div>
