<!--
Definition of Done completa: https://github.com/cconovax/FitFlowProject/blob/main/docs/DEFINITION_OF_DONE.md
ADRs vigentes: https://github.com/cconovax/FitFlowProject/tree/main/docs/decisions
-->

## Qué hace este PR

## Módulo(s) afectado(s)

<!-- shared / gym / membership / subscription / inventory / notifications / (nuevo) -->

## Checklist — Definition of Done (backend)

- [ ] Sigue la estructura `domain/application/infrastructure/presentation` del módulo.
- [ ] Respeta [ADR 0001](https://github.com/cconovax/FitFlowProject/blob/main/docs/decisions/0001-inter-module-communication.md) (comunicación entre módulos) y [ADR 0003](https://github.com/cconovax/FitFlowProject/blob/main/docs/decisions/0003-event-vs-direct-call.md) (evento vs. llamada directa).
- [ ] Todo endpoint nuevo protegido con `@RequirePermission` (o se justifica explícitamente por qué es público, ver abajo).
- [ ] Si toca datos gym-scoped: incluye un test cross-tenant (gym A no accede a datos de gym B).
- [ ] Todo cambio de esquema es una migración Flyway **nueva** (`V{n}__...sql`), nunca `ddl-auto` ([ADR 0004](https://github.com/cconovax/FitFlowProject/blob/main/docs/decisions/0004-database-migration-strategy.md)).
- [ ] `./gradlew test` pasa en verde.
- [ ] Sin `catch (Exception e)` genérico nuevo.
- [ ] Ningún ADR aceptado contradicho sin marcarlo explícitamente como excepción (ver justificación abajo si aplica).

## Tests agregados/modificados

## Excepciones / justificaciones (si alguna casilla de arriba no aplica, explica por qué)

## Riesgos conocidos
