<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
    ${message?ifExists}
    <div class="mb-3">Login to your account</div>
    <@l.login "/login" false/>
</@c.page>