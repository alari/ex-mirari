<%--
 * @author Dmitry Kurinskiy
 * @since 24.10.11 23:13  
--%>
<div${upload ? ' data-avatar="' + upload + '"' : ""} class="avatar-holder">
    <img width="210" height="336" src="${url}"/>

    <g:if test="${upload}">

        <r:require module="mirariAvatarUpload"/>
        <form method="post" enctype="multipart/form-data">
            <label class="fileinput-button btn info"><input type="file" name="avatar"/>${message(code: "avatar.upload")}
            </label>
        </form>
        <br clear="all"/>

        <div class="avatar-progressbar ui-progressbar"></div>
    </g:if>
</div>