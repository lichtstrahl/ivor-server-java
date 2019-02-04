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
        <li>/api/evaluation - добавлена возможность оценивания</li>
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

    <h2>Evaluation</h2>
    <p>
        Отдельный сервлет, обрабатывающий запросы на оценку. Подход тот же, любое положительное число увелчивает корректность на 1,
        любое отрицательное на 1 уменьшает. Для того, чтобы выполнить запрос (POST) нужно передать три поля:
        <lu>
            <li>type (communication, communication_key)</li>
            <li>eval</li>
            <li>id</li>
        </lu>
        В зависимости от переданного типа выбирается таблица для внесения изменений. И соответствующий id.
        Важный момент! Теперь power увеличивается ТОЛЬКО тогда, когда была произведена оценка.
        Возможно стоит подумать над тем, чтобы power увеличивался при ответе на вопрос. Так сказать в любом случае.
    </p>

    <h3>Планы на будущее</h3>
    <p>
        Подумать над введением пула соединений
    </p>

    <h3>Логи</h3><br>
    <%= RequestProcessor.init()%>
</body>
</html>