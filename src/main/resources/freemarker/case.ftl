<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="content-type" />
<title>Auto Test Report</title>
<style type="text/css">
body {
    font-family: verdana, arial, helvetica;
    color: #000000;
    font-size: 12px;
}

table tr td,table tr th {
    font-family: verdana, arial, helvetica;
    font-size: 12px;
}

table.details tr th {
    font-family: verdana, arial, helvetica;
    font-weight: bold;
    text-align: left;
    background: #a6caf0;
}

table.details tr td {
    background: #eeeee0;
}

p {
    line-height: 1.5em;
    margin-top: 0.5em;
    margin-bottom: 1.0em;
    font-size: 12px;
}

h1 {
    margin: 0px 0px 5px;
    font-family: verdana, arial, helvetica;
}

h2 {
    margin-top: 1em;
    margin-bottom: 0.5em;
    font-family: verdana, arial, helvetica;
}

h3 {
    margin-bottom: 0.5em;
    font-family: verdana, arial, helvetica;
}

h4 {
    margin-bottom: 0.5em;
    font-family: verdana, arial, helvetica;
}

h5 {
    margin-bottom: 0.5em;
    font-family: verdana, arial, helvetica;
}

h6 {
    margin-bottom: 0.5em;
    font-family: verdana, arial, helvetica;
}

.Error {
    font-weight: bold;
    color: red;
}

.Failure {
    font-weight: bold;
    color: purple;
}

.small {
    font-size: 9px;
}

.result_ok {
    font-family: verdana;
    font-size: 12px;
    font-weight: bold;
    text-align: center;
    color: green;
}

.subcall_tag {
    font-family: verdana;
    font-size: 12px;
    text-align: center;
    color: #800080;
}

.result_nok {
    font-family: verdana;
    font-size: 12px;
    font-weight: bold;
    text-align: center;
    color: red;
}

.overall_ok {
    font-family: verdana;
    font-size: 12px;
    font-weight: bold;
    text-align: left;
    color: green;
}

.overall_nok {
    font-family: verdana;
    font-size: 12px;
    font-weight: bold;
    text-align: left;
    color: red;
}

a {
    color: #003399;
}

a:hover {
    color: #888888;
}
</style>
</head>
<body>
<a name="top"></a>
<h1> AutoTest Results</h1>
<table width="100%">
    <tr>
        <td align="left"></td>
        <td align="right">Designed for <a href="http://www.aia.com.cn">AIAIT System Support</a>.
        </td>
    </tr>
</table>
<table border=0 width=95% cellpadding=0 cellspacing=0>
    <tbody>
        <tr>
            <td width=20%>
            <div class=bold_text>Case Name</div>
            </td>
            <td width=5%>
            <div class=bold_text>:</div>
            </td>
            <td width=75%>
            <div class=normal_text>${case.caseName}</div>
            </td>
        </tr>
        <tr>
            <td width=20%>
            <div class=bold_text>Time</div>
            </td>
            <td width=5%>
            <div class=bold_text>:</div>
            </td>
            <td width=75%>${case.time}</td>
        </tr>
        <tr>
            <td width=20%>
            <div class=bold_text>detail</div>
            </td>
            <td width=5%>
            <div class=bold_text>:</div>
            </td>

			<td width=75%>Success:${case.success}&nbsp;&nbsp;Failures:${case.failures}</td>
        </tr>
        <tr>
            <td width=20%>
            <div class=bold_text>state</div>
            </td>
            <td width=5%>
            <div class=bold_text>:</div>
            </td>
			
			<#if case.state == 'SUCCESS'>
            <td width=75%>
                     <div class=overall_ok>${case.state}</div>
            </td>
			<#else>
			 <td width=75%>
					<div class=overall_nok>${case.state}</div>
			</td>
			</#if>
        </tr>
    </tbody>
</table>
<table class="details" border="0" cellpadding="0" cellspacing="2" width="95%">
    <tbody>
        <tr valign="top">
			<th width=10%>Step</th>
            <th width=30%>Description</th>
            <th width=10%>Status</th>
            <th width="40%">Message</th>
			<th width="10%">Comment</th>
        </tr>           
		<#list details as detail>
		<tr>
			<td>${detail.step}</td>
            <td>${detail.description}</td>
			<#if detail.status == 'SUCCESS'>
            <td><div class=result_ok>${detail.status}</div></td>
			<#else>
			<td><div class=result_nok>${detail.status}</div></td>
			</#if>
            <td>${detail.message}</td>
			<td>${detail.comment}</td>
        </tr>
		</#list>
		
            </tbody>
</table>