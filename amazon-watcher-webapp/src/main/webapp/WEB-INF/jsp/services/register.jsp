<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml"
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:observations="urn:jsptagdir:/WEB-INF/tags/observation"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:bootstrap="urn:jsptagdir:/WEB-INF/tags/utils/bootstrap"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:c="http://java.sun.com/jsp/jstl/core">
        	
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<fmt:message key="register.title" var="pageTitle" />
   	
   	<basic:layout pageTitle="${pageTitle}">
   		<jsp:attribute name="content">
   			<div class="page-header">
   				<h1>
   					<c:out value="${pageTitle}" />
   				</h1>
   			</div>
   			<div class="row">
   				<div class="span8">
   					<c:url var="registerUrl" value="/register" />
		   			<form:form commandName="userCommand" action="${registerUrl}" method="post" cssClass="form-horizontal">
		   				<bootstrap:input labelMessageKey="user.name" path="user.name" required="${true}" />
		   				<bootstrap:input labelMessageKey="user.mail" path="user.mail" required="${true}" />
		   				
		   				
		   				<h2>Authentication</h2>
		   				<ul class="nav nav-tabs">
		   					<li class="active"><a href="#tab1" data-toggle="tab">Password</a></li>
		   					<li><a href="#tab2" data-toggle="tab">OpenId</a></li>
		   				</ul>
		   				<div class="tab-content">
			   				<div class="tab-pane active" id="tab1">
			   					<bootstrap:password labelMessageKey="user.password" path="password" />
			   					<bootstrap:password labelMessageKey="user.password.retype" path="retype" />
			   				</div>
			   				<div class="tab-pane" id="tab2">
			   					TODO2
			   				</div>
		   				</div>
		   				<div class="form-actions">
		   					<fmt:message key="user.register.action" var="actionValue" />
		   					<input type="submit" class="btn btn-primary pull-right" value="${actionValue}" />
		   				</div>
		   			</form:form>
   				</div>
   				<div class="span4">
   					Please choose your …
   				</div>
   			</div>
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>