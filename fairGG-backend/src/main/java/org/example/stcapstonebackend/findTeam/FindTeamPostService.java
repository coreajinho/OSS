package org.example.stcapstonebackend.findTeam;

import lombok.RequiredArgsConstructor;
import org.example.stcapstonebackend.findTeam.dto.FindTeamPostRequest;
import org.example.stcapstonebackend.findTeam.dto.FindTeamPostResponse;
import org.example.stcapstonebackend.findTeam.exception.FindTeamPostNotFoundException;
import org.example.stcapstonebackend.findTeam.mapper.FindTeamPostMapper;
import org.example.stcapstonebackend.findTeam.model.FindTeamPost;
import org.example.stcapstonebackend.findTeam.model.PostStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindTeamPostService {

    private final FindTeamPostRepository findTeamPostRepository;
    private final FindTeamPostMapper findTeamPostMapper;

    private FindTeamPost getPostEntity(Long id) {
        return findTeamPostRepository.findById(id)
                .orElseThrow(() -> new FindTeamPostNotFoundException("Cannot find post with id: " + id));
    }

    @Transactional
    public FindTeamPostResponse createPost(FindTeamPostRequest request) {
        FindTeamPost post = findTeamPostMapper.toEntity(request);
        FindTeamPost savedPost = findTeamPostRepository.save(post);
        return findTeamPostMapper.toDto(savedPost);
    }

    @Transactional
    public FindTeamPostResponse updatePost(Long id, FindTeamPostRequest request) {
        FindTeamPost post = getPostEntity(id);
        post.update(request.title(), request.content(), request.writer(), request.tags());
        return findTeamPostMapper.toDto(post);
    }

    @Transactional
    public void deletePost(Long id) {
        findTeamPostRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public FindTeamPostResponse getPost(Long id) {
        FindTeamPost post = getPostEntity(id);
        return findTeamPostMapper.toDto(post);
    }

    @Transactional(readOnly = true)
    public List<FindTeamPostResponse> getAllPosts() {
        return findTeamPostRepository.findAll().stream()
                .map(findTeamPostMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FindTeamPostResponse> getActivePosts() {
        return findTeamPostRepository.findByStatusOrderByCreatedAtDesc(PostStatus.ACTIVE).stream()
                .map(findTeamPostMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FindTeamPostResponse> searchByTitle(String title) {
        return findTeamPostRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(findTeamPostMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FindTeamPostResponse> searchByWriter(String writer) {
        return findTeamPostRepository.findByWriterContainingIgnoreCase(writer).stream()
                .map(findTeamPostMapper::toDto)
                .collect(Collectors.toList());
    }
}
