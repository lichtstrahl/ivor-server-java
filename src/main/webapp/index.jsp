<%@ page import="root.id.util.RequestProcessor" %>
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
    <h2>This is backend for Ivor-web-application! Build with Java + Servlet + JDBC</h2><br/>
    <h3>Версия 1.0.2</h3>
    <p>Основные изменения:</p>
    <lu>
        <li>/api/request - единая точка входа, для получения ответа</li>
        <li>/api/login - единая точка входа для проверки регистрации</li>
        <li>ServerAnswer - унифицированный ответ от сервера</li>
    </lu>

    <h3>ServerAnswer</h3>
    <p>
        Любой сервлет посылает в ответ клиенту указанную структуру.
        Содержит внутри себя 3 поля:
        <lu>
            <li>error - код ошибки (0, -1)</li>
            <li>msg - сообщение об ошибке или "ОК"</li>
            <li>data - содержит в себе непосредственно JSON-представление ответа. Может быть любой сложности.</li>
        </lu>
    </p>

    <h3>Request</h3>
    <p>
        При обращении по адресу '/api/request' сервлет LoginServlet ожидает получить структуру RequestDTO, содержащую одно поле "request"
        С текстом вопроса.
    </p>

    <h3>Login</h3>
    <p>
        Принимает JSON User. Но указывать все поля не нужно. Достаточно только "login" и "pass"
        Если указанный пользователь будет найден, то вернётся JSON, представляющий этого пользователя. (в поле data)
        Если нет, то в data будет содержаться строка с ошибкой
    </p>

    <h3>Планы на будущее</h3>
    <p>
        Реализовать отдельный API для мобильных приложений. Например по адресу: /api/mobile/...
    </p>

    <h3>Логи</h3><br>
    <%= RequestProcessor.init()%>
</body>
</html>