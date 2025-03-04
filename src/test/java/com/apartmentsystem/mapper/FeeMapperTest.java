package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.Fee;
import com.apartmentsystem.util.DateFormatUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class FeeMapperTest {
    @Autowired
    private FeeMapper feeMapper;

    @Test
    public void testInsertFee() {
        Fee fee = new Fee();
        fee.setHouse_number("101");
        fee.setType("power");
        fee.setAmount(BigDecimal.valueOf(100));
        feeMapper.insertFee(fee);
        System.out.println(fee.getId());
    }

    @Test
    public void testUpdateFeePaidByHouseId() {
        feeMapper.updateFeePaidById(2);
    }

    @Test
    public void testInsertdepositFee() {
        Fee fee = new Fee();
        fee.setHouse_number("202");
        fee.setType("deposit");
        fee.setAmount(BigDecimal.valueOf(100));
        feeMapper.insertFee(fee);
        System.out.println(fee.getId());
    }

    @Test
    public void testGetFeeByUserId() {
        System.out.println(feeMapper.getFeeByUserId(14));
    }

    @Test
    public void testGetFeeByFeeNumber() {
        System.out.println(feeMapper.getFeeByFeeNumber("Fee202502241959254976212"));
    }
}
