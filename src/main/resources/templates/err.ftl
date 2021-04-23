<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>hello world!</h1>

测试循环list<br>
<#list ls as p>
    ${p.id}-----${p.name}-----${p.pass}<br>
</#list>

<br><br><br>

测试循环list中的map<br>
<#list list as map>
    <#list map?keys as key>
        <#if key="phone">
	     	Phone:${map[key]}
        </#if>
        <#if key="email">
	     	Email:${map[key]}
        </#if>
        <br>
    </#list>
</#list>


</body>
</html>