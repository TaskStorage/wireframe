<#include "security.ftl">

<div class="card-columns">
    <#list feeds as feed>
        <div class="card my-3">
            <div class="card-header">
                ${feed.title}
            </div>
            <div class="m-2">
                <p class="card-text">${feed.description}</p>
            </div>
        </div>
    <#else>
        No records
    </#list>
</div>