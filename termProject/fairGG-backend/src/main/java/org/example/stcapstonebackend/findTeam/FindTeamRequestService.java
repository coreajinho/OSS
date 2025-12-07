package org.example.stcapstonebackend.findTeam;

import lombok.RequiredArgsConstructor;
import org.example.stcapstonebackend.findTeam.dto.FindTeamRequestRequest;
import org.example.stcapstonebackend.findTeam.dto.FindTeamRequestResponse;
import org.example.stcapstonebackend.findTeam.exception.DuplicateAcceptanceException;
import org.example.stcapstonebackend.findTeam.exception.FindTeamPostNotFoundException;
import org.example.stcapstonebackend.findTeam.exception.FindTeamRequestNotFoundException;
import org.example.stcapstonebackend.findTeam.exception.InvalidTagSelectionException;
import org.example.stcapstonebackend.findTeam.mapper.FindTeamRequestMapper;
import org.example.stcapstonebackend.findTeam.model.FindTeamPost;
import org.example.stcapstonebackend.findTeam.model.FindTeamRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FindTeamRequestService {

    private final FindTeamPostRepository findTeamPostRepository;
    private final FindTeamRequestRepository findTeamRequestRepository;
    private final FindTeamRequestMapper findTeamRequestMapper;

    public FindTeamRequestResponse createRequest(Long postId, FindTeamRequestRequest request) {
        FindTeamPost post = findTeamPostRepository.findById(postId)
                .orElseThrow(() -> new FindTeamPostNotFoundException("Cannot find post with id: " + postId));

        if (!post.getTags().contains(request.desiredTag())) {
            throw new InvalidTagSelectionException("Selected tag is not available for this post");
        }

        if (post.isTagAccepted(request.desiredTag())) {
            throw new InvalidTagSelectionException("This tag is already accepted");
        }

        FindTeamRequest findTeamRequest = FindTeamRequest.builder()
                .content(request.content())
                .writer(request.writer())
                .desiredTag(request.desiredTag())
                .build();

        post.addRequest(findTeamRequest);
        FindTeamRequest savedRequest = findTeamRequestRepository.save(findTeamRequest);

        return findTeamRequestMapper.toDto(savedRequest);
    }

    @Transactional(readOnly = true)
    public List<FindTeamRequestResponse> getRequestsByPostId(Long postId) {
        if (!findTeamPostRepository.existsById(postId)) {
            throw new FindTeamPostNotFoundException("Cannot find post with id: " + postId);
        }

        return findTeamRequestRepository.findByFindTeamPostId(postId).stream()
                .map(findTeamRequestMapper::toDto)
                .toList();
    }

    public FindTeamRequestResponse updateRequest(Long postId, Long requestId, FindTeamRequestRequest request) {
        FindTeamRequest findTeamRequest = findTeamRequestRepository.findById(requestId)
                .orElseThrow(() -> new FindTeamRequestNotFoundException("Cannot find request with id: " + requestId));

        if (!findTeamRequest.getFindTeamPost().getId().equals(postId)) {
            throw new IllegalArgumentException("Post ID mismatch");
        }

        FindTeamPost post = findTeamRequest.getFindTeamPost();

        if (!post.getTags().contains(request.desiredTag())) {
            throw new InvalidTagSelectionException("Selected tag is not available for this post");
        }

        if (!findTeamRequest.getDesiredTag().equals(request.desiredTag())
                && post.isTagAccepted(request.desiredTag())) {
            throw new InvalidTagSelectionException("This tag is already accepted");
        }

        findTeamRequest.update(request.content(), request.writer(), request.desiredTag());

        return findTeamRequestMapper.toDto(findTeamRequest);
    }

    public void deleteRequest(Long postId, Long requestId) {
        FindTeamRequest request = findTeamRequestRepository.findById(requestId)
                .orElseThrow(() -> new FindTeamRequestNotFoundException("Cannot find request with id: " + requestId));

        if (!request.getFindTeamPost().getId().equals(postId)) {
            throw new IllegalArgumentException("Post ID mismatch");
        }

        findTeamRequestRepository.delete(request);
    }

    public FindTeamRequestResponse toggleAcceptance(Long postId, Long requestId) {
        FindTeamRequest request = findTeamRequestRepository.findById(requestId)
                .orElseThrow(() -> new FindTeamRequestNotFoundException("Cannot find request with id: " + requestId));

        if (!request.getFindTeamPost().getId().equals(postId)) {
            throw new IllegalArgumentException("Post ID mismatch");
        }

        FindTeamPost post = request.getFindTeamPost();

        if (request.getIsAccepted()) {
            request.cancelAccept();
            post.cancelAcceptance(requestId);
        } else {
            if (post.isTagAccepted(request.getDesiredTag())) {
                throw new DuplicateAcceptanceException("This tag is already accepted by another request");
            }

            request.accept();
            post.acceptRequest(requestId, request.getDesiredTag());
        }

        return findTeamRequestMapper.toDto(request);
    }
}
