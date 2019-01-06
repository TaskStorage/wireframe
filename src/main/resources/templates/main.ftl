<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TaskStorage</title>
</head>
<body>
<#--Добавление-->
<form method="post" action="/addTask" enctype="multipart/form-data">
    <div>
        <input type="text" name="description" placeholder="Enter description">
    </div>
    <div>
        <input type="text" name="content" placeholder="Details">
    </div>
    <div>
        <button type="submit">Добавить</button>
    </div>
</form>
<#--Вывод-->
<table>
<thead>
<tr>
    <th>Description</th>
    <th>Content</th>
    <th>Action</th>
</tr>
</thead>
<tbody>
<#list tasks as task>
    <tr>
        <td>${task.description}</td>
        <td>${task.content}</td>
        <td>
                <form method="post" action="/delTask/${task.id}">
                    <button type="submit">Удалить</button>
                </form>
        </td>
    </tr>
<#else>
    <tr>
        <td>No records</td>
    </tr>
</#list>
</tbody>
</table>
<#--Сортировка-->
<div>
    <form method="post" action="/search">
        <input type="text" name="searchTag"/>
        <button type="submit">Найти</button>
    </form>
</div>

</body>
</html>