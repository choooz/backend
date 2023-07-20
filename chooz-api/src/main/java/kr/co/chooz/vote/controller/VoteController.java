package kr.co.chooz.vote.controller;

import kr.co.chooz.vote.dto.VoteInfo;
import kr.co.chooz.vote.dto.request.UpdateVoteRequest;
import kr.co.chooz.vote.dto.response.GetVoteResponse;
import kr.co.chooz.vote.port.in.VoteUseCase;
import kr.co.chooz.vote.dto.request.CreateVoteRequest;
import kr.co.chooz.vote.dto.response.CreateVoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes")
@Tag(name = "vote", description = "vote api")
public class VoteController {

    private final VoteUseCase voteUseCase;

    @Operation(summary = "투표 생성", description = "헤더에 토큰 담고, 바디에 {title, titleA, titleB, imageA, imageB, filteredGender, filteredAge, filteredMbti} json 형식으로 보내주시면 됩니다.")
    @PostMapping("")
    public ResponseEntity<CreateVoteResponse> createVote(@Valid @RequestBody CreateVoteRequest request, @RequestAttribute Long userId) {

        Long voteId = voteUseCase.createVote(request.toDomain(userId));

        return new ResponseEntity(new CreateVoteResponse(voteId), HttpStatus.CREATED);
    }

    @Operation(summary = "투표 단건 조회", description = "파라미터에 voteId 보내주시면 됩니다.")
    @GetMapping("/{voteId}")
    public ResponseEntity<GetVoteResponse> getVote(@PathVariable Long voteId) {

        VoteInfo voteInfo = voteUseCase.getVote(voteId);

        return new ResponseEntity(new GetVoteResponse(voteInfo), HttpStatus.OK);
    }

    @Operation(summary = "투표 수정", description = "파라미터에 voteId, 바디에 {title, detail, category, titleA, titleB} json 형식으로 보내주시면 됩니다.")
    @PatchMapping("/{voteId}")
    public ResponseEntity updateVote(@PathVariable("voteId") Long voteId, @Valid @RequestBody UpdateVoteRequest request, @RequestAttribute Long userId) {

        voteUseCase.updateVote(request.toDomain(userId, voteId));

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "투표 삭제", description = "헤더에 토큰 담고, 파라미터에 voteId 보내주시면 됩니다")
    @DeleteMapping("/{voteId}")
    public ResponseEntity deleteVote(@PathVariable("voteId") Long voteId, @RequestAttribute Long userId) {
        voteUseCase.deleteVote(voteId, userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
