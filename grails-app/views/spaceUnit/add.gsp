<%--
 * @author alari
 * @since 10/27/11 10:00 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>Adding the Unit (${space} / ${spaceName})</title>

</head>

<body>

<style>
.unit-title {
    width: 100%;
    border: 0 solid transparent;
    font-size: 30px;
    font-family: Tahoma;
    height: 34px;
}

.unit-adder {
    height: 350px;
    border: 1px solid gray;
    border-radius: 3px;
}

.unit-adder-drop {
    background: #ffdab9;
    height: 100%;
    position: relative;
    overflow: hidden;
}

.unit-adder-drop input {
    width: 1000px;
    height: 1000px;
    border: solid transparent;
    border-width: 0 0 300px 300px;
    opacity: 0;
    filter: alpha(opacity = 0);
    /*-o-transform: translate(250px, -50px) scale(1);*/
    -moz-transform: translate(-700px, 0) scale(4);
    direction: ltr;
    cursor: pointer;
    position: absolute;
    right: 0;
    bottom: 0;
    font-size: 600px;
}
</style>

<div class="unit-envelop" id="unit" data-unit-action="<g:createLink params="[spaceName:spaceName]" action="addUnit"/>">
    <h1><input class="unit-title" type="text" placeholder="Заголовок" name="title"/></h1>

    <div class="unit-content"></div>

    <div class="unit-adder">
        <div class="span6 unit-adder-drop">
            <form method="post" enctype="multipart/form-data" action="<g:createLink params="[spaceName:spaceName]"
                                                                                    action="addFile"/>">
                Left Zone (droppable)
                <input type="file" name="unitFile"/>

            </form>
        </div>

        <div class="span6">
            Main Zone There
        </div>
    </div>


    <br clear="all"/>
    <mk:formActions>
        <button class="btn primary unit-pub">pub it</button>
        <button class="btn primary unit-draft">draft it</button>
    </mk:formActions>
</div>

<r:require module="mirariUnitAdd"/>

</body>
</html>