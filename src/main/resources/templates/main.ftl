<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<#--Добавление и поиск-->
    <#--Шапка-->
    <form method="get" action="/tasks">
        <div class="form-row align-items-center">
            <div class="col-auto">
            <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">Add new task</a>
            </div>
            <div class="col-auto">
            <input type="text" name="searchTag" class="form-control" value="${searchTag?ifExists}" placeholder="Search"/>
            </div>
            <div class="col-auto">
            <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </div>
    </form>
    <#--/Шапка-->
    <#--Тело-->
    <div class="collapse" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" action="/addTask" enctype="multipart/form-data">
            <div class="form-group">
                <input  class="form-control" type="text" name="description" placeholder="Enter description">
            </div>
            <div class="form-group">
                <#--<input type="text" name="content" placeholder="Details">-->
                <textarea class="form-control" name="content" placeholder="Details" rows="3"></textarea>
            </div>
            <div class="custom-file mb-2">
                <input type="file" name="file" class="custom-file-input" id="customFile">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div>
                <button type="submit" class="btn btn-primary">Добавить</button>
            </div>
        </form>
    </div>
    </div>
    <#--/Тело-->
<#--/Добавление и поиск-->
<#--Вывод-->
<table class="table table-hover mt-3">
<thead class="bg-secondary">
<tr>
    <th scope="col" class="col-1.5">Description</th>
    <th scope="col">Content</th>
    <th scope="col" class="col-1">Author</th>
    <th scope="col" class="col-1">Attachment</th>
    <th scope="col" class="col-1">Action</th>
</tr>
</thead>
<tbody>
<#list tasks as task>
    <tr>
        <td>${task.description}</td>
        <td>${task.content}</td>
        <td class="text-center">${(task.author.username)!"&lt;anonymous&gt;"}</td>
        <td class="text-center"><#if task.filename??><a download href="/attachment/${task.filename}">Download</a><#else>N/A</#if></td>
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
<#--/Вывод-->
</@c.page>