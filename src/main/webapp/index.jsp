<%@ page import="root.id.service.RequestService" %>
<%@page contentType="text/html; charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ivor backend</title>
    <%--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>--%>
    <style>
        body {
            background: url("background.jpg");
        }
    </style>
</head>
<body>
    <h2>This is backend for Ivor! Build with Java + Servlet + JDBC</h2><br/>
    <h3>Версия 2.0.0</h3>
    <p>Основные изменения:</p>
    <lu>
        <li>Убрана большая часть статики. Разнесена логика из RequestProcessor. Сами "процессоры" теперь "сервисы"</li>
        <li>Осуществляется общение с сервером на Python для морфологического разбора слов</li>
        <li>Изменено понятие "стандартного" вида строки. Теперь там все слова ставятся в начальную форму.</li>
    </lu>



    <h3>Планы на будущее</h3>
    <p>
    </p>

    <h3>Логи</h3><br>
    <%= RequestService.init()%>
</body>
</html>