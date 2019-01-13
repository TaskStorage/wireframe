<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<#--Добавление-->
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new task
    </a>
    <div class="collapse" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" action="/addTask" enctype="multipart/form-data">
            <div>
                <input type="text" name="description" placeholder="Enter description">
            </div>
            <div>
                <input type="text" name="content" placeholder="Details">
            </div>
            <div>
                <input type="file" name="file">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div>
                <button type="submit">Добавить</button>
            </div>
        </form>
    </div>
    </div>
<#--Вывод-->
<table class="table table-sm mt-3">
<thead>
<tr>
    <th scope="col">Description</th>
    <th scope="col">Content</th>
    <th scope="col">Author</th>
    <th scope="col">Attachment</th>
    <th scope="col">Action</th>
</tr>
</thead>
<tbody>
<#list tasks as task>
    <tr>
        <td>${task.description}</td>
        <td>${task.content}</td>
        <td>${(task.author.username)!"&lt;anonymous&gt;"}</td>
        <td><#if task.filename??><a download href="/attachment/${task.filename}">Download</a><#else>Not available</#if></td>
        <td>
                <form method="post" action="/delTask/${task.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
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

<form method="get" action="/tasks" class="form-inline">
    <input type="text" name="searchTag" class="form-control" value="${searchTag?ifExists}" placeholder="Search"/>
    <button type="submit" class="btn btn-primary ml-1">Search</button>
</form>

</@c.page>