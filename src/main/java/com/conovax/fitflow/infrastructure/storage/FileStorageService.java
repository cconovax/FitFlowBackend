package com.conovax.fitflow.infrastructure.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileStorageService {

	private final Cloudinary cloudinary;

	public String storeGymLogo(MultipartFile file) {
		validateImage(file, "El logo es requerido", "El logo debe ser una imagen");
		return upload(file, "fitflow/gyms");
	}

	public String storePeoplePhoto(MultipartFile file) {
		validateImage(file, "La foto es requerida", "La foto debe ser una imagen");
		return upload(file, "fitflow/people");
	}

	private void validateImage(MultipartFile file, String emptyMsg, String typeMsg) {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException(emptyMsg);
		}
		String contentType = file.getContentType();
		if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
			throw new IllegalArgumentException(typeMsg);
		}
	}

	private String upload(MultipartFile file, String folder) {
		try {
			Map<?, ?> result = cloudinary.uploader().upload(
					file.getBytes(),
					ObjectUtils.asMap("folder", folder)
			);
			return (String) result.get("secure_url");
		} catch (IOException e) {
			throw new IllegalStateException("No se pudo subir la imagen a Cloudinary", e);
		}
	}
}
