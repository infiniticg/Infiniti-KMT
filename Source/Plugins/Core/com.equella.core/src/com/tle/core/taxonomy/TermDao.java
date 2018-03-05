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

package com.tle.core.taxonomy;

import java.util.List;

import com.tle.common.taxonomy.Taxonomy;
import com.tle.common.taxonomy.terms.Term;
import com.tle.core.hibernate.dao.GenericDao;

public interface TermDao extends GenericDao<Term, Long>
{
	Term getTerm(Taxonomy taxonomy, String termFullPath);

	Term getTermByUuid(Taxonomy taxonomy, String termUuid);

	List<Term> getRootTerms(Taxonomy taxonomy);

	List<TermResult> getRootTermResults(Taxonomy taxonomy);

	List<String> getRootTermValues(Taxonomy taxonomy);

	List<Term> getChildTerms(Term parentTerm);

	List<TermResult> getChildTermResults(Term parentTerm);

	List<String> getChildTermValues(Term parentTerm);

	String getDataForTerm(Term term, String key);

	List<Term> getAllTermsInOrder(Taxonomy taxonomy);

	/**
	 * @param taxonomy
	 * @param parent
	 * @param termValue
	 * @param index
	 * @return The new term, so you don't have to load it up again if you want
	 *         to use it
	 */
	Term insertNewTerm(Taxonomy taxonomy, Term parent, String termValue, int index);

	void move(Term moveThisTerm, Term parent, int index);

	void renameTermValue(Term term, String newValue);

	void deleteForTaxonomy(Taxonomy taxonomy);

	void validateTerm(Taxonomy taxonomy, Term parent, String termValue);
}
