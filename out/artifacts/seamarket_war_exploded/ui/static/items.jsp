<%@ page import="model.Item" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="table-items">
    <th>NAME</th>
    <th>ARTICLE</th>
    <th>PRICE</th>
    <%
        List<Item> items = (List) request.getAttribute("items");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
    %>
    <tr>
        <td><%=item.getName()%>
        </td>
        <td><%=item.getArticle()%>
        </td>
        <td><%=item.getPrice()%>
        </td>
        <td>
            <input type="checkbox" id=<%=item.getArticle()%> value="Bike">
        </td>
    </tr>
    <%
        }
    %>
</table>