-- Tabla de plantillas de email
-- Las plantillas usan sintaxis Freemarker: ${variable}
CREATE TABLE IF NOT EXISTS email_templates (
    id         BIGSERIAL    PRIMARY KEY,
    code       VARCHAR(100) NOT NULL UNIQUE,
    subject    VARCHAR(255) NOT NULL,
    html       TEXT         NOT NULL,
    enabled    BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- Ejemplo: plantilla de bienvenida
INSERT INTO email_templates (code, subject, html) VALUES (
    'welcome',
    'Bienvenido a FitFlow, ${userName}',
    '<!DOCTYPE html>
<html>
<body>
  <h1>¡Hola, ${userName}!</h1>
  <p>Tu cuenta en FitFlow ha sido creada exitosamente.</p>
  <p>Ya puedes iniciar sesión y comenzar a usar la plataforma.</p>
</body>
</html>'
) ON CONFLICT (code) DO NOTHING;

-- Ejemplo: plantilla de membresía asignada
INSERT INTO email_templates (code, subject, html) VALUES (
    'membership_assigned',
    'Tu membresía ${membershipName} está activa',
    '<!DOCTYPE html>
<html>
<body>
  <h1>¡Hola, ${userName}!</h1>
  <p>Se te ha asignado la membresía <strong>${membershipName}</strong>.</p>
  <p>Válida desde ${startDate} hasta ${endDate}.</p>
</body>
</html>'
) ON CONFLICT (code) DO NOTHING;

-- Plantilla de restablecimiento de contraseña
INSERT INTO email_templates (code, subject, html) VALUES (
    'password_reset',
    'Restablecer contraseña — FitFlow',
    '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Restablecer contraseña</title>
  <!--[if mso]><style>body,table,td{font-family:Arial,sans-serif !important;}</style><![endif]-->
</head>
<body style="margin:0;padding:0;background-color:#070b18;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;">

  <!-- Preheader oculto -->
  <div style="display:none;font-size:1px;color:#070b18;line-height:1px;max-height:0;max-width:0;opacity:0;overflow:hidden;">
    Restablece tu contraseña de FitFlow. Este enlace expira en ${expirationMinutes} minutos.
  </div>

  <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="background-color:#070b18;">
    <tr>
      <td align="center" style="padding:48px 16px;">

        <!-- Contenedor principal -->
        <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="560" style="max-width:560px;width:100%;">

          <!-- Barra superior degradada -->
          <tr>
            <td style="height:3px;background:linear-gradient(90deg,#06b6d4 0%,#818cf8 50%,#4f46e5 100%);border-radius:12px 12px 0 0;"></td>
          </tr>

          <!-- Cuerpo de la tarjeta -->
          <tr>
            <td style="background-color:#0d1526;border-left:1px solid rgba(99,179,237,0.08);border-right:1px solid rgba(99,179,237,0.08);padding:40px 48px 32px;">

              <!-- Logo + nombre -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td align="center" style="padding-bottom:28px;">
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td style="background:linear-gradient(135deg,#06b6d4,#4f46e5);border-radius:14px;width:52px;height:52px;text-align:center;vertical-align:middle;">
                          <span style="font-size:22px;line-height:52px;display:block;">&#x1F3CB;</span>
                        </td>
                      </tr>
                      <tr>
                        <td align="center" style="padding-top:12px;">
                          <span style="font-family:''Poppins'',Arial,sans-serif;font-size:20px;font-weight:700;color:#f1f5f9;letter-spacing:-0.3px;">FitFlow</span>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>

              <!-- Separador -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr><td style="height:1px;background:rgba(99,179,237,0.08);"></td></tr>
              </table>

              <!-- Ícono de candado + título -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td align="center" style="padding:32px 0 20px;">
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td style="width:68px;height:68px;background:rgba(6,182,212,0.07);border:1px solid rgba(6,182,212,0.18);border-radius:50%;text-align:center;vertical-align:middle;">
                          <span style="font-size:26px;line-height:68px;display:block;">&#x1F512;</span>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td align="center">
                    <h1 style="font-family:''Poppins'',Arial,sans-serif;font-size:22px;font-weight:700;color:#f1f5f9;margin:0 0 10px;letter-spacing:-0.4px;">Restablecer contraseña</h1>
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:15px;color:#94a3b8;margin:0;line-height:1.65;">
                      Hola <strong style="color:#e2e8f0;">${userName}</strong>, recibimos una solicitud para restablecer la contraseña de tu cuenta.
                    </p>
                  </td>
                </tr>
              </table>

              <!-- Caja informativa -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="margin-top:24px;">
                <tr>
                  <td style="background:rgba(6,182,212,0.05);border:1px solid rgba(6,182,212,0.14);border-radius:12px;padding:16px 20px;">
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:13px;color:#94a3b8;margin:0;line-height:1.75;">
                      &#x23F1;&nbsp;<strong style="color:#cbd5e1;">Este enlace expira en ${expirationMinutes} minutos.</strong><br/>
                      &#x1F6E1;&nbsp;Si no solicitaste este cambio, ignora este correo. Tu contraseña actual permanece sin cambios.
                    </p>
                  </td>
                </tr>
              </table>

              <!-- Botón CTA -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="margin-top:28px;">
                <tr>
                  <td align="center">
                    <!--[if mso]>
                    <v:roundrect xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w="urn:schemas-microsoft-com:office:word"
                      href="${resetUrl}" style="height:48px;v-text-anchor:middle;width:240px;" arcsize="25%"
                      fillcolor="#06b6d4">
                      <w:anchorlock/>
                      <center style="color:#ffffff;font-family:Arial,sans-serif;font-size:15px;font-weight:bold;">
                        Restablecer mi contraseña
                      </center>
                    </v:roundrect>
                    <![endif]-->
                    <!--[if !mso]><!-->
                    <a href="${resetUrl}" target="_blank"
                      style="display:inline-block;padding:14px 36px;background:linear-gradient(135deg,#06b6d4 0%,#4f46e5 100%);color:#ffffff;text-decoration:none;font-family:''Poppins'',Arial,sans-serif;font-size:15px;font-weight:600;border-radius:12px;letter-spacing:0.2px;mso-hide:all;">
                      Restablecer mi contraseña
                    </a>
                    <!--<![endif]-->
                  </td>
                </tr>
              </table>

              <!-- URL de respaldo -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="margin-top:28px;">
                <tr>
                  <td style="background:#070f1f;border-radius:10px;padding:14px 18px;">
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:10px;color:#475569;margin:0 0 5px;text-transform:uppercase;letter-spacing:0.9px;font-weight:600;">
                      O pega este enlace en tu navegador
                    </p>
                    <p style="font-family:monospace;font-size:11px;color:#06b6d4;margin:0;word-break:break-all;line-height:1.5;">
                      ${resetUrl}
                    </p>
                  </td>
                </tr>
              </table>

            </td>
          </tr>

          <!-- Footer -->
          <tr>
            <td style="background-color:#090e1e;border-left:1px solid rgba(99,179,237,0.08);border-right:1px solid rgba(99,179,237,0.08);padding:20px 48px 24px;">
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr><td style="height:1px;background:rgba(99,179,237,0.06);margin-bottom:20px;"></td></tr>
                <tr>
                  <td align="center" style="padding-top:16px;">
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:11px;color:#334155;margin:0;line-height:1.8;">
                      Este correo fue enviado automáticamente por <strong style="color:#475569;">FitFlow</strong>.<br/>
                      Por favor no respondas a este mensaje.
                    </p>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <!-- Barra inferior degradada -->
          <tr>
            <td style="height:3px;background:linear-gradient(90deg,transparent,#06b6d4,#4f46e5,transparent);border-radius:0 0 12px 12px;"></td>
          </tr>

        </table>
      </td>
    </tr>
  </table>

</body>
</html>'
) ON CONFLICT (code) DO NOTHING;
