/*
Licensed to Jasig under one or more contributor license
agreements. See the NOTICE file distributed with this work
for additional information regarding copyright ownership.
Jasig licenses this file to you under the Apache License,
Version 2.0 (the "License"); you may not use this file
except in compliance with the License. You may obtain a
copy of the License at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied. See the License for the
specific language governing permissions and limitations
under the License.
 **/
package org.jasig.portlet.cms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.cms.model.Post;
import org.jasig.portlet.cms.model.RepositorySearchOptions;
import org.jasig.portlet.cms.model.repository.RepositoryDao;
import org.jasig.portlet.cms.view.PortletView;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractCommandController;
import org.springframework.web.portlet.util.PortletUtils;

public class SearchController extends AbstractCommandController {
	private final Log _logger = LogFactory.getLog(getClass());
	private RepositoryDao _repositoryDao = null;

	public void setRepositoryDao(final RepositoryDao repositoryDao) {
		_repositoryDao = repositoryDao;
	}

	private RepositoryDao geRepositoryDao() {
		return _repositoryDao;
	}

	@Override
	protected void handleAction(final ActionRequest actionrequest, final ActionResponse actionresponse,
	        final Object obj, final BindException bindexception) throws Exception {
		PortletUtils.clearAllRenderParameters(actionresponse);
	}

	@Override
	protected ModelAndView handleRender(final RenderRequest renderrequest,
	        final RenderResponse renderresponse, final Object obj, final BindException bindexception)
	        throws Exception {

		final RepositorySearchOptions options = (RepositorySearchOptions) obj;

		_logger.debug("Executing search");
		final List<Post> results = geRepositoryDao().search(options);

		final Map<String, Object> model = new HashMap<String, Object>();

		if (results != null && results.size() > 0) {
			_logger.debug("Number of search results found: " + results.size());
			model.put("searchResults", results);
			model.put("portletPreferences", new PortletPreferencesWrapper(renderrequest));
		} else
			_logger.debug("No results are found");

		_logger.debug("Returning search results");
		final ModelAndView modelAndView = new ModelAndView(PortletView.VIEW_SEARCH_RESULTS, model);

		return modelAndView;
	}

}
