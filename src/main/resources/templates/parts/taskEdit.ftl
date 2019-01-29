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
<div class="collapse <#if task??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input class="form-control ${(descriptionError??)?string('is-invalid', '')}" type="text" name="description"
                       value="<#if task??>${task.description}</#if>" placeholder="Enter description">
                <#if descriptionError??>
                    <div class="invalid-feedback">
                        ${descriptionError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input class="form-control ${(contentError??)?string('is-invalid', '')}" name="content" value="<#if task??>${task.content}</#if>"
                          placeholder="Details"></input>
                <#if contentError??>
                    <div class="invalid-feedback">
                        ${contentError}
                    </div>
                </#if>
            </div>
            <div class="custom-file mb-2">
                <input type="file" name="file" class="custom-file-input" id="customFile">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if task??>${task.id}<#else>0</#if>"/>
            <div>
                <button type="submit" class="btn btn-primary">Добавить</button>
            </div>
        </form>
    </div>
</div>
<#--/Тело-->
<#--/Добавление и поиск-->