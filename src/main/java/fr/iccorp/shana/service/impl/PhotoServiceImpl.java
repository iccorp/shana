package fr.iccorp.shana.service.impl;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.iccorp.shana.domain.Photo;
import fr.iccorp.shana.domain.enumeration.FORMAT_PHOTO;
import fr.iccorp.shana.repository.PhotoRepository;
import fr.iccorp.shana.service.PhotoService;
import fr.iccorp.shana.service.dto.PhotoDTO;
import fr.iccorp.shana.service.mapper.PhotoMapper;

/**
 * Service Implementation for managing Photo.
 */
@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {

	private final Logger log = LoggerFactory.getLogger(PhotoServiceImpl.class);

	private final PhotoRepository photoRepository;

	private final PhotoMapper photoMapper;

	@Value("${photo.base-directory}")
	private String photoBaseDir;

	@Value("${photo.f-l-h-couverture}")
	private Double facteurLHCouverture;

	@Value("${photo.f-l-h-carte}")
	private Double facteurLHCarte;

	@Value("${photo.f-l-h-vignette}")
	private Double facteurLHVignette;

	@Value("${photo.f-l-h-section}")
	private Double facteurLHSection;

	@Value("${photo.vignette-width}")
	private int vignetteWidth;

	public PhotoServiceImpl(PhotoRepository photoRepository, PhotoMapper photoMapper) {
		this.photoRepository = photoRepository;
		this.photoMapper = photoMapper;
	}

	private String saveInAllFormats(byte[] data, String contentType) throws Exception {
		List<FORMAT_PHOTO> formats = Arrays.asList(FORMAT_PHOTO.values());
		return save(data, contentType, formats);
	}

	/**
	 * Save a photo in specified formats.
	 *
	 * @param photoDTO
	 *            the entity to save
	 * @return the persisted entity
	 * @throws Exception
	 */
	private String save(byte[] data, String contentType, List<FORMAT_PHOTO> formats) throws Exception {
		log.info("photoBaseDir: {}", photoBaseDir);
		log.debug("Request to save Photo : ct:{}, formats {}", formats.toString());

		// hash data -> id
		String idPhoto = createPhotoID(data);

		// save the original image
		String ext = contentType.split("/")[1];
//		byte[] decodedData = Base64.getDecoder().decode(data);
		Files.write(Paths.get(photoBaseDir + File.separator + idPhoto + "." + ext), data);

		// save data if fs for every format
		for (FORMAT_PHOTO format : formats) {
			createPhotoInFormat(idPhoto, ext, format);
		}

		// return id

		return idPhoto;
	}

	private String createPhotoID(byte[] data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		StringBuilder sb = new StringBuilder();
		byte[] digest = md.digest(data);
		for (int i = 0; i < digest.length; ++i) {
			sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	private void createPhotoInFormat(String idPhoto, String ext, FORMAT_PHOTO format) throws Exception {
		if (format.equals(FORMAT_PHOTO.COUVERTURE)) {
			formatPhotoCouverture(idPhoto, ext);
		} else if (format.equals(FORMAT_PHOTO.CARTE)) {
			formatPhotoCarte(idPhoto, ext);
		} else if (format.equals(FORMAT_PHOTO.VIGNETTE)) {
			formatPhotoVignette(idPhoto, ext);
		} else if (format.equals(FORMAT_PHOTO.SECTION)) {
			formatPhotoSection(idPhoto, ext);
		} else {
			throw new Exception("fomat photo " + format + " inconnu");
		}
	}

	private void formatPhotoCouverture(String idPhoto, String ext) throws IOException {
		BufferedImage bimg = ImageIO.read(new File(photoBaseDir + File.separator + idPhoto + "." + ext));
		int type = bimg.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bimg.getType();
		int imgSrcW = bimg.getWidth();
		int imgSrcH = bimg.getHeight();
		double fSrc = ((double) imgSrcW / (double) imgSrcH);
		int x, y, w, h;
		if (fSrc > facteurLHCouverture) {
			w = (int) (facteurLHCouverture * imgSrcH);
			h = imgSrcH;
			x = (imgSrcW - w) / 2;
			y = 0;
		} else {
			w = imgSrcW;
			h = (int) (imgSrcW / facteurLHCouverture);
			x = 0;
			y = (imgSrcH - h) / 2;
		}
		BufferedImage resizedImg = resizeImageWithHint(bimg, x, y, w, h, type);
		File newImg = new File(photoBaseDir + File.separator + FORMAT_PHOTO.COUVERTURE.toString() + File.separator
				+ idPhoto + "." + ext);
		newImg.mkdirs();
		ImageIO.write(resizedImg, ext, newImg);
	}

	private void formatPhotoCarte(String idPhoto, String ext) throws IOException {
		BufferedImage bimg = ImageIO.read(new File(photoBaseDir + File.separator + idPhoto + "." + ext));
		int type = bimg.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bimg.getType();
		int imgSrcW = bimg.getWidth();
		int imgSrcH = bimg.getHeight();
		double fSrc = ((double) imgSrcW / (double) imgSrcH);
		int x, y, w, h;
		if (fSrc > facteurLHCarte) {
			w = (int) (facteurLHCarte * imgSrcH);
			h = imgSrcH;
			x = (imgSrcW - w) / 2;
			y = 0;
		} else {
			w = imgSrcW;
			h = (int) (imgSrcW / facteurLHCarte);
			x = 0;
			y = (imgSrcH - h) / 2;
		}
		BufferedImage resizedImg = resizeImageWithHint(bimg, x, y, w, h, type);
		File newImg = new File(
				photoBaseDir + File.separator + FORMAT_PHOTO.CARTE.toString() + File.separator + idPhoto + "." + ext);
		newImg.mkdirs();
		ImageIO.write(resizedImg, ext, newImg);
	}

	private void formatPhotoVignette(String idPhoto, String ext) throws IOException {
		BufferedImage bimg = ImageIO.read(new File(photoBaseDir + File.separator + idPhoto + "." + ext));
		int type = bimg.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bimg.getType();
		int imgSrcW = bimg.getWidth();
		int imgSrcH = bimg.getHeight();
		double fSrc = ((double) imgSrcW / (double) imgSrcH);
		int x, y, w, h;
		if (fSrc > facteurLHVignette) {
			w = (int) (facteurLHVignette * imgSrcH);
			h = imgSrcH;
			x = (imgSrcW - w) / 2;
			y = 0;
		} else {
			w = imgSrcW;
			h = (int) (imgSrcW / facteurLHVignette);
			x = 0;
			y = (imgSrcH - h) / 2;
		}
		BufferedImage resizedImg = resizeImageVignette(bimg, x, y, w, h, type);
		File newImg = new File(photoBaseDir + File.separator + FORMAT_PHOTO.VIGNETTE.toString() + File.separator
				+ idPhoto + "." + ext);
		newImg.mkdirs();
		ImageIO.write(resizedImg, ext, newImg);
	}

	private void formatPhotoSection(String idPhoto, String ext) throws IOException {
		BufferedImage bimg = ImageIO.read(new File(photoBaseDir + File.separator + idPhoto + "." + ext));
		int type = bimg.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bimg.getType();
		int imgSrcW = bimg.getWidth();
		int imgSrcH = bimg.getHeight();
		double fSrc = ((double) imgSrcW / (double) imgSrcH);
		int x, y, w, h;
		if (fSrc > facteurLHSection) {
			w = (int) (facteurLHSection * imgSrcH);
			h = imgSrcH;
			x = (imgSrcW - w) / 2;
			y = 0;
		} else {
			w = imgSrcW;
			h = (int) (imgSrcW / facteurLHSection);
			x = 0;
			y = (imgSrcH - h) / 2;
		}
		BufferedImage resizedImg = resizeImageWithHint(bimg, x, y, w, h, type);
		File newImg = new File(
				photoBaseDir + File.separator + FORMAT_PHOTO.SECTION.toString() + File.separator + idPhoto + "." + ext);
		newImg.mkdirs();
		ImageIO.write(resizedImg, ext, newImg);
	}

	private BufferedImage resizeImageWithHint(BufferedImage originalImage, int x, int y, int w, int h,
			int type) {

		BufferedImage resizedImage = new BufferedImage(w, h, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, w, h, x, y, x + w, y + h, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}

	private BufferedImage resizeImageVignette(BufferedImage originalImage, int x, int y, int w, int h,
			int type) {

		int height = (int)(vignetteWidth / facteurLHVignette);
		BufferedImage resizedImage = new BufferedImage(vignetteWidth, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, vignetteWidth, height, x, y, x + w, y + h, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}

	/**
	 * Save a photo.
	 *
	 * @param photoDTO
	 *            the entity to save
	 * @return the persisted entity
	 * @throws Exception
	 */
	@Override
	public PhotoDTO save(PhotoDTO photoDTO) throws Exception {
		log.debug("Request to save Photo : {}", photoDTO);
		String idPhoto = saveInAllFormats(photoDTO.getPhoto(), photoDTO.getPhotoContentType());
		photoDTO.setIdPhoto(idPhoto);
		Photo photo = photoMapper.toEntity(photoDTO);
		photo = photoRepository.save(photo);
		photo.setPhoto(null);
		return photoMapper.toDto(photo);
	}

	/**
	 * Get all the photos.
	 *
	 * @return the list of entities
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true)
	public PhotoDTO getPhotoByDTO(PhotoDTO photoDTO) throws Exception {
		log.debug("Request to get all Photos");
		PhotoDTO res = photoMapper.toDto(photoRepository.findByIdPhoto(photoDTO.getIdPhoto()));
		if (res == null) {
			throw new Exception("Photo " + photoDTO.getIdPhoto() + " inconnue !");
		}
		byte[] photo = getPhotoData(res.getIdPhoto(), res.getPhotoContentType(), photoDTO.getFormat());
		res.setPhoto(photo);
		res.setFormat(photoDTO.getFormat());
		return res;
	}

	private byte[] getPhotoData(String idPhoto, String photoContentType, FORMAT_PHOTO format) throws IOException {
		String ext = photoContentType.split("/")[1];
		return Files.readAllBytes(
				Paths.get(photoBaseDir + File.separator + format.toString() + File.separator + idPhoto + "." + ext));
	}

	/**
	 * Get all the photos.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<PhotoDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Photos");
		return photoRepository.findAll(pageable).map(photoMapper::toDto);
	}

	/**
	 * Get all the photos.
	 *
	 * @return the list of entities
	 * @throws IOException
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PhotoDTO> findAll() throws IOException {
		log.debug("Request to get all Photos");
		List<Photo> photos = photoRepository.findAll();
		List<PhotoDTO> res = new ArrayList<>();
		for (Photo photo: photos) {
			PhotoDTO photoDTO = photoMapper.toDto(photo);
			photoDTO.setFormat(FORMAT_PHOTO.VIGNETTE);
			photoDTO.setPhoto(getPhotoData(photoDTO.getIdPhoto(), photoDTO.getPhotoContentType(), FORMAT_PHOTO.VIGNETTE));
			res.add(photoDTO);
		}
		return res;
	}

	/**
	 * Get one photo by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public PhotoDTO findOne(Long id) {
		log.debug("Request to get Photo : {}", id);
		Photo photo = photoRepository.findOne(id);
		return photoMapper.toDto(photo);
	}

	/**
	 * Delete the photo by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Photo : {}", id);
		photoRepository.delete(id);
	}
}
