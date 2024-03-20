package com.goormthonuniv.ownearth.controller;

import java.time.YearMonth;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.goormthonuniv.ownearth.annotation.auth.AuthMember;
import com.goormthonuniv.ownearth.common.BaseResponse;
import com.goormthonuniv.ownearth.converter.MemberConverter;
import com.goormthonuniv.ownearth.domain.enums.MissionCategory;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.domain.member.Member;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.LoginMemberRequest;
import com.goormthonuniv.ownearth.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.CompletedMissionResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.LoginMemberResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.MonthlyMissionStatusResponse;
import com.goormthonuniv.ownearth.dto.response.MemberResponseDto.SignUpMemberResponse;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.service.MemberCommandService;
import com.goormthonuniv.ownearth.service.MemberQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members")
@Tag(name = "😎 Member", description = "사용자 관련 API")
public class MemberController {

  private final MemberCommandService memberCommandService;
  private final MemberQueryService memberQueryService;

  @Operation(summary = "회원가입 API", description = "이메일, 비밀번호를 사용해 회원가입을 진행합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<SignUpMemberResponse> signUpMember(@RequestBody SignUpMemberRequest request) {
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED,
        MemberConverter.toSignUpMemberResponse(memberCommandService.signUpMember(request)));
  }

  @Operation(summary = "로그인 API", description = "이메일, 비밀번호를 사용한 로그인을 진행합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
  })
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<LoginMemberResponse> loginMember(@RequestBody LoginMemberRequest request) {
    return BaseResponse.onSuccess(GlobalErrorCode.CREATED, memberCommandService.login(request));
  }

  @Operation(summary = "내 월간 미션 달성률 조회 API", description = "현재 로그인한 사용자의 월간 달성률을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "성공")
  @GetMapping("/me/missions/monthly")
  public BaseResponse<MonthlyMissionStatusResponse> getMonthlyMissionStatus(
      @Parameter(hidden = true) @AuthMember Member member) {
    Integer completedMissionCount = memberQueryService.getMonthlyMissionStatus(member);
    return BaseResponse.onSuccess(
        MemberConverter.toMonthlyMissionStatusResponse(member, completedMissionCount));
  }

  @Operation(summary = "완료한 미션 목록 조회 API", description = "날짜별, 종류별로 완료한 미션을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "성공")
  @GetMapping("/me/missions/completed")
  public BaseResponse<List<CompletedMissionResponse>> getCompletedMissions(
      @Parameter(hidden = true) @AuthMember Member member,
      @Parameter(description = "날짜별 조회, 형식 yyyy-mm")
          @RequestParam(value = "yearMonth", required = false)
          YearMonth yearMonth,
      @RequestParam(value = "category", required = false) MissionCategory category) {
    List<MemberMission> memberMissions =
        memberQueryService.getMonthlyCompletedMissions(member, yearMonth, category);
    return BaseResponse.onSuccess(MemberConverter.toCompletedMissionResponseList(memberMissions));
  }
}
