package com.ccs.conclave.api.cii.response;

import com.ccs.conclave.api.cii.pojo.DBData;
import com.ccs.conclave.api.cii.pojo.Scheme;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class GetCIIDBDataTestEndpointResponse {

    private List<DBData> dbData;

    public GetCIIDBDataTestEndpointResponse(List<DBData> dbData) {
        this.dbData = dbData;
    }
}
