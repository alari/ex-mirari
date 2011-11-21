<%--
  @author Dmitry Kurinskiy
  @since 27.08.11 22:20
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
Здравствуйте ${username},<br/>
<br/>
Вы (или кто-то, претворяющийся Вами) создал аккаунт, указав этот адрес электронной почты.<br/>
<br/>
Если это были Вы, пожалуйста, перейдите по <g:link absolute="true" controller="register" action="verifyRegistration"
                                                   params="[t:token]">этой ссылке</g:link>, чтобы завершить регистрацию.
<br/>