package boogakcong.domain.cafe._owner.controller;

import boogakcong.domain.cafe._owner.entity.CafeOwner;
import boogakcong.domain.cafe._owner.service.CafeOwnerService;
import boogakcong.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cafes/owners")
public class CafeOwnerManageController {
    private final CafeOwnerService cafeOwnerService;


    @Comment("카페 소유자 신청")
    @Secured({"ROLE_NORMAL_USER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER"})
    @PostMapping("/request")
    public ResponseEntity<?> requestCafeOwner(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(name = "cafeId") Long cafeId
    ) {
        cafeOwnerService.requestCafeOwner(
                userDetails.getUserId(),
                cafeId
        );
        return ResponseEntity.ok().build();
    }

    @Comment("회원이 카페 소유자인지 확인")
    @Secured({"ROLE_NORMAL_USER", "ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER", "ROLE_CAFE_OWNER"})
    @GetMapping("/check")
    public ResponseEntity<CafeOwner> checkCafeOwner(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(cafeOwnerService.checkCafeOwner(
                userDetails.getUserId()
        ));
    }

    @Comment("카페 소유자 신청 조회")
    @Secured({"ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER"})
    @GetMapping("/request")
    public ResponseEntity<List<CafeOwner>> getCafeOwnerRequests(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(cafeOwnerService.getCafeOwnerRequests());
    }

    @Comment("카페 소유자 승인/반려")
    @Secured({"ROLE_ADMIN", "ROLE_COMMUNITY_MANAGER"})
    @PostMapping("/accept")
    public ResponseEntity<?> acceptCafeOwner(
            @RequestParam(name = "requestId") Long requestId,
            @RequestParam(name = "accept") boolean accept
    ) {
        cafeOwnerService.acceptCafeOwner(
                requestId,
                accept
        );
        return ResponseEntity.ok().build();
    }


}
