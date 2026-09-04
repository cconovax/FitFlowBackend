INSERT INTO email_templates (code, subject, html, created_at,enabled, updated_at) VALUES (
    'welcome',
    'Bienvenido a FitFlow, ${userName}',
    '<!DOCTYPE html
  PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Bienvenido a FitFlow</title>
  <!--[if mso]><style>body,table,td{font-family:Arial,sans-serif !important;}</style><![endif]-->
  <style>
    body {
      margin: 0;
      padding: 0;
      background-color: #070b18;
      -webkit-text-size-adjust: 100%;
      -ms-text-size-adjust: 100%;
    }

    .preheader {
      display: none;
      font-size: 1px;
      color: #070b18;
      line-height: 1px;
      max-height: 0;
      max-width: 0;
      opacity: 0;
      overflow: hidden;
    }

    .outer-table { background-color: #070b18; }
    .outer-td    { padding: 48px 16px; }

    .main-container {
      max-width: 560px;
      width: 100%;
    }

    /* ── Barras decorativas ── */
    .top-bar {
      height: 3px;
      background: linear-gradient(90deg, #06b6d4 0%, #818cf8 50%, #4f46e5 100%);
      border-radius: 12px 12px 0 0;
    }

    .bottom-bar {
      height: 3px;
      background: linear-gradient(90deg, transparent, #06b6d4, #4f46e5, transparent);
      border-radius: 0 0 12px 12px;
    }

    /* ── Tarjeta principal ── */
    .card-body {
      background-color: #0d1526;
      border-left: 1px solid rgba(99, 179, 237, 0.08);
      border-right: 1px solid rgba(99, 179, 237, 0.08);
      padding: 40px 48px 32px;
    }

    /* ── Logo ── */
    .logo-td      { padding-bottom: 28px; }
    .logo-name-td { padding-top: 12px; }

    .logo-img {
      display: block;
      border: 0;
      outline: none;
      text-decoration: none;
    }

    .logo-name {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 20px;
      font-weight: 700;
      color: #f1f5f9;
      letter-spacing: -0.3px;
    }

    /* ── Separador ── */
    .separator { height: 1px; background: rgba(99, 179, 237, 0.08); }

    /* ── Ícono ── */
    .icon-td { padding: 32px 0 20px; }

    .main-icon-td {
      width: 68px;
      height: 68px;
      background: rgba(6, 182, 212, 0.07);
      border: 1px solid rgba(6, 182, 212, 0.18);
      border-radius: 50%;
      text-align: center;
      vertical-align: middle;
    }

    .main-icon {
      font-size: 26px;
      line-height: 68px;
      display: block;
    }

    /* ── Título y subtítulo ── */
    .card-title {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 22px;
      font-weight: 700;
      color: #f1f5f9;
      margin: 0 0 10px;
      letter-spacing: -0.4px;
    }

    .card-subtitle {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 15px;
      color: #94a3b8;
      margin: 0;
      line-height: 1.65;
    }

    .card-subtitle strong { color: #e2e8f0; }

    /* ── Lista de features ── */
    .features-table { margin-top: 24px; }

    .features-td {
      background: rgba(6, 182, 212, 0.05);
      border: 1px solid rgba(6, 182, 212, 0.14);
      border-radius: 12px;
      padding: 20px 24px;
    }

    .features-title {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 12px;
      font-weight: 600;
      color: #06b6d4;
      margin: 0 0 14px;
      text-transform: uppercase;
      letter-spacing: 0.8px;
    }

    .feature-row-td { padding: 5px 0; }

    .feature-text {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 13px;
      color: #94a3b8;
      margin: 0;
      line-height: 1.5;
    }

    .feature-text strong { color: #cbd5e1; }

    /* ── Botón CTA ── */
    .cta-table { margin-top: 28px; }

    .cta-btn {
      display: inline-block;
      padding: 14px 36px;
      background: linear-gradient(135deg, #06b6d4 0%, #4f46e5 100%);
      color: #ffffff;
      text-decoration: none;
      font-family: "Poppins", Arial, sans-serif;
      font-size: 15px;
      font-weight: 600;
      border-radius: 12px;
      letter-spacing: 0.2px;
      mso-hide: all;
    }

    /* ── Footer ── */
    .footer-td {
      background-color: #090e1e;
      border-left: 1px solid rgba(99, 179, 237, 0.08);
      border-right: 1px solid rgba(99, 179, 237, 0.08);
      padding: 20px 48px 24px;
    }

    .footer-separator {
      height: 1px;
      background: rgba(99, 179, 237, 0.06);
      margin-bottom: 20px;
    }

    .footer-content-td { padding-top: 16px; }

    .footer-text {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 11px;
      color: #334155;
      margin: 0;
      line-height: 1.8;
    }

    .footer-text strong { color: #475569; }
  </style>
</head>

<body>

  <!-- Preheader oculto -->
  <div class="preheader">
    ¡Bienvenido a FitFlow, ${userName}! Tu cuenta está lista para comenzar.
  </div>

  <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" class="outer-table">
    <tr>
      <td align="center" class="outer-td">

        <!-- Contenedor principal -->
        <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="560" class="main-container">

          <!-- Barra superior degradada -->
          <tr>
            <td class="top-bar"></td>
          </tr>

          <!-- Cuerpo de la tarjeta -->
          <tr>
            <td class="card-body">

              <!-- Logo + nombre -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td align="center" class="logo-td">
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td>
                          <img src="https://res.cloudinary.com/dbt6q1rlx/image/upload/v1786108905/fitflow/Logos/logo_completo_bkkybs.png"
                            alt="FitFlow Logo" width="64" height="64" class="logo-img" />
                        </td>
                      </tr>
                      <tr>
                        <td align="center" class="logo-name-td">
                          <span class="logo-name">FitFlow</span>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>

              <!-- Separador -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td class="separator"></td>
                </tr>
              </table>

              <!-- Ícono + título -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td align="center" class="icon-td">
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td class="main-icon-td">
                          <span class="main-icon">&#x1F389;</span>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td align="center">
                    <h1 class="card-title">¡Bienvenido a FitFlow!</h1>
                    <p class="card-subtitle">
                      Hola <strong>${userName}</strong>, tu cuenta ha sido creada exitosamente.
                      Estamos muy contentos de tenerte con nosotros.
                    </p>
                  </td>
                </tr>
              </table>

              <!-- Features -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" class="features-table">
                <tr>
                  <td class="features-td">
                    <p class="features-title">¿Qué puedes hacer en FitFlow?</p>
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                      <tr>
                        <td class="feature-row-td">
                          <p class="feature-text">&#x1F4AA;&nbsp;<strong>Gestiona tus membresías</strong> y accede a todos los beneficios de tu gimnasio.</p>
                        </td>
                      </tr>
                      <tr>
                        <td class="feature-row-td">
                          <p class="feature-text">&#x1F4C5;&nbsp;<strong>Consulta tu historial</strong> de asistencia y seguimiento de progreso.</p>
                        </td>
                      </tr>
                      <tr>
                        <td class="feature-row-td">
                          <p class="feature-text">&#x1F6D2;&nbsp;<strong>Compra productos</strong> y servicios directamente desde la plataforma.</p>
                        </td>
                      </tr>
                      <tr>
                        <td class="feature-row-td">
                          <p class="feature-text">&#x1F511;&nbsp;<strong>Accede con tu QR</strong> a las instalaciones de forma rápida y segura.</p>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>

              <!-- Botón CTA -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" class="cta-table">
                <tr>
                  <td align="center">
                    <!--[if mso]>
                    <v:roundrect xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w="urn:schemas-microsoft-com:office:word"
                      href="${loginUrl}" style="height:48px;v-text-anchor:middle;width:200px;" arcsize="25%"
                      fillcolor="#06b6d4">
                      <w:anchorlock/>
                      <center style="color:#ffffff;font-family:Arial,sans-serif;font-size:15px;font-weight:bold;">
                        Ir a mi cuenta
                      </center>
                    </v:roundrect>
                    <![endif]-->
                    <!--[if !mso]><!-->
                    <a href="${loginUrl}" target="_blank" class="cta-btn">
                      Ir a mi cuenta
                    </a>
                    <!--<![endif]-->
                  </td>
                </tr>
              </table>

            </td>
          </tr>

          <!-- Footer -->
          <tr>
            <td class="footer-td">
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td class="footer-separator"></td>
                </tr>
                <tr>
                  <td align="center" class="footer-content-td">
                    <p class="footer-text">
                      Este correo fue enviado automáticamente por <strong>FitFlow</strong>.<br />
                      Por favor no respondas a este mensaje.
                    </p>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <!-- Barra inferior degradada -->
          <tr>
            <td class="bottom-bar"></td>
          </tr>

        </table>
      </td>
    </tr>
  </table>

</body>

</html>', now(), true, now()
) ON CONFLICT (code) DO UPDATE SET subject = EXCLUDED.subject, html = EXCLUDED.html, updated_at = NOW();

-- Ejemplo: plantilla de membresía asignada
INSERT INTO email_templates (code, subject, html, created_at,enabled, updated_at) VALUES (
    'membership_assigned',
    'Tu membresía ${membershipName} está activa',
    '<!DOCTYPE html
  PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Membresía asignada</title>
  <!--[if mso]><style>body,table,td{font-family:Arial,sans-serif !important;}</style><![endif]-->
  <style>
    body {
      margin: 0;
      padding: 0;
      background-color: #070b18;
      -webkit-text-size-adjust: 100%;
      -ms-text-size-adjust: 100%;
    }

    .preheader {
      display: none;
      font-size: 1px;
      color: #070b18;
      line-height: 1px;
      max-height: 0;
      max-width: 0;
      opacity: 0;
      overflow: hidden;
    }

    .outer-table { background-color: #070b18; }
    .outer-td    { padding: 48px 16px; }

    .main-container {
      max-width: 560px;
      width: 100%;
    }

    /* ── Barras decorativas ── */
    .top-bar {
      height: 3px;
      background: linear-gradient(90deg, #06b6d4 0%, #818cf8 50%, #4f46e5 100%);
      border-radius: 12px 12px 0 0;
    }

    .bottom-bar {
      height: 3px;
      background: linear-gradient(90deg, transparent, #06b6d4, #4f46e5, transparent);
      border-radius: 0 0 12px 12px;
    }

    /* ── Tarjeta principal ── */
    .card-body {
      background-color: #0d1526;
      border-left: 1px solid rgba(99, 179, 237, 0.08);
      border-right: 1px solid rgba(99, 179, 237, 0.08);
      padding: 40px 48px 32px;
    }

    /* ── Logo ── */
    .logo-td      { padding-bottom: 28px; }
    .logo-name-td { padding-top: 12px; }

    .logo-img {
      display: block;
      border: 0;
      outline: none;
      text-decoration: none;
    }

    .logo-name {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 20px;
      font-weight: 700;
      color: #f1f5f9;
      letter-spacing: -0.3px;
    }

    /* ── Separador ── */
    .separator { height: 1px; background: rgba(99, 179, 237, 0.08); }

    /* ── Ícono ── */
    .icon-td { padding: 32px 0 20px; }

    .main-icon-td {
      width: 68px;
      height: 68px;
      background: rgba(6, 182, 212, 0.07);
      border: 1px solid rgba(6, 182, 212, 0.18);
      border-radius: 50%;
      text-align: center;
      vertical-align: middle;
    }

    .main-icon {
      font-size: 26px;
      line-height: 68px;
      display: block;
    }

    /* ── Título y subtítulo ── */
    .card-title {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 22px;
      font-weight: 700;
      color: #f1f5f9;
      margin: 0 0 10px;
      letter-spacing: -0.4px;
    }

    .card-subtitle {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 15px;
      color: #94a3b8;
      margin: 0;
      line-height: 1.65;
    }

    .card-subtitle strong { color: #e2e8f0; }

    /* ── Badge de membresía ── */
    .membership-badge-table { margin-top: 24px; }

    .membership-badge-td {
      background: linear-gradient(135deg, rgba(6, 182, 212, 0.08) 0%, rgba(79, 70, 229, 0.08) 100%);
      border: 1px solid rgba(129, 140, 248, 0.2);
      border-radius: 12px;
      padding: 18px 24px;
      text-align: center;
    }

    .membership-name-label {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 11px;
      font-weight: 600;
      color: #818cf8;
      margin: 0 0 6px;
      text-transform: uppercase;
      letter-spacing: 0.9px;
    }

    .membership-name-value {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 20px;
      font-weight: 700;
      color: #f1f5f9;
      margin: 0;
      letter-spacing: -0.3px;
    }

    /* ── Tabla de detalles ── */
    .details-table { margin-top: 20px; }

    .details-td {
      background: rgba(6, 182, 212, 0.05);
      border: 1px solid rgba(6, 182, 212, 0.14);
      border-radius: 12px;
      padding: 4px 0;
    }

    .detail-row-td {
      padding: 12px 20px;
      border-bottom: 1px solid rgba(99, 179, 237, 0.06);
    }

    /* último fila sin borde inferior */
    .detail-row-last { border-bottom: none; }

    .detail-label {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 11px;
      font-weight: 600;
      color: #475569;
      text-transform: uppercase;
      letter-spacing: 0.7px;
      white-space: nowrap;
      padding-right: 16px;
      width: 1%;
    }

    .detail-value {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 13px;
      color: #cbd5e1;
      text-align: right;
    }

    /* ── Caja informativa ── */
    .info-box-table { margin-top: 20px; }

    .info-box-td {
      background: rgba(6, 182, 212, 0.05);
      border: 1px solid rgba(6, 182, 212, 0.14);
      border-radius: 12px;
      padding: 14px 20px;
    }

    .info-text {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 13px;
      color: #94a3b8;
      margin: 0;
      line-height: 1.75;
    }

    .info-text strong { color: #cbd5e1; }

    /* ── Botón CTA ── */
    .cta-table { margin-top: 28px; }

    .cta-btn {
      display: inline-block;
      padding: 14px 36px;
      background: linear-gradient(135deg, #06b6d4 0%, #4f46e5 100%);
      color: #ffffff;
      text-decoration: none;
      font-family: "Poppins", Arial, sans-serif;
      font-size: 15px;
      font-weight: 600;
      border-radius: 12px;
      letter-spacing: 0.2px;
      mso-hide: all;
    }

    /* ── Footer ── */
    .footer-td {
      background-color: #090e1e;
      border-left: 1px solid rgba(99, 179, 237, 0.08);
      border-right: 1px solid rgba(99, 179, 237, 0.08);
      padding: 20px 48px 24px;
    }

    .footer-separator {
      height: 1px;
      background: rgba(99, 179, 237, 0.06);
      margin-bottom: 20px;
    }

    .footer-content-td { padding-top: 16px; }

    .footer-text {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 11px;
      color: #334155;
      margin: 0;
      line-height: 1.8;
    }

    .footer-text strong { color: #475569; }
  </style>
</head>

<body>

  <!-- Preheader oculto -->
  <div class="preheader">
    ¡${userName}, se te ha asignado la membresía ${membershipName} en ${gymName}!
  </div>

  <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" class="outer-table">
    <tr>
      <td align="center" class="outer-td">

        <!-- Contenedor principal -->
        <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="560" class="main-container">

          <!-- Barra superior degradada -->
          <tr>
            <td class="top-bar"></td>
          </tr>

          <!-- Cuerpo de la tarjeta -->
          <tr>
            <td class="card-body">

              <!-- Logo + nombre -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td align="center" class="logo-td">
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td>
                          <img src="https://res.cloudinary.com/dbt6q1rlx/image/upload/v1786108905/fitflow/Logos/logo_completo_bkkybs.png"
                            alt="FitFlow Logo" width="64" height="64" class="logo-img" />
                        </td>
                      </tr>
                      <tr>
                        <td align="center" class="logo-name-td">
                          <span class="logo-name">FitFlow</span>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>

              <!-- Separador -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td class="separator"></td>
                </tr>
              </table>

              <!-- Ícono + título -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td align="center" class="icon-td">
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td class="main-icon-td">
                          <span class="main-icon">&#x1F3CB;</span>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td align="center">
                    <h1 class="card-title">¡Membresía asignada!</h1>
                    <p class="card-subtitle">
                      Hola <strong>${userName}</strong>, se te ha asignado una nueva membresía
                      en <strong>${gymName}</strong>. Ya puedes disfrutar de todos sus beneficios.
                    </p>
                  </td>
                </tr>
              </table>

              <!-- Badge nombre de membresía -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" class="membership-badge-table">
                <tr>
                  <td class="membership-badge-td">
                    <p class="membership-name-label">Plan activo</p>
                    <p class="membership-name-value">${membershipName}</p>
                  </td>
                </tr>
              </table>

              <!-- Detalles de la membresía -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" class="details-table">
                <tr>
                  <td class="details-td">

                    <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                      <tr>
                        <td class="detail-row-td detail-label">Gimnasio</td>
                        <td class="detail-row-td detail-value">${gymName}</td>
                      </tr>
                    </table>

                    <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                      <tr>
                        <td class="detail-row-td detail-label">Fecha de inicio</td>
                        <td class="detail-row-td detail-value">${startDate}</td>
                      </tr>
                    </table>

                    <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                      <tr>
                        <td class="detail-row-td detail-label">Fecha de vencimiento</td>
                        <td class="detail-row-td detail-value">${endDate}</td>
                      </tr>
                    </table>

                    <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                      <tr>
                        <td class="detail-row-td detail-row-last detail-label">Estado</td>
                        <td class="detail-row-td detail-row-last detail-value" style="color:#4ade80;">&#x2714;&nbsp;Activa</td>
                      </tr>
                    </table>

                  </td>
                </tr>
              </table>

              <!-- Caja informativa -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" class="info-box-table">
                <tr>
                  <td class="info-box-td">
                    <p class="info-text">
                      &#x1F511;&nbsp;<strong>Accede con tu código QR</strong> directamente desde la app.<br />
                      &#x1F4AC;&nbsp;Si tienes dudas sobre tu membresía, comunícate con tu gimnasio.
                    </p>
                  </td>
                </tr>
              </table>

              <!-- Botón CTA -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" class="cta-table">
                <tr>
                  <td align="center">
                    <!--[if mso]>
                    <v:roundrect xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w="urn:schemas-microsoft-com:office:word"
                      href="${dashboardUrl}" style="height:48px;v-text-anchor:middle;width:220px;" arcsize="25%"
                      fillcolor="#06b6d4">
                      <w:anchorlock/>
                      <center style="color:#ffffff;font-family:Arial,sans-serif;font-size:15px;font-weight:bold;">
                        Ver mi membresía
                      </center>
                    </v:roundrect>
                    <![endif]-->
                    <!--[if !mso]><!-->
                    <a href="${dashboardUrl}" target="_blank" class="cta-btn">
                      Ver mi membresía
                    </a>
                    <!--<![endif]-->
                  </td>
                </tr>
              </table>

            </td>
          </tr>

          <!-- Footer -->
          <tr>
            <td class="footer-td">
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td class="footer-separator"></td>
                </tr>
                <tr>
                  <td align="center" class="footer-content-td">
                    <p class="footer-text">
                      Este correo fue enviado automáticamente por <strong>FitFlow</strong>.<br />
                      Por favor no respondas a este mensaje.
                    </p>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <!-- Barra inferior degradada -->
          <tr>
            <td class="bottom-bar"></td>
          </tr>

        </table>
      </td>
    </tr>
  </table>

</body>

</html>', now(), true, now()
) ON CONFLICT (code) DO UPDATE SET subject = EXCLUDED.subject, html = EXCLUDED.html, updated_at = NOW();




INSERT INTO email_templates (code, subject, html, created_at, enabled, updated_at) VALUES (
    'password_reset',
    'Restablecer contraseña — FitFlow',
    '<!DOCTYPE html
  PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Restablecer contraseña</title>
  <!--[if mso]><style>body,table,td{font-family:Arial,sans-serif !important;}</style><![endif]-->
  <style>
    body {
      margin: 0;
      padding: 0;
      background-color: #070b18;
      -webkit-text-size-adjust: 100%;
      -ms-text-size-adjust: 100%;
    }

    .preheader {
      display: none;
      font-size: 1px;
      color: #070b18;
      line-height: 1px;
      max-height: 0;
      max-width: 0;
      opacity: 0;
      overflow: hidden;
    }

    .outer-table { background-color: #070b18; }
    .outer-td    { padding: 48px 16px; }

    .main-container {
      max-width: 560px;
      width: 100%;
    }

    /* ── Barras decorativas ── */
    .top-bar {
      height: 3px;
      background: linear-gradient(90deg, #06b6d4 0%, #818cf8 50%, #4f46e5 100%);
      border-radius: 12px 12px 0 0;
    }

    .bottom-bar {
      height: 3px;
      background: linear-gradient(90deg, transparent, #06b6d4, #4f46e5, transparent);
      border-radius: 0 0 12px 12px;
    }

    /* ── Tarjeta principal ── */
    .card-body {
      background-color: #0d1526;
      border-left: 1px solid rgba(99, 179, 237, 0.08);
      border-right: 1px solid rgba(99, 179, 237, 0.08);
      padding: 40px 48px 32px;
    }

    /* ── Logo ── */
    .logo-td      { padding-bottom: 28px; }
    .logo-name-td { padding-top: 12px; }

    .logo-img {
      display: block;
      border: 0;
      outline: none;
      text-decoration: none;
    }

    .logo-name {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 20px;
      font-weight: 700;
      color: #f1f5f9;
      letter-spacing: -0.3px;
    }

    /* ── Separador ── */
    .separator { height: 1px; background: rgba(99, 179, 237, 0.08); }

    /* ── Ícono candado ── */
    .icon-td { padding: 32px 0 20px; }

    .lock-icon-td {
      width: 68px;
      height: 68px;
      background: rgba(6, 182, 212, 0.07);
      border: 1px solid rgba(6, 182, 212, 0.18);
      border-radius: 50%;
      text-align: center;
      vertical-align: middle;
    }

    .lock-icon {
      font-size: 26px;
      line-height: 68px;
      display: block;
    }

    /* ── Título y subtítulo ── */
    .card-title {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 22px;
      font-weight: 700;
      color: #f1f5f9;
      margin: 0 0 10px;
      letter-spacing: -0.4px;
    }

    .card-subtitle {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 15px;
      color: #94a3b8;
      margin: 0;
      line-height: 1.65;
    }

    .card-subtitle strong { color: #e2e8f0; }

    /* ── Caja informativa ── */
    .info-box-table { margin-top: 24px; }

    .info-box-td {
      background: rgba(6, 182, 212, 0.05);
      border: 1px solid rgba(6, 182, 212, 0.14);
      border-radius: 12px;
      padding: 16px 20px;
    }

    .info-text {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 13px;
      color: #94a3b8;
      margin: 0;
      line-height: 1.75;
    }

    .info-text strong { color: #cbd5e1; }

    /* ── Botón CTA ── */
    .cta-table { margin-top: 28px; }

    .cta-btn {
      display: inline-block;
      padding: 14px 36px;
      background: linear-gradient(135deg, #06b6d4 0%, #4f46e5 100%);
      color: #ffffff;
      text-decoration: none;
      font-family: "Poppins", Arial, sans-serif;
      font-size: 15px;
      font-weight: 600;
      border-radius: 12px;
      letter-spacing: 0.2px;
      mso-hide: all;
    }

    /* ── URL de respaldo ── */
    .fallback-table { margin-top: 28px; }

    .fallback-td {
      background: #070f1f;
      border-radius: 10px;
      padding: 14px 18px;
    }

    .fallback-label {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 10px;
      color: #475569;
      margin: 0 0 5px;
      text-transform: uppercase;
      letter-spacing: 0.9px;
      font-weight: 600;
    }

    .fallback-url {
      font-family: monospace;
      font-size: 11px;
      color: #06b6d4;
      margin: 0;
      word-break: break-all;
      line-height: 1.5;
    }

    /* ── Footer ── */
    .footer-td {
      background-color: #090e1e;
      border-left: 1px solid rgba(99, 179, 237, 0.08);
      border-right: 1px solid rgba(99, 179, 237, 0.08);
      padding: 20px 48px 24px;
    }

    .footer-separator {
      height: 1px;
      background: rgba(99, 179, 237, 0.06);
      margin-bottom: 20px;
    }

    .footer-content-td { padding-top: 16px; }

    .footer-text {
      font-family: "Poppins", Arial, sans-serif;
      font-size: 11px;
      color: #334155;
      margin: 0;
      line-height: 1.8;
    }

    .footer-text strong { color: #475569; }
  </style>
</head>

<body>

  <!-- Preheader oculto -->
  <div class="preheader">
    Restablece tu contraseña de FitFlow. Este enlace expira en ${expirationMinutes} minutos.
  </div>

  <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" class="outer-table">
    <tr>
      <td align="center" class="outer-td">

        <!-- Contenedor principal -->
        <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="560" class="main-container">

          <!-- Barra superior degradada -->
          <tr>
            <td class="top-bar"></td>
          </tr>

          <!-- Cuerpo de la tarjeta -->
          <tr>
            <td class="card-body">

              <!-- Logo + nombre -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td align="center" class="logo-td">
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td>
                          <img src="https://res.cloudinary.com/dbt6q1rlx/image/upload/v1786108905/fitflow/Logos/logo_completo_bkkybs.png"
                            alt="FitFlow Logo" width="64" height="64" class="logo-img" />
                        </td>
                      </tr>
                      <tr>
                        <td align="center" class="logo-name-td">
                          <span class="logo-name">FitFlow</span>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>

              <!-- Separador -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td class="separator"></td>
                </tr>
              </table>

              <!-- Ícono de candado + título -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td align="center" class="icon-td">
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td class="lock-icon-td">
                          <span class="lock-icon">&#x1F512;</span>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td align="center">
                    <h1 class="card-title">Restablecer contraseña</h1>
                    <p class="card-subtitle">
                      Hola <strong>${userName}</strong>, recibimos una solicitud para restablecer
                      la contraseña de tu cuenta.
                    </p>
                  </td>
                </tr>
              </table>

              <!-- Caja informativa -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" class="info-box-table">
                <tr>
                  <td class="info-box-td">
                    <p class="info-text">
                      &#x23F1;&nbsp;<strong>Este enlace expira en ${expirationMinutes} minutos.</strong><br />
                      &#x1F6E1;&nbsp;Si no solicitaste este cambio, ignora este correo. Tu contraseña actual permanece
                      sin cambios.
                    </p>
                  </td>
                </tr>
              </table>

              <!-- Botón CTA -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" class="cta-table">
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
                    <a href="${resetUrl}" target="_blank" class="cta-btn">
                      Restablecer mi contraseña
                    </a>
                    <!--<![endif]-->
                  </td>
                </tr>
              </table>

              <!-- URL de respaldo -->
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" class="fallback-table">
                <tr>
                  <td class="fallback-td">
                    <p class="fallback-label">O pega este enlace en tu navegador</p>
                    <p class="fallback-url">${resetUrl}</p>
                  </td>
                </tr>
              </table>

            </td>
          </tr>

          <!-- Footer -->
          <tr>
            <td class="footer-td">
              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td class="footer-separator"></td>
                </tr>
                <tr>
                  <td align="center" class="footer-content-td">
                    <p class="footer-text">
                      Este correo fue enviado automáticamente por <strong>FitFlow</strong>.<br />
                      Por favor no respondas a este mensaje.
                    </p>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <!-- Barra inferior degradada -->
          <tr>
            <td class="bottom-bar"></td>
          </tr>

        </table>
      </td>
    </tr>
  </table>

</body>

</html>', now(), true, now()
) ON CONFLICT (code) DO UPDATE SET subject = EXCLUDED.subject, html = EXCLUDED.html, updated_at = NOW();




INSERT INTO email_templates (code, subject, html, created_at, enabled, updated_at) VALUES (
    'gym_subscription_activated',
    'Tu suscripción ${planName} está activa — FitFlow',
    '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Suscripción activada</title>
  <!--[if mso]><style>body,table,td{font-family:Arial,sans-serif !important;}</style><![endif]-->
</head>
<body style="margin:0;padding:0;background-color:#070b18;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;">

  <div style="display:none;font-size:1px;color:#070b18;line-height:1px;max-height:0;max-width:0;opacity:0;overflow:hidden;">
    Tu suscripción ${planName} ya está activa. Vigente desde ${startDate} hasta ${endDate}.
  </div>

  <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="background-color:#070b18;">
    <tr>
      <td align="center" style="padding:48px 16px;">

        <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="560" style="max-width:560px;width:100%;">

          <tr>
            <td style="height:3px;background:linear-gradient(90deg,#06b6d4 0%,#818cf8 50%,#4f46e5 100%);border-radius:12px 12px 0 0;"></td>
          </tr>

          <tr>
            <td style="background-color:#0d1526;border-left:1px solid rgba(99,179,237,0.08);border-right:1px solid rgba(99,179,237,0.08);padding:40px 48px 32px;">

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

              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr><td style="height:1px;background:rgba(99,179,237,0.08);"></td></tr>
              </table>

              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td align="center" style="padding:32px 0 20px;">
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td style="width:68px;height:68px;background:rgba(6,182,212,0.07);border:1px solid rgba(6,182,212,0.18);border-radius:50%;text-align:center;vertical-align:middle;">
                          <span style="font-size:26px;line-height:68px;display:block;">&#x1F680;</span>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td align="center">
                    <h1 style="font-family:''Poppins'',Arial,sans-serif;font-size:22px;font-weight:700;color:#f1f5f9;margin:0 0 10px;letter-spacing:-0.4px;">¡Tu suscripción está activa!</h1>
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:15px;color:#94a3b8;margin:0;line-height:1.65;">
                      Hola <strong style="color:#e2e8f0;">${gymName}</strong>, tu suscripción al plan <strong style="color:#e2e8f0;">${planName}</strong> ya se encuentra activa.
                    </p>
                  </td>
                </tr>
              </table>

              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="margin-top:24px;">
                <tr>
                  <td style="background:rgba(6,182,212,0.05);border:1px solid rgba(6,182,212,0.14);border-radius:12px;padding:16px 20px;">
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:13px;color:#94a3b8;margin:0;line-height:1.9;">
                      &#x1F4B0;&nbsp;<strong style="color:#cbd5e1;">Precio:</strong> ${price}<br/>
                      &#x1F4C5;&nbsp;<strong style="color:#cbd5e1;">Vigencia:</strong> ${startDate} — ${endDate}
                    </p>
                  </td>
                </tr>
              </table>

              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="margin-top:24px;">
                <tr>
                  <td>
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:12px;color:#475569;margin:0 0 10px;text-transform:uppercase;letter-spacing:0.8px;font-weight:600;">
                      Beneficios de tu plan
                    </p>
                    <#if features?has_content>
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                      <#list features as feature>
                      <tr>
                        <td style="padding:6px 0;font-family:''Poppins'',Arial,sans-serif;font-size:13px;color:#cbd5e1;line-height:1.6;">
                          &#x2705;&nbsp;${feature}
                        </td>
                      </tr>
                      </#list>
                    </table>
                    <#else>
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:13px;color:#94a3b8;margin:0;">Este plan no tiene beneficios adicionales configurados.</p>
                    </#if>
                  </td>
                </tr>
              </table>

            </td>
          </tr>

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

          <tr>
            <td style="height:3px;background:linear-gradient(90deg,transparent,#06b6d4,#4f46e5,transparent);border-radius:0 0 12px 12px;"></td>
          </tr>

        </table>
      </td>
    </tr>
  </table>

</body>
</html>', now(), true, now()
) ON CONFLICT (code) DO UPDATE SET subject = EXCLUDED.subject, html = EXCLUDED.html, updated_at = NOW();;




INSERT INTO email_templates (code, subject, html, created_at, enabled, updated_at) VALUES (
    'membership_activated',
    'Tu membresía ${membershipName} está activa — FitFlow',
    '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Membresía activada</title>
  <!--[if mso]><style>body,table,td{font-family:Arial,sans-serif !important;}</style><![endif]-->
</head>
<body style="margin:0;padding:0;background-color:#070b18;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;">

  <div style="display:none;font-size:1px;color:#070b18;line-height:1px;max-height:0;max-width:0;opacity:0;overflow:hidden;">
    Tu membresía ${membershipName} ya está activa. Vigente desde ${startDate} hasta ${endDate}.
  </div>

  <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="background-color:#070b18;">
    <tr>
      <td align="center" style="padding:48px 16px;">

        <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="560" style="max-width:560px;width:100%;">

          <tr>
            <td style="height:3px;background:linear-gradient(90deg,#06b6d4 0%,#818cf8 50%,#4f46e5 100%);border-radius:12px 12px 0 0;"></td>
          </tr>

          <tr>
            <td style="background-color:#0d1526;border-left:1px solid rgba(99,179,237,0.08);border-right:1px solid rgba(99,179,237,0.08);padding:40px 48px 32px;">

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

              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr><td style="height:1px;background:rgba(99,179,237,0.08);"></td></tr>
              </table>

              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td align="center" style="padding:32px 0 20px;">
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td style="width:68px;height:68px;background:rgba(6,182,212,0.07);border:1px solid rgba(6,182,212,0.18);border-radius:50%;text-align:center;vertical-align:middle;">
                          <span style="font-size:26px;line-height:68px;display:block;">&#x1F389;</span>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td align="center">
                    <h1 style="font-family:''Poppins'',Arial,sans-serif;font-size:22px;font-weight:700;color:#f1f5f9;margin:0 0 10px;letter-spacing:-0.4px;">¡Tu membresía está activa!</h1>
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:15px;color:#94a3b8;margin:0;line-height:1.65;">
                      Hola <strong style="color:#e2e8f0;">${userName}</strong>, tu membresía <strong style="color:#e2e8f0;">${membershipName}</strong> ya se encuentra activa.
                    </p>
                  </td>
                </tr>
              </table>

              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="margin-top:24px;">
                <tr>
                  <td style="background:rgba(6,182,212,0.05);border:1px solid rgba(6,182,212,0.14);border-radius:12px;padding:16px 20px;">
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:13px;color:#94a3b8;margin:0;line-height:1.9;">
                      &#x1F4B0;&nbsp;<strong style="color:#cbd5e1;">Precio:</strong> ${price}<br/>
                      &#x1F4C5;&nbsp;<strong style="color:#cbd5e1;">Vigencia:</strong> ${startDate} — ${endDate}
                    </p>
                  </td>
                </tr>
              </table>

              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="margin-top:24px;">
                <tr>
                  <td>
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:12px;color:#475569;margin:0 0 10px;text-transform:uppercase;letter-spacing:0.8px;font-weight:600;">
                      Beneficios incluidos
                    </p>
                    <#if benefits?has_content>
                    <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                      <#list benefits as benefit>
                      <tr>
                        <td style="padding:6px 0;font-family:''Poppins'',Arial,sans-serif;font-size:13px;color:#cbd5e1;line-height:1.6;">
                          &#x2705;&nbsp;${benefit}
                        </td>
                      </tr>
                      </#list>
                    </table>
                    <#else>
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:13px;color:#94a3b8;margin:0;">Esta membresía no tiene beneficios adicionales configurados.</p>
                    </#if>
                  </td>
                </tr>
              </table>

            </td>
          </tr>

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

          <tr>
            <td style="height:3px;background:linear-gradient(90deg,transparent,#06b6d4,#4f46e5,transparent);border-radius:0 0 12px 12px;"></td>
          </tr>

        </table>
      </td>
    </tr>
  </table>

</body>
</html>', now(), true, now()
)ON CONFLICT (code) DO UPDATE SET subject = EXCLUDED.subject, html = EXCLUDED.html, updated_at = NOW();


INSERT INTO email_templates (code, subject, html, created_at, enabled, updated_at) VALUES (
    'login_notification',
    'Nuevo inicio de sesion',
    '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Nuevo inicio de sesion</title>
</head>
<body style="margin:0;padding:0;background-color:#070b18;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;">

  <div style="display:none;font-size:1px;color:#070b18;line-height:1px;max-height:0;max-width:0;opacity:0;overflow:hidden;">
    Hola ${userName} acabas de iniciar sesion con el gimnasio ${gymName}.
  </div>

  <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="background-color:#070b18;">
    <tr>
      <td align="center" style="padding:48px 16px;">

        <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="560" style="max-width:560px;width:100%;">

          <tr>
            <td style="height:3px;background:linear-gradient(90deg,#06b6d4 0%,#818cf8 50%,#4f46e5 100%);border-radius:12px 12px 0 0;"></td>
          </tr>

          <tr>
            <td style="background-color:#0d1526;border-left:1px solid rgba(99,179,237,0.08);border-right:1px solid rgba(99,179,237,0.08);padding:40px 48px 32px;">

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

              <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                  <td align="center">
                    <h1 style="font-family:''Poppins'',Arial,sans-serif;font-size:22px;font-weight:700;color:#f1f5f9;margin:0 0 10px;letter-spacing:-0.4px;">¡Nuevo inicio de sesion!</h1>
                    <p style="font-family:''Poppins'',Arial,sans-serif;font-size:15px;color:#94a3b8;margin:0;line-height:1.65;">
                      Hola <strong style="color:#e2e8f0;">${userName}</strong>, acabas de iniciar sesion en el gimnasio <strong style="color:#e2e8f0;">${gymName}</strong> el <strong style="color:#cbd5e1;">${date}</strong>
                    </p>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

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

          <tr>
            <td style="height:3px;background:linear-gradient(90deg,transparent,#06b6d4,#4f46e5,transparent);border-radius:0 0 12px 12px;"></td>
          </tr>

        </table>
      </td>
    </tr>
  </table>

</body>
</html>', now(), true, now()
)ON CONFLICT (code) DO UPDATE SET subject = EXCLUDED.subject, html = EXCLUDED.html, updated_at = NOW();