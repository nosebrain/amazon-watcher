<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml"
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:observations="urn:jsptagdir:/WEB-INF/tags/observation"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:services="urn:jsptagdir:/WEB-INF/tags/services"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:c="http://java.sun.com/jsp/jstl/core"
        	xmlns:sec="http://www.springframework.org/security/tags">
	<jsp:useBean id="now" class="java.util.Date" />
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<basic:layout hideLogin="${true}">
   		<jsp:attribute name="content">
   			<sec:authorize access="isAuthenticated()">
   				<c:url value="/observations/edit" var="newItemUrl" />
   				<a href="${newItemUrl}" class="btn pull-right"><i class="icon-plus"><!-- keep me --></i></a>
   				<div class="page-header">
   					<h1>
   						<fmt:message key="home.header" /><c:out value=" " />
   						<small>
	   					<c:choose>
							<c:when test="${not empty lastUpdateDate}">
								<fmt:formatNumber value="${(now.time - lastUpdateDate.time) / (36000)}" maxFractionDigits="0" var="minutes" />
								<c:set var="messageKey" value="updater.lastupdated.minutes" />
								<c:if test="${minutes == 1}">
									<c:set var="messageKey" value="updater.lastupdated.minutes" />
								</c:if>
								<fmt:message key="${messageKey}">
									<fmt:param value="${minutes}" />
								</fmt:message>
							</c:when>
			 				<c:otherwise>
			 					<fmt:message key="updater.notupdated" />
			 				</c:otherwise>
		 					<!-- TODO: next update -->
						</c:choose>
	   					</small>
   					</h1>
   				</div>
   			
	   			<div id="items">
	   				<!--
	   				<ul class="sortMenu">
	   					<li>Name (<a href="?sortBy=name">asc</a> | <a href="?sortBy=name">desc</a>)</li>
	   					<li>Last update (<a href="?sortBy=lastupdate">asc</a> | <a href="?sortBy=lastupdate">desc</a>)</li>
	   				</ul>
	   				-->
		   			<c:forEach var="watchedItem" items="${observations}">
		   				<observations:details observation="${watchedItem}" />
		   			</c:forEach>
		   			
	   			</div>
   			</sec:authorize>
   			
   			<sec:authorize access="isAnonymous()">
   				<div id="introduction">
   				<div class="row">
   					<div class="span8">
	   				<div class="hero-unit">
	   					<h1><fmt:message key="home.overview" /></h1>
	   					
	   					<p>
	   						<fmt:message key="system.about">
	   							<fmt:param value="${properties['general.name']}"/>
	   						</fmt:message>
	   					</p>
	   					<c:url var="register" value="/register" />
	   					<a href="${register}" class="btn btn-primary">Register for free »</a>
	   				</div>
	   				</div>
	   				<div class="span4">
	   					<h3>Sign in to ${projectName}</h3>
	   					<c:url var="loginUrl" value="/login_internal" />
   						<form action="${loginUrl}" method="post" class="form-horizontal">
   							<div class="control-group">
   								<input type="text" name="name" placeholder="username" />
   							</div>
   							<div class="control-group">
   								<input type="password" name="secret" placeholder="password" />
   							</div>
   							
   							<input type="submit" value="login" class="btn btn-primary" />
   						</form>
	   				</div>
	   			</div>
	   				<div class="row">
	   					<div class="span4">
	   						<h2>Save money</h2>
	   						<p>
	   							Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.
	   						</p>
	   					</div>
	   					<div class="span4">
	   						<h2>Notification</h2>
	   						<p>
	   							Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.
	   						</p>
	   					</div>
	   					<div class="span4">
	   						<h2>History</h2>
	   						<p>
	   							Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.
	   						</p>
	   					</div>
	   				</div>
   				</div>
   			</sec:authorize>
   			
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>