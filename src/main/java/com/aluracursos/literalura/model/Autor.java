package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity @Table(name="autores") @JsonIgnoreProperties(ignoreUnknown = true)
public final class Autor {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonAlias("name") @Column(unique = true)
	private String nombre;
	@JsonAlias("birth_year")
	private int anioNacimiento;
	@JsonAlias("death_year")
	private int anioFallecimiento;
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
	private List<Libro> libros;

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getAnioNacimiento() {
		return anioNacimiento;
	}

	public int getAnioFallecimiento() {
		return anioFallecimiento;
	}

	@Override
	public String toString() {
		return 	"Autor(a): \t\t\t\t" + nombre + "\n" +
				"Año de nacimiento: \t\t" + anioNacimiento + "\n" +
				"Año de fallecimiento: \t" + anioFallecimiento + "\n";
	}
}
