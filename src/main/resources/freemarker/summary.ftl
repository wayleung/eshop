<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="content-type" />
<title>Debug Testing Result 1.0</title>
<style type="text/css">
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
</style>
</head>
<body>
<h1>Debug Testing Result 1.0</h1>
<table width="100%">
    <tr>
        <td align="left"></td>
        <td align="right">Designed for <a href="http://www.aia.com.cn">AIAIT System Support</a>.
    </tr>
</table>
<hr size="1">
<h2>Summary</h2>
<table width="80%" cellspacing="2" cellpadding="5" border="0"
    class="details">
    <tbody>
        <tr valign="top">
            <th>Start Date</th>
            <th>End Date</th>
            <th>Time Spent(s)</th>
        </tr>
        <tr valign="top" class="Pass">
            <td>${summary.startDate}</td>
            <td>${summary.endDate}</td>
            <td>${summary.timeSpent}</td>
        </tr>
        <tr valign="top">
            <th>Passed</th>
            <th>Failed</th>
            <th>Auto. Test Case</th>
        </tr>
        <tr valign="top" class="Pass">
            <td>${summary.passed}</td>
            <td>${summary.failed}</td>
            <td>${summary.autoTestCase}</td>
        </tr>
        <tr valign="top">
            <th>Total Test Case</th>
            <th>Passing Rate</th>
            <th>Coverage Rate</th>
        </tr>
        <tr valign="top" class="Pass">
            <td>${summary.totalTestCase}</td>
            <td>${summary.passingRate}</td>
            <td>${summary.coverageRate}</td>
        </tr>
    </tbody>
</table>
<h2>Tests Result</h2>
<p>
<a href="javascript:show_all();">Show All</a>&nbsp;&nbsp;<a href="javascript:show_success();">Show Success</a>&nbsp;&nbsp;<a href="javascript:show_fail();">Show Fail</a>
</p>
<table width="95%" cellspacing="2" cellpadding="5" border="0"
    class="details">
    <tr valign="top">
        <th width="80%">Name</th>
        <th>State</th>
        <!-- <th>Errors</th> -->
        <th>Failures</th>
        <th nowrap="">Time(s)</th>
    </tr>
	
	<#list cases as case>
	<#if case.state == 'SUCCESS'>
	<tr name='tr_1' class="Pass" valign="top" style="">
		<td>
			<a href="./TestCase/${case.caseName}.html">${case.caseName}</a>
		</td>
		<td>
					
			<font style="color: green; font-weight: bold;">${case.state}</font>
		
		
		</td>
		<td>${case.failures}</td>
		<td>${case.time}</td>
	</tr>
	<#else>
	<tr name='tr_2' class="Pass" valign="top" style="">
		<td>
			<a href="./TestCase/${case.caseName}.html">${case.caseName}</a>
		</td>
		<td>
					
		
			<font style="color: red; font-weight: bold;">${case.state}</font>
		
		</td>
		<td>${case.failures}</td>
		<td>${case.time}</td>
	</tr>
	</#if>
	</#list>
	
</table>

</body>
<script>
function show_success() {

	var tr_1 = document.getElementsByName("tr_1");
	
	
    for (tr1=0; tr1<tr_1.length;tr1++) {
        tr_1[tr1].style.display = '';
    }
	
	var tr_2 = document.getElementsByName("tr_2");
    for (tr2=0; tr2<tr_2.length;tr2++) {
        tr_2[tr2].style.display = 'none';
    }
}
function show_fail() {
	var tr_1 = document.getElementsByName("tr_1");
	
    for (tr1=0; tr1<tr_1.length;tr1++) {
        tr_1[tr1].style.display = 'none';
    }
	
	var tr_2 = document.getElementsByName("tr_2");
	for (tr2=0; tr2<tr_2.length;tr2++) {
        tr_2[tr2].style.display = '';
    }
}
function show_all() {
	var tr_1 = document.getElementsByName("tr_1");
	
    for (tr1=0; tr1<tr_1.length;tr1++) {
        tr_1[tr1].style.display = '';
    }
	
	var tr_2 = document.getElementsByName("tr_2");
    for (tr2=0; tr2<tr_2.length;tr2++) {
        tr_2[tr2].style.display = '';
    }
}
</script>
</body>
</html>