<#include "security.ftl">

<#--Вывод-->
<table class="table table-hover mt-3">
    <thead class="bg-secondary">
    <tr>
        <th scope="col" class="col-1.5">Description</th>
        <th scope="col">Content</th>
        <th scope="col" class="col-1">Author</th>
        <th scope="col" class="col-1">Attachment</th>
        <th scope="col" class="col-1">Action</th>
        <th scope="col" class="col-1"></th>
    </tr>
    </thead>
    <tbody>
    <#list tasks as task>
        <tr>
            <td>${task.description}</td>
            <td>${task.content}</td>
            <td class="text-center"><a href="/personal-tasks/${task.author.id}">${(task.author.username)!"&lt;Anonymous&gt;"}</a></td>
            <td class="text-center"><#if task.filename??><a download href="/attachment/${task.filename}">Download</a><#else>N/A</#if></td>
            <td>
                <#if task.author.id = currentUserId>
                <form method="post" action="/delTask/${task.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                </form>
                </#if>
            </td>
            <td>
                <#if task.author.id = currentUserId>
                    <a class="btn btn-primary btn-sm" href="/personal-tasks/${task.author.id}?task=${task.id}">Edit</a>
                </#if>
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