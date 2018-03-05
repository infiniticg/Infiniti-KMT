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

package com.tle.core.security;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ListMultimap;
import com.tle.beans.security.AccessEntry;
import com.tle.beans.security.AccessExpression;
import com.tle.common.Triple;
import com.tle.common.security.PrivilegeTree.Node;
import com.tle.common.security.TargetList;
import com.tle.common.security.remoting.RemoteTLEAclManager;
import com.tle.common.usermanagement.user.UserState;

/**
 * @author Nicholas Read
 */
public interface TLEAclManager extends RemoteTLEAclManager
{
	/**
	 * Removes objects from a collection that the user has not been granted the
	 * given privileges. (All privileges must be met, this is not an OR)
	 */
	<T> Collection<T> filterNonGrantedObjects(Collection<String> privileges, Collection<T> domainObjs);

	/**
	 * Same as filterNonGrantedObjects, but much easier to use.
	 * @param privilege
	 * @param domainObj
	 * @return true if the privilege is granted
	 */
	<T> boolean checkPrivilege(String privilege, T domainObj);

	/**
	 * Return a map of domain objects to maps of privileges.
	 */
	<T> Map<T, Map<String, Boolean>> getPrivilegesForObjects(Collection<String> privileges, Collection<T> domainObjs);

	/**
	 * Filters out privileges that the user does not have for any domain object.
	 */
	Set<String> filterNonGrantedPrivileges(Collection<String> privileges, boolean includePossibleOwnerAcls);

	/**
	 * Adds an access entry to the system.
	 */
	void addAccessEntry(Object domainObj, Node privilegeNode, boolean grant, boolean override, String privilege,
		String expression, Date expiry);

	AccessExpression retrieveOrCreate(String expression);

	/**
	 * Sets a list of ACL details for a given target, clearing any previously
	 * set.
	 */
	void setTargetList(Node privilegeNode, Object domainObj, TargetList targetList);

	/**
	 * Gets all of the ACL expressions for the given user state.
	 */
	Triple<Collection<Long>, Collection<Long>, Collection<Long>> getAclExpressions(UserState userState,
		boolean enableIpReferAcl);

	/**
	 * Gets all of the ACL expressions for the given user state.
	 */
	Triple<Collection<Long>, Collection<Long>, Collection<Long>> getAclExpressions(UserState userState);

	/**
	 * Deletes things like workflow tasks, item metadata rules, etc...
	 */
	void deleteAllEntityChildren(Node type, long id);

	void deleteExpiredAccessEntries();

	/**
	 * Transposes user IDs for all matching expressions.
	 */
	void userIdChanged(String fromUserId, String toUserId);

	/**
	 * Transposes group IDs for all matching expressions.
	 */
	void groupIdChanged(String fromGroupId, String toGroupId);

	ListMultimap<String, AccessEntry> getExistingEntriesForVirtualNodes(Node... nodes);

	String getKeyForVirtualNode(Node node, Object domainObject);

}