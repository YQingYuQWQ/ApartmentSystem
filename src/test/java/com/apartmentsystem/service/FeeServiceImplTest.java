package com.apartmentsystem.service;

import com.alipay.api.AlipayApiException;
import com.apartmentsystem.entity.Fee;
import com.apartmentsystem.service.impl.FeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class FeeServiceImplTest {
    @Autowired
    private FeeServiceImpl feeServiceImpl;

    @Test
    public void testInsertFee() throws AlipayApiException {
        Fee fee = new Fee();
        fee.setHouse_number("101");
        fee.setType("power");
        fee.setAmount(BigDecimal.valueOf(500));
        feeServiceImpl.insertFee(fee);
    }


}
