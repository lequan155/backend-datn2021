package com.datn2021.repo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FilesStorageServiceImpl implements FilesStorageService{
	private final Path root = Paths.get("uploads");
	
	@Override
	public void init() {
		try {
			Files.createDirectory(root);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Could not initialize folder for upload");
		}
	}
	
	@Override
	public void save(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Could not store the file. Error: "+e.getMessage());
		}
	}
	
	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Error: "+e.getMessage());
		}
	}
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}
	@Override
	public Stream<Path> loadAll(){
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Could not load the files");
		}
	}
}