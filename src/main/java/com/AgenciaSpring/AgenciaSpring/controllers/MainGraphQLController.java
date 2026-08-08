package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.*;
import com.AgenciaSpring.AgenciaSpring.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

@Controller
public class MainGraphQLController {

    @Autowired private BCryptPasswordEncoder passwordEncoder;
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
    @Autowired private ClusterService clusterService;

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
    @QueryMapping public List<Oferta>            listarOfertasPorReclutador(@Argument UUID reclutadorId) { return ofertaService.findByReclutadorId(reclutadorId); }
    @QueryMapping public Oferta                  obtenerOferta(@Argument UUID id) { return ofertaService.findById(id).orElse(null); }

    @QueryMapping public List<Postulacion>       listarPostulaciones()        { return postulacionService.findAll(); }
    @QueryMapping public List<Postulacion>       listarPostulacionesPorReclutador(@Argument UUID reclutadorId) { return postulacionService.findByReclutadorId(reclutadorId); }
    @QueryMapping public List<Postulacion>       listarPostulacionesPorCandidato(@Argument UUID candidatoId) { return postulacionService.findByCandidatoId(candidatoId); }
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
    @QueryMapping public List<Cluster>           listarClusters()             { return clusterService.findAll(); }
    @QueryMapping public Cluster                 obtenerCluster(@Argument UUID id) { return clusterService.findById(id).orElse(null); }
    @QueryMapping public List<Cluster>           listarClustersPorTipo(@Argument String tipo) { return clusterService.findByTipo(tipo); }

    // ══════════════════════ MUTATIONS - ROL ══════════════════════════════════

    @MutationMapping
    public Rol crearRol(@Argument String nombre, @Argument String description) {
        Rol r = new Rol(); r.setId(UUID.randomUUID()); r.setNombre(nombre); r.setDescription(description);
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
                                @Argument Integer nit, @Argument String direccion, @Argument Integer celular,
                                @Argument Double latitud, @Argument Double longitud) {
        Empresa e = new Empresa(); e.setId(UUID.randomUUID());
        e.setNombre_legal(nombre_legal); e.setNombre_comercial(nombre_comercial);
        e.setNit(nit); e.setDireccion(direccion); e.setCelular(celular);
        e.setLatitud(latitud); e.setLongitud(longitud);
        return empresaService.save(e);
    }

    @MutationMapping
    public Empresa actualizarEmpresa(@Argument UUID id, @Argument String nombre_legal, @Argument String nombre_comercial,
                                     @Argument Integer nit, @Argument String direccion, @Argument Integer celular,
                                     @Argument Double latitud, @Argument Double longitud) {
        Empresa e = empresaService.findById(id).orElseThrow();
        if (nombre_legal != null) e.setNombre_legal(nombre_legal);
        if (nombre_comercial != null) e.setNombre_comercial(nombre_comercial);
        if (nit != null) e.setNit(nit);
        if (direccion != null) e.setDireccion(direccion);
        if (celular != null) e.setCelular(celular);
        if (latitud != null) e.setLatitud(latitud);
        if (longitud != null) e.setLongitud(longitud);
        return empresaService.save(e);
    }

    @MutationMapping
    public Boolean eliminarEmpresa(@Argument UUID id) {
        empresaService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - TRABAJOS ══════════════════════════════

    @MutationMapping
    public Trabajos crearTrabajo(@Argument String nombre, @Argument String codigo) {
        Trabajos t = new Trabajos(); t.setId(UUID.randomUUID()); t.setNombre(nombre); t.setCodigo(codigo);
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
        Categoria c = new Categoria(); c.setId(UUID.randomUUID()); c.setNombre(nombre);
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
        u.setId(UUID.randomUUID());
        u.setNombre(nombre); u.setApellido(apellido); u.setEmail(email);
        u.setPassword(password != null ? passwordEncoder.encode(password) : null); u.setTelefono(telefono); u.setEstado(estado);
        u.setCreated_at(Instant.now());
        u.setUpdated_at(Instant.now());
        if (rol_id != null) {
            Rol r = rolService.findById(rol_id).orElse(null);
            u.setRolObj(r);
            if (r != null) u.setRol(r.getNombre());
        }
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
        u.setUpdated_at(Instant.now());
        return usuarioService.save(u);
    }

    @MutationMapping
    public Boolean eliminarUsuario(@Argument UUID id) {
        usuarioService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - CLUSTER ═══════════════════════════════
    @MutationMapping
    public Cluster crearCluster(@Argument Integer clusterNumero, @Argument String nombre, @Argument String tipo) {
        Cluster c = new Cluster();
        c.setId(UUID.randomUUID());
        c.setClusterNumero(clusterNumero);
        c.setNombre(nombre);
        c.setTipo(tipo);
        c.setFechaEntrenamiento(Instant.now());
        return clusterService.save(c);
    }

    @MutationMapping
    public Boolean eliminarCluster(@Argument UUID id) {
        clusterService.deleteById(id); return true;
    }

    // ══════════════════════ MUTATIONS - RECLUTADOR ════════════════════════════

    @MutationMapping
    public Reclutador crearReclutador(@Argument String nombre, @Argument String apellido,
                                      @Argument String email, @Argument String password,
                                      @Argument Integer telefono, @Argument String cargo,
                                      @Argument UUID empresa_id, @Argument String estado) {
        Reclutador r = new Reclutador();
        r.setId(UUID.randomUUID());
        r.setNombre(nombre); r.setApellido(apellido); r.setEmail(email);
        r.setPassword(password != null ? passwordEncoder.encode(password) : null); r.setTelefono(telefono != null ? telefono.toString() : null);
        r.setTelefonoReclutador(telefono); r.setCargo(cargo); r.setEstado(estado);
        r.setCreated_at(Instant.now());
        r.setUpdated_at(Instant.now());
        if (empresa_id != null) r.setEmpresa(empresaService.findById(empresa_id).orElse(null));
        
        Rol rol = rolService.findAll().stream()
                .filter(x -> "Reclutador".equalsIgnoreCase(x.getNombre()))
                .findFirst()
                .orElse(null);
        if (rol != null) {
            r.setRolObj(rol);
            r.setRol(rol.getNombre());
        }
        
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
        r.setUpdated_at(Instant.now());
        
        if (r.getRolObj() == null) {
            Rol rol = rolService.findAll().stream()
                    .filter(x -> "Reclutador".equalsIgnoreCase(x.getNombre()))
                    .findFirst()
                    .orElse(null);
            if (rol != null) {
                r.setRolObj(rol);
                r.setRol(rol.getNombre());
            }
        }
        
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
                                    @Argument String nacionalidad, @Argument UUID cluster_id,
                                    @Argument String estado) {
        Candidato c = new Candidato();
        c.setId(UUID.randomUUID());
        c.setNombre(nombre); c.setApellido(apellido); c.setEmail(email);
        c.setPassword(password != null ? passwordEncoder.encode(password) : null); c.setEstado(estado); c.setRegistro(registro);
        c.setNacionalidad(nacionalidad);
        if (cluster_id != null) {
            c.setCluster(clusterService.findById(cluster_id).orElse(null));
        }
        c.setModalidad_preferida(modalidad_preferida); c.setNivel_educativo(nivel_educativo);
        if (sueldo_esperado != null) c.setSueldo_esperado(BigDecimal.valueOf(sueldo_esperado));
        c.setCreated_at(Instant.now());
        c.setUpdated_at(Instant.now());
        
        Rol rol = rolService.findAll().stream()
                .filter(x -> "Candidato".equalsIgnoreCase(x.getNombre()))
                .findFirst()
                .orElse(null);
        if (rol != null) {
            c.setRolObj(rol);
            c.setRol(rol.getNombre());
        }
        
        return candidatoService.save(c);
    }

    @MutationMapping
    public Candidato actualizarCandidato(@Argument UUID id, @Argument String nombre, @Argument String apellido,
                                         @Argument String email, @Argument Integer registro,
                                         @Argument Double sueldo_esperado, @Argument String modalidad_preferida,
                                         @Argument String nivel_educativo, @Argument String nacionalidad,
                                         @Argument UUID cluster_id, @Argument String estado) {
        Candidato c = candidatoService.findById(id).orElseThrow();
        if (nombre != null) c.setNombre(nombre);
        if (apellido != null) c.setApellido(apellido);
        if (email != null) c.setEmail(email);
        if (registro != null) c.setRegistro(registro);
        if (sueldo_esperado != null) c.setSueldo_esperado(BigDecimal.valueOf(sueldo_esperado));
        if (modalidad_preferida != null) c.setModalidad_preferida(modalidad_preferida);
        if (nivel_educativo != null) c.setNivel_educativo(nivel_educativo);
        if (nacionalidad != null) c.setNacionalidad(nacionalidad);
        if (cluster_id != null) {
            c.setCluster(clusterService.findById(cluster_id).orElse(null));
        }
        if (estado != null) c.setEstado(estado);
        c.setUpdated_at(Instant.now());
        
        if (c.getRolObj() == null) {
            Rol rol = rolService.findAll().stream()
                    .filter(x -> "Candidato".equalsIgnoreCase(x.getNombre()))
                    .findFirst()
                    .orElse(null);
            if (rol != null) {
                c.setRolObj(rol);
                c.setRol(rol.getNombre());
            }
        }
        
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
                              @Argument UUID cluster_id,
                              @Argument UUID categoria_id, @Argument UUID reclutador_id) {
        Oferta o = new Oferta();
        o.setId(UUID.randomUUID());
        o.setTitulo(titulo); o.setDescripcion(descripcion); o.setContrato(contrato);
        o.setRequisitos(requisitos); o.setExperiencia_tiempo(experiencia_tiempo);
        o.setModalidad_trabajo(modalidad_trabajo); o.setEstado(estado);
        if (cluster_id != null) {
            o.setCluster(clusterService.findById(cluster_id).orElse(null));
        }
        if (sueldo != null) o.setSueldo(BigDecimal.valueOf(sueldo));
        if (categoria_id != null) o.setCategoria(categoriaService.findById(categoria_id).orElse(null));
        if (reclutador_id != null) {
            Reclutador rec = reclutadorService.findById(reclutador_id).orElse(null);
            if (rec == null) {
                Usuario usr = usuarioService.findById(reclutador_id).orElse(null);
                if (usr != null) {
                    rec = new Reclutador();
                    rec.setId(usr.getId());
                    rec.setNombre(usr.getNombre());
                    rec.setApellido(usr.getApellido());
                    rec.setEmail(usr.getEmail());
                    rec.setTelefono(usr.getTelefono());
                    rec.setEstado(usr.getEstado());
                    rec.setVideo_id(usr.getVideo_id());
                    rec.setCreated_at(usr.getCreated_at());
                    rec.setUpdated_at(usr.getUpdated_at());
                    rec.setRolObj(usr.getRolObj());
                    rec.setRol(usr.getRol());
                }
            }
            o.setReclutador(rec);
        }
        o.setFecha_publicacion(Instant.now());
        return ofertaService.save(o);
    }

    @MutationMapping
    public Oferta actualizarOferta(@Argument UUID id, @Argument String titulo, @Argument String descripcion,
                                   @Argument String contrato, @Argument String requisitos,
                                   @Argument Integer experiencia_tiempo, @Argument String modalidad_trabajo,
                                   @Argument String estado, @Argument Double sueldo,
                                   @Argument UUID cluster_id,
                                   @Argument UUID categoria_id, @Argument UUID reclutador_id) {
        Oferta o = ofertaService.findById(id).orElseThrow();
        if (titulo != null) o.setTitulo(titulo);
        if (descripcion != null) o.setDescripcion(descripcion);
        if (contrato != null) o.setContrato(contrato);
        if (requisitos != null) o.setRequisitos(requisitos);
        if (experiencia_tiempo != null) o.setExperiencia_tiempo(experiencia_tiempo);
        if (modalidad_trabajo != null) o.setModalidad_trabajo(modalidad_trabajo);
        if (estado != null) o.setEstado(estado);
        if (cluster_id != null) {
            o.setCluster(clusterService.findById(cluster_id).orElse(null));
        }
        if (sueldo != null) o.setSueldo(BigDecimal.valueOf(sueldo));
        if (categoria_id != null) o.setCategoria(categoriaService.findById(categoria_id).orElse(null));
        if (reclutador_id != null) {
            Reclutador rec = reclutadorService.findById(reclutador_id).orElse(null);
            if (rec == null) {
                Usuario usr = usuarioService.findById(reclutador_id).orElse(null);
                if (usr != null) {
                    rec = new Reclutador();
                    rec.setId(usr.getId());
                    rec.setNombre(usr.getNombre());
                    rec.setApellido(usr.getApellido());
                    rec.setEmail(usr.getEmail());
                    rec.setTelefono(usr.getTelefono());
                    rec.setEstado(usr.getEstado());
                    rec.setVideo_id(usr.getVideo_id());
                    rec.setCreated_at(usr.getCreated_at());
                    rec.setUpdated_at(usr.getUpdated_at());
                    rec.setRolObj(usr.getRolObj());
                    rec.setRol(usr.getRol());
                }
            }
            o.setReclutador(rec);
        }
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
        p.setId(UUID.randomUUID());
        p.setFase_alcanzada(fase_alcanzada); p.setId_cv(id_cv);
        if (candidato_id != null) p.setCandidato(candidatoService.findById(candidato_id).orElse(null));
        if (oferta_id != null) p.setOferta(ofertaService.findById(oferta_id).orElse(null));
        p.setFecha(Instant.now());
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
        Habilidades h = new Habilidades(); h.setId(UUID.randomUUID()); h.setNombre(nombre);
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
        ch.setId(UUID.randomUUID());
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
        oh.setId(UUID.randomUUID());
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
        ot.setId(UUID.randomUUID());
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
    public String entrenarKMeansCandidatos() {
        return machineLearningService.entrenarCandidatosManual();
    }

    @MutationMapping
    public String entrenarKMeansOfertas() {
        return machineLearningService.entrenarOfertasManual();
    }

    @MutationMapping
    public Integer clasificarCandidato(@Argument UUID id) {
        return machineLearningService.clasificarCandidato(id);
    }

    @MutationMapping
    public Integer clasificarOferta(@Argument UUID id) {
        return machineLearningService.clasificarOferta(id);
    }

    @MutationMapping
    public String dispararEntrenamientoRandomForestManual() {
        return machineLearningService.entrenarRandomForestManual();
    }

    @MutationMapping
    public String predecirExitoPostulacion(@Argument UUID id) {
        return machineLearningService.predecirExitoPostulacion(id);
    }

    // ══════════════════════ REGISTRO DISTRIBUIDO (PYTHON) ════════════════════════════════════

    @QueryMapping
    public Usuario getUserByEmail(@Argument String email) {
        java.util.Optional<Usuario> u = usuarioService.findByEmail(email);
        if (u.isPresent()) {
            return u.get();
        }
        java.util.Optional<Reclutador> r = reclutadorService.findByEmail(email);
        if (r.isPresent()) {
            return r.get();
        }
        java.util.Optional<Candidato> c = candidatoService.findByEmail(email);
        if (c.isPresent()) {
            return c.get();
        }
        return null;
    }

    @MutationMapping
    public com.AgenciaSpring.AgenciaSpring.dto.CreateUserResponse createUserFromPython(
            @Argument com.AgenciaSpring.AgenciaSpring.dto.CreateUserFromPythonInput input) {
        try {
            Usuario saved = usuarioService.createUserFromPython(input);
            return new com.AgenciaSpring.AgenciaSpring.dto.CreateUserResponse(
                    true, 
                    saved.getId().toString(), 
                    "Usuario registrado exitosamente desde Python"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new com.AgenciaSpring.AgenciaSpring.dto.CreateUserResponse(
                    false, 
                    null, 
                    "Error al guardar: " + e.getMessage()
            );
        }
    }

    @MutationMapping
    public Usuario actualizarVideoId(@Argument UUID id, @Argument String videoId) {
        try {
            java.util.Optional<Usuario> u = usuarioService.findById(id);
            if (u.isPresent()) {
                Usuario usr = u.get();
                usr.setVideo_id(videoId);
                return usuarioService.save(usr);
            }
            java.util.Optional<Reclutador> r = reclutadorService.findById(id);
            if (r.isPresent()) {
                Reclutador rec = r.get();
                rec.setVideo_id(videoId);
                return reclutadorService.save(rec);
            }
            java.util.Optional<Candidato> c = candidatoService.findById(id);
            if (c.isPresent()) {
                Candidato cand = c.get();
                cand.setVideo_id(videoId);
                return candidatoService.save(cand);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SchemaMapping(typeName = "Oferta", field = "cluster_id")
    public UUID getClusterId(Oferta o) {
        return o.getCluster() != null ? o.getCluster().getId() : null;
    }

    @SchemaMapping(typeName = "Candidato", field = "cluster_id")
    public UUID getClusterId(Candidato c) {
        return c.getCluster() != null ? c.getCluster().getId() : null;
    }
}
