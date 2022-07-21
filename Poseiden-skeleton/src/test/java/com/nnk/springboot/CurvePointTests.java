package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointTests {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Test
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint(1l, 10d, 30d);

		// Save
		curvePoint = curvePointRepository.save(curvePoint);
		Assertions.assertThat(curvePoint.getIdCurve()).isNotNull();
		Assertions.assertThat(curvePoint.getIdCurve()).isEqualTo(1L);

		// Update
		curvePoint.setIdCurve(20l);
		curvePoint = curvePointRepository.save(curvePoint);
		Assertions.assertThat(curvePoint.getIdCurve()).isEqualTo(20L);

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		Assertions.assertThat(listResult.size()).isGreaterThan(0);
		//Assert.assertTrue(listResult.size() > 0);

		// Delete
		Long id = curvePoint.getCurveId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		Assertions.assertThat(curvePointList.isPresent()).isFalse();
		//Assert.assertFalse(curvePointList.isPresent());
	}

}
