document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("contactForm");
  if (!form) return;

  const formReady = document.getElementById("formReady");

  const fullName = document.getElementById("fullName");
  const email = document.getElementById("email");
  const phone = document.getElementById("phone");
  const subject = document.getElementById("subject");
  const message = document.getElementById("message");
  const messageHint = document.getElementById("messageHint");

  const MIN_NAME = 3;
  const MIN_PHONE = 7;
  const MAX_PHONE = 15;
  const MIN_MSG = 20;
  const MAX_MSG = 400;

  function setError(input, msg) {
    const group = input.closest(".form-group");
    const err = group ? group.querySelector(".error") : null;
    if (err) err.textContent = msg;

    input.classList.add("is-invalid");
    input.classList.remove("is-valid");
  }

  function setOk(input) {
    const group = input.closest(".form-group");
    const err = group ? group.querySelector(".error") : null;
    if (err) err.textContent = "";

    input.classList.remove("is-invalid");
    input.classList.add("is-valid");
  }

  function onlyDigits(s) {
    return /^[0-9]+$/.test(s);
  }

  function emailOk(s) {
    // cumple: debe tener @ y un punto después de la @
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(s);
  }

  function updateMessageHint() {
    const len = message.value.trim().length;

    if (len < MIN_MSG) {
      messageHint.textContent = `Te faltan ${MIN_MSG - len} caracteres (mínimo ${MIN_MSG}).`;
    } else if (len <= MAX_MSG) {
      messageHint.textContent = `Te quedan ${MAX_MSG - len} caracteres (máximo ${MAX_MSG}).`;
    } else {
      messageHint.textContent = `Te pasaste por ${len - MAX_MSG} caracteres (máximo ${MAX_MSG}).`;
    }
  }

  function validateAll(showReadyBanner = true) {
    let ok = true;

    // Nombre
    const n = fullName.value.trim();
    if (n.length < MIN_NAME) {
      setError(fullName, "Nombre obligatorio. Mínimo 3 caracteres y no solo espacios.");
      ok = false;
    } else {
      setOk(fullName);
    }

    // Email
    const e = email.value.trim();
    if (!emailOk(e)) {
      setError(email, "Correo inválido. Debe incluir @ y un punto (.) después de la @.");
      ok = false;
    } else {
      setOk(email);
    }

    // Teléfono
    const p = phone.value.trim();
    if (!p) {
      setError(phone, "Teléfono obligatorio.");
      ok = false;
    } else if (!onlyDigits(p)) {
      setError(phone, "Teléfono inválido. Solo se permiten números.");
      ok = false;
    } else if (p.length < MIN_PHONE || p.length > MAX_PHONE) {
      setError(phone, "Teléfono inválido. Debe tener entre 7 y 15 dígitos.");
      ok = false;
    } else {
      setOk(phone);
    }

    // Asunto (no permitir valor por defecto)
    const s = subject.value.trim();
    if (!s) {
      setError(subject, "Asunto obligatorio. No puedes dejar 'Seleccione una opción'.");
      ok = false;
    } else {
      setOk(subject);
    }

    // Mensaje
    const m = message.value.trim();
    if (!m) {
      setError(message, "Mensaje obligatorio.");
      ok = false;
    } else if (m.length < MIN_MSG) {
      setError(message, `Mensaje muy corto. Faltan ${MIN_MSG - m.length} caracteres.`);
      ok = false;
    } else if (m.length > MAX_MSG) {
      setError(message, `Mensaje muy largo. Te pasaste por ${m.length - MAX_MSG} caracteres.`);
      ok = false;
    } else {
      setOk(message);
    }

    // Feedback visual de “listo para enviar”
    if (formReady) {
      if (ok && showReadyBanner) formReady.style.display = "block";
      else formReady.style.display = "none";
    }

    return ok;
  }

  // Contador del mensaje
  message.addEventListener("input", () => {
    updateMessageHint();
    validateAll(false);
  });

  // Validación en vivo (sin molestar demasiado)
  [fullName, email, phone, subject].forEach((el) => {
    el.addEventListener("input", () => validateAll(false));
    el.addEventListener("change", () => validateAll(false));
    el.addEventListener("blur", () => validateAll(false));
  });

  // Opcional: limpiar caracteres no numéricos mientras escribe
  phone.addEventListener("input", () => {
    phone.value = phone.value.replace(/[^\d]/g, "");
  });

  // Evitar envío si hay errores
  form.addEventListener("submit", (ev) => {
    const ok = validateAll(true);
    if (!ok) ev.preventDefault();
  });

  updateMessageHint();
});
