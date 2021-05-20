package com.ccs.conclave.api.cii.response;

import com.ccs.conclave.api.cii.pojo.OrgIdentifier;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostSFInfoResponse {
    private List<OrgIdentifier> orgIdentifier;

    public PostSFInfoResponse(List<OrgIdentifier> ccsOrgId) {
        this.orgIdentifier = ccsOrgId;
    }
}
