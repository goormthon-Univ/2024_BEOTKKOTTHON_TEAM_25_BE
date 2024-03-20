package com.goormthonuniv.ownearth.dto.response;

import java.time.LocalDateTime;

import com.goormthonuniv.ownearth.domain.enums.MissionCategory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberResponseDto {

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class SignUpMemberResponse {

    Long memberId;
    String email;
    String name;
    String earthName;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class LoginMemberResponse {

    Long memberId;
    String accessToken;
    String refreshToken;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class MonthlyMissionStatusResponse {

    Long memberId;
    String name;
    Integer completedMissionCount;
    Integer accumulatedPoint;
    Integer completionRate;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CompletedMissionResponse {

    Long memberMissionId;
    String imageUrl;
    String missionContent;
    MissionCategory missionCategory;
    LocalDateTime completedAt;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class FriendRequestResponse {

    Long requestId;
  }
}
