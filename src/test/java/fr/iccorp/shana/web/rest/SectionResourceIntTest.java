package fr.iccorp.shana.web.rest;

import fr.iccorp.shana.ShanaApp;

import fr.iccorp.shana.domain.Section;
import fr.iccorp.shana.repository.SectionRepository;
import fr.iccorp.shana.service.SectionService;
import fr.iccorp.shana.service.dto.SectionDTO;
import fr.iccorp.shana.service.mapper.SectionMapper;
import fr.iccorp.shana.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static fr.iccorp.shana.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SectionResource REST controller.
 *
 * @see SectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShanaApp.class)
public class SectionResourceIntTest {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_AVANT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_AVANT = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_APRES = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_APRES = "BBBBBBBBBB";

    private static final String DEFAULT_ID_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_ID_PHOTO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private SectionMapper sectionMapper;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSectionMockMvc;

    private Section section;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SectionResource sectionResource = new SectionResource(sectionService);
        this.restSectionMockMvc = MockMvcBuilders.standaloneSetup(sectionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Section createEntity(EntityManager em) {
        Section section = new Section()
            .titre(DEFAULT_TITRE)
            .textAvant(DEFAULT_TEXT_AVANT)
            .textApres(DEFAULT_TEXT_APRES)
            .idPhoto(DEFAULT_ID_PHOTO)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return section;
    }

    @Before
    public void initTest() {
        section = createEntity(em);
    }

    @Test
    @Transactional
    public void createSection() throws Exception {
        int databaseSizeBeforeCreate = sectionRepository.findAll().size();

        // Create the Section
        SectionDTO sectionDTO = sectionMapper.toDto(section);
        restSectionMockMvc.perform(post("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate + 1);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testSection.getTextAvant()).isEqualTo(DEFAULT_TEXT_AVANT);
        assertThat(testSection.getTextApres()).isEqualTo(DEFAULT_TEXT_APRES);
        assertThat(testSection.getIdPhoto()).isEqualTo(DEFAULT_ID_PHOTO);
        assertThat(testSection.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testSection.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createSectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sectionRepository.findAll().size();

        // Create the Section with an existing ID
        section.setId(1L);
        SectionDTO sectionDTO = sectionMapper.toDto(section);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectionMockMvc.perform(post("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSections() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList
        restSectionMockMvc.perform(get("/api/sections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(section.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].textAvant").value(hasItem(DEFAULT_TEXT_AVANT.toString())))
            .andExpect(jsonPath("$.[*].textApres").value(hasItem(DEFAULT_TEXT_APRES.toString())))
            .andExpect(jsonPath("$.[*].idPhoto").value(hasItem(DEFAULT_ID_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get the section
        restSectionMockMvc.perform(get("/api/sections/{id}", section.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(section.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.textAvant").value(DEFAULT_TEXT_AVANT.toString()))
            .andExpect(jsonPath("$.textApres").value(DEFAULT_TEXT_APRES.toString()))
            .andExpect(jsonPath("$.idPhoto").value(DEFAULT_ID_PHOTO.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingSection() throws Exception {
        // Get the section
        restSectionMockMvc.perform(get("/api/sections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // Update the section
        Section updatedSection = sectionRepository.findOne(section.getId());
        // Disconnect from session so that the updates on updatedSection are not directly saved in db
        em.detach(updatedSection);
        updatedSection
            .titre(UPDATED_TITRE)
            .textAvant(UPDATED_TEXT_AVANT)
            .textApres(UPDATED_TEXT_APRES)
            .idPhoto(UPDATED_ID_PHOTO)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        SectionDTO sectionDTO = sectionMapper.toDto(updatedSection);

        restSectionMockMvc.perform(put("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isOk());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testSection.getTextAvant()).isEqualTo(UPDATED_TEXT_AVANT);
        assertThat(testSection.getTextApres()).isEqualTo(UPDATED_TEXT_APRES);
        assertThat(testSection.getIdPhoto()).isEqualTo(UPDATED_ID_PHOTO);
        assertThat(testSection.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testSection.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // Create the Section
        SectionDTO sectionDTO = sectionMapper.toDto(section);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSectionMockMvc.perform(put("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);
        int databaseSizeBeforeDelete = sectionRepository.findAll().size();

        // Get the section
        restSectionMockMvc.perform(delete("/api/sections/{id}", section.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Section.class);
        Section section1 = new Section();
        section1.setId(1L);
        Section section2 = new Section();
        section2.setId(section1.getId());
        assertThat(section1).isEqualTo(section2);
        section2.setId(2L);
        assertThat(section1).isNotEqualTo(section2);
        section1.setId(null);
        assertThat(section1).isNotEqualTo(section2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectionDTO.class);
        SectionDTO sectionDTO1 = new SectionDTO();
        sectionDTO1.setId(1L);
        SectionDTO sectionDTO2 = new SectionDTO();
        assertThat(sectionDTO1).isNotEqualTo(sectionDTO2);
        sectionDTO2.setId(sectionDTO1.getId());
        assertThat(sectionDTO1).isEqualTo(sectionDTO2);
        sectionDTO2.setId(2L);
        assertThat(sectionDTO1).isNotEqualTo(sectionDTO2);
        sectionDTO1.setId(null);
        assertThat(sectionDTO1).isNotEqualTo(sectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sectionMapper.fromId(null)).isNull();
    }
}
