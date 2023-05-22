package com.study.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsImg {
	private int postsImgId;
	private int postsId;
	private String originName;
	private String tempName;
	private String imgSize;
	
}
