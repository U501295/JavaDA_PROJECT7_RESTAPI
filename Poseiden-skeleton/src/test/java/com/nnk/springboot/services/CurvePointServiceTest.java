package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointServiceTest {

    @Autowired
    private CurvePointService curvePointService;

    @Test(expected = IllegalArgumentException.class)
    public void bidListTest() {
        CurvePoint curve = new CurvePoint(10l, 1d, 10d);

        // Save
        curve = curvePointService.saveCurvePoint(curve);
        Assert.assertNotNull(curve.getCurveId());
        Assert.assertEquals(curve.getValue(), 10d, 10d);

        // Update
        curve.setValue(20d);
        curve = curvePointService.saveCurvePoint(curve);
        Assert.assertEquals(curve.getValue(), 20d, 20d);

        // FindAll
        List<CurvePoint> listResult = curvePointService.findAllCurvePoints();
        Assert.assertTrue(listResult.size() > 0);

        // FindOne
        Long id = curve.getCurveId();
        CurvePoint curvePoint = curvePointService.findCurvePointById(id);
        Assert.assertTrue(curvePoint.getCurveId() > 0);

        // Delete
        curvePointService.deleteCurvePoint(id);
        CurvePoint curvePointException = curvePointService.findCurvePointById(id);

    }


}
