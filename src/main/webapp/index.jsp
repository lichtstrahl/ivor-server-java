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
    <h3>Версия 1.1.0</h3>
    <p>Основные изменения:</p>
    <lu>
        <li>Исправлена ошибка 500 при первом подключении. DBContentLoader превращён в Singleton. </li>
        <li>Добавлена структура AnswerContainer</li>
        <li></li>
    </lu>

    <h2>DBContentLoader</h2>
    <p>
        Теперь подключение пересоздаётся каждый раз. Медленно, зато стабильно работает.
    </p>

    <h2>AnswerContainer</h2>
    <p>
        Исправлен принцип ответов сервера на задаваемые вопросы.
        Теперь в поле data возвращается не строка, а объект, содержащий помимо строки ответа ещё и id коммуникации.
        В зависимости от имени поля (communication, communication_key) можно определить, в какой именно таблице искать последнюю связь.
    </p>

    <h3>Планы на будущее</h3>
    <p>
        Подумать над введением пула соединений
    </p>

    <h3>Логи</h3><br>
    <%= RequestProcessor.init()%>
</body>
</html>