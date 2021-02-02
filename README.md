# ccs-conclave-api-tests
This repo has api tests which cover Conclave endpoints. This is a Rest assured based test suit.

i) CII Api Tests - tests endpoints described in https://app.swaggerhub.com/apis/miahnanu/CII/1.0.0#/identities/app.putOrg


#### Acronymns:
COH - Companies House

CHC - Charity Commission

NIC - Northern Ireland Charities

SC - Scottish Charities

DUNS - Duns and Bradstreet

####Tests:
GetSchemesTests: 
    Getting all the supported schemes and verifies the same
    
GetSchemeInfoTests: 
    Get SchemeInfo for different supported registries and verify the response with expected result.

PostSchemesTests: 
    Post call performs the Organisation registration. The test first performs a GetScheme and get 
    the response and pass it to Post as Request payload. Then get the Post response and verifies the
    CII database entries using Test endpoints.
    Note: PostSchemesTests depends on Test endpoints, so test endpoints should be deployed to the
    environment before running these tests.


 

