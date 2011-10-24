<%--
 * @author Dmitry Kurinskiy
 * @since 20.10.11 19:23  
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${message(code: "personPreferences.title")}</title>
    <meta name="layout" content="mono"/>
</head>

<body>

<mk:pageHeader>${message(code: "personPreferences.title")}</mk:pageHeader>

<mk:withLeftSidebar>
    <mk:leftSidebar>
        <sbj:link/>
        <div id="ava-holder">
            <img id="ava" width="210" height="336" src="${imageUrl}"/>
        </div>


        <r:require module="fileUploader"/>



        <div id="fileupload">
            <g:form method="post" action="uploadAvatar" name="uploadAvatar" enctype="multipart/form-data">
                <div class="fileupload-buttonbar">
                    <label class="fileinput-button">
                        <span>Add files...</span>
                        <input type="file" name="avatar"/>
                    </label>
                </div>

            </g:form>
        </div>


        <script type="text/javascript">
            $(function () {
                'use strict';

                // Initialize the jQuery File Upload widget:
                $('#fileupload').fileupload({
                    dataType: "json",
                    add: function (e, data) {
                        data.submit();
                    },
                    done: function(e, data) {
                        $("#ava").attr("src", data.result.thumbnail + "?" + new Date().getTime() + new Date().getUTCMilliseconds());

                    },
                    fail: function(e, data) {
                        alert(data.result);
                    }
                });


                // Open download dialogs via iframes,
                // to prevent aborting current uploads:
                $('#fileupload .files a:not([target^=_blank])').live('click', function (e) {
                    e.preventDefault();
                    $('<iframe style="display:none;"></iframe>')
                            .prop('src', this.href)
                            .appendTo('body');
                });

            });
        </script>

    </mk:leftSidebar>
    <mk:content>

        <g:render template="changePassword"/>

        <g:formRemote update="changeEmailUpdate" name="changeEmail" url="[action:'changeEmail']" action="changeEmail"
                      method="post">
            <fieldset>
                <legend>${message(code: "personPreferences.changeEmail.title")}</legend>

                <div id="changeEmailUpdate"></div>
                <mk:formLine labelCode="personPreferences.changeEmail.field">
                    <g:textField name="email"/> <g:submitButton name="submit" class="btn info"
                                                                value="${message(code:'personPreferences.changeEmail.submit')}"/>
                </mk:formLine>

            </fieldset>
        </g:formRemote>

    </mk:content>
</mk:withLeftSidebar>

</body>
</html>