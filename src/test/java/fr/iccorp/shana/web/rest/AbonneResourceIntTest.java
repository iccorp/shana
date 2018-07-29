package fr.iccorp.shana.web.rest;

import fr.iccorp.shana.ShanaApp;

import fr.iccorp.shana.domain.Abonne;
import fr.iccorp.shana.repository.AbonneRepository;
import fr.iccorp.shana.service.AbonneService;
import fr.iccorp.shana.service.dto.AbonneDTO;
import fr.iccorp.shana.service.mapper.AbonneMapper;
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

import javax.persistence.EntityManager;
import java.util.List;

import static fr.iccorp.shana.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AbonneResource REST controller.
 *
 * @see AbonneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShanaApp.class)
public class AbonneResourceIntTest {

    private static final String DEFAULT_PSEUDO = "AAAAAAAAAA";
    private static final String UPDATED_PSEUDO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOT_DE_PASSE = "AAAAAAAAAA";
    private static final String UPDATED_MOT_DE_PASSE = "BBBBBBBBBB";

    @Autowired
    private AbonneRepository abonneRepository;

    @Autowired
    private AbonneMapper abonneMapper;

    @Autowired
    private AbonneService abonneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAbonneMockMvc;

    private Abonne abonne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AbonneResource abonneResource = new AbonneResource(abonneService);
        this.restAbonneMockMvc = MockMvcBuilders.standaloneSetup(abonneResource)
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
    public static Abonne createEntity(EntityManager em) {
        Abonne abonne = new Abonne()
            .pseudo(DEFAULT_PSEUDO)
            .email(DEFAULT_EMAIL)
            .motDePasse(DEFAULT_MOT_DE_PASSE);
        return abonne;
    }

    @Before
    public void initTest() {
        abonne = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbonne() throws Exception {
        int databaseSizeBeforeCreate = abonneRepository.findAll().size();

        // Create the Abonne
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);
        restAbonneMockMvc.perform(post("/api/abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abonneDTO)))
            .andExpect(status().isCreated());

        // Validate the Abonne in the database
        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeCreate + 1);
        Abonne testAbonne = abonneList.get(abonneList.size() - 1);
        assertThat(testAbonne.getPseudo()).isEqualTo(DEFAULT_PSEUDO);
        assertThat(testAbonne.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAbonne.getMotDePasse()).isEqualTo(DEFAULT_MOT_DE_PASSE);
    }

    @Test
    @Transactional
    public void createAbonneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = abonneRepository.findAll().size();

        // Create the Abonne with an existing ID
        abonne.setId(1L);
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbonneMockMvc.perform(post("/api/abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abonneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Abonne in the database
        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPseudoIsRequired() throws Exception {
        int databaseSizeBeforeTest = abonneRepository.findAll().size();
        // set the field null
        abonne.setPseudo(null);

        // Create the Abonne, which fails.
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);

        restAbonneMockMvc.perform(post("/api/abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abonneDTO)))
            .andExpect(status().isBadRequest());

        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = abonneRepository.findAll().size();
        // set the field null
        abonne.setEmail(null);

        // Create the Abonne, which fails.
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);

        restAbonneMockMvc.perform(post("/api/abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abonneDTO)))
            .andExpect(status().isBadRequest());

        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotDePasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = abonneRepository.findAll().size();
        // set the field null
        abonne.setMotDePasse(null);

        // Create the Abonne, which fails.
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);

        restAbonneMockMvc.perform(post("/api/abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abonneDTO)))
            .andExpect(status().isBadRequest());

        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAbonnes() throws Exception {
        // Initialize the database
        abonneRepository.saveAndFlush(abonne);

        // Get all the abonneList
        restAbonneMockMvc.perform(get("/api/abonnes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abonne.getId().intValue())))
            .andExpect(jsonPath("$.[*].pseudo").value(hasItem(DEFAULT_PSEUDO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].motDePasse").value(hasItem(DEFAULT_MOT_DE_PASSE.toString())));
    }

    @Test
    @Transactional
    public void getAbonne() throws Exception {
        // Initialize the database
        abonneRepository.saveAndFlush(abonne);

        // Get the abonne
        restAbonneMockMvc.perform(get("/api/abonnes/{id}", abonne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(abonne.getId().intValue()))
            .andExpect(jsonPath("$.pseudo").value(DEFAULT_PSEUDO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.motDePasse").value(DEFAULT_MOT_DE_PASSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAbonne() throws Exception {
        // Get the abonne
        restAbonneMockMvc.perform(get("/api/abonnes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbonne() throws Exception {
        // Initialize the database
        abonneRepository.saveAndFlush(abonne);
        int databaseSizeBeforeUpdate = abonneRepository.findAll().size();

        // Update the abonne
        Abonne updatedAbonne = abonneRepository.findOne(abonne.getId());
        // Disconnect from session so that the updates on updatedAbonne are not directly saved in db
        em.detach(updatedAbonne);
        updatedAbonne
            .pseudo(UPDATED_PSEUDO)
            .email(UPDATED_EMAIL)
            .motDePasse(UPDATED_MOT_DE_PASSE);
        AbonneDTO abonneDTO = abonneMapper.toDto(updatedAbonne);

        restAbonneMockMvc.perform(put("/api/abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abonneDTO)))
            .andExpect(status().isOk());

        // Validate the Abonne in the database
        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeUpdate);
        Abonne testAbonne = abonneList.get(abonneList.size() - 1);
        assertThat(testAbonne.getPseudo()).isEqualTo(UPDATED_PSEUDO);
        assertThat(testAbonne.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAbonne.getMotDePasse()).isEqualTo(UPDATED_MOT_DE_PASSE);
    }

    @Test
    @Transactional
    public void updateNonExistingAbonne() throws Exception {
        int databaseSizeBeforeUpdate = abonneRepository.findAll().size();

        // Create the Abonne
        AbonneDTO abonneDTO = abonneMapper.toDto(abonne);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAbonneMockMvc.perform(put("/api/abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abonneDTO)))
            .andExpect(status().isCreated());

        // Validate the Abonne in the database
        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAbonne() throws Exception {
        // Initialize the database
        abonneRepository.saveAndFlush(abonne);
        int databaseSizeBeforeDelete = abonneRepository.findAll().size();

        // Get the abonne
        restAbonneMockMvc.perform(delete("/api/abonnes/{id}", abonne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Abonne.class);
        Abonne abonne1 = new Abonne();
        abonne1.setId(1L);
        Abonne abonne2 = new Abonne();
        abonne2.setId(abonne1.getId());
        assertThat(abonne1).isEqualTo(abonne2);
        abonne2.setId(2L);
        assertThat(abonne1).isNotEqualTo(abonne2);
        abonne1.setId(null);
        assertThat(abonne1).isNotEqualTo(abonne2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbonneDTO.class);
        AbonneDTO abonneDTO1 = new AbonneDTO();
        abonneDTO1.setId(1L);
        AbonneDTO abonneDTO2 = new AbonneDTO();
        assertThat(abonneDTO1).isNotEqualTo(abonneDTO2);
        abonneDTO2.setId(abonneDTO1.getId());
        assertThat(abonneDTO1).isEqualTo(abonneDTO2);
        abonneDTO2.setId(2L);
        assertThat(abonneDTO1).isNotEqualTo(abonneDTO2);
        abonneDTO1.setId(null);
        assertThat(abonneDTO1).isNotEqualTo(abonneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(abonneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(abonneMapper.fromId(null)).isNull();
    }
}
