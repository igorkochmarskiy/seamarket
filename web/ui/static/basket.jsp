<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/seamarket/ui/css/style.css"/>
    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>
</head>
<body>
<div id="buyItems">
    <table>
        <th>ARTICLE</th>
        <th>NAME</th>
        <th>PRICE</th>
        <%
            List<Item> items = (ArrayList) request.getAttribute("items");
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                if (item.getPrice() >= 0) {
        %>
        <tr>
            <td class="articleCell"><%=item.getArticle()%>
            </td>
            <td><%=item.getName()%>
            </td>
            <td><%=item.getPrice()%>
            </td>
        </tr>
        <%
        } else {
        %>
        <tr>
            <td class="noItemCell"><%=item.getArticle()%>
            </td>
            <td>
            </td>
            <td>
            </td>
        </tr>
        <% }
        }
        %>
    </table>
    <button id="buy" type="button">Buy</button>
</div>
<script>
    $("#buy").on("click", function () {
        var articles = $('.articleCell').map(function () {
            return $.trim($(this).text());
        }).get();

        $.ajax({
            url: "/seamarket/buyService ",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(articles),
            contentType: 'application/json',
            mimeType: 'application/json'
        }).always(function (jqXHR) {
            if (jqXHR.status == 201) {
                window.location.href = '/seamarket/shop/success';
            } else {
                window.location.href = '/seamarket/shop/failure';
            }
        });
    });
</script>

</body>
</html>
