<#include "/com.tle.web.freemarker@/macro/sections.ftl"/>
<#include "/com.tle.web.sections.standard@/textfield.ftl"/>
<#include "/com.tle.web.sections.equella@/component/button.ftl"/>

<@css path="logon.css" hasRtl=true />

<div class="area">
	<h2>${b.key("logon.title")}</h2>
	
	<#if m.failed??>
		<p class="warning">${b.gkey(m.failed)}</p>
	</#if>
	<#if m.error??>
		<p class="warning">${b.key('logon.problems')}</p>
		<p class="warning">${m.error?html}</p>
	</#if>

	<noscript>
		<p class="warning">${b.key("logon.enablejs")}</p>
	</noscript>
	<p id="cookieWarning" class="warning" style="display: none">${b.key("logon.enablecookies")}</p>
	
	<p>
		<label for="username">${b.key("logon.username")}</label>
		<@textfield id="username" section=s.username autoSubmitButton=s.logonButton/>
	</p>
	<p>
		<label for="password">${b.key("logon.password")}</label>
		<@textfield id="password" section=s.password password=true autoSubmitButton=s.logonButton/>
		<@button section=s.logonButton class="loginbutton" size="medium" />
	</p>
	<#if m.childSections??>
		<@render m.childSections/>
	</#if>
	
	<#list m.loginLinks as link>
		<@render link />
	</#list>
</div>
