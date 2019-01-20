<#import "parts/common.ftl" as c>

<@c.page>
    <#if isCurrentUser>
        <#include "parts/taskEdit.ftl" />
    </#if>
    <#include "parts/taskList.ftl" />
</@c.page>