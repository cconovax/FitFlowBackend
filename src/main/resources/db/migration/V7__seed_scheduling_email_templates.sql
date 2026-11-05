-- Plantillas de email para reservas de clases: cambio de entrenador y oferta de cupo desde la lista de espera.
INSERT INTO public.email_templates (code, created_at, enabled, subject, updated_at, html)
SELECT 'class_trainer_changed', now(), true, 'Cambió el entrenador de tu clase — FitFlow', now(), $tpl$<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Cambio de entrenador</title>
</head>
<body style="margin:0;padding:0;background-color:#070b18;">
  <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="background-color:#070b18;">
    <tr>
      <td align="center" style="padding:48px 16px;">
        <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="560" style="max-width:560px;width:100%;">
          <tr><td style="height:3px;background:linear-gradient(90deg,#06b6d4 0%,#818cf8 50%,#4f46e5 100%);border-radius:12px 12px 0 0;"></td></tr>
          <tr>
            <td style="background-color:#0d1526;padding:40px 48px;">
              <p style="font-family:'Poppins',Arial,sans-serif;font-size:20px;font-weight:700;color:#f1f5f9;margin:0 0 20px;text-align:center;">FitFlow</p>
              <h1 style="font-family:'Poppins',Arial,sans-serif;font-size:22px;font-weight:700;color:#f1f5f9;margin:0 0 10px;">Cambió el entrenador de tu clase</h1>
              <p style="font-family:'Poppins',Arial,sans-serif;font-size:15px;color:#94a3b8;margin:0 0 20px;line-height:1.65;">
                Hola <strong style="color:#e2e8f0;">${userName?html}</strong>, la clase <strong style="color:#e2e8f0;">${className?html}</strong>
                ahora estará a cargo de <strong style="color:#e2e8f0;">${newTrainerName?html}</strong> (antes: ${previousTrainerName?html}).
              </p>
              <p style="font-family:'Poppins',Arial,sans-serif;font-size:12px;color:#475569;margin:0 0 10px;text-transform:uppercase;letter-spacing:0.8px;font-weight:600;">Sesiones afectadas</p>
              <#list sessions as session>
              <p style="font-family:'Poppins',Arial,sans-serif;font-size:13px;color:#cbd5e1;margin:0;padding:4px 0;">&#x1F4C5;&nbsp;${session}</p>
              </#list>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</body>
</html>$tpl$
WHERE NOT EXISTS (SELECT 1 FROM public.email_templates WHERE code = 'class_trainer_changed');

INSERT INTO public.email_templates (code, created_at, enabled, subject, updated_at, html)
SELECT 'class_waitlist_offer', now(), true, 'Se liberó un cupo en tu clase — FitFlow', now(), $tpl$<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Cupo disponible</title>
</head>
<body style="margin:0;padding:0;background-color:#070b18;">
  <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="background-color:#070b18;">
    <tr>
      <td align="center" style="padding:48px 16px;">
        <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="560" style="max-width:560px;width:100%;">
          <tr><td style="height:3px;background:linear-gradient(90deg,#06b6d4 0%,#818cf8 50%,#4f46e5 100%);border-radius:12px 12px 0 0;"></td></tr>
          <tr>
            <td style="background-color:#0d1526;padding:40px 48px;">
              <p style="font-family:'Poppins',Arial,sans-serif;font-size:20px;font-weight:700;color:#f1f5f9;margin:0 0 20px;text-align:center;">FitFlow</p>
              <h1 style="font-family:'Poppins',Arial,sans-serif;font-size:22px;font-weight:700;color:#f1f5f9;margin:0 0 10px;">¡Se liberó un cupo para ti!</h1>
              <p style="font-family:'Poppins',Arial,sans-serif;font-size:15px;color:#94a3b8;margin:0 0 20px;line-height:1.65;">
                Hola <strong style="color:#e2e8f0;">${userName?html}</strong>, hay un cupo disponible en la clase
                <strong style="color:#e2e8f0;">${className?html}</strong> del <strong style="color:#e2e8f0;">${startsAt}</strong>.
              </p>
              <p style="font-family:'Poppins',Arial,sans-serif;font-size:14px;color:#cbd5e1;margin:0 0 20px;line-height:1.65;">
                Tienes <strong>${expirationMinutes} minutos</strong> (hasta las ${expiresAt}) para aceptarlo. Ingresa a FitFlow, abre tus reservas de clases
                y elige <em>Aceptar</em> o <em>Rechazar</em> la oferta. Si no respondes a tiempo, el cupo pasará a la siguiente persona.
              </p>
              <p style="margin:0;text-align:center;">
                <a href="${appUrl}" style="font-family:'Poppins',Arial,sans-serif;display:inline-block;background:#4f46e5;color:#ffffff;text-decoration:none;font-size:14px;font-weight:600;padding:12px 28px;border-radius:10px;">Ir a FitFlow</a>
              </p>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</body>
</html>$tpl$
WHERE NOT EXISTS (SELECT 1 FROM public.email_templates WHERE code = 'class_waitlist_offer');
