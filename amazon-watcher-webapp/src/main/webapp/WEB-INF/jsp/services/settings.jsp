<?xml version="1.0" encoding="utf-8"?>
<jsp:root	version="2.0"
			xmlns="http://www.w3.org/1999/xhtml"
        	xmlns:jsp="http://java.sun.com/JSP/Page"
        	xmlns:observations="urn:jsptagdir:/WEB-INF/tags/observation"
        	xmlns:basic="urn:jsptagdir:/WEB-INF/tags/basic"
        	xmlns:bootstrap="urn:jsptagdir:/WEB-INF/tags/utils/bootstrap"
        	xmlns:form="http://www.springframework.org/tags/form"
        	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
        	xmlns:c="http://java.sun.com/jsp/jstl/core"
        	xmlns:fn="http://java.sun.com/jsp/jstl/functions">
    <jsp:directive.page contentType="text/html; charset=utf-8" language="java" pageEncoding="UTF-8" session="true" />
   	
   	<fmt:message key="settings.title" var="pageTitle" />
   	<c:set var="user" value="${userCommand.user}" />
   	
   	<basic:layout pageTitle="${pageTitle}">
   		<jsp:attribute name="content">
   			<div class="page-header">
   				<h1><c:out value="${pageTitle}" /></h1>
   			</div>
   			
   			<div class="row">
   				<c:set var="generalSelected" value="${empty active_tab or active_tab eq 'general'}" />
   				<c:set var="authoritiesSelected" value="${active_tab eq 'authorities'}" />
   				<c:set var="infoServicesSelected" value="${active_tab eq 'infoService'}" />
				<div class="span3">
					<div class="well" style="padding: 8px 0;">
		   			<ul class="nav nav-list nav-toggle">
						<li class="${generalSelected ? 'active' : ''}">
					   		<a href="#general"><i class="icon-user"><!-- keep me --></i><fmt:message key="settings.general" /></a>
					  	</li>
					  	<li class="${authoritiesSelected ? 'active' : ''}">
					  		<a href="#authorities"><i class="icon-check"><!-- keep me --></i><fmt:message key="settings.authorities" /></a>
					  	</li>
					  	<li class="${infoServicesSelected ? 'active' : ''}">
					  		<a href="#infoServices"><i class="icon-info-sign"><!-- keep me --></i><fmt:message key="settings.infoServices" /></a>
					  	</li>
					</ul>
					</div>
				</div>
				<div class="span9">
					<section id="general" class="${generalSelected ? '' : 'hidden'}">
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
			   			
			   			<form:form commandName="userCommand" cssClass="form-horizontal">
			   				
			   			</form:form>
		   			</section>
		   			<section id="authorities" class="${authoritiesSelected ? '' : 'hidden'}">
			   			<h2><fmt:message key="settings.authorities" /></h2>
			   			<div class="sectionInfo">
			   				<fmt:message key="settings.authorities.description" />
			   				
			   			</div>
		   				<ul>
		   				<c:set var="numberAuthorities" value="${fn:length(user.authorities)}" />
		   				<c:forEach var="authority" items="${user.authorities}" varStatus="status">
		   					<c:set var="authorityClass" value="${authority.class.simpleName}" />
		   					<c:url var="authIcon" value="${iconPath}/authorities/${fn:toLowerCase(authority.method)}.png" />
		   					<li class="ajaxDelete">
		   						<img src="${authIcon}" />
		   						<c:if test="${authorityClass == 'PasswordAuthority'}">
		   							<c:out value=" *******" />
		   							
		   							<c:url var="editPassword" value="/settings/authorities/password" />
		   							<form:form method="POST" modelAttribute="passwordAuthorityCommand" cssClass="form-horizontal" action="${editPassword}">
		   								<input type="hidden" name="_method" value="PUT" />
		   								<bootstrap:password labelMessageKey="settings.authorities.password.oldPassword" path="oldPassword" />
		   								<bootstrap:password labelMessageKey="settings.authorities.password.newPassword" path="passwordAuthority.password"></bootstrap:password>
		   								<bootstrap:password labelMessageKey="settings.authorities.password.newPasswordRetype" path="newPasswordRetype"></bootstrap:password>
		   								
		   								<bootstrap:submit messageKey="settings.authorities.password.edit" />
		   							</form:form>
		   						</c:if>
		   					</li>
		   				</c:forEach>
		   				</ul>
		   				<div class="add">
		   				
		   					<fmt:message key="settings.authority.add" />
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
		   			
		   			<section id="infoServices" class="${infoServicesSelected ? '' : 'hidden'}">
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
					   				<li class="ajaxDelete">
					   					<img src="${serviceIcon}" data-service="${infoService}" class="iconSelector" /> - <c:out value="${infoService.settings}" />
					   					<ul class="menu">
					   						<c:url var="serviceUrl" value="/settings/infoServices/${infoService.hash}" />
					   						<li><a href="${serviceUrl}/test"><fmt:message key="settings.infoServices.test" /></a></li>
					   						<li><fmt:message key="settings.infoServices.edit" /></li>
					   						<li><a href="${serviceUrl}?_method=DELETE" class="ajaxDeleteLink"><fmt:message key="settings.infoServices.delete" /></a></li>
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
				   			<c:url var="addServiceUrl" value="/settings/infoServices" />
				   			<c:forEach var="infoService" items="${properties['information.services']}">
				   				<div id="service_${infoService}" class="hidden infoServiceAddEditor">
				   					<fmt:message key="settings.infoServices.${infoService}.info" />
				   					<!-- TODO -->
				   					<form action="${addServiceUrl}" method="POST" class="form-horizontal">
				   						<input type="hidden" name="infoServiceKey" value="${infoService}"/>
				   						<div class="control-group">
				   							<label class="control-label"><fmt:message key="settings.infoServices.${infoService}.settings" />:</label>
				   							<div class="controls">
				   								<input type="text" name="settings" value="" />
				   							</div>
				   						</div>		
				   						
				   						<bootstrap:submit messageKey="settings.infoServices.${infoService}.add" />
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