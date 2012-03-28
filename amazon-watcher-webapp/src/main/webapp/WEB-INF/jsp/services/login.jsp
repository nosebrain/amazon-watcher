<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml"
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:observations="urn:jsptagdir:/WEB-INF/tags/observation"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:services="urn:jsptagdir:/WEB-INF/tags/services"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:c="http://java.sun.com/jsp/jstl/core">
        	
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<fmt:message key="login.title" var="pageTitle">
   		<fmt:param value="${properties['general.name']}" />
   	</fmt:message>
   	
   	<basic:layout pageTitle="${pageTitle}" hideLogin="${true}">
   		<jsp:attribute name="content">
   			<div class="page-header">
   				<h1><c:out value="${pageTitle}" /></h1>
   			</div>
			
			<div class="row">
				<div class="span3">
					<div class="well" style="padding: 8px 0;">
		   			<ul class="nav nav-list">
						<li class="active">
					   		<a href="#">Internal</a>
					  	</li>
					  	<li class="nav-header">External Services</li>
					  	<c:url var="googlePath" value="${iconPath}/authorities/google.png" />
					  	<li><a href="#"><img src="${googlePath}" class="icon" />Google</a></li>
					  	<c:url var="yahooPath" value="${iconPath}/authorities/yahoo.png" />
					  	<li><a href="#"><img src="${yahooPath}" class="icon" /> Yahoo!</a></li>
					  	<li>
					    	<a href="#">Facebook</a>
					  	</li>
					</ul>
					</div>
				</div>
				
				<div class="span6">
					<c:if test="${not empty lastException}">
						<div class="alert alert-error">
							<fmt:message key="login.error.${lastException}" />
						</div>
					</c:if>
					
					<h2>Internal</h2>
					<services:internalLoginForm />
   				</div>
   				<div class="span3">
   					<div class="alert alert-waring">
   						If you don't have an account
   					</div>
   				</div>
			</div>
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>