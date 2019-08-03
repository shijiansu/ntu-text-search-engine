<!-- Description: -->
<!-- Copyright(C),2015 -->
<!-- This program is protected by copyright laws. -->
<!-- Data: 2015-04-04 -->
<!-- @version 1.0 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
.tg {
	border-collapse: collapse;
	border-spacing: 0;
}

.tg td {
	font-family: Arial, sans-serif;
	font-size: 14px;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
}

.tg th {
	font-family: Arial, sans-serif;
	font-size: 14px;
	font-weight: normal;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
}
</style>
</head>
<body>
	<h3>CI6226: Information Retrieval and Analysis</h3>
	<form action="${pageContext.request.contextPath}/search" method="post">
		<table class="tg">
			<tr>
				<td colspan="2">Search Setting:</td>
			</tr>
			<tr>
				<td>Is Case Sensitive?</td>
				<td><input type="radio" name="case" value="true"> Yes <input
					type="radio" name="case" value="false" checked="checked">
					No</td>
			</tr>
			<tr>
				<td>Is Excluded Stop Words?</td>
				<td><input type="radio" name="stop" value="true"> Yes <input
					type="radio" name="stop" value="false" checked="checked">
					No</td>
			</tr>
			<tr>
				<td>Is Performing Stem?</td>
				<td><input type="radio" name="stem" value="true"> Yes <input
					type="radio" name="stem" value="false" checked="checked">
					No</td>
			</tr>
			<tr>
				<td>TopN:</td>
				<td><input type="text" name="topn" value="" maxlength="3"
					style="width: 50px;">(support 1~100, default is 10)</td>
			</tr>
			<tr>
				<td>Key Word:</td>
				<td><input type="text" name="keyword" value="" maxlength="90"
					style="width: 500px;"></td>
			</tr>
			<tr>
				<td>Location:</td>
				<td>
					<table class="tg">
						<tr>
							<td>latitude</td>
							<td><input type="radio" name="latitude_plus" value="true"
								checked="checked"> + <input type="radio"
								name="latitude_plus" value="false"> - <input type="text"
								name="latitude" value="" maxlength="2" style="width: 50px;">(support
								2 digits numeric)</td>
							<td>range</td>
							<td><input type="text" name="latitude_range" value=""
								maxlength="2" style="width: 50px;">(support 0~10)</td>
						</tr>
						<tr>
							<td>longitude</td>
							<td><input type="radio" name="longitude_plus" value="true"
								checked="checked"> + <input type="radio"
								name="longitude_plus" value="false"> - <input
								type="text" name="longitude" value="" maxlength="3"
								style="width: 50px;">(support 3 digits numeric)</td>
							<td>range</td>
							<td><input type="text" name="longitude_range" value=""
								maxlength="2" style="width: 50px;">(support 0~10)</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
					href="${pageContext.request.contextPath}/">Reset</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
					type="submit" value="Submit"></td>
			</tr>
		</table>
	</form>
	<br>
	<table class="tg">
		<tr>
			<td colspan="2">Current Search Setting:</td>
			<td>
				<table class="tg">
					<tr>
						<td>Is Case Sensitive:</td>
						<td>${requestScope.model.lowerCase}</td>
					</tr>
					<tr>
						<td>Is Excluded Stop Words:</td>
						<td>${requestScope.model.stop}</td>
					</tr>
					<tr>
						<td>Is Performing Stem:</td>
						<td>${requestScope.model.porterStem}</td>
					</tr>
					<tr>
						<td>TopN:</td>
						<td>${requestScope.model.topN}</td>
					</tr>
					<tr>
						<td>Key Word:</td>
						<td>${requestScope.model.searchQuery}</td>
					</tr>
					<tr>
						<td>Location:</td>
						<td>
							<table>
								<tr>
									<td>Latitude Range:</td>
									<td>[${requestScope.model.latitude1},${requestScope.model.latitude2}]</td>
								</tr>
								<tr>
									<td>Longitude Range:</td>
									<td>[${requestScope.model.longtitude1},${requestScope.model.longtitude2}]</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>Lucene Search Query:</td>
						<td>${requestScope.model.luceneQuery}</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">Search Result:</td>
			<td>time cost: ${requestScope.model.searchCostTime}(ms), found
				${requestScope.model.searchResultFound} records</td>
		</tr>
		<tr>
			<th>Doc ID</th>
			<th>Score</th>
			<th>Fields</th>
		</tr>
		<c:forEach items="${requestScope.model.results}" var="item">
			<tr>
				<td>${item.docId}</td>
				<td>${item.score}</td>
				<td><c:forEach items="${item.fields}" var="entry">
						<b>${entry.key}:</b>
					${entry.value}
					<br>
					</c:forEach></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>


