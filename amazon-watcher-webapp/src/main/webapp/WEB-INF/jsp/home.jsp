<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml"
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:observations="urn:jsptagdir:/WEB-INF/tags/observation"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:services="urn:jsptagdir:/WEB-INF/tags/services"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
        	xmlns:c="http://java.sun.com/jsp/jstl/core"
        	xmlns:sec="http://www.springframework.org/security/tags">
	<jsp:useBean id="now" class="java.util.Date" />
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<basic:layout hideLogin="${true}">
   		<jsp:attribute name="content">
   			<c:set var="projectName" value="${properties['general.name']}" />
   			<sec:authorize access="isAuthenticated()">   				
   				<sec:authentication property="principal.user.settings.viewMode" var="viewMode" />
   				
   				<c:if test="${not empty requestedViewMode}">
   					<c:set var="viewMode" value="${requestedViewMode}" />
   				</c:if>
   				<c:set var="viewMode" value="${fn:toLowerCase(viewMode) }" />
   				
   				<div class="row itemmenu">
   					<div class="span2 offset9">
   						<div class="btn-group pull-right" data-toggle="buttons-radio" id="viewMode">
   							<button class="btn ${viewMode == 'gallery' ? 'active' : '' }"><i class="icon-th-large"><!-- keep me --></i></button>
  							<button class="btn ${viewMode == 'detail' ? 'active' : '' }"><i class=" icon-th-list"><!-- keep me --></i></button>
   						</div>
   					</div>
   					
   					<div class="span1">
   						<c:url value="/observations/edit" var="newItemUrl" />
   						<a href="${newItemUrl}" class="btn pull-right"><i class="icon-plus"><!-- keep me --></i></a>
   					</div>
   				</div>
   				<c:if test="${viewMode eq 'gallery' }">
   					<c:set var="cssClass" value="row" />
   				</c:if>
   				<c:if test="${welcome}">
   					<div class="hero-unit span8" style="height: 300px">
	   					<h1>
	   						<fmt:message key="help.header">
	   							<fmt:param value="${projectName}" />
	   						</fmt:message>
	   					</h1>
	   					
	   					<p style="margin-top:2em;">
	   						<fmt:message key="help.intro.first">
	   							<fmt:param value="${projectName}" />
	   						</fmt:message>
	   					</p>
	   					
	   					<p>
	   						<fmt:message key="help.intro.second">
	   							<fmt:param value="${projectName}" />
	   						</fmt:message>
	   					</p>
	   					<!-- TODO add tour -->
	   					<a href="#step1" class="btn btn-primary pull-right">Take a tour »</a>
	   				</div>
   				</c:if>
   				
   				<!-- TODO: handle no items -->
	   			<div id="items" class="${cssClass}" data-viewmode="${viewMode}">
		   			<c:forEach var="watchedItem" items="${observations}">
		   				<observations:details observation="${watchedItem}" viewMode="${viewMode}" />
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
	   							<fmt:param value="${projectName}"/>
	   						</fmt:message>
	   					</p>
	   					<c:url var="register" value="/register" />
	   					<a href="${register}" class="btn btn-primary">Register for free »</a>
	   				</div>
	   				</div>
	   				<div class="span4">
	   					<h3>
	   						<fmt:message key="home.signin.header">
	   							<fmt:param value="${projectName}" />
	   						</fmt:message>
	   					</h3>
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