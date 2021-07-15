package com.ccs.conclave.api.cii.response;

import com.ccs.conclave.api.cii.pojo.OrgIdentifier;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostSchemeInfoResponse {
    private List<OrgIdentifier> orgIdentifier;

    public PostSchemeInfoResponse(List<OrgIdentifier> ccsOrgId) {
        this.orgIdentifier = ccsOrgId;
    }
}

