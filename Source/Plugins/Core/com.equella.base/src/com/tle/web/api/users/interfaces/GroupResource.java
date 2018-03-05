/*
 * Copyright 2017 Apereo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tle.web.api.users.interfaces;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.tle.web.api.interfaces.beans.SearchBean;
import com.tle.web.api.interfaces.beans.UserBean;
import com.tle.web.api.users.interfaces.beans.GroupBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

// Note: EQUELLA overrides the root Path
@Produces(MediaType.APPLICATION_JSON)
@Path("localgroup/")
@Api(value = "Local groups", description = "localgroup")
public interface GroupResource
{
	@GET
	@Path("/{uuid}")
	@ApiOperation("Retrieve a group")
	public GroupBean getGroup(@Context UriInfo uriInfo, @PathParam("uuid") String uuid);

	@GET
	@Path("/{uuid}/user")
	@ApiOperation("Get users in a group")
	public SearchBean<UserBean> getUsersInGroup(@Context UriInfo uriInfo, @PathParam("uuid") String uuid,
		@QueryParam("recursive") boolean recursive);

	@PUT
	@Path("/{uuid}")
	@ApiOperation("Edit a group")
	public Response editGroup(@PathParam("uuid") String uuid, @ApiParam GroupBean group);

	@DELETE
	@Path("/{uuid}")
	@ApiOperation("Delete a group")
	public Response deleteGroup(@PathParam("uuid") String uuid);

	@POST
	@Path("/")
	@ApiOperation("Add a group")
	public Response addGroup(@ApiParam GroupBean group);
}
