package com.amancode.blogappserver.Services.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amancode.blogappserver.Entities.Category;
import com.amancode.blogappserver.Entities.Like;
import com.amancode.blogappserver.Entities.Post;
import com.amancode.blogappserver.Entities.User;
import com.amancode.blogappserver.Exceptions.ResourceNotFoundException;
import com.amancode.blogappserver.Payloads.LikeDTO;
import com.amancode.blogappserver.Payloads.PostDTO;
import com.amancode.blogappserver.Payloads.PostResponse;
import com.amancode.blogappserver.Repositories.CategoryRepo;
import com.amancode.blogappserver.Repositories.PostRepo;
import com.amancode.blogappserver.Repositories.UserRepo;
import com.amancode.blogappserver.Services.PostService;

@Service
public class PostServiceImpl implements PostService {

        @Autowired
        private PostRepo postRepo;

        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private UserRepo userRepo;

        @Autowired
        private CategoryRepo categoryRepo;

        @Override
        public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {

                User user = this.userRepo.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

                Category category = this.categoryRepo.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id",
                                                categoryId));

                Post post = this.modelMapper.map(postDTO, Post.class);
                post.setImageName("default.png");
                post.setAddedDate(new Date());
                post.setUser(user);
                post.setCategory(category);

                Post newPost = this.postRepo.save(post);

                return this.modelMapper.map(newPost, PostDTO.class);
        }

        @Override
        public PostDTO updatePost(PostDTO postDTO, Integer postId) {
                Post post = this.postRepo.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

                Category category = this.categoryRepo.findById(postDTO.getCategory().getCategoryId()).get();
                if (postDTO.getImageName() != null && !postDTO.getImageName().isEmpty()) {
                        post.setImageName(postDTO.getImageName());
                }

                post.setTitle(postDTO.getTitle());
                post.setContent(postDTO.getContent());
                // post.setImageName(postDTO.getImageName());
                post.setCategory(category);
                post.setActive(postDTO.getActive());

                Post updatedPost = this.postRepo.save(post);
                return this.modelMapper.map(updatedPost, PostDTO.class);

        }

        @Override
        public void deletePost(Integer postId) {
                Post post = this.postRepo.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

                this.postRepo.delete(post);
        }

        @Override
        public PostDTO getPostById(Integer postId) {
                Post post = this.postRepo.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

                return this.modelMapper.map(post, PostDTO.class);
        }

        @Override
        public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

                Sort sort = null;
                if (sortDir.equalsIgnoreCase("asc")) {
                        sort = Sort.by(sortBy).ascending();
                } else {
                        sort = Sort.by(sortBy).descending();
                }

                Pageable p = PageRequest.of(pageNumber, pageSize, sort);

                Page<Post> pagePost = this.postRepo.findAll(p);
                List<Post> allPosts = pagePost.getContent();

                List<PostDTO> postDTOs = allPosts.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());

                PostResponse postResponse = new PostResponse();
                postResponse.setContent(postDTOs);
                postResponse.setPageNumber(pagePost.getNumber());
                postResponse.setPageSize(pagePost.getSize());
                postResponse.setTotalElements(pagePost.getTotalElements());
                postResponse.setTotalPages(pagePost.getTotalPages());
                postResponse.setLastPage(pagePost.isLast());
                postResponse.setFirstPage(pagePost.isFirst());
                postResponse.setEmptyPage(pagePost.isEmpty());

                return postResponse;
        }

        @Override
        public PostResponse getPaginatedActivePosts(Integer pageNumber, Integer pageSize, String sortBy,
                        String sortDir) {
                Sort sort = null;
                if (sortDir.equalsIgnoreCase("asc")) {
                        sort = Sort.by(sortBy).ascending();
                } else {
                        sort = Sort.by(sortBy).descending();
                }

                Pageable p = PageRequest.of(pageNumber, pageSize, sort);

                Page<Post> pagePost = this.postRepo.findAllByActiveTrue(p);
                List<Post> allPosts = pagePost.getContent();

                List<PostDTO> postDTOs = allPosts.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());

                PostResponse postResponse = new PostResponse();
                postResponse.setContent(postDTOs);
                postResponse.setPageNumber(pagePost.getNumber());
                postResponse.setPageSize(pagePost.getSize());
                postResponse.setTotalElements(pagePost.getTotalElements());
                postResponse.setTotalPages(pagePost.getTotalPages());
                postResponse.setLastPage(pagePost.isLast());
                postResponse.setFirstPage(pagePost.isFirst());
                postResponse.setEmptyPage(pagePost.isEmpty());

                return postResponse;
        }

        @Override
        public List<PostDTO> getPostsByCategory(Integer categoryId) {

                Category category = this.categoryRepo.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id",
                                                categoryId));

                List<Post> posts = this.postRepo.findByCategory(category);

                List<PostDTO> postDTOs = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());

                return postDTOs;
        }

        @Override
        public List<PostDTO> getPostsByUser(Integer userId) {

                User user = this.userRepo.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

                List<Post> posts = this.postRepo.findByUser(user);

                List<PostDTO> postDTOs = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());

                return postDTOs;
        }

        @Override
        public List<PostDTO> searchPosts(String keyword) {
                List<Post> posts = this.postRepo.findByTitleContaining(keyword);

                List<PostDTO> postDTOs = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());

                return postDTOs;
        }

        @Override
        public List<PostDTO> getAllPostsWithOutPage() {

                List<Post> posts = this.postRepo.findAll();
                List<PostDTO> postDTOs = posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());

                return postDTOs;
        }

        // Get Active Posts

        @Override
        public List<PostDTO> getActivePosts() {
                List<Post> activePosts = this.postRepo.findByActive(true);
                return activePosts.stream()
                                .map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());
        }

        @Override
        public List<PostDTO> getActivePostsOfCategory(Category category) {
                List<Post> activePostsInCategory = this.postRepo.findByCategoryAndActive(category, true);
                return activePostsInCategory.stream()
                                .map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());
        }

        // View Posts
        // public void incrementViewCount(Integer postId) {
        // Post post = this.postRepo.findById(postId)
        // .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

        // if (post != null) {
        // post.setViewCount(post.getViewCount() + 1);
        // postRepo.save(post);
        // }
        // }

        @Override
        public List<PostDTO> getAllActivePostsWithLikes() {
                List<Post> activePosts = this.postRepo.findByActive(true);

                return activePosts.stream()
                                .map(post -> {
                                        PostDTO postDTO = this.modelMapper.map(post, PostDTO.class);
                                        List<LikeDTO> likeDTOs = post.getLike().stream()
                                                        .map(like -> this.modelMapper.map(like, LikeDTO.class))
                                                        .collect(Collectors.toList());
                                        postDTO.setLikes(likeDTOs);
                                        return postDTO;
                                })
                                .collect(Collectors.toList());
        }

        @Override
        public int getTotalLikesOnActivePosts() {
                List<Post> activePosts = this.postRepo.findByActive(true);
                int totalLikes = 0;
                for (Post post : activePosts) {
                        totalLikes += post.getLike().size();
                }
                return totalLikes;
        }

}
