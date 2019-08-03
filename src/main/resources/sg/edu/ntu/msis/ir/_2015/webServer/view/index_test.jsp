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
	<h3>CI6226: Information Retrieval and Analysis (Test Data)</h3>
	<form action="${pageContext.request.contextPath}/search" target="_blank" method="post">
		<input type="hidden" name="topn" value="10">
		<input type="hidden" name="keyword" value="frustrating">
		<table class="tg">
			<tr>
				<td>Is Case Sensitive?<input type="radio" name="case" value="true"> Yes <input type="radio" name="case" value="false" checked="checked"> No, Is Excluded Stop Words?<input type="radio" name="stop" value="true"> Yes <input type="radio" name="stop" value="false" checked="checked">No, Is Performing Stem?<input type="radio" name="stem" value="true"> Yes <input type="radio" name="stem" value="false" checked="checked">No, TopN: 10, Key Word: frustrating</td>
				<td> 
				<input type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/search" target="_blank" method="post">
		<input type="hidden" name="topn" value="10">
		<input type="hidden" name="keyword" value='"frustrating trying"'>
		<table class="tg">
			<tr>
				<td>Is Case Sensitive?<input type="radio" name="case" value="true"> Yes <input type="radio" name="case" value="false" checked="checked"> No, Is Excluded Stop Words?<input type="radio" name="stop" value="true"> Yes <input type="radio" name="stop" value="false" checked="checked">No, Is Performing Stem?<input type="radio" name="stem" value="true"> Yes <input type="radio" name="stem" value="false" checked="checked">No, TopN: 10, Key Word: "frustrating trying"</td>
				<td> 
				<input type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/search" target="_blank" method="post">
		<input type="hidden" name="topn" value="10">
		<input type="hidden" name="keyword" value='"frustrating trying" unintelligent'>
		<table class="tg">
			<tr>
				<td>Is Case Sensitive?<input type="radio" name="case" value="true"> Yes <input type="radio" name="case" value="false" checked="checked"> No, Is Excluded Stop Words?<input type="radio" name="stop" value="true"> Yes <input type="radio" name="stop" value="false" checked="checked">No, Is Performing Stem?<input type="radio" name="stem" value="true"> Yes <input type="radio" name="stem" value="false" checked="checked">No, TopN: 10, Key Word: "frustrating trying" unintelligent</td>
				<td> 
				<input type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/search" target="_blank" method="post">
		<input type="hidden" name="topn" value="10">
		<input type="hidden" name="keyword" value='love "frustrating trying" unintelligent'>
		<table class="tg">
			<tr>
				<td>Is Case Sensitive?<input type="radio" name="case" value="true"> Yes <input type="radio" name="case" value="false" checked="checked"> No, Is Excluded Stop Words?<input type="radio" name="stop" value="true"> Yes <input type="radio" name="stop" value="false" checked="checked">No, Is Performing Stem?<input type="radio" name="stem" value="true"> Yes <input type="radio" name="stem" value="false" checked="checked">No, TopN: 10, Key Word: love "frustrating trying" unintelligent</td>
				<td> 
				<input type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/search" target="_blank" method="post">
		<input type="hidden" name="topn" value="10">
		<input type="hidden" name="latitude_plus" value="true">
		<input type="hidden" name="latitude" value="35">
		<input type="hidden" name="latitude_range" value="0">
		<table class="tg">
			<tr>
				<td>Is Case Sensitive?<input type="radio" name="case" value="true"> Yes <input type="radio" name="case" value="false" checked="checked"> No, Is Excluded Stop Words?<input type="radio" name="stop" value="true"> Yes <input type="radio" name="stop" value="false" checked="checked">No, Is Performing Stem?<input type="radio" name="stem" value="true"> Yes <input type="radio" name="stem" value="false" checked="checked">No, TopN: 10, latitude: 35, latitude range: 0</td>
				<td> 
				<input type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/search" target="_blank" method="post">
		<input type="hidden" name="topn" value="10">
		<input type="hidden" name="longitude_plus" value="false">
		<input type="hidden" name="longitude" value="81">
		<input type="hidden" name="longitude_range" value="0">
		<table class="tg">
			<tr>
				<td>Is Case Sensitive?<input type="radio" name="case" value="true"> Yes <input type="radio" name="case" value="false" checked="checked"> No, Is Excluded Stop Words?<input type="radio" name="stop" value="true"> Yes <input type="radio" name="stop" value="false" checked="checked">No, Is Performing Stem?<input type="radio" name="stem" value="true"> Yes <input type="radio" name="stem" value="false" checked="checked">No, TopN: 10, longitude: -81, longitude range: 0</td>
				<td> 
				<input type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/search" target="_blank" method="post">
		<input type="hidden" name="topn" value="10">
		<input type="hidden" name="latitude_plus" value="true">
		<input type="hidden" name="latitude" value="35">
		<input type="hidden" name="latitude_range" value="0">
		<input type="hidden" name="longitude_plus" value="false">
		<input type="hidden" name="longitude" value="81">
		<input type="hidden" name="longitude_range" value="0">
		<table class="tg">
			<tr>
				<td>Is Case Sensitive?<input type="radio" name="case" value="true"> Yes <input type="radio" name="case" value="false" checked="checked"> No, Is Excluded Stop Words?<input type="radio" name="stop" value="true"> Yes <input type="radio" name="stop" value="false" checked="checked">No, Is Performing Stem?<input type="radio" name="stem" value="true"> Yes <input type="radio" name="stem" value="false" checked="checked">No, TopN: 10, latitude: 35, latitude range: 0, longitude: -81, longitude range: 0</td>
				<td> 
				<input type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/search" target="_blank" method="post">
		<input type="hidden" name="topn" value="10">
		<input type="hidden" name="keyword" value="definitely">
		<input type="hidden" name="latitude_plus" value="true">
		<input type="hidden" name="latitude" value="35">
		<input type="hidden" name="latitude_range" value="0">
		<input type="hidden" name="longitude_plus" value="false">
		<input type="hidden" name="longitude" value="81">
		<input type="hidden" name="longitude_range" value="0">
		<table class="tg">
			<tr>
				<td>Is Case Sensitive?<input type="radio" name="case" value="true"> Yes <input type="radio" name="case" value="false" checked="checked"> No, Is Excluded Stop Words?<input type="radio" name="stop" value="true"> Yes <input type="radio" name="stop" value="false" checked="checked">No, Is Performing Stem?<input type="radio" name="stem" value="true"> Yes <input type="radio" name="stem" value="false" checked="checked">No, TopN: 10, Key Word: definitely, latitude: 35, latitude range: 0, longitude: -81, longitude range: 0</td>
				<td> 
				<input type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/search" target="_blank" method="post">
		<input type="hidden" name="topn" value="10">
		<input type="hidden" name="keyword" value='"frustrating trying"'>
		<input type="hidden" name="latitude_plus" value="true">
		<input type="hidden" name="latitude" value="35">
		<input type="hidden" name="latitude_range" value="0">
		<input type="hidden" name="longitude_plus" value="false">
		<input type="hidden" name="longitude" value="81">
		<input type="hidden" name="longitude_range" value="0">
		<table class="tg">
			<tr>
				<td>Is Case Sensitive?<input type="radio" name="case" value="true"> Yes <input type="radio" name="case" value="false" checked="checked"> No, Is Excluded Stop Words?<input type="radio" name="stop" value="true"> Yes <input type="radio" name="stop" value="false" checked="checked">No, Is Performing Stem?<input type="radio" name="stem" value="true"> Yes <input type="radio" name="stem" value="false" checked="checked">No, TopN: 10, Key Word: "frustrating trying", latitude: 35, latitude range: 0, longitude: -81, longitude range: 0</td>
				<td> 
				<input type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
	<form action="${pageContext.request.contextPath}/search" target="_blank" method="post">
		<input type="hidden" name="topn" value="10">
		<input type="hidden" name="keyword" value='love "frustrating trying" unintelligent'>
		<input type="hidden" name="latitude_plus" value="true">
		<input type="hidden" name="latitude" value="35">
		<input type="hidden" name="latitude_range" value="0">
		<input type="hidden" name="longitude_plus" value="false">
		<input type="hidden" name="longitude" value="81">
		<input type="hidden" name="longitude_range" value="0">
		<table class="tg">
			<tr>
				<td>Is Case Sensitive?<input type="radio" name="case" value="true"> Yes <input type="radio" name="case" value="false" checked="checked"> No, Is Excluded Stop Words?<input type="radio" name="stop" value="true"> Yes <input type="radio" name="stop" value="false" checked="checked">No, Is Performing Stem?<input type="radio" name="stem" value="true"> Yes <input type="radio" name="stem" value="false" checked="checked">No, TopN: 10, Key Word: love "frustrating trying" unintelligent, latitude: 35, latitude range: 0, longitude: -81, longitude range: 0</td>
				<td> 
				<input type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
