package com.conovax.sexbody.infrastructure.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;

@Service
public class FileStorageService {

	private final Path uploadDir;

	public FileStorageService(@Value("${app.upload.dir:uploads}") String uploadDir) {
		this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
	}

	public String storeGymLogo(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("El logo es requerido");
		}
		String contentType = file.getContentType();
		if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
			throw new IllegalArgumentException("El logo debe ser una imagen");
		}

		String originalName = file.getOriginalFilename();
		String extension = getExtension(originalName);
		if (extension == null) {
			extension = extensionFromContentType(contentType);
		}
		if (extension == null) {
			extension = "bin";
		}

		String safeExt = extension.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
		String filename = UUID.randomUUID().toString().replace("-", "") + "." + safeExt;

		Path targetDir = uploadDir.resolve("gyms");
		Path targetFile = targetDir.resolve(filename).normalize();
		if (!targetFile.startsWith(targetDir)) {
			throw new IllegalArgumentException("Nombre de archivo inválido");
		}

		try {
			Files.createDirectories(targetDir);
			file.transferTo(targetFile.toFile());
		} catch (IOException e) {
			throw new IllegalStateException("No se pudo guardar la imagen", e);
		}

		return "/uploads/gyms/" + filename;
	}

	private String getExtension(String filename) {
		if (filename == null) {
			return null;
		}
		int lastDot = filename.lastIndexOf('.');
		if (lastDot < 0 || lastDot == filename.length() - 1) {
			return null;
		}
		return filename.substring(lastDot + 1);
	}

	private String extensionFromContentType(String contentType) {
		String ct = contentType.toLowerCase(Locale.ROOT);
		return switch (ct) {
			case "image/png" -> "png";
			case "image/jpeg" -> "jpg";
			case "image/webp" -> "webp";
			case "image/gif" -> "gif";
			default -> null;
		};
	}
}
