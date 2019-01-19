<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
    <#--${message?ifExists}-->
    <#--<div class="mb-3">Login to your account</div>-->

<#--Ошибки валидации-->
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>

<#--Стасус активации-->
<#--Из RegistrationController success и danger стили-->
    <#if messageType??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>

<#--Сообщение при принудительном окончании сессии-->
    <#if message??>
        <div class="mb-3">${message}</div>
    </#if>


    <@l.login "/login" false/>
</@c.page>