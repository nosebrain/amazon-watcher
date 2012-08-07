<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml"
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:observations="urn:jsptagdir:/WEB-INF/tags/observation"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:bootstrap="urn:jsptagdir:/WEB-INF/tags/utils/bootstrap"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:c="http://java.sun.com/jsp/jstl/core">
        	
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<fmt:message key="settings.title" var="pageTitle" />
   	<c:set var="user" value="${userCommand.user}" />
   	
   	<basic:layout pageTitle="${pageTitle}">
   		<jsp:attribute name="content">
   			<div class="page-header">
   				<h1><c:out value="${pageTitle}" /></h1>
   			</div>
   			
   			<div class="row">
				<div class="span3">
					<div class="well" style="padding: 8px 0;">
		   			<ul class="nav nav-list nav-toggle">
						<li class="active">
					   		<a href="#general"><i class="icon-user"><!-- keep me --></i><fmt:message key="settings.general" /></a>
					  	</li>
					  	<li>
					  		<a href="#authorities"><i class="icon-check"><!-- keep me --></i><fmt:message key="settings.authorities" /></a>
					  	</li>
					  	<li>
					  		<a href="#infoServices"><i class="icon-info-sign"><!-- keep me --></i><fmt:message key="settings.infoServices" /></a>
					  	</li>
					</ul>
					</div>
				</div>
				<div class="span9">
					<section id="general">
			   			<h2><fmt:message key="settings.general" /></h2>
			   			<div class="sectionInfo">
			   				<fmt:message key="settings.general.description" />
			   			</div>
			   			
			   			<div>
			   				<fmt:message key="settings.apiKey" />
			   				<code>${user.apiKey}</code>
			   				
			   				<fmt:message key="settings.apiKey.info" />
			   			</div>
			   			
			   			<form:form commandName="userCommand" cssClass="form-horizontal">
			   				<bootstrap:input labelMessageKey="user.mail" path="user.mail" />
			   				
			   				<bootstrap:input labelMessageKey="user.settings.minDelta" path="user.settings.minDelta" />
			   			</form:form>
		   			</section>
		   			<section id="authorities" class="hidden">
			   			<h2><fmt:message key="settings.authorities" /></h2>
			   			<div class="sectionInfo">
			   				<fmt:message key="settings.authorities.description" />
			   				
			   			</div>
		   			
		   				<c:forEach var="authority" items="${user.authorities}">
		   					<c:url var="authIcon" value="${iconPath}/authorities/${authority.method}.png" />
		   					<img src="${authIcon}" />
		   					
		   					<!-- TODO: delete button -->
		   					<!-- TODO: edit menu -->
		   				</c:forEach>
		   				
		   				<div class="add">
		   					<!-- TODO: remove non multiple -->
		   					<div class="overview">
			   					<c:forEach var="supportedAuthority" items="${properties['general.authorities']}">
			   						<c:url var="authIcon" value="${iconPath}/authorities/${supportedAuthority}.png" />
			   						<fmt:message var="supportedAuthorityInfo" key="settings.authorities.${supportedAuthority}.add.description"/>
			   						<img src="${authIcon}" class="iconSelector" title="${supportedAuthorityInfo}" />
			   					</c:forEach>
		   					</div>
		   					
		   					<c:forEach var="supportedAuthority" items="${properties['general.authorities']}">
		   						<div id="authority_${supportedAuthority}" class="hidden">
		   							
		   						</div>
		   					</c:forEach>
		   				</div>
		   			</section>
		   			
		   			
		   			
		   			<section id="infoServices" class="hidden">
			   			<h2><fmt:message key="settings.infoServices"/></h2>
			   			<div class="sectionInfo">
			   				<fmt:message key="settings.infoServices.description" />
			   			</div>
			   			<c:set var="infoServices" value="${user.settings.infoServices}" />
			   			<c:set var="hasInfoServices" value="${not empty infoServices}" />
			   			
			   			<c:if test="${hasInfoServices}">
			   				<fmt:message key="settings.infoServices.current" />
			   				<ul>
					   			<c:forEach var="infoService" items="${infoServices}">
					   				<c:url var="serviceIcon" value="${iconPath}/services/${infoService.infoServiceKey}.png" />
					   				<li>
					   					<img src="${serviceIcon}" data-service="${infoService}" class="iconSelector" /> - <c:out value="${infoService.settings}" />
					   					<ul class="menu">
					   						<li><fmt:message key="settings.infoServices.edit" /></li>
					   						<li><fmt:message key="settings.infoServices.delete" /></li>
					   					</ul>
					   				</li>
					   			</c:forEach>
				   			</ul>
			   			</c:if>
			   			
			   			<div>
				   			<c:if test="${!hasInfoServices }">
				   				<fmt:message key="settings.infoServices.noServices" />
				   			</c:if>
				   			
				   			<fmt:message key="settings.infoServices.add" />
			   			</div>
			   			
			   			<div class="infoServices">
			   				<div class="overview">
					   			<c:forEach var="infoService" items="${properties['information.services']}">
					   				<c:url var="serviceIcon" value="${iconPath}/services/${infoService}.png" />
					   				
					   				<img src="${serviceIcon}" data-service="${infoService}" class="infoServiceSelector" />
					   			</c:forEach>
				   			</div>
				   			<c:url var="addService" value="/settings/infoServices" />
				   			<c:forEach var="infoService" items="${properties['information.services']}">
				   				<div id="service_${infoService}" class="hidden infoServiceAddEditor">
				   					<fmt:message key="settings.infoServices.${infoService}.info" />
				   					<!-- TODO -->
				   					<form action="${addService}" method="POST">
				   						<input type="hidden" name="infoServiceKey" value="${infoService}"/>
				   						<div>
				   							<p>
				   								<label><fmt:message key="settings.infoServices.${infoService}.settings" />:</label>
				   								<input type="text" name="settings" value="" />
				   							</p>
				   						</div>		
				   						
				   						<fmt:message key="settings.infoServices.${infoService}.add" var="addService" />
				   						<input type="submit" value="${addService}" />
				   					</form>
				   				</div>
				   			</c:forEach>
			   			</div>
		   			</section>
				</div>
			</div>
   		</jsp:attribute>
   	</basic:layout>
</jsp:root>