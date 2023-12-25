<%@ page import="com.nhnacademy.shoppingmall.user.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>

<%
    HttpSession session = request.getSession();
    User user = (User)session.getAttribute("user");
    String name = user.getUserName();
    String id = user.getUserId();
    String ps = user.getUserPassword();
    String birth = user.getUserBirth();

%>
<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <form method="post" action="/updateAction.do">

            <h1 class="h3 mb-3 fw-normal">회원 정보 수정</h1>

            <div class="form-floating">
                <input type="text" name="user_id" class="form-control" id="user_id" placeholder="<%=id%>" value="<%=id%>" required>
                <label for="user_id">회원아이디</label>
            </div>

            <div class="form-floating">
                <input type="text" name="user_name" class="form-control" id="user_name" placeholder="<%=name%>" value="<%=name%>" required>
                <label for="user_id">회원이름</label>
            </div>


            <div class="form-floating">
                <input type="password" name="user_password" class="form-control" id="user_password" placeholder="<%=ps%>" value="<%=ps%>" required>
                <label for="user_password">비밀번호</label>
            </div>

            <div class="form-floating">
                <input type="text" name="user_birth" maxlength="8" class="form-control" id="user_birth" placeholder="<%=birth%>" value="<%=birth%>" required>
                <label for="user_password">생년월일</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">업데이트 하기</button>

            <p class="mt-5 mb-3 text-muted">© 2022-2024</p>

        </form>
    </div>
</div>