-- =============================================================================
-- SEED DATA PARA AGENCIA SPRING - MACHINE LEARNING TRAINING
-- Base de datos: PostgreSQL (MSSpring)
-- Genera: ~2000 candidatos, ~80 ofertas, ~6000+ postulaciones con fases,
--         habilidades para candidatos y ofertas, empresas, reclutadores, etc.
-- =============================================================================
-- INSTRUCCIONES:
--   1. Asegúrate de que las tablas ya existan (Spring Boot con ddl-auto=update las crea).
--   2. Ejecuta este script en PostgreSQL: psql -U postgres -d MSSpring -f seed_data.sql
--   3. Luego puedes disparar el entrenamiento K-Means y Random Forest desde GraphiQL.
-- =============================================================================

-- Limpieza previa (orden inverso por FK)
TRUNCATE TABLE postulaciones CASCADE;
TRUNCATE TABLE candidato_habilidades CASCADE;
TRUNCATE TABLE oferta_habilidades CASCADE;
TRUNCATE TABLE clusters CASCADE;
TRUNCATE TABLE oferta_trabajos CASCADE;
TRUNCATE TABLE ofertas CASCADE;
TRUNCATE TABLE candidatos CASCADE;
TRUNCATE TABLE reclutadores CASCADE;
TRUNCATE TABLE usuarios CASCADE;
TRUNCATE TABLE empresas CASCADE;
TRUNCATE TABLE categorias CASCADE;
TRUNCATE TABLE habilidades CASCADE;
TRUNCATE TABLE trabajos CASCADE;
TRUNCATE TABLE roles CASCADE;

-- =============================================================================
-- 1. ROLES
-- =============================================================================
INSERT INTO roles (id, nombre, description) VALUES
  ('a0000000-0000-0000-0000-000000000001', 'Administrador', 'Acceso total al sistema'),
  ('a0000000-0000-0000-0000-000000000002', 'Candidato', 'Persona que busca empleo'),
  ('a0000000-0000-0000-0000-000000000003', 'Reclutador', 'Persona que publica ofertas');

-- =============================================================================
-- 2. EMPRESAS (30 empresas bolivianas ficticias)
-- =============================================================================
INSERT INTO empresas (id, nombre_legal, nombre_comercial, nit, direccion, celular) VALUES
  ('b0000000-0000-0000-0000-000000000001', 'TechBolivia SRL', 'TechBolivia', 10200301, 'Av. Arce 2500, La Paz', 71234501),
  ('b0000000-0000-0000-0000-000000000002', 'Innovación Digital SA', 'InnoDigital', 10200302, 'C. Sucre 450, Cochabamba', 71234502),
  ('b0000000-0000-0000-0000-000000000003', 'Soluciones Cloud SRL', 'CloudSol', 10200303, 'Av. Cristo Redentor, Santa Cruz', 71234503),
  ('b0000000-0000-0000-0000-000000000004', 'DataMining Bolivia SA', 'DataMine', 10200304, 'Av. Camacho 1200, La Paz', 71234504),
  ('b0000000-0000-0000-0000-000000000005', 'Software Express SRL', 'SoftExpress', 10200305, 'C. Bolívar 320, Sucre', 71234505),
  ('b0000000-0000-0000-0000-000000000006', 'Grupo Financiero Altiplano SA', 'GFA', 10200306, 'Av. 16 de Julio 1580, La Paz', 71234506),
  ('b0000000-0000-0000-0000-000000000007', 'Constructora Andes SRL', 'ConAndes', 10200307, 'Av. Bush 400, Santa Cruz', 71234507),
  ('b0000000-0000-0000-0000-000000000008', 'Agro Industrial Oriente SA', 'AgroOriente', 10200308, 'Av. Santos Dumont, Santa Cruz', 71234508),
  ('b0000000-0000-0000-0000-000000000009', 'Telecomunicaciones Sur SRL', 'TeleSur', 10200309, 'C. Junín 200, Tarija', 71234509),
  ('b0000000-0000-0000-0000-000000000010', 'Energía Limpia Bolivia SA', 'EnerLimpia', 10200310, 'Av. Petrolera, Cochabamba', 71234510),
  ('b0000000-0000-0000-0000-000000000011', 'Logística Express SRL', 'LogExpress', 10200311, 'Zona Franca, El Alto', 71234511),
  ('b0000000-0000-0000-0000-000000000012', 'Minería Digital SA', 'MineDigit', 10200312, 'Av. Cívica, Oruro', 71234512),
  ('b0000000-0000-0000-0000-000000000013', 'Salud Integral SRL', 'SaludInt', 10200313, 'C. España 1500, Cochabamba', 71234513),
  ('b0000000-0000-0000-0000-000000000014', 'Educación Virtual SA', 'EduVirtual', 10200314, 'Av. Ballivián 800, La Paz', 71234514),
  ('b0000000-0000-0000-0000-000000000015', 'Transportes Nacionales SRL', 'TransNac', 10200315, 'Terminal Bimodal, Santa Cruz', 71234515),
  ('b0000000-0000-0000-0000-000000000016', 'Comercio Digital SRL', 'ComDigital', 10200316, 'Av. Banzer 3000, Santa Cruz', 71234516),
  ('b0000000-0000-0000-0000-000000000017', 'Farmacéutica Andina SA', 'FarmAndina', 10200317, 'C. Potosí 600, La Paz', 71234517),
  ('b0000000-0000-0000-0000-000000000018', 'Textiles Modernas SRL', 'TextMod', 10200318, 'Zona Industrial, El Alto', 71234518),
  ('b0000000-0000-0000-0000-000000000019', 'Consultoría Integral SA', 'ConsInt', 10200319, 'Av. Montes 900, La Paz', 71234519),
  ('b0000000-0000-0000-0000-000000000020', 'Alimentos del Sur SRL', 'AliSur', 10200320, 'Av. Circunvalación, Tarija', 71234520),
  ('b0000000-0000-0000-0000-000000000021', 'Banca Móvil Solutions SA', 'BancaMob', 10200321, 'C. Mercado 150, La Paz', 71234521),
  ('b0000000-0000-0000-0000-000000000022', 'CyberSeguridad Bolivia SRL', 'CyberSeg', 10200322, 'Av. Irala 2200, Santa Cruz', 71234522),
  ('b0000000-0000-0000-0000-000000000023', 'Marketing 360 SRL', 'Mark360', 10200323, 'C. Colón 780, Cochabamba', 71234523),
  ('b0000000-0000-0000-0000-000000000024', 'Desarrollo Urbano SA', 'DesUrb', 10200324, 'Av. Blanco Galindo, Cochabamba', 71234524),
  ('b0000000-0000-0000-0000-000000000025', 'Turismo Aventura SRL', 'TurAventura', 10200325, 'C. Sagárnaga 300, La Paz', 71234525),
  ('b0000000-0000-0000-0000-000000000026', 'Petróleo y Gas Bolivia SA', 'PetGasBol', 10200326, 'Av. Busch, Santa Cruz', 71234526),
  ('b0000000-0000-0000-0000-000000000027', 'Seguros Confianza SA', 'SegConfianza', 10200327, 'Av. Arce 2100, La Paz', 71234527),
  ('b0000000-0000-0000-0000-000000000028', 'Medios Digitales SRL', 'MedDigit', 10200328, 'C. Junín 600, Cochabamba', 71234528),
  ('b0000000-0000-0000-0000-000000000029', 'Ingeniería Aplicada SA', 'IngAplicada', 10200329, 'Av. Petrolera 1500, Santa Cruz', 71234529),
  ('b0000000-0000-0000-0000-000000000030', 'Retail Group Bolivia SA', 'RetailBol', 10200330, 'Av. Monseñor Rivero, Santa Cruz', 71234530);

-- =============================================================================
-- 3. CATEGORÍAS (15 categorías de empleo)
-- =============================================================================
INSERT INTO categorias (id, nombre) VALUES
  ('c0000000-0000-0000-0000-000000000001', 'Desarrollo de Software'),
  ('c0000000-0000-0000-0000-000000000002', 'Ciencia de Datos'),
  ('c0000000-0000-0000-0000-000000000003', 'Diseño UX/UI'),
  ('c0000000-0000-0000-0000-000000000004', 'DevOps / Cloud'),
  ('c0000000-0000-0000-0000-000000000005', 'Marketing Digital'),
  ('c0000000-0000-0000-0000-000000000006', 'Administración'),
  ('c0000000-0000-0000-0000-000000000007', 'Contabilidad y Finanzas'),
  ('c0000000-0000-0000-0000-000000000008', 'Recursos Humanos'),
  ('c0000000-0000-0000-0000-000000000009', 'Ventas'),
  ('c0000000-0000-0000-0000-000000000010', 'Atención al Cliente'),
  ('c0000000-0000-0000-0000-000000000011', 'Ingeniería Civil'),
  ('c0000000-0000-0000-0000-000000000012', 'Salud'),
  ('c0000000-0000-0000-0000-000000000013', 'Educación'),
  ('c0000000-0000-0000-0000-000000000014', 'Logística'),
  ('c0000000-0000-0000-0000-000000000015', 'Seguridad Informática');

-- =============================================================================
-- 4. HABILIDADES (50 habilidades técnicas y blandas)
-- =============================================================================
INSERT INTO habilidades (id, nombre) VALUES
  ('d0000000-0000-0000-0000-000000000001', 'Java'),
  ('d0000000-0000-0000-0000-000000000002', 'Python'),
  ('d0000000-0000-0000-0000-000000000003', 'JavaScript'),
  ('d0000000-0000-0000-0000-000000000004', 'TypeScript'),
  ('d0000000-0000-0000-0000-000000000005', 'React'),
  ('d0000000-0000-0000-0000-000000000006', 'Angular'),
  ('d0000000-0000-0000-0000-000000000007', 'Node.js'),
  ('d0000000-0000-0000-0000-000000000008', 'Spring Boot'),
  ('d0000000-0000-0000-0000-000000000009', 'PostgreSQL'),
  ('d0000000-0000-0000-0000-000000000010', 'MongoDB'),
  ('d0000000-0000-0000-0000-000000000011', 'Docker'),
  ('d0000000-0000-0000-0000-000000000012', 'Kubernetes'),
  ('d0000000-0000-0000-0000-000000000013', 'AWS'),
  ('d0000000-0000-0000-0000-000000000014', 'Azure'),
  ('d0000000-0000-0000-0000-000000000015', 'Git'),
  ('d0000000-0000-0000-0000-000000000016', 'CI/CD'),
  ('d0000000-0000-0000-0000-000000000017', 'Machine Learning'),
  ('d0000000-0000-0000-0000-000000000018', 'Deep Learning'),
  ('d0000000-0000-0000-0000-000000000019', 'SQL'),
  ('d0000000-0000-0000-0000-000000000020', 'Excel Avanzado'),
  ('d0000000-0000-0000-0000-000000000021', 'Power BI'),
  ('d0000000-0000-0000-0000-000000000022', 'Tableau'),
  ('d0000000-0000-0000-0000-000000000023', 'Figma'),
  ('d0000000-0000-0000-0000-000000000024', 'Adobe XD'),
  ('d0000000-0000-0000-0000-000000000025', 'Photoshop'),
  ('d0000000-0000-0000-0000-000000000026', 'Scrum'),
  ('d0000000-0000-0000-0000-000000000027', 'Liderazgo'),
  ('d0000000-0000-0000-0000-000000000028', 'Comunicación'),
  ('d0000000-0000-0000-0000-000000000029', 'Trabajo en Equipo'),
  ('d0000000-0000-0000-0000-000000000030', 'Negociación'),
  ('d0000000-0000-0000-0000-000000000031', 'SAP'),
  ('d0000000-0000-0000-0000-000000000032', 'Contabilidad'),
  ('d0000000-0000-0000-0000-000000000033', 'Administración de Proyectos'),
  ('d0000000-0000-0000-0000-000000000034', 'SEO/SEM'),
  ('d0000000-0000-0000-0000-000000000035', 'Google Ads'),
  ('d0000000-0000-0000-0000-000000000036', 'Redes Sociales'),
  ('d0000000-0000-0000-0000-000000000037', 'Redacción'),
  ('d0000000-0000-0000-0000-000000000038', 'Inglés'),
  ('d0000000-0000-0000-0000-000000000039', 'Flutter'),
  ('d0000000-0000-0000-0000-000000000040', 'React Native'),
  ('d0000000-0000-0000-0000-000000000041', 'Linux'),
  ('d0000000-0000-0000-0000-000000000042', 'Networking'),
  ('d0000000-0000-0000-0000-000000000043', 'C#'),
  ('d0000000-0000-0000-0000-000000000044', '.NET'),
  ('d0000000-0000-0000-0000-000000000045', 'PHP'),
  ('d0000000-0000-0000-0000-000000000046', 'Laravel'),
  ('d0000000-0000-0000-0000-000000000047', 'Kotlin'),
  ('d0000000-0000-0000-0000-000000000048', 'Swift'),
  ('d0000000-0000-0000-0000-000000000049', 'GraphQL'),
  ('d0000000-0000-0000-0000-000000000050', 'Redis');

-- =============================================================================
-- 5. TRABAJOS (20 tipos de trabajo)
-- =============================================================================
INSERT INTO trabajos (id, nombre, codigo) VALUES
  ('e0000000-0000-0000-0000-000000000001', 'Desarrollador Backend', 'DEV-BACK'),
  ('e0000000-0000-0000-0000-000000000002', 'Desarrollador Frontend', 'DEV-FRONT'),
  ('e0000000-0000-0000-0000-000000000003', 'Desarrollador Full Stack', 'DEV-FULL'),
  ('e0000000-0000-0000-0000-000000000004', 'Científico de Datos', 'DATA-SCI'),
  ('e0000000-0000-0000-0000-000000000005', 'Analista de Datos', 'DATA-ANA'),
  ('e0000000-0000-0000-0000-000000000006', 'Diseñador UX/UI', 'UX-UI'),
  ('e0000000-0000-0000-0000-000000000007', 'DevOps Engineer', 'DEVOPS'),
  ('e0000000-0000-0000-0000-000000000008', 'QA Tester', 'QA'),
  ('e0000000-0000-0000-0000-000000000009', 'Product Manager', 'PM'),
  ('e0000000-0000-0000-0000-000000000010', 'Scrum Master', 'SCRUM'),
  ('e0000000-0000-0000-0000-000000000011', 'Gerente de Marketing', 'MKT-GER'),
  ('e0000000-0000-0000-0000-000000000012', 'Community Manager', 'MKT-CM'),
  ('e0000000-0000-0000-0000-000000000013', 'Contador', 'CONT'),
  ('e0000000-0000-0000-0000-000000000014', 'Administrador', 'ADMIN'),
  ('e0000000-0000-0000-0000-000000000015', 'Ejecutivo de Ventas', 'VENTAS'),
  ('e0000000-0000-0000-0000-000000000016', 'Ingeniero de Redes', 'NET-ENG'),
  ('e0000000-0000-0000-0000-000000000017', 'Desarrollador Móvil', 'DEV-MOB'),
  ('e0000000-0000-0000-0000-000000000018', 'Analista de Seguridad', 'SEC-ANA'),
  ('e0000000-0000-0000-0000-000000000019', 'Soporte Técnico', 'SOPORTE'),
  ('e0000000-0000-0000-0000-000000000020', 'Arquitecto de Software', 'ARCH');

-- =============================================================================
-- 6. RECLUTADORES (30 reclutadores, uno por empresa)
--    Cada reclutador es un Usuario con rol 'Reclutador'
-- =============================================================================

-- Primero insertamos los usuarios base de los reclutadores
DO $$
DECLARE
  emp_ids UUID[] := ARRAY[
    'b0000000-0000-0000-0000-000000000001','b0000000-0000-0000-0000-000000000002',
    'b0000000-0000-0000-0000-000000000003','b0000000-0000-0000-0000-000000000004',
    'b0000000-0000-0000-0000-000000000005','b0000000-0000-0000-0000-000000000006',
    'b0000000-0000-0000-0000-000000000007','b0000000-0000-0000-0000-000000000008',
    'b0000000-0000-0000-0000-000000000009','b0000000-0000-0000-0000-000000000010',
    'b0000000-0000-0000-0000-000000000011','b0000000-0000-0000-0000-000000000012',
    'b0000000-0000-0000-0000-000000000013','b0000000-0000-0000-0000-000000000014',
    'b0000000-0000-0000-0000-000000000015','b0000000-0000-0000-0000-000000000016',
    'b0000000-0000-0000-0000-000000000017','b0000000-0000-0000-0000-000000000018',
    'b0000000-0000-0000-0000-000000000019','b0000000-0000-0000-0000-000000000020',
    'b0000000-0000-0000-0000-000000000021','b0000000-0000-0000-0000-000000000022',
    'b0000000-0000-0000-0000-000000000023','b0000000-0000-0000-0000-000000000024',
    'b0000000-0000-0000-0000-000000000025','b0000000-0000-0000-0000-000000000026',
    'b0000000-0000-0000-0000-000000000027','b0000000-0000-0000-0000-000000000028',
    'b0000000-0000-0000-0000-000000000029','b0000000-0000-0000-0000-000000000030'
  ];
  nombres TEXT[] := ARRAY[
    'Carlos','María','Pedro','Ana','Jorge','Lucía','Roberto','Elena','Miguel','Sandra',
    'Fernando','Patricia','Alejandro','Gabriela','Ricardo','Mónica','Diego','Valeria','Andrés','Claudia',
    'Sergio','Daniela','Martín','Isabel','Raúl','Carla','Hugo','Natalia','Óscar','Silvia'
  ];
  apellidos TEXT[] := ARRAY[
    'Mamani','Quispe','Condori','Choque','Flores','Gutiérrez','Rojas','Vargas','López','Morales',
    'Calle','Huanca','Torrez','Mendoza','Sánchez','Rivera','Poma','Limachi','Fernández','Camacho',
    'Arce','Bautista','Céspedes','Durán','Espinoza','Fuentes','García','Herrera','Ibáñez','Justiniano'
  ];
  cargos TEXT[] := ARRAY[
    'Gerente de RRHH','Jefe de Selección','Coordinador de Talento','Director de Personal',
    'Analista de Reclutamiento','Especialista en Selección','Manager de RRHH','Head of Talent',
    'Recruiter Senior','Coordinadora de RRHH','Jefa de Talento','Directora de Selección',
    'Gerente de Talento','Especialista de RRHH','Lead Recruiter','HR Business Partner',
    'Talent Acquisition Lead','Coordinador de Personal','Directora de RRHH','Gerente de Personal',
    'Jefe de Recursos Humanos','Analista de Talento','Recruiter','HR Manager',
    'People Operations','Talent Manager','Coordinadora de Selección','Jefe de Reclutamiento',
    'Director de Talento','Gerenta de Selección'
  ];
  rec_id UUID;
  i INT;
BEGIN
  FOR i IN 1..30 LOOP
    rec_id := ('f0000000-0000-0000-0000-0000000000' || LPAD(i::TEXT, 2, '0'))::UUID;
    
    INSERT INTO usuarios (id, nombre, apellido, email, password, telefono, rol, estado, updated_at, created_at, rol_id)
    VALUES (
      rec_id,
      nombres[i],
      apellidos[i],
      'reclutador' || i || '@agencia.bo',
      '$2a$10$fakehashforseeding' || LPAD(i::TEXT, 2, '0'),
      '7' || (1000000 + i * 1111)::TEXT,
      'Reclutador',
      'Activo',
      NOW(),
      NOW() - INTERVAL '1 year' * (random() * 2),
      'a0000000-0000-0000-0000-000000000003'
    );
    
    INSERT INTO reclutadores (id, telefono_reclutador, cargo, empresa_id)
    VALUES (
      rec_id,
      (70000000 + i * 11111),
      cargos[i],
      emp_ids[i]
    );
  END LOOP;
END $$;

-- =============================================================================
-- 7. OFERTAS (80 ofertas laborales diversas)
-- =============================================================================
DO $$
DECLARE
  titulos TEXT[] := ARRAY[
    'Desarrollador Backend Java Senior','Desarrollador Frontend React','Full Stack Developer',
    'Científico de Datos Python','Analista de Datos Junior','Diseñador UX/UI Senior',
    'DevOps Engineer AWS','QA Automation Tester','Product Manager Digital','Scrum Master Certificado',
    'Desarrollador Backend Node.js','Desarrollador Angular Senior','Data Engineer',
    'Diseñador UX Mobile','Cloud Architect','Analista de Business Intelligence',
    'Desarrollador PHP Laravel','Desarrollador .NET C#','Mobile Developer Flutter',
    'Machine Learning Engineer','Desarrollador Python Django','Frontend Developer Vue.js',
    'Ingeniero de Infraestructura','Analista de Seguridad Informática','Administrador de BD',
    'Gerente de Marketing Digital','Community Manager Senior','Ejecutivo de Ventas IT',
    'Contador Senior','Administrador de Empresas','Analista Financiero',
    'Ingeniero de Redes','Soporte Técnico N2','Desarrollador Kotlin Android',
    'Desarrollador iOS Swift','Arquitecto de Software','Tech Lead Java',
    'Desarrollador React Native','Ingeniero de Datos AWS','Analista QA Manual',
    'Consultor SAP','Coordinador de Proyectos IT','Desarrollador GraphQL',
    'SRE Engineer','Especialista en Ciberseguridad','DBA PostgreSQL',
    'Desarrollador Microservicios','API Developer REST','Scrum Master / Agile Coach',
    'Growth Hacker','Especialista SEO/SEM','Content Manager',
    'Diseñador Gráfico Digital','Analista de Procesos','Auditor de Sistemas',
    'Desarrollador Backend Python','Full Stack JavaScript Developer','DevOps Engineer Azure',
    'Data Analyst Senior','ML Ops Engineer','Desarrollador Blockchain',
    'Pentester / Ethical Hacker','Ingeniero de Software Senior','QA Lead',
    'UX Researcher','Product Owner','Release Manager',
    'Desarrollador Java Spring Boot','Frontend React + TypeScript','Backend Developer Go',
    'Analista de Datos Power BI','Cloud Engineer GCP','Desarrollador Salesforce',
    'Ingeniero de Automatización','Especialista en IoT','Desarrollador AR/VR',
    'Analista de Costos','Ejecutivo Comercial B2B','Coordinador de RRHH',
    'Administrador de Servidores','Project Manager PMI'
  ];
  descripciones TEXT[] := ARRAY[
    'Buscamos un profesional con experiencia sólida para unirse a nuestro equipo de desarrollo.',
    'Estamos en búsqueda de talento para fortalecer nuestro equipo de tecnología.',
    'Oportunidad para profesional apasionado por la innovación tecnológica.',
    'Únete a nuestro equipo de trabajo dinámico y en constante crecimiento.',
    'Buscamos profesional comprometido con la excelencia y la mejora continua.'
  ];
  contratos TEXT[] := ARRAY['Tiempo Completo','Medio Tiempo','Por Proyecto','Temporal'];
  modalidades TEXT[] := ARRAY['Remoto','Presencial','Híbrido'];
  niveles_edu TEXT[] := ARRAY['Bachiller','Técnico','Universitario','Maestría','Doctorado'];
  estados_of TEXT[] := ARRAY['Activa','Activa','Activa','Cerrada'];
  cat_ids UUID[];
  rec_ids UUID[];
  oferta_id UUID;
  r_idx INT;
  o_idx INT;
  sueldo_base NUMERIC;
  exp_req INT;
BEGIN
  -- Recoger IDs de categorías
  cat_ids := ARRAY(SELECT id FROM categorias ORDER BY id);
  -- Recoger IDs de reclutadores
  rec_ids := ARRAY(SELECT id FROM reclutadores ORDER BY id);

  FOR r_idx IN 1..30 LOOP
    FOR o_idx IN 1..100 LOOP
      oferta_id := gen_random_uuid();
      
      -- Sueldo base variable según posición (entre 2500 y 25000 BOB)
      sueldo_base := 2500 + (random() * 22500)::INT;
      -- Redondear a centenas
      sueldo_base := ROUND(sueldo_base / 100) * 100;
      
      -- Experiencia requerida (0 a 10 años en meses)
      exp_req := (random() * 120)::INT;

      INSERT INTO ofertas (id, titulo, descripcion, contrato, requisitos, experiencia_tiempo, modalidad_trabajo, nivel_educativo, estado, sueldo, cluster_id, fecha_publicacion, fecha_vencimiento, categoria_id, reclutador_id)
      VALUES (
        oferta_id,
        titulos[1 + ((r_idx * 100 + o_idx) % 80)],
        descripciones[1 + ((r_idx * 100 + o_idx) % 5)],
        contratos[1 + ((r_idx * 100 + o_idx) % 4)],
        'Requisitos específicos para la posición de ' || titulos[1 + ((r_idx * 100 + o_idx) % 80)],
        exp_req,
        modalidades[1 + ((r_idx * 100 + o_idx) % 3)],
        niveles_edu[1 + ((r_idx * 100 + o_idx) % 5)],
        estados_of[1 + ((r_idx * 100 + o_idx) % 4)],
        sueldo_base,
        NULL,
        NOW() - INTERVAL '1 day' * (random() * 365)::INT,
        NOW() + INTERVAL '1 day' * (random() * 90)::INT,
        cat_ids[1 + ((r_idx * 100 + o_idx) % 15)],
        rec_ids[r_idx]
      );
    END LOOP;
  END LOOP;
END $$;

-- =============================================================================
-- 8. OFERTA_HABILIDADES (3 a 6 habilidades por oferta)
-- =============================================================================
DO $$
DECLARE
  of_id UUID;
  hab_ids UUID[];
  niveles_imp TEXT[] := ARRAY['Alta','Media','Baja'];
  num_hab INT;
  selected_hab UUID[];
  i INT;
  j INT;
  oh_id UUID;
BEGIN
  hab_ids := ARRAY(SELECT id FROM habilidades ORDER BY id);

  FOR of_id IN SELECT id FROM ofertas LOOP
    num_hab := 3 + (random() * 3)::INT;  -- 3 a 6 habilidades
    
    -- Seleccionar habilidades aleatorias sin repetir
    selected_hab := ARRAY(
      SELECT id FROM habilidades ORDER BY random() LIMIT num_hab
    );
    
    FOR j IN 1..array_length(selected_hab, 1) LOOP
      oh_id := gen_random_uuid();
      INSERT INTO oferta_habilidades (id, nivel_importancia, oferta_id, habilidad_id)
      VALUES (oh_id, niveles_imp[1 + (j % 3)], of_id, selected_hab[j]);
    END LOOP;
  END LOOP;
END $$;

-- =============================================================================
-- 9. OFERTA_TRABAJOS (1 a 2 trabajos por oferta)
-- =============================================================================
DO $$
DECLARE
  of_id UUID;
  trab_ids UUID[];
  num_trab INT;
  selected_trab UUID[];
  j INT;
  ot_id UUID;
BEGIN
  trab_ids := ARRAY(SELECT id FROM trabajos ORDER BY id);

  FOR of_id IN SELECT id FROM ofertas LOOP
    num_trab := 1 + (random() * 1)::INT;  -- 1 a 2 trabajos

    selected_trab := ARRAY(
      SELECT id FROM trabajos ORDER BY random() LIMIT num_trab
    );

    FOR j IN 1..array_length(selected_trab, 1) LOOP
      ot_id := gen_random_uuid();
      INSERT INTO oferta_trabajos (id, oferta_id, trabajo_id)
      VALUES (ot_id, of_id, selected_trab[j]);
    END LOOP;
  END LOOP;
END $$;

-- =============================================================================
-- 10. CANDIDATOS (2000 candidatos con perfiles variados)
--     Cada candidato es un Usuario + fila en candidatos
-- =============================================================================
DO $$
DECLARE
  nombres TEXT[] := ARRAY[
    'Juan','María','Carlos','Ana','Pedro','Lucía','Jorge','Elena','Miguel','Sandra',
    'Fernando','Patricia','Alejandro','Gabriela','Ricardo','Mónica','Diego','Valeria','Andrés','Claudia',
    'Sergio','Daniela','Martín','Isabel','Raúl','Carla','Hugo','Natalia','Óscar','Silvia',
    'Luis','Rosa','David','Andrea','Eduardo','Lorena','Gustavo','Verónica','Álvaro','Paola',
    'Ernesto','Carolina','Ángel','Beatriz','Gonzalo','Adriana','Iván','Tatiana','Pablo','Marcela',
    'Arturo','Jimena','Ramón','Fabiola','Javier','Liliana','Alberto','Karina','Nelson','Ximena',
    'Rodrigo','Sonia','Mario','Teresa','Enrique','Diana','Víctor','Pamela','Orlando','Gloria',
    'Gerardo','Alicia','Alfredo','Esmeralda','César','Marisol','Rubén','Nadia','Ignacio','Viviana',
    'Emilio','Leticia','Cristian','Araceli','Xavier','Cinthia','Fabián','Rocío','Boris','Mariana',
    'Wálter','Yolanda','Freddy','Rosario','Ronald','Cecilia','Erick','Susana','Milton','Soledad'
  ];
  apellidos TEXT[] := ARRAY[
    'Mamani','Quispe','Condori','Choque','Flores','Gutiérrez','Rojas','Vargas','López','Morales',
    'Calle','Huanca','Torrez','Mendoza','Sánchez','Rivera','Poma','Limachi','Fernández','Camacho',
    'Arce','Bautista','Céspedes','Durán','Espinoza','Fuentes','García','Herrera','Ibáñez','Justiniano',
    'Cáceres','Montaño','Peña','Quiroga','Salazar','Tapia','Urzagasti','Villarroel','Yáñez','Zárate',
    'Alarcón','Balderrama','Cabrera','Delgado','Escalante','Franco','Gallegos','Hinojosa','Illanes','Jiménez'
  ];
  nacionalidades TEXT[] := ARRAY[
    'Boliviana','Boliviana','Boliviana','Boliviana','Boliviana','Boliviana','Boliviana','Boliviana',
    'Peruana','Argentina','Colombiana','Chilena','Brasileña','Venezolana','Ecuatoriana','Paraguaya'
  ];
  modalidades TEXT[] := ARRAY['Remoto','Presencial','Híbrido'];
  niveles_edu TEXT[] := ARRAY['Bachiller','Técnico','Universitario','Maestría','Doctorado'];
  -- Pesos para nivel educativo: más universitarios, menos doctorados
  niveles_pesos INT[] := ARRAY[1,1,1,1,2,2,2,3,3,3,3,3,3,3,3,4,4,5];
  cand_id UUID;
  i INT;
  sueldo_esp NUMERIC;
  meses_exp INT;
  nivel_idx INT;
BEGIN
  FOR i IN 1..2000 LOOP
    cand_id := gen_random_uuid();
    
    -- Sueldo esperado entre 1800 y 20000 BOB
    sueldo_esp := 1800 + (random() * 18200)::INT;
    sueldo_esp := ROUND(sueldo_esp / 100) * 100;
    
    -- Meses experiencia: 0 a 180 (15 años)
    meses_exp := (random() * 180)::INT;
    
    -- Nivel educativo con distribución realista
    nivel_idx := niveles_pesos[1 + (random() * 17)::INT];

    -- Insertar usuario base
    INSERT INTO usuarios (id, nombre, apellido, email, password, telefono, rol, estado, updated_at, created_at, rol_id)
    VALUES (
      cand_id,
      nombres[1 + (random() * 99)::INT],
      apellidos[1 + (random() * 49)::INT] || ' ' || apellidos[1 + (random() * 49)::INT],
      'candidato' || i || '_' || EXTRACT(EPOCH FROM NOW())::INT || '@mail.com',
      '$2a$10$fakecandidatehash' || LPAD(i::TEXT, 4, '0'),
      '6' || (1000000 + (random() * 8999999)::INT)::TEXT,
      'Candidato',
      CASE WHEN random() < 0.95 THEN 'Activo' ELSE 'Inactivo' END,
      NOW(),
      NOW() - INTERVAL '1 day' * (random() * 730)::INT,
      'a0000000-0000-0000-0000-000000000002'
    );

    -- Insertar candidato específico
    INSERT INTO candidatos (id, registro, sueldo_esperado, modalidad_preferida, nivel_educativo, nacionalidad, meses_experiencia_total, cluster_id)
    VALUES (
      cand_id,
      1000 + i,
      sueldo_esp,
      modalidades[1 + (random() * 2)::INT],
      niveles_edu[nivel_idx],
      nacionalidades[1 + (random() * 15)::INT],
      meses_exp,
      NULL
    );
  END LOOP;
END $$;

-- =============================================================================
-- 11. CANDIDATO_HABILIDADES (2 a 8 habilidades por candidato)
-- =============================================================================
DO $$
DECLARE
  cand_id UUID;
  num_hab INT;
  selected_hab UUID[];
  j INT;
  ch_id UUID;
BEGIN
  FOR cand_id IN SELECT id FROM candidatos LOOP
    num_hab := 2 + (random() * 6)::INT;  -- 2 a 8 habilidades

    selected_hab := ARRAY(
      SELECT id FROM habilidades ORDER BY random() LIMIT num_hab
    );

    FOR j IN 1..array_length(selected_hab, 1) LOOP
      ch_id := gen_random_uuid();
      INSERT INTO candidato_habilidades (id, candidato_id, habilidad_id)
      VALUES (ch_id, cand_id, selected_hab[j]);
    END LOOP;
  END LOOP;
END $$;

-- =============================================================================
-- 12. POSTULACIONES (~6000+ postulaciones con fases realistas)
--     Cada candidato postula a 1-8 ofertas diferentes
--     Fases: Postulado, Aprobó Entrevista Técnica, Oferta Realizada, Contratado
--     Distribución realista:
--       ~40% queda en "Postulado" (rechazado en filtro inicial)
--       ~25% llega a "Aprobó Entrevista Técnica" 
--       ~20% llega a "Oferta Realizada"
--       ~15% llega a "Contratado"
-- =============================================================================
DO $$
DECLARE
  cand_id UUID;
  oferta_ids UUID[];
  num_post INT;
  selected_ofertas UUID[];
  j INT;
  post_id UUID;
  fase TEXT;
  fase_rand FLOAT;
  fases TEXT[] := ARRAY['Postulado', 'Aprobó Entrevista Técnica', 'Oferta Realizada', 'Contratado'];
  -- Variables para lógica correlacionada con perfil
  cand_exp INT;
  cand_nivel TEXT;
  cand_sueldo NUMERIC;
  oferta_sueldo NUMERIC;
  oferta_exp INT;
  oferta_nivel TEXT;
  bonus FLOAT;
  cand_habs UUID[];
  oferta_habs UUID[];
  match_count INT;
BEGIN
  oferta_ids := ARRAY(SELECT id FROM ofertas WHERE estado = 'Activa' ORDER BY id);
  
  -- Si no hay ofertas activas suficientes, tomar todas
  IF array_length(oferta_ids, 1) IS NULL OR array_length(oferta_ids, 1) < 10 THEN
    oferta_ids := ARRAY(SELECT id FROM ofertas ORDER BY id);
  END IF;

  FOR cand_id IN SELECT id FROM candidatos LOOP
    -- Cada candidato postula a entre 1 y 8 ofertas
    num_post := 1 + (random() * 7)::INT;
    
    -- Seleccionar ofertas aleatorias
    selected_ofertas := ARRAY(
      SELECT id FROM ofertas ORDER BY random() LIMIT num_post
    );
    
    -- Obtener datos del candidato para correlación
    SELECT c.meses_experiencia_total, c.nivel_educativo, c.sueldo_esperado
    INTO cand_exp, cand_nivel, cand_sueldo
    FROM candidatos c WHERE c.id = cand_id;
    
    -- Habilidades del candidato
    cand_habs := ARRAY(
      SELECT habilidad_id FROM candidato_habilidades WHERE candidato_id = cand_id
    );

    FOR j IN 1..array_length(selected_ofertas, 1) LOOP
      post_id := gen_random_uuid();
      
      -- Obtener datos de la oferta para correlación
      SELECT o.sueldo, o.experiencia_tiempo, o.nivel_educativo
      INTO oferta_sueldo, oferta_exp, oferta_nivel
      FROM ofertas o WHERE o.id = selected_ofertas[j];
      
      -- Habilidades de la oferta
      oferta_habs := ARRAY(
        SELECT habilidad_id FROM oferta_habilidades WHERE oferta_id = selected_ofertas[j]
      );
      
      -- Calcular bonus basado en match del perfil (para hacer datos más realistas para ML)
      bonus := 0.0;
      
      -- Bonus por experiencia suficiente
      IF cand_exp IS NOT NULL AND oferta_exp IS NOT NULL AND cand_exp >= oferta_exp THEN
        bonus := bonus + 0.10;
      END IF;
      
      -- Bonus por sueldo compatible (candidato pide menos o igual)
      IF cand_sueldo IS NOT NULL AND oferta_sueldo IS NOT NULL AND cand_sueldo <= oferta_sueldo * 1.1 THEN
        bonus := bonus + 0.10;
      END IF;
      
      -- Bonus por nivel educativo
      IF cand_nivel = oferta_nivel THEN
        bonus := bonus + 0.05;
      END IF;
      
      -- Bonus por match de habilidades
      IF array_length(oferta_habs, 1) IS NOT NULL AND array_length(oferta_habs, 1) > 0 THEN
        match_count := 0;
        FOR j IN 1..array_length(oferta_habs, 1) LOOP
          IF oferta_habs[j] = ANY(cand_habs) THEN
            match_count := match_count + 1;
          END IF;
        END LOOP;
        IF match_count::FLOAT / array_length(oferta_habs, 1)::FLOAT > 0.5 THEN
          bonus := bonus + 0.15;
        END IF;
      END IF;
      
      -- Determinar fase con distribución + bonus
      fase_rand := random();
      
      IF fase_rand < (0.40 - bonus) THEN
        fase := 'Postulado';
      ELSIF fase_rand < (0.65 - bonus * 0.5) THEN
        fase := 'Aprobó Entrevista Técnica';
      ELSIF fase_rand < (0.85 - bonus * 0.3) THEN
        fase := 'Oferta Realizada';
      ELSE
        fase := 'Contratado';
      END IF;

      INSERT INTO postulaciones (id, fecha, fase_alcanzada, id_cv, candidato_id, oferta_id)
      VALUES (
        post_id,
        NOW() - INTERVAL '1 day' * (random() * 365)::INT,
        fase,
        'cv_' || REPLACE(cand_id::TEXT, '-', '') || '_' || j || '.pdf',
        cand_id,
        selected_ofertas[j]
      );
    END LOOP;
  END LOOP;
END $$;

-- =============================================================================
-- VERIFICACIÓN DE DATOS GENERADOS
-- =============================================================================
DO $$
DECLARE
  total_usuarios INT;
  total_candidatos INT;
  total_reclutadores INT;
  total_ofertas INT;
  total_postulaciones INT;
  total_cand_hab INT;
  total_oferta_hab INT;
  total_empresas INT;
  total_categorias INT;
  total_habilidades INT;
BEGIN
  SELECT COUNT(*) INTO total_usuarios FROM usuarios;
  SELECT COUNT(*) INTO total_candidatos FROM candidatos;
  SELECT COUNT(*) INTO total_reclutadores FROM reclutadores;
  SELECT COUNT(*) INTO total_ofertas FROM ofertas;
  SELECT COUNT(*) INTO total_postulaciones FROM postulaciones;
  SELECT COUNT(*) INTO total_cand_hab FROM candidato_habilidades;
  SELECT COUNT(*) INTO total_oferta_hab FROM oferta_habilidades;
  SELECT COUNT(*) INTO total_empresas FROM empresas;
  SELECT COUNT(*) INTO total_categorias FROM categorias;
  SELECT COUNT(*) INTO total_habilidades FROM habilidades;

  RAISE NOTICE '══════════════════════════════════════════════════════';
  RAISE NOTICE '  SEED COMPLETADO - RESUMEN DE DATOS GENERADOS';
  RAISE NOTICE '══════════════════════════════════════════════════════';
  RAISE NOTICE '  Usuarios totales:        %', total_usuarios;
  RAISE NOTICE '  Candidatos:              %', total_candidatos;
  RAISE NOTICE '  Reclutadores:            %', total_reclutadores;
  RAISE NOTICE '  Empresas:                %', total_empresas;
  RAISE NOTICE '  Categorías:              %', total_categorias;
  RAISE NOTICE '  Habilidades:             %', total_habilidades;
  RAISE NOTICE '  Ofertas laborales:       %', total_ofertas;
  RAISE NOTICE '  Postulaciones:           %', total_postulaciones;
  RAISE NOTICE '  Candidato-Habilidades:   %', total_cand_hab;
  RAISE NOTICE '  Oferta-Habilidades:      %', total_oferta_hab;
  RAISE NOTICE '══════════════════════════════════════════════════════';
END $$;

-- Distribución de fases alcanzadas
SELECT 
  fase_alcanzada, 
  COUNT(*) as total,
  ROUND(COUNT(*)::NUMERIC / (SELECT COUNT(*) FROM postulaciones) * 100, 2) as porcentaje
FROM postulaciones 
GROUP BY fase_alcanzada 
ORDER BY total DESC;
