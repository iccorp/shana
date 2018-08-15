package fr.iccorp.shana.service;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import fr.iccorp.shana.ShanaApp;
import fr.iccorp.shana.domain.enumeration.FORMAT_PHOTO;
import fr.iccorp.shana.service.dto.PhotoDTO;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShanaApp.class)
@Transactional
public class PhotoServiceImplTest {

    @Autowired
    private PhotoService photoService;

    @Test
    @Transactional
    public void testSave() throws Exception {
    	ClassLoader classLoader = getClass().getClassLoader();
    	URL imgUrl = classLoader.getResource("test.jpg");
    	File file = new File(imgUrl.getFile());
    	byte[] imgIn = IOUtils.toByteArray(new FileInputStream(file));
    	PhotoDTO photoDTO = new PhotoDTO();
    	photoDTO.setPhoto(imgIn);
    	photoDTO.setPhotoContentType("image/jpg");
    	photoService.save(photoDTO);
    }
}
