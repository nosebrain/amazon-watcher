<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml" 
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:c="http://java.sun.com/jsp/jstl/core">
        	
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<basic:layout pageTitle="Edit item">
   		<jsp:attribute name="content">
   			<h2>edit item</h2>
   			
   			<form:form method="POST" commandName="item">
   				<div>
   					<p>
   						<form:label path="name">name*:</form:label>
   						<form:input path="name" />
   						<form:errors path="name" cssClass="errors" />
   					</p>
   					<p>
   						<form:label path="url">url*:</form:label>
   						<form:input path="url" />
   						<form:errors path="url" cssClass="errors" />
   					</p>
   				</div>
   				<div>
   					<p>
   						<form:select path="mode">
   							<form:options />
   						</form:select>					
   					</p>
   					<p>
   						<form:label path="limit">limit:</form:label>
   						<form:input path="limit" />
   						<form:errors path="limit" cssClass="errors" />
   					</p>
   				</div>
   				<div>
   					<input type="submit" value="create new item" />
   				</div>
   			</form:form>
   			
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>