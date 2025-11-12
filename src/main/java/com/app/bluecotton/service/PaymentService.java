package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.PaymentPrepareRequest;
import com.app.bluecotton.domain.dto.PortOneDTO;
import com.app.bluecotton.domain.dto.PortOneResponse;

import java.util.Map;

public interface PaymentService {



    PortOneDTO processPayment(Map<String, Object> paymentData);

    PortOneResponse preparePayment(PaymentPrepareRequest request);
}
