package com.ccs.conclave.api.cii.response;

import com.ccs.conclave.api.cii.pojo.OrgIdentifier;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostSchemeInfoResponse {
    private OrgIdentifier orgIdentifier;

    public PostSchemeInfoResponse(OrgIdentifier ccsOrgId) {
        this.orgIdentifier = ccsOrgId;
    }
}
