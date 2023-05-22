package com.study.oauth2.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.study.oauth2.dto.posts.RegisterPostsReqDto;
import com.study.oauth2.entity.Posts;
import com.study.oauth2.entity.PostsImg;
import com.study.oauth2.repository.PostsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostsService {
	@Value("${file.path}")
	private String filePath;
	
	private final PostsRepository postsRepository;
	public int registerPosts(RegisterPostsReqDto registerPostsReqDto) {
		Posts posts = registerPostsReqDto.toEntity();
		System.out.println(posts);
		System.out.println(uploadFile(posts.getPostsId(), registerPostsReqDto.getImgFiles()));
		
//		uploadFile(posts.getPostsId(), registerPostsReqDto.getImgFiles());
		return postsRepository.registerPostsImgs(uploadFile(posts.getPostsId(), registerPostsReqDto.getImgFiles()));
	}
	
	private List<PostsImg> uploadFile(int postId,List<MultipartFile> files) {
		if(files ==null) {
			return null;
		}
		
		List<PostsImg> postsFiles = new ArrayList<>();
		files.forEach(file -> {
			String originFileName = file.getOriginalFilename();
			String extension = originFileName.substring(originFileName.lastIndexOf("."));
			//파일 이름은 UUID를 사용하여 계속 랜덤하게 만들어줌
			String tempFileName = UUID.randomUUID().toString().replaceAll("-", "") + extension;
			// 파일이 저장될 경로를 만들어 주는거임
			Path uploadPath = Paths.get(filePath + "post/" + tempFileName);
			
			//만약 경로에 폴더가 없다면 만들어주는 역할
			File f = new File(filePath + "post");
			if(!f.exists()) {
				f.mkdirs();
			}
			//파일을 파일에 넣어주는거임
			try {
				Files.write(uploadPath, file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			postsFiles.add(PostsImg.builder()
					.postsId(postId)
					.originName(originFileName)
					.imgSize(Long.toString(file.getSize()))
					.tempName(tempFileName)
					.build()); 
			
		});
		return postsFiles;
		
	}
}
