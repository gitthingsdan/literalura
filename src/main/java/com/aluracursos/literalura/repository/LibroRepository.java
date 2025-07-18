package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
	@Query("SELECT DISTINCT l.idioma FROM Libro l")
	List<String> obtenerIdiomas();
	List<Libro> findByIdioma(String idioma);
}
