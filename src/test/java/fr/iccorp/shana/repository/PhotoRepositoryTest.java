package fr.iccorp.shana.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.iccorp.shana.ShanaApp;
import fr.iccorp.shana.service.PhotoService;
import fr.iccorp.shana.service.dto.PhotoDTO;
import fr.iccorp.shana.service.mapper.PhotoMapper;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShanaApp.class)
@Transactional
public class PhotoRepositoryTest {

	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private PhotoRepository PhotoRepository;

	@Autowired
	private PhotoMapper photoMapper;
	
	@Test
	public void testGetByPhotoDTO() throws Exception {
//		PhotoDTO photoDTO = new PhotoDTO();
//		photoDTO.setIdPhoto("iccorp");
//		PhotoRepository.save(photoMapper.toEntity(photoDTO));
		PhotoDTO param = new PhotoDTO();
		param.setIdPhoto("b91c41140eeb3496bbefd134f3dba453fccc0ae7cbf72845a76644719f561c31");
		PhotoDTO res = photoService.getPhotoByDTO(param);
		assertThat(res.getIdPhoto()).isEqualTo("b91c41140eeb3496bbefd134f3dba453fccc0ae7cbf72845a76644719f561c31");
		assertThat(res.getId()).isNotNull();
		assertThat(res.getPhoto()).isNotNull();
	}
}
