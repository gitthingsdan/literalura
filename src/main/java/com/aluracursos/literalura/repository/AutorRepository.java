package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
//	Encontrar autores que hayan estado vivos en cierto año
	@Query("SELECT a FROM Autor a WHERE a.anioNacimiento < :anio AND :anio < a.anioFallecimiento")
	List<Autor> obtenerAutoresVivosEnAnioDeterminado(int anio);
	Optional<Autor> findByNombre(String nombre);
}
