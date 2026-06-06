package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.*;
import com.AgenciaSpring.AgenciaSpring.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

@Controller
public class MainGraphQLController {

    @Autowired private RolService rolService;
    @Autowired private EmpresaService empresaService;
    @Autowired private UsuarioService usuarioService;
    @Autowired private ReclutadorService reclutadorService;
    @Autowired private CandidatoService candidatoService;
    @Autowired private OfertaService ofertaService;
    @Autowired private PostulacionService postulacionService;
    @Autowired private CategoriaService categoriaService;
    @Autowired private TrabajosService trabajosService;
    @Autowired private OfertaTrabajoService ofertaTrabajoService;
    @Autowired private HabilidadesService habilidadesService;
    @Autowired private CandidatoHabilidadService candidatoHabilidadService;
    @Autowired private OfertaHabilidadService ofertaHabilidadService;

    // ══════════════════════ QUERIES ══════════════════════════════════════════

    @QueryMapping public List<Rol>               listarRoles()                { return rolService.findAll(); }
    @QueryMapping public Rol                     obtenerRol(@Argument UUID id) { return rolService.findById(id).orElse(null); }

    @QueryMapping public List<Empresa>           listarEmpresas()             { return empresaService.findAll(); }
    @QueryMapping public Empresa                 obtenerEmpresa(@Argument UUID id) { return empresaService.findById(id).orElse(null); }

    @QueryMapping public List<Usuario>           listarUsuarios()             { return usuarioService.findAll(); }
    @QueryMapping public Usuario                 obtenerUsuario(@Argument UUID id) { return usuarioService.findById(id).orElse(null); }

    @QueryMapping public List<Reclutador>        listarReclutadores()         { return reclutadorService.findAll(); }
    @QueryMapping public Reclutador              obtenerReclutador(@Argument UUID id) { return reclutadorService.findById(id).orElse(null); }

    @QueryMapping public List<Candidato>         listarCandidatos()           { return candidatoService.findAll(); }
    @QueryMapping public Candidato               obtenerCandidato(@Argument UUID id) { return candidatoService.findById(id).orElse(null); }

    @QueryMapping public List<Oferta>            listarOfertas()              { return ofertaService.findAll(); }
    @QueryMapping public Oferta                  obtenerOferta(@Argument UUID id) { return ofertaService.findById(id).orElse(null); }

    @QueryMapping public List<Postulacion>       listarPostulaciones()        { return postulacionService.findAll(); }
    @QueryMapping public Postulacion             obtenerPostulacion(@Argument UUID id) { return postulacionService.findById(id).orElse(null); }

    @QueryMapping public List<Categoria>         listarCategorias()           { return categoriaService.findAll(); }
    @QueryMapping public Categoria               obtenerCategoria(@Argument UUID id) { return categoriaService.findById(id).orElse(null); }

    @QueryMapping public List<Trabajos>          listarTrabajos()             { return trabajosService.findAll(); }
    @QueryMapping public Trabajos                obtenerTrabajo(@Argument UUID id) { return trabajosService.findById(id).orElse(null); }

    @QueryMapping public List<OfertaTrabajo>     listarOfertaTrabajos()       { return ofertaTrabajoService.findAll(); }

    @QueryMapping public List<Habilidades>       listarHabilidades()          { return habilidadesService.findAll(); }
    @QueryMapping public Habilidades             obtenerHabilidad(@Argument UUID id) { return habilidadesService.findById(id).orElse(null); }

    @QueryMapping public List<CandidatoHabilidad> listarCandidatoHabilidades() { return candidatoHabilidadService.findAll(); }
    @QueryMapping public List<OfertaHabilidad>   listarOfertaHabilidades()    { return ofertaHabilidadService.findAll(); }

    // ══════════════════════ MUTATIONS - ROL ══════════════════════════════════

    @MutationMapping
    public Rol crearRol(@Argument String nombre, @Argument String description) {
        Rol r = new Rol(); r.setNombre(nombre); r.setDescription(description);
        return rolService.save(r);
    }

    @MutationMapping
    public Rol actualizarRol(@Argument UUID id, @Argument String nombre, @Argument String description) {
        Rol r = rolService.findById(id).orElseThrow();
        if (nombre != null) r.setNombre(nombre);
        if (description != null) r.setDescription(description);
        return rolService.save(r);
    }

    @MutationMapping
    public Boolean eliminarRol(@Argument UUID id) {
        rolService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - EMPRESA ═══════════════════════════════

    @MutationMapping
    public Empresa crearEmpresa(@Argument String nombre_legal, @Argument String nombre_comercial,
                                @Argument Integer nit, @Argument String direccion, @Argument Integer celular) {
        Empresa e = new Empresa();
        e.setNombre_legal(nombre_legal); e.setNombre_comercial(nombre_comercial);
        e.setNit(nit); e.setDireccion(direccion); e.setCelular(celular);
        return empresaService.save(e);
    }

    @MutationMapping
    public Empresa actualizarEmpresa(@Argument UUID id, @Argument String nombre_legal, @Argument String nombre_comercial,
                                     @Argument Integer nit, @Argument String direccion, @Argument Integer celular) {
        Empresa e = empresaService.findById(id).orElseThrow();
        if (nombre_legal != null) e.setNombre_legal(nombre_legal);
        if (nombre_comercial != null) e.setNombre_comercial(nombre_comercial);
        if (nit != null) e.setNit(nit);
        if (direccion != null) e.setDireccion(direccion);
        if (celular != null) e.setCelular(celular);
        return empresaService.save(e);
    }

    @MutationMapping
    public Boolean eliminarEmpresa(@Argument UUID id) {
        empresaService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - TRABAJOS ══════════════════════════════

    @MutationMapping
    public Trabajos crearTrabajo(@Argument String nombre, @Argument String codigo) {
        Trabajos t = new Trabajos(); t.setNombre(nombre); t.setCodigo(codigo);
        return trabajosService.save(t);
    }

    @MutationMapping
    public Trabajos actualizarTrabajo(@Argument UUID id, @Argument String nombre, @Argument String codigo) {
        Trabajos t = trabajosService.findById(id).orElseThrow();
        if (nombre != null) t.setNombre(nombre);
        if (codigo != null) t.setCodigo(codigo);
        return trabajosService.save(t);
    }

    @MutationMapping
    public Boolean eliminarTrabajo(@Argument UUID id) {
        trabajosService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - CATEGORIA ═════════════════════════════

    @MutationMapping
    public Categoria crearCategoria(@Argument String nombre) {
        Categoria c = new Categoria(); c.setNombre(nombre);
        return categoriaService.save(c);
    }

    @MutationMapping
    public Categoria actualizarCategoria(@Argument UUID id, @Argument String nombre) {
        Categoria c = categoriaService.findById(id).orElseThrow();
        if (nombre != null) c.setNombre(nombre);
        return categoriaService.save(c);
    }

    @MutationMapping
    public Boolean eliminarCategoria(@Argument UUID id) {
        categoriaService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - USUARIO ═══════════════════════════════

    @MutationMapping
    public Usuario crearUsuario(@Argument String nombre, @Argument String apellido,
                                @Argument String email, @Argument String password,
                                @Argument String telefono, @Argument UUID rol_id, @Argument String estado) {
        Usuario u = new Usuario();
        u.setNombre(nombre); u.setApellido(apellido); u.setEmail(email);
        u.setPassword(password); u.setTelefono(telefono); u.setEstado(estado);
        u.setCreated_at(new Timestamp(System.currentTimeMillis()));
        u.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        if (rol_id != null) u.setRolObj(rolService.findById(rol_id).orElse(null));
        return usuarioService.save(u);
    }

    @MutationMapping
    public Usuario actualizarUsuario(@Argument UUID id, @Argument String nombre, @Argument String apellido,
                                     @Argument String email, @Argument String telefono,
                                     @Argument UUID rol_id, @Argument String estado) {
        Usuario u = usuarioService.findById(id).orElseThrow();
        if (nombre != null) u.setNombre(nombre);
        if (apellido != null) u.setApellido(apellido);
        if (email != null) u.setEmail(email);
        if (telefono != null) u.setTelefono(telefono);
        if (estado != null) u.setEstado(estado);
        if (rol_id != null) u.setRolObj(rolService.findById(rol_id).orElse(null));
        u.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        return usuarioService.save(u);
    }

    @MutationMapping
    public Boolean eliminarUsuario(@Argument UUID id) {
        usuarioService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - RECLUTADOR ════════════════════════════

    @MutationMapping
    public Reclutador crearReclutador(@Argument String nombre, @Argument String apellido,
                                      @Argument String email, @Argument String password,
                                      @Argument Integer telefono, @Argument String cargo,
                                      @Argument UUID empresa_id, @Argument String estado) {
        Reclutador r = new Reclutador();
        r.setNombre(nombre); r.setApellido(apellido); r.setEmail(email);
        r.setPassword(password); r.setTelefono(telefono != null ? telefono.toString() : null);
        r.setTelefonoReclutador(telefono); r.setCargo(cargo); r.setEstado(estado);
        r.setCreated_at(new Timestamp(System.currentTimeMillis()));
        r.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        if (empresa_id != null) r.setEmpresa(empresaService.findById(empresa_id).orElse(null));
        return reclutadorService.save(r);
    }

    @MutationMapping
    public Reclutador actualizarReclutador(@Argument UUID id, @Argument String nombre, @Argument String apellido,
                                           @Argument String email, @Argument Integer telefono,
                                           @Argument String cargo, @Argument UUID empresa_id, @Argument String estado) {
        Reclutador r = reclutadorService.findById(id).orElseThrow();
        if (nombre != null) r.setNombre(nombre);
        if (apellido != null) r.setApellido(apellido);
        if (email != null) r.setEmail(email);
        if (telefono != null) { r.setTelefonoReclutador(telefono); r.setTelefono(telefono.toString()); }
        if (cargo != null) r.setCargo(cargo);
        if (estado != null) r.setEstado(estado);
        if (empresa_id != null) r.setEmpresa(empresaService.findById(empresa_id).orElse(null));
        r.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        return reclutadorService.save(r);
    }

    @MutationMapping
    public Boolean eliminarReclutador(@Argument UUID id) {
        reclutadorService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - CANDIDATO ═════════════════════════════

    @MutationMapping
    public Candidato crearCandidato(@Argument String nombre, @Argument String apellido,
                                    @Argument String email, @Argument String password,
                                    @Argument Integer registro, @Argument Double sueldo_esperado,
                                    @Argument String modalidad_preferida, @Argument String nivel_educativo,
                                    @Argument String nacionalidad, @Argument Integer cluster_id,
                                    @Argument String estado) {
        Candidato c = new Candidato();
        c.setNombre(nombre); c.setApellido(apellido); c.setEmail(email);
        c.setPassword(password); c.setEstado(estado); c.setRegistro(registro);
        c.setNacionalidad(nacionalidad); c.setCluster_id(cluster_id);
        c.setModalidad_preferida(modalidad_preferida); c.setNivel_educativo(nivel_educativo);
        if (sueldo_esperado != null) c.setSueldo_esperado(BigDecimal.valueOf(sueldo_esperado));
        c.setCreated_at(new Timestamp(System.currentTimeMillis()));
        c.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        return candidatoService.save(c);
    }

    @MutationMapping
    public Candidato actualizarCandidato(@Argument UUID id, @Argument String nombre, @Argument String apellido,
                                         @Argument String email, @Argument Integer registro,
                                         @Argument Double sueldo_esperado, @Argument String modalidad_preferida,
                                         @Argument String nivel_educativo, @Argument String nacionalidad,
                                         @Argument Integer cluster_id, @Argument String estado) {
        Candidato c = candidatoService.findById(id).orElseThrow();
        if (nombre != null) c.setNombre(nombre);
        if (apellido != null) c.setApellido(apellido);
        if (email != null) c.setEmail(email);
        if (registro != null) c.setRegistro(registro);
        if (sueldo_esperado != null) c.setSueldo_esperado(BigDecimal.valueOf(sueldo_esperado));
        if (modalidad_preferida != null) c.setModalidad_preferida(modalidad_preferida);
        if (nivel_educativo != null) c.setNivel_educativo(nivel_educativo);
        if (nacionalidad != null) c.setNacionalidad(nacionalidad);
        if (cluster_id != null) c.setCluster_id(cluster_id);
        if (estado != null) c.setEstado(estado);
        c.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        return candidatoService.save(c);
    }

    @MutationMapping
    public Boolean eliminarCandidato(@Argument UUID id) {
        candidatoService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - OFERTA ════════════════════════════════

    @MutationMapping
    public Oferta crearOferta(@Argument String titulo, @Argument String descripcion,
                              @Argument String contrato, @Argument String requisitos,
                              @Argument Integer experiencia_tiempo, @Argument String modalidad_trabajo,
                              @Argument String estado, @Argument Double sueldo,
                              @Argument Integer cluster_id,
                              @Argument UUID categoria_id, @Argument UUID reclutador_id) {
        Oferta o = new Oferta();
        o.setTitulo(titulo); o.setDescripcion(descripcion); o.setContrato(contrato);
        o.setRequisitos(requisitos); o.setExperiencia_tiempo(experiencia_tiempo);
        o.setModalidad_trabajo(modalidad_trabajo); o.setEstado(estado); o.setCluster_id(cluster_id);
        if (sueldo != null) o.setSueldo(BigDecimal.valueOf(sueldo));
        if (categoria_id != null) o.setCategoria(categoriaService.findById(categoria_id).orElse(null));
        if (reclutador_id != null) o.setReclutador(reclutadorService.findById(reclutador_id).orElse(null));
        o.setFecha_publicacion(new Date(System.currentTimeMillis()));
        return ofertaService.save(o);
    }

    @MutationMapping
    public Oferta actualizarOferta(@Argument UUID id, @Argument String titulo, @Argument String descripcion,
                                   @Argument String contrato, @Argument String requisitos,
                                   @Argument Integer experiencia_tiempo, @Argument String modalidad_trabajo,
                                   @Argument String estado, @Argument Double sueldo,
                                   @Argument Integer cluster_id,
                                   @Argument UUID categoria_id, @Argument UUID reclutador_id) {
        Oferta o = ofertaService.findById(id).orElseThrow();
        if (titulo != null) o.setTitulo(titulo);
        if (descripcion != null) o.setDescripcion(descripcion);
        if (contrato != null) o.setContrato(contrato);
        if (requisitos != null) o.setRequisitos(requisitos);
        if (experiencia_tiempo != null) o.setExperiencia_tiempo(experiencia_tiempo);
        if (modalidad_trabajo != null) o.setModalidad_trabajo(modalidad_trabajo);
        if (estado != null) o.setEstado(estado);
        if (cluster_id != null) o.setCluster_id(cluster_id);
        if (sueldo != null) o.setSueldo(BigDecimal.valueOf(sueldo));
        if (categoria_id != null) o.setCategoria(categoriaService.findById(categoria_id).orElse(null));
        if (reclutador_id != null) o.setReclutador(reclutadorService.findById(reclutador_id).orElse(null));
        return ofertaService.save(o);
    }

    @MutationMapping
    public Boolean eliminarOferta(@Argument UUID id) {
        ofertaService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - POSTULACION ═══════════════════════════

    @MutationMapping
    public Postulacion crearPostulacion(@Argument String fase_alcanzada, @Argument String id_cv,
                                        @Argument UUID candidato_id, @Argument UUID oferta_id) {
        Postulacion p = new Postulacion();
        p.setFase_alcanzada(fase_alcanzada); p.setId_cv(id_cv);
        if (candidato_id != null) p.setCandidato(candidatoService.findById(candidato_id).orElse(null));
        if (oferta_id != null) p.setOferta(ofertaService.findById(oferta_id).orElse(null));
        p.setFecha(new Date(System.currentTimeMillis()));
        return postulacionService.save(p);
    }

    @MutationMapping
    public Postulacion actualizarPostulacion(@Argument UUID id, @Argument String fase_alcanzada,
                                             @Argument String id_cv, @Argument UUID candidato_id,
                                             @Argument UUID oferta_id) {
        Postulacion p = postulacionService.findById(id).orElseThrow();
        if (fase_alcanzada != null) p.setFase_alcanzada(fase_alcanzada);
        if (id_cv != null) p.setId_cv(id_cv);
        if (candidato_id != null) p.setCandidato(candidatoService.findById(candidato_id).orElse(null));
        if (oferta_id != null) p.setOferta(ofertaService.findById(oferta_id).orElse(null));
        return postulacionService.save(p);
    }

    @MutationMapping
    public Boolean eliminarPostulacion(@Argument UUID id) {
        postulacionService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - HABILIDADES ════════════════════════════

    @MutationMapping
    public Habilidades crearHabilidad(@Argument String nombre) {
        Habilidades h = new Habilidades(); h.setNombre(nombre);
        return habilidadesService.save(h);
    }

    @MutationMapping
    public Habilidades actualizarHabilidad(@Argument UUID id, @Argument String nombre) {
        Habilidades h = habilidadesService.findById(id).orElseThrow();
        if (nombre != null) h.setNombre(nombre);
        return habilidadesService.save(h);
    }

    @MutationMapping
    public Boolean eliminarHabilidad(@Argument UUID id) {
        habilidadesService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - CANDIDATO_HABILIDAD ════════════════════

    @MutationMapping
    public CandidatoHabilidad crearCandidatoHabilidad(@Argument UUID candidato_id, @Argument UUID habilidad_id) {
        CandidatoHabilidad ch = new CandidatoHabilidad();
        if (candidato_id != null) ch.setCandidato(candidatoService.findById(candidato_id).orElse(null));
        if (habilidad_id != null) ch.setHabilidad(habilidadesService.findById(habilidad_id).orElse(null));
        return candidatoHabilidadService.save(ch);
    }

    @MutationMapping
    public Boolean eliminarCandidatoHabilidad(@Argument UUID id) {
        candidatoHabilidadService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - OFERTA_HABILIDAD ══════════════════════

    @MutationMapping
    public OfertaHabilidad crearOfertaHabilidad(@Argument String nivel_importancia,
                                                @Argument UUID oferta_id, @Argument UUID habilidad_id) {
        OfertaHabilidad oh = new OfertaHabilidad();
        oh.setNivel_importancia(nivel_importancia);
        if (oferta_id != null) oh.setOferta(ofertaService.findById(oferta_id).orElse(null));
        if (habilidad_id != null) oh.setHabilidad(habilidadesService.findById(habilidad_id).orElse(null));
        return ofertaHabilidadService.save(oh);
    }

    @MutationMapping
    public OfertaHabilidad actualizarOfertaHabilidad(@Argument UUID id, @Argument String nivel_importancia,
                                                     @Argument UUID oferta_id, @Argument UUID habilidad_id) {
        OfertaHabilidad oh = ofertaHabilidadService.findById(id).orElseThrow();
        if (nivel_importancia != null) oh.setNivel_importancia(nivel_importancia);
        if (oferta_id != null) oh.setOferta(ofertaService.findById(oferta_id).orElse(null));
        if (habilidad_id != null) oh.setHabilidad(habilidadesService.findById(habilidad_id).orElse(null));
        return ofertaHabilidadService.save(oh);
    }

    @MutationMapping
    public Boolean eliminarOfertaHabilidad(@Argument UUID id) {
        ofertaHabilidadService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - OFERTA_TRABAJO ════════════════════════

    @MutationMapping
    public OfertaTrabajo crearOfertaTrabajo(@Argument UUID oferta_id, @Argument UUID trabajo_id) {
        OfertaTrabajo ot = new OfertaTrabajo();
        if (oferta_id != null) ot.setOferta(ofertaService.findById(oferta_id).orElse(null));
        if (trabajo_id != null) ot.setTrabajos(trabajosService.findById(trabajo_id).orElse(null));
        return ofertaTrabajoService.save(ot);
    }

    @MutationMapping
    public Boolean eliminarOfertaTrabajo(@Argument UUID id) {
        ofertaTrabajoService.deleteById(id); return true;
    }

    // ══════════════════════ MACHINE LEARNING ══════════════════════════════════
    @Autowired 
    private MachineLearningIntegrationService machineLearningService;

    @MutationMapping
    public String dispararEntrenamientoKMeansManual() {
        String res1 = machineLearningService.entrenarCandidatosManual();
        String res2 = machineLearningService.entrenarOfertasManual();
        return res1 + " | " + res2;
    }

    @MutationMapping
    public Integer clasificarCandidato(@Argument UUID id) {
        return machineLearningService.clasificarCandidato(id);
    }

    @MutationMapping
    public Integer clasificarOferta(@Argument UUID id) {
        return machineLearningService.clasificarOferta(id);
    }
}
