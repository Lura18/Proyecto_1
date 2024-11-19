package testProyecto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Persistencia.PersistenciaUsuarios;
import proyecto.Estudiante;
import proyecto.Usuario;
import proyecto.LearningPath;
import proyecto.Profesor;
import proyecto.Registro;

public class TestRegistro {
	private Registro registro;
    private Profesor profesor1;
    private Estudiante estudiante1;
    private LearningPath lp1;
    
    @BeforeEach
    public void setUp() {
        registro = new Registro();
        profesor1 = new Profesor("Juan", "juan@mail.com", "12345");
        estudiante1 = new Estudiante("Ana", "ana@mail.com", "password");
        lp1 = new LearningPath("LP1", "Descripcion LP1", "Objetivos", "Intermedio", profesor1, 10);
    }
    
    @AfterEach
    public void limpiarArchivoPrueba() {
        try {
            FileWriter writer = new FileWriter("usuariosTest.json");
            writer.write("{\"usuarios\": []}");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error al limpiar el archivo de prueba: " + e.getMessage());
        }
    }

    @Test
    public void testRegistrarProfesor() {
        registro.registrarProfesor(profesor1);
        assertEquals(1, registro.getUsuarios().size());
        assertTrue(registro.getUsuarios().contains(profesor1));
    }
    
    @Test
    public void testRegistrarProfesorRepetido() {
        registro.registrarProfesor(profesor1);
        registro.registrarProfesor(profesor1);
        assertEquals(1, registro.getUsuarios().size());
        assertTrue(registro.getUsuarios().contains(profesor1));
    }

    @Test
    public void testRegistrarEstudiante() throws Exception {
        registro.registrarEstudiante(estudiante1);
        assertEquals(1, registro.getUsuarios().size());
        assertTrue(registro.getUsuarios().contains(estudiante1));
    }
    
    @Test
    public void testRegistrarEstudianteReptido() throws Exception {
        registro.registrarEstudiante(estudiante1);
        registro.registrarEstudiante(estudiante1);
        assertEquals(1, registro.getUsuarios().size());
        assertTrue(registro.getUsuarios().contains(estudiante1));
    }

    @Test
    public void testLoginProfesorExitoso() throws Exception {
        registro.registrarProfesor(profesor1);
        Profesor resultado = registro.loginProfesor("juan@mail.com", "12345");
        assertNotNull(resultado);
        assertEquals(profesor1, resultado);
    }

    @Test
    public void testLoginProfesorFallido() {
        registro.registrarProfesor(profesor1);
        Exception exception = assertThrows(Exception.class, () -> {
            registro.loginProfesor("incorrecto@mail.com", "12345");
        });
        assertEquals("Login fallido. Usuario o contraseña incorrectos.", exception.getMessage());
    }

    @Test
    public void testLoginEstudianteExitoso() throws Exception {
        registro.registrarEstudiante(estudiante1);
        Estudiante resultado = registro.loginEstudiante("ana@mail.com", "password");
        assertNotNull(resultado);
        assertEquals(estudiante1, resultado);
    }

    @Test
    public void testLoginEstudianteFallido() {
        registro.registrarEstudiante(estudiante1);
        Exception exception = assertThrows(Exception.class, () -> {
            registro.loginEstudiante("ana@mail.com", "wrongpassword");
        });
        assertEquals("Login fallido. Usuario o contraseña incorrectos.", exception.getMessage());
    }

    @Test
    public void testAgregarPaths() {
        registro.agregarPaths(lp1);
        assertEquals(1, registro.getPaths().size());
        assertTrue(registro.getPaths().contains(lp1));
    }

    @Test
    public void testCargarUsuarios() throws IOException {
    	
        FileWriter writer = new FileWriter("usuariosTest.json");
        writer.write("{\n"
                + "  \"usuarios\": [\n"
                + "    {\n"
                + "      \"password\": \"12345\",\n"
                + "      \"correo\": \"juan@mail.com\",\n"
                + "      \"tipoUsuario\": \"Profesor\",\n"
                + "      \"nombre\": \"Juan\"\n"
                + "    },"
                + "    {\n"
                + "      \"password\": \"password\",\n"
                + "      \"correo\": \"ana@mail.com\",\n"
                + "      \"tipoUsuario\": \"Estudiante\",\n"
                + "      \"nombre\": \"Ana\"\n"
                + "    }"
                + "  ]\n"
                + "}");
        writer.close();
        
        
        List<Usuario> usuariosCargados = registro.cargarUsuarios("usuariosTest.json");
        assertEquals(2, usuariosCargados.size());
        assertEquals("juan@mail.com", usuariosCargados.get(0).getCorreo());
        assertEquals("ana@mail.com", usuariosCargados.get(1).getCorreo());
    }

    @Test
    public void testSalvarUsuariosArchivoVacio() throws Exception {

        registro.salvarUsuarios("usuariosTest.json");
        
        File archivo = new File("usuariosTest.json");
        assertTrue(archivo.exists(), "El archivo de prueba debería existir.");
        
        String contenido = Files.readString(archivo.toPath());
        assertEquals("{\"usuarios\": []}", contenido.trim(), "El archivo debería estar vacío.");
    }
    
    @Test
    public void testSalvarUsuarios() throws Exception {

        registro.registrarProfesor(profesor1);
        
        registro.salvarUsuarios("./datos/usuariosTest.json");


        Registro nuevoRegistro = new Registro();
        nuevoRegistro.cargarUsuarios("./datos/usuariosTest.json");

        List<Usuario> usuariosCargados = nuevoRegistro.getUsuarios();
        
        assertEquals(1, usuariosCargados.size(), "Debería haber 2 usuarios cargados.");
        assertEquals("juan@mail.com", usuariosCargados.get(0).getCorreo());
}

    @Test
    public void testGetEstudiantesInscritosEnLearningPaths() throws Exception {
        registro.registrarEstudiante(estudiante1);
        estudiante1.inscripcion(lp1);
        List<LearningPath> paths = new ArrayList<>();
        paths.add(lp1);
        
        List<Estudiante> estudiantesInscritos = registro.getEstudiantesInscritosEnLearningPaths(paths);
        assertEquals(1, estudiantesInscritos.size());
        assertTrue(estudiantesInscritos.contains(estudiante1));
    }
}
