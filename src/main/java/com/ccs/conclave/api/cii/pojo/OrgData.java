package com.ccs.conclave.api.cii.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class OrgData {
    private String legalName;
    private String ciiOrganisationId;
    private ContactPoint contactPoint;
    private Address address;
    private String organisationUri;
    private String rightToBuy;
}
