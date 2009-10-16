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
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.jasig.portlet.cms.model.Post;
import org.jasig.portlet.cms.model.RepositoryDao;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

public class ViewPostController extends AbstractController {
	private RepositoryDao _repositoryDao = null;

	public void setRepositoryDao(final RepositoryDao repositoryDao) {
		_repositoryDao = repositoryDao;
	}

	private RepositoryDao geRepositoryDao() {
		return _repositoryDao;
	}

	@Override
	protected ModelAndView handleRenderRequestInternal(final RenderRequest request,
	        final RenderResponse response) throws Exception {

		System.out.println("handleRenderRequestInternal() in view");

		final Map<String, Object> map = new HashMap<String, Object>();
		final PortletPreferencesWrapper pref = new PortletPreferencesWrapper(request);
		final Post post = geRepositoryDao().getPost(pref.getPortletRepositoryRoot());

		map.put("post", post);
		final ModelAndView view = new ModelAndView("viewPost", map);
		return view;

	}
}