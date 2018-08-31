package com.jobdetails.shant.getjobdetails.Protocol;

import com.uniken.rdna.RDNA;

public interface CheckChallenge {

    void onChallengeResponse(RDNA.RDNAChallenge[] challenges, RDNA.RDNAResponseStatusCode statusCode);

}
