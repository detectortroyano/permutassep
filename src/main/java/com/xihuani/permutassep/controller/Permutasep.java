package com.xihuani.permutassep.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import retrofit.Callback;

import com.xihuani.permutassep.common.Constants;
import com.xihuani.permutassep.model.Post;
import com.xihuani.permutassep.service.IPermutasSEPService;
import com.xihuani.permutassep.service.PermutasSEPRestClient;
import com.xihuani.web.common.ControllerUtil;

@Controller
public class Permutasep {
	
	public IPermutasSEPService ipermutasSEPService;	
	
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String listSentences(Map<String, Object> map) {
		map.put("post", new Post());
		Callback<List<Post>> call = new PermutasSEPRestClient().get().getPosts();
		List<Post> post = new PermutasSEPRestClient().get().getListPost();
		/*Map<String, String> params = new HashMap<>();
		params.put("place_from_state", "Monterrey");
		new PermutasSEPRestClient(new GsonConverter(gson)).get().searchPosts(params, new Callback<List<Post>>(){
			@Override
			public void success(List<Post>, Response response){
				
			}
			@Override
			public void failure(RetrofitError error){
				
			}
		});*/
		map.put("postList", call);
		map.put("post_nav_class", "active");
		return Constants.View.PERMUTASSEP_HOME_JSP;	
	}
	
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String createPost(@ModelAttribute("post") Post post, BindingResult result) {
		//ipermutasSEPService.newPost(post);
		return ControllerUtil.redirect(Constants.View.PERMUTASSEP_HOME);
	}
	
}
