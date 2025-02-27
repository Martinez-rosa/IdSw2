# Tutorial: Transferencia selectiva de cambios con Git (Cherry Pick)

Este documento describe el proceso detallado que seguimos para transferir cambios específicos entre ramas de un repositorio Git, manteniendo la integridad del proyecto y evitando mezclar características no deseadas.

## Contexto

Teníamos dos ramas en nuestro repositorio:
- **main**: La rama principal del proyecto
- **masIAs-y-CC**: Una rama de desarrollo con múltiples cambios

Nuestro objetivo era transferir a la rama `main` **únicamente** los cambios relacionados con las carpetas:
- `temario/01-diseño/`
- `temario/sesionesDiseño/`

Y explícitamente **NO** queríamos transferir:
- Los cambios en la carpeta `temario/03-diseñoOO/`
- El archivo `CLAUDE.md`
- Cualquier otro cambio no relacionado con las carpetas especificadas

## Análisis inicial

### 1. Identificación de los cambios disponibles

Lo primero fue examinar qué cambios existían en la rama de desarrollo:

```bash
git diff main..masIAs-y-CC -- temario/01-diseño/
git diff main..masIAs-y-CC -- temario/sesionesDiseño/
git diff main..masIAs-y-CC -- temario/00-introduccion/
```

Descubrimos que:
- En `01-diseño/`: Se habían renombrado archivos con prefijos numéricos y actualizado el README
- En `sesionesDiseño/`: Se había renombrado `infoEquipos.md` a `README.md` y añadido un nuevo archivo `reflexiones.md`
- En `00-introduccion/`: No había cambios

### 2. Identificación de los commits relevantes

Examinamos el historial de commits para identificar aquellos relacionados con nuestras carpetas de interés:

```bash
git log masIAs-y-CC --pretty=format:"%h %s" -n 10
git show 283d3b5 --name-only  # Cambios en README de 01-diseño
git show 3742175 --name-only  # Reorganización de archivos en 01-diseño
git show d921b33 --name-only  # Cambios en sesionesDiseño
```

## Estrategia empleada

Decidimos usar una combinación de `cherry-pick` con la opción `-n` (no commit) para:
1. Seleccionar los commits exactos que contenían los cambios deseados
2. Aplicar esos cambios sin hacer commit automáticamente
3. Revisar y ajustar los cambios antes de confirmarlos
4. Mantener una rama temporal como referencia del proceso

## Proceso paso a paso

### 1. Creación de una rama temporal

```bash
git checkout main
git checkout -b temp-main-changes
```

Esto nos dio un espacio de trabajo limpio basado en `main` para aplicar nuestros cambios selectivos.

### 2. Aplicación de cambios de reorganización en 01-diseño

```bash
git cherry-pick -n 3742175  # Commit que reorganiza 01-diseño
```

Después de aplicar este commit, revisamos los cambios en preparación:

```bash
git status
```

Observamos que se había incluido `CLAUDE.md` y cambios en `README.md` de la raíz que no queríamos, así que los excluimos:

```bash
git reset HEAD CLAUDE.md README.md
git checkout -- README.md
```

### 3. Aplicación de cambios en README de 01-diseño

```bash
git cherry-pick -n 283d3b5  # Commit que actualiza README de 01-diseño
```

### 4. Aplicación de cambios en sesionesDiseño

```bash
git cherry-pick -n d921b33  # Commit que mejora documentación en sesionesDiseño
```

### 5. Revisión final antes de confirmar

```bash
git status
```

Comprobamos que teníamos exactamente los cambios deseados:
- Los archivos renombrados en `01-diseño/`
- El README actualizado en `01-diseño/`
- `infoEquipos.md` renombrado a `README.md` en `sesionesDiseño/`
- El nuevo archivo `reflexiones.md` en `sesionesDiseño/`

Y confirmamos que NO teníamos:
- `CLAUDE.md`
- Cambios en la carpeta `03-diseñoOO/`

### 6. Primer commit: reorganización y documentación

```bash
git commit -m "Actualiza estructura de las carpetas 01-diseño y sesionesDiseño

- Reorganiza archivos en la carpeta 01-diseño con prefijos numéricos
- Actualiza enlaces en el README de la carpeta 01-diseño
- Renombra infoEquipos.md a README.md en la carpeta sesionesDiseño
- Añade documento de reflexiones con análisis del enfoque pedagógico"
```

### 7. Actualización del README principal

Para mantener la coherencia, actualizamos manualmente el README principal para que reflejara la nueva estructura:

```bash
git edit README.md  # Actualizamos los enlaces a los archivos renombrados
git add README.md
git commit -m "Actualiza enlaces en README principal a la estructura reorganizada"
```

### 8. Integración en la rama principal

Una vez satisfechos con los cambios en nuestra rama temporal:

```bash
git checkout main
git merge temp-main-changes
git push
```

### 9. Preservación del proceso

En lugar de eliminar la rama temporal, la mantuvimos y publicamos como referencia del proceso:

```bash
git push -u origin temp-main-changes
```

## Resultado

Conseguimos exitosamente:
1. Transferir **solo** los cambios específicos de las carpetas indicadas
2. Mantener la rama `main` limpia de cambios no deseados
3. Preservar la historia del proceso para referencia futura
4. Documentar el procedimiento detallado para fines educativos

## Lecciones aprendidas

- **Cherry-pick con `-n`** es una herramienta poderosa para aplicar cambios selectivamente
- La **verificación en cada paso** es crucial para asegurar que solo los cambios deseados se transfieren
- Mantener una **rama temporal** proporciona mayor seguridad y capacidad de revisión
- Documentar el **proceso completo** es valioso para el aprendizaje y referencia futura

## Comandos Git clave utilizados

- `git diff main..masIAs-y-CC -- path/to/dir`: Ver diferencias específicas entre ramas
- `git show <commit> --name-only`: Ver archivos afectados por un commit
- `git cherry-pick -n <commit>`: Aplicar cambios de un commit sin confirmar automáticamente
- `git reset HEAD <file>`: Quitar archivos del área de preparación
- `git checkout -- <file>`: Descartar cambios en archivos específicos
- `git push -u origin <branch>`: Publicar una rama nueva al repositorio remoto

---

*Este documento fue creado con propósitos educativos para ilustrar técnicas avanzadas de gestión de cambios en Git.*