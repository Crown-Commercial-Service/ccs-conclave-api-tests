package com.ccs.conclave.api.cii.response;

import com.ccs.conclave.api.cii.pojo.OrgIdentifier;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostSFInfoResponse {
    private OrgIdentifier orgIdentifier;

    public PostSFInfoResponse(OrgIdentifier ccsOrgId) {
        this.orgIdentifier = ccsOrgId;
    }
}
