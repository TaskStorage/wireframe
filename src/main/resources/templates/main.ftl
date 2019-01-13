<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<#--Добавление-->
<form method="post" action="/addTask" enctype="multipart/form-data">
    <div>
        <input type="text" name="description" placeholder="Enter description">
    </div>
    <div>
        <input type="text" name="content" placeholder="Details">
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
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
    <th>Author</th>
    <th>Action</th>
</tr>
</thead>
<tbody>
<#list tasks as task>
    <tr>
        <td>${task.description}</td>
        <td>${task.content}</td>
        <td>${task.author.getUsername()}</td>
        <td>
                <form method="post" action="/delTask/${task.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
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
    <form method="get" action="/tasks">
        <input type="text" name="searchTag" value="${searchTag?ifExists}"/>
        <button type="submit">Найти</button>
    </form>
</div>
<@l.logout/>
</@c.page>