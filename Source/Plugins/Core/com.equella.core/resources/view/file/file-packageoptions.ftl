<#include "/com.tle.web.freemarker@/macro/sections.ftl" />
<#include "/com.tle.web.sections.standard@/list.ftl" />
<#include "/com.tle.web.sections.standard@/radio.ftl"/>
<#include m.commonIncludePath />

<@detailArea>
	<h4><@bundlekey "handlers.file.label.packageoption"/></h4>
	<@boollist section=s.packageOptions ; option, check>
		<div class="input radio">
			<@radio check />
		</div>
	</@boollist>
</@detailArea>