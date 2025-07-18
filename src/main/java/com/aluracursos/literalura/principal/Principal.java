package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.model.Resultados;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ClienteApi;
import com.aluracursos.literalura.service.ConversorDatos;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Principal {
	Scanner scanner = new Scanner(System.in);
	String urlBase = "https://gutendex.com/books";
//	List<Libro> listaLibros = new ArrayList<>();
	private LibroRepository libroRepositorio;
	private AutorRepository autorRepositorio;

	public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
		this.libroRepositorio = libroRepository;
		this.autorRepositorio = autorRepository;
	}

	public void mostrarMenu() {
		String menu = """
				***************************
				*¡Bienvenid@ a Literalura!*
				***************************
				Elija la opción a través de su número:
				1 - Buscar libro por título
				2 - Listar libros registrados
				3 - Listar autores registrados
				4 - Listar autores vivos en un determinado año
				5 - Listar libros por idioma
				
				0 - Salir
				""";

		int opcion = -1;

		while (opcion != 0) {
			System.out.println(menu);
			opcion = scanner.hasNextInt() ? scanner.nextInt() : -1;
			scanner.nextLine();

			switch (opcion) {
				case 1:
					buscarLibroPorTitulo();
					break;

				case 2:
					listarLibrosRegistrados();
					break;

				case 3:
					listarAutoresRegistrados();
					break;

				case 4:
					listarAutoresVivosEnAnioDeterminado();
					break;

				case 5:
					listarLibrosPorIdioma();
					break;

				case 0:
					break;

				default:
					System.out.println("Opción no válida.");
					break;
			}
		}
	}

	public void buscarLibroPorTitulo() {
		System.out.println("Ingrese el título del libro que desea buscar: ");
		String tituloBuscado = scanner.nextLine();

		String json = ClienteApi.enviarSolicitud(urlBase + "/?search=" + URLEncoder.encode(tituloBuscado, StandardCharsets.UTF_8));
		Resultados resultados = ConversorDatos.convertirDatos(json);

		Optional<Libro> libroBuscado = resultados.libros().stream().findFirst();
		if (libroBuscado.isPresent()) {
			Libro libroEncontrado = libroBuscado.get();
			System.out.println(libroEncontrado);
			Optional<Autor> autorExistente = autorRepositorio.findByNombre(libroEncontrado.getAutor().getNombre());
			if (autorExistente.isPresent()) {
				libroEncontrado.setAutor(autorExistente.get());
			} else {
				autorRepositorio.save(libroEncontrado.getAutor());
			}
			libroRepositorio.save(libroEncontrado);
		} else {
			System.out.println("No se ha encontrado ningún libro con el título ingresado.");
		}
	}

	public void listarLibrosRegistrados() {
		List<Libro> librosEnBD = libroRepositorio.findAll();
		if (librosEnBD.isEmpty()) {
			System.out.println("No se han encontrado libros registrados.");
		} else {
			librosEnBD.forEach(System.out::println);
		}
	}

	public void listarAutoresRegistrados() {
		List<Autor> autoresEnBD = autorRepositorio.findAll();
		if (autoresEnBD.isEmpty()) {
			System.out.println("No se han encontrado autores registrados.");
		} else {
			autoresEnBD.stream().sorted(Comparator.comparing(Autor::getNombre)).forEach(System.out::println);
		}
	}

	public void listarAutoresVivosEnAnioDeterminado() {
		System.out.println("Ingrese el año por el que desea buscar: ");
		int anio = scanner.hasNextInt() ? scanner.nextInt() : 3000;
		scanner.nextLine();
		List<Autor> autoresVivos = autorRepositorio.obtenerAutoresVivosEnAnioDeterminado(anio);
		if (autoresVivos.isEmpty()) {
			System.out.println("No se ha encontrado ningún autor vivo en el año ingresado.");
		} else {
			autoresVivos.forEach(System.out::println);
		}
	}

	public void listarLibrosPorIdioma() {
		System.out.println("Ingrese el idioma por el que desea buscar, de las siguientes opciones: ");
		libroRepositorio.obtenerIdiomas().forEach(System.out::println);
		String idioma = scanner.nextLine();
		if (!libroRepositorio.obtenerIdiomas().contains(idioma)) {
			System.out.println("Por favor, ingrese un idioma válido.");
		} else if (libroRepositorio.findByIdioma(idioma).isEmpty()) {
			System.out.println("No se han encontrado libros en el idioma ingresado.");
		} else {
			System.out.println("Se ha(n) encontrado " + libroRepositorio.findByIdioma(idioma).size() + " libro(s) en el idioma ingresado:");
			libroRepositorio.findByIdioma(idioma).forEach(System.out::println);
		}
	}
}
